package com.jacoblip.andriod.starwarstrivia.response

data class Todo(
    val completed: Boolean,
    val id: Int,
    val title: String,
    val userId: Int
)