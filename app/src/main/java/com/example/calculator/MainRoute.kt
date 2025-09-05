package com.example.calculator

object MainRouteConstants {

    /** route **/
    const val MAIN = "main"
    const val DETAIL = "detail"
}


sealed class MainRoute(val path: String) {

    object Home : MainRoute(MainRouteConstants.MAIN)
    object Detail : MainRoute(MainRouteConstants.DETAIL)
}