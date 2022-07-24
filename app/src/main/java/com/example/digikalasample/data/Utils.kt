package com.example.digikalasample.data

import com.example.digikalasample.data.model.address.Address
import com.example.digikalasample.data.model.product.Category
import com.example.digikalasample.data.model.product.Product
import com.example.digikalasample.data.model.review.Review


enum class OrderByEnum(val orderTypeString: String) {
    POPULARITY("popularity"),
    RATING("rating"),
    DATE("date"),
    PRICE("price")
}

enum class OrderSortEnum(val orderSortString: String) {
    ASC("asc"),
    DESC("desc")
}


enum class FilterTypeEnum(val filterTypeString: String) {
    Size("pa_size"),
    Color("pa_color")
}


object RemoveHTMLTags {
    @JvmStatic
    fun removeHTMLTagsFromString(string: String): String {
        return string.replace("<.*?>".toRegex(), "")
    }
}
