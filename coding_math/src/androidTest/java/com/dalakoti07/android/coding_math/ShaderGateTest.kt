package com.dalakoti07.android.coding_math

import android.graphics.RuntimeShader
import android.os.Build
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.dalakoti07.android.coding_math.examples.shaders.W0_SHADERS
import org.junit.Assert.fail
import org.junit.Assume.assumeTrue
import org.junit.Test
import org.junit.runner.RunWith

/**
 * The `W0` acceptance test — "Done when", made mechanical.
 *
 * Why this exists rather than just looking at the screen: **AGSL is never checked at
 * build time.** The shader is a Kotlin string, so `kotlinc` is happy with any typo in
 * it, and so is every JVM unit test. The source is only parsed when `RuntimeShader` is
 * constructed, on-device, at runtime — so an AGSL mistake shows up as a crash in the
 * exact moment you were trying to prove the venue works.
 *
 * Run it with:
 *
 *     ./gradlew :coding_math:connectedDebugAndroidTest
 *
 * Needs a connected device or emulator on **API 33+**; on anything older the tests
 * skip rather than fail, because `RuntimeShader` simply does not exist there.
 */
@RunWith(AndroidJUnit4::class)
class ShaderGateTest {

    private fun requireTiramisu() = assumeTrue(
        "RuntimeShader needs API 33; this device is ${Build.VERSION.SDK_INT}",
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU,
    )

    /** Every AGSL program in the gate parses and links. This is the real gate. */
    @Test
    fun everyW0ShaderCompiles() {
        requireTiramisu()
        W0_SHADERS.forEach { (name, source, _) ->
            try {
                RuntimeShader(source)
            } catch (e: IllegalArgumentException) {
                // AGSL compile errors arrive here with the line number in the message.
                fail("AGSL for \"$name\" did not compile:\n${e.message}")
            }
        }
    }

    /**
     * Each declared uniform can actually be set.
     *
     * Catches the quiet failure mode: renaming a uniform in the AGSL and forgetting the
     * Kotlin side, which does not crash — it just leaves the shader reading zero and
     * hands you a black screen with no error to search for.
     */
    @Test
    fun everyDeclaredUniformIsSettable() {
        requireTiramisu()
        W0_SHADERS.forEach { (name, source, uniforms) ->
            val shader = RuntimeShader(source)
            uniforms.forEach { uniform ->
                try {
                    shader.setFloatUniform(uniform, 1080f, 1920f)
                } catch (e: IllegalArgumentException) {
                    fail("\"$name\" declares no float2 uniform named \"$uniform\": ${e.message}")
                }
            }
        }
    }
}
