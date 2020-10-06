package com.wsobocinski.githubapp.model

data class SingleCommit (
    val message: String?,
    val shaValue: String?,
    val author: String?,
    var selected: Boolean = false
) {
    fun itemClicked() {
        selected = !selected
    }
}