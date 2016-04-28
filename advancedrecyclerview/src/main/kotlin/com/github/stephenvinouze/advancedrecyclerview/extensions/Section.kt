package com.github.stephenvinouze.advancedrecyclerview.extensions

import com.github.stephenvinouze.advancedrecyclerview.adapters.RecyclerSectionAdapter

/**
 * Created by stephenvinouze on 26/04/16.
 */

val RecyclerSectionAdapter<*, *>.hasSections: Boolean
    get() = !sectionItems.isEmpty()

fun RecyclerSectionAdapter<*, *>.numberOfSections(): Int {
    return sectionItems.size
}

fun RecyclerSectionAdapter<*, *>.numberOfItemsInSection(section: Int): Int {
    return sectionItems[sectionAt(section)]?.size ?: 0
}

fun RecyclerSectionAdapter<*, *>.sectionAt(position: Int): Any? {
    return allSections()[position]
}

fun RecyclerSectionAdapter<*, *>.allSections(): List<*> {
    return sectionItems.keys.toMutableList()
}

/**
 * Check that the given position in the list matches with a section position
 * @param position: The absolute position in the list
 * @return True if this is a section
 */
fun RecyclerSectionAdapter<*, *>.isSectionAt(position: Int): Boolean {
    var absoluteSectionPosition = 0
    for (section in 0..numberOfSections() - 1) {
        if (position == absoluteSectionPosition) {
            return true
        } else if (position < absoluteSectionPosition) {
            return false
        }

        absoluteSectionPosition += numberOfItemsInSection(section) + 1
    }
    return false
}

/**
 * Compute the relative section position in the list depending on the number of items in each section
 * @param position: The absolute position in the list
 * @return The relative section position of the given position
 */
fun RecyclerSectionAdapter<*, *>.sectionPosition(position: Int): Int {
    var sectionPosition = 0
    var absoluteSectionPosition = 0
    for (section in 0..numberOfSections() - 1) {
        absoluteSectionPosition += numberOfItemsInSection(section)
        if (position <= absoluteSectionPosition) {
            return sectionPosition
        }

        sectionPosition++
        absoluteSectionPosition++
    }
    return sectionPosition
}

/**
 * Compute the relative position in the list that omits the sections
 * @param position: The absolute position in the list
 * @return The relative position without sections
 */
fun RecyclerSectionAdapter<*, *>.relativePosition(position: Int): Int {
    var relativePosition = position
    for (absolutePosition in 0..position) {
        if (isSectionAt(absolutePosition)) {
            relativePosition--
        }
    }
    return relativePosition
}

/**
 * Compute the absolute position in the list that includes the sections
 * @param position: The relative position in the list
 * @return The absolute position with sections
 */
fun RecyclerSectionAdapter<*, *>.absolutePosition(position: Int): Int {
    var absolutePosition = position
    for (relative in 0..position) {
        if (isSectionAt(relative)) {
            absolutePosition++
        }
    }
    return absolutePosition
}
