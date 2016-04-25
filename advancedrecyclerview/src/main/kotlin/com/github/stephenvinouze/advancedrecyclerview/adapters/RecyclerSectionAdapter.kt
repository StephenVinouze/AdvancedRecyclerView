package com.github.stephenvinouze.advancedrecyclerview.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.github.stephenvinouze.advancedrecyclerview.views.BaseViewHolder

/**
 * Created by Stephen Vinouze on 09/11/2015.
 */
abstract class RecyclerSectionAdapter<T>(context: Context): RecyclerAdapter<T>(context) {

    private val SECTION_TYPE = 0

    abstract fun numberOfSections(): Int
    abstract fun numberOfItemsInSection(section: Int): Int
    abstract fun onCreateSectionItemView(parent: ViewGroup, viewType: Int): View
    abstract fun onBindSectionItemView(v: View, section: Int)

    override fun getItemViewType(position: Int): Int {
        return if (isSectionAt(position)) SECTION_TYPE else super.getItemViewType(relativePosition(position)) + 1
    }

    override fun getItemId(position: Int): Long {
        return if (isSectionAt(position)) Long.MAX_VALUE - sectionPosition(position) else super.getItemId(relativePosition(position))
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + numberOfSections()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        if (viewType == SECTION_TYPE) {
            return BaseViewHolder(onCreateSectionItemView(parent, viewType))
        } else {
            return super.onCreateViewHolder(parent, viewType - 1)
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        if (isSectionAt(position)) {
            onBindSectionItemView(holder.view, sectionPosition(position))
        } else {
            super.onBindViewHolder(holder, relativePosition(position))
        }
    }

    /**
     * Check that the given position in the list matches with a section position
     * @param position: The absolute position in the list
     * @return True if this is a section
     */
    private fun isSectionAt(position: Int): Boolean {
        var absoluteSectionPosition = 0
        for (section in 0..numberOfSections() - 1) {
            if (position == absoluteSectionPosition) {
                return true
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
    private fun sectionPosition(position: Int): Int {
        var sectionPosition = 0
        var absoluteSectionPosition = 0
        for (section in 0..numberOfSections() - 1) {
            if (position <= absoluteSectionPosition + numberOfItemsInSection(section)) {
                return sectionPosition
            }
            sectionPosition++
            absoluteSectionPosition += numberOfItemsInSection(section) + 1
        }
        return sectionPosition
    }

    /**
     * Compute the relative position in the list that omits the sections
     * @param position: The absolute position in the list
     * @return The relative position without sections
     */
    private fun relativePosition(position: Int): Int {
        var relativePosition = position
        for (absolutePosition in 0..position) {
            if (isSectionAt(absolutePosition)) {
                relativePosition--
            }
        }
        return relativePosition
    }

}