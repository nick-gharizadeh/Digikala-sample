package com.example.digikalasample.ui


object RemoveHTMLTags {
    @JvmStatic
    fun removeHTMLTagsFromString(string: String):String {
        return string.replace("<.*?>".toRegex(), "")
    }
}