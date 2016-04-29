package com.github.stephenvinouze.advancedrecyclerview.extensions

import java.util.*

/**
 * Created by stephenvinouze on 28/04/16.
 */

fun <K, T> LinkedHashMap<K, MutableList<T>>.buildSections(items: List<T>, section: (T) -> K) {
    clear()

    for (item in items) {
        val itemSection = section(item)
        val itemsInSection = this[itemSection] ?: arrayListOf()

        itemsInSection.add(item)

        put(itemSection, itemsInSection)
    }
}
