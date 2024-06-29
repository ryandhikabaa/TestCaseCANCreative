package com.rba29.testcasecancreative

import com.rba29.testcasecancreative.Data.Response.ListResultItem

object DataDummy {
    fun generateDummyQuoteResponse(): List<ListResultItem> {
        val items: MutableList<ListResultItem> = arrayListOf()
        for (i in 0..100) {
            val stories = ListResultItem(
                "id: $i",
                "name: $i",
                "realesed: $i",
                "slug: $i",
                "rating: $i",
                "background_image: $i"
            )
            items.add(stories)
        }
        return items
    }
}