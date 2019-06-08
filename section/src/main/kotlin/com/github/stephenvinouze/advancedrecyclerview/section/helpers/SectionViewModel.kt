package com.github.stephenvinouze.advancedrecyclerview.section.helpers

import androidx.recyclerview.widget.RecyclerView

class SectionViewModel<SECTION : Comparable<SECTION>, MODEL> {

    val sectionCount: Int
        get() = sectionItems.size

    val sections: List<SECTION>
        get() = sectionItems.keys.toList()

    val items: List<MODEL>
        get() = sectionItems.values.flatten()

    private var sectionItems: Map<SECTION, List<MODEL>> = mapOf()

    fun itemCountInSection(section: Int): Int = sectionItems[sectionAt(section)]?.size ?: 0

    fun sectionAt(position: Int): SECTION? = sections[position]

    fun buildSections(items: List<MODEL>, section: (MODEL) -> SECTION) {
        sectionItems = items.groupBy(section).toSortedMap()
    }

    /**
     * Check that the given position in the list matches with a section position
     * @param position: The absolute position in the list
     * @return True if this is a section
     */
    fun isSectionAt(position: Int): Boolean {
        var absoluteSectionPosition = 0
        (0 until sectionCount).forEach {
            if (position == absoluteSectionPosition) {
                return true
            } else if (position < absoluteSectionPosition) {
                return false
            }

            absoluteSectionPosition += itemCountInSection(it) + 1
        }
        return false
    }

    /**
     * Compute the relative section position in the list depending on the number of items in each section
     * @param position: The absolute position in the list
     * @return The relative section position of the given position
     */
    fun sectionPosition(position: Int): Int {
        var sectionPosition = 0
        var absoluteSectionPosition = 0
        (0 until sectionCount).forEach {
            absoluteSectionPosition += itemCountInSection(it)
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
     * @return The relative position without sections or NO_POSITION if matches section position
     */
    fun relativePosition(position: Int): Int {
        if (isSectionAt(position)) {
            return RecyclerView.NO_POSITION
        }

        var relativePosition = position
        (0..position).forEach {
            if (isSectionAt(it)) {
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
    fun absolutePosition(position: Int): Int {
        var offset = 0
        (0..position).forEach {
            if (isSectionAt(it + offset)) {
                offset++
            }
        }
        return position + offset
    }
}