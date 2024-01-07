package com.dalakoti.android.hellocompose.appBar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

val HomeRoute = "home"

// 1
object Home : Screen {
    // 2
    override val route: String = HomeRoute
    override val isAppBarVisible: Boolean = true
    override val navigationIcon: ImageVector? = null
    override val onNavigationIconClick: (() -> Unit)? = null
    override val navigationIconContentDescription: String? = null
    override val title: String = "Home"
    override val actions: List<ActionMenuItem> = listOf(
        ActionMenuItem.IconMenuItem.AlwaysShown(
            title = "Settings",
            onClick = {
                // 3
                _buttons.tryEmit(AppBarIcons.Settings)
            },
            icon = Icons.Filled.Settings,
            contentDescription = null,
        )
    )

    // 1
    enum class AppBarIcons {
        Settings
    }
    // 2
    private val _buttons = MutableSharedFlow<AppBarIcons>(extraBufferCapacity = 1)
    val buttons: Flow<AppBarIcons> = _buttons.asSharedFlow()

}
