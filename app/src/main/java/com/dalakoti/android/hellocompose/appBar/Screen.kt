package com.dalakoti.android.hellocompose.appBar

import androidx.compose.ui.graphics.vector.ImageVector

sealed interface Screen {
    val route: String
    val isAppBarVisible: Boolean
    val navigationIcon: ImageVector?
    val navigationIconContentDescription: String?
    val onNavigationIconClick: (() -> Unit)?
    val title: String
    val actions: List<ActionMenuItem>
}

fun getScreen(route: String?): Screen? = Screen::class.nestedClasses.map {
        kClass -> kClass.objectInstance as Screen
}.firstOrNull { screen -> screen.route == route }
