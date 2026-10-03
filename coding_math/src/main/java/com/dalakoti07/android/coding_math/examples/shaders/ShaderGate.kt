package com.dalakoti07.android.coding_math.examples.shaders

import android.graphics.RuntimeShader
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

/**
 * Week 0 of the shader plan — THE GATE.
 *
 * Goal, verbatim from `plan.md` §11: "Get a solid red screen rendering through
 * `RuntimeShader` in a Compose app on your phone. Then a gradient. Then stop."
 *
 * Until this runs on a real device the whole curriculum is theoretical. That is the
 * entire point of the rung — it proves the venue, not the maths.
 *
 * Four things worth knowing before reading the AGSL below:
 *
 *  1. **`RuntimeShader` is API 33+ (Android 13 / TIRAMISU).** This module is `minSdk 24`,
 *     so every shader entry point here is version-guarded rather than bumping minSdk and
 *     breaking the other dozen `coding_math` examples.
 *  2. **AGSL is not GLSL.** Types are `float2` / `float3` / `half4`, not `vec2` / `vec4`.
 *     There is no `gl_FragCoord`; the coordinate arrives as `main`'s parameter.
 *  3. **AGSL is y-DOWN, origin top-left.** GLSL, the Book of Shaders editor and Shadertoy
 *     are y-UP, origin bottom-left. `plan.md` 2026-09-20 standing decision: never flip
 *     inside a shader body — convert once, at the AGSL boundary. The single flip lives in
 *     the DOMAIN stage below and is labelled as such.
 *  4. **`main` returns PREMULTIPLIED alpha.** Irrelevant while alpha is 1.0, lethal later.
 *  5. **Entry point is exactly `half4 main(float2 fragCoord)`** — the only one supported.
 *     And unlike GLSL, constructors that change a vector's component count are not
 *     supported; swizzle instead (`half4(v.xyz, 1.0)`, never `half4(someFloat4)`).
 *
 * Why `ShaderBrush` and not `RenderEffect.createRuntimeShaderEffect`: these two shaders
 * invent their pixels from nothing, so there is no input to sample. `RenderEffect` is for
 * bending pixels that already exist — that is The 50 #50, and it needs `uniform shader`.
 */

/* ──────────────────────────────────────────────────────────────────────────────
 * Gate, part 1 — the smallest shader that can possibly work.
 * No uniforms, no coordinates, no maths. If this is not red, nothing else matters.
 * ────────────────────────────────────────────────────────────────────────────── */
internal const val RED_SCREEN_AGSL = """
    half4 main(float2 fragCoord) {
        return half4(1.0, 0.0, 0.0, 1.0);
    }
"""

/* ──────────────────────────────────────────────────────────────────────────────
 * Gate, part 2 — a gradient, written in the four-stage house style.
 *
 * The stages are overkill for a gradient and that is deliberate: the banners go in
 * from rung one so that by S12 they are reflex rather than discipline.
 * ────────────────────────────────────────────────────────────────────────────── */
internal const val GRADIENT_AGSL = """
    uniform float2 resolution;

    half4 main(float2 fragCoord) {
        // ── DOMAIN ──────────────────────────────────────────────────────────
        // AGSL hands us PIXELS, y-down, origin top-left.
        // >>> THIS LINE IS THE AGSL BOUNDARY. The flip happens here, once, and
        //     never again anywhere downstream. See plan.md §7, 2026-09-20.
        float2 uv = fragCoord / resolution;
        uv.y = 1.0 - uv.y;                 // y-up from here on, BoS convention

        // ── FIELD ───────────────────────────────────────────────────────────
        // One scalar per pixel. Distance along the diagonal, 0 at bottom-left.
        float f = (uv.x + uv.y) * 0.5;

        // ── MASK ────────────────────────────────────────────────────────────
        // Nothing to gate yet, so this is the identity. The stage stays visible
        // because S23 onward is nothing but "one field, many masks".
        float m = f;

        // ── COLOUR ──────────────────────────────────────────────────────────
        // AGSL gotcha: constructors that CHANGE a vector's component count are
        // not supported the way GLSL allows -- half4(someFloat4) is out, and
        // half4(v.xyz, 1.0) is the documented pattern. So stay in half here and
        // build the alpha on with a swizzle, never a cross-type cast.
        half3 cool = half3(0.08, 0.42, 0.75);
        half3 warm = half3(0.98, 0.78, 0.22);
        half3 c    = mix(cool, warm, half(m));
        return half4(c.rgb, 1.0);
    }
"""

/**
 * Every AGSL program in the W0 gate, with the uniform names each one expects.
 *
 * `ShaderGateTest` walks this and asserts each source actually compiles on-device.
 * AGSL is only validated at `RuntimeShader` construction, which means a typo here is
 * invisible to `kotlinc` and to every unit test — it surfaces as a runtime crash on
 * the one device you were trying to prove the venue on. Hence the list.
 */
internal val W0_SHADERS: List<Triple<String, String, List<String>>> = listOf(
    Triple("red screen", RED_SCREEN_AGSL, emptyList()),
    Triple("gradient", GRADIENT_AGSL, listOf("resolution")),
)

/**
 * Draws one AGSL shader across the whole composable.
 *
 * [setUniforms] runs on every draw with the current pixel size, which is the only
 * sane time to push `resolution` — it is not known at construction.
 */
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
private fun AgslSurface(
    agsl: String,
    modifier: Modifier = Modifier,
    setUniforms: RuntimeShader.(size: Size) -> Unit = {},
) {
    val shader = remember(agsl) { RuntimeShader(agsl) }
    Canvas(modifier = modifier) {
        shader.setUniforms(size)
        drawRect(brush = ShaderBrush(shader))
    }
}

/** Shown instead of a shader on anything below Android 13. */
@Composable
private fun NeedsTiramisu(modifier: Modifier = Modifier) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Text(
            text = "RuntimeShader needs Android 13 (API 33).\n" +
                "This device is API ${Build.VERSION.SDK_INT}.",
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(32.dp),
        )
    }
}

/** `W0`, part 1. A solid red screen through `RuntimeShader`. The gate. */
@Composable
fun RedScreenShader(modifier: Modifier = Modifier.fillMaxSize()) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        AgslSurface(agsl = RED_SCREEN_AGSL, modifier = modifier)
    } else {
        NeedsTiramisu(modifier)
    }
}

/** `W0`, part 2. A gradient, four stages, one documented y-flip. Then stop. */
@Composable
fun GradientShader(modifier: Modifier = Modifier.fillMaxSize()) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        AgslSurface(agsl = GRADIENT_AGSL, modifier = modifier) { size ->
            setFloatUniform("resolution", size.width, size.height)
        }
    } else {
        NeedsTiramisu(modifier)
    }
}
