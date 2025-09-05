package com.example.calculator

object MainRouteConstants {

    /** route **/
    const val MAIN = "main"
    const val DETAIL = "detail"

    /** route pattern **/
    const val USER_DETAIL_PATTERN = "user_detail/{userId}"

    /** route parameter **/
    const val USER_ID = "userId"
}


sealed class MainRoute(val path : String) {

    data object Home : MainRoute(MainRouteConstants.MAIN)
    data class Detail(val sentence : String) : MainRoute("${MainRouteConstants.DETAIL}/${sentence}")
}