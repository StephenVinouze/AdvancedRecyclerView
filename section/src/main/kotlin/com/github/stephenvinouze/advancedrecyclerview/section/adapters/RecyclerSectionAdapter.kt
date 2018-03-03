package com.github.stephenvinouze.advancedrecyclerview.section.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.github.stephenvinouze.advancedrecyclerview.core.adapters.RecyclerAdapter
import com.github.stephenvinouze.advancedrecyclerview.core.views.BaseViewHolder
import java.util.*

/**
 * Created by Stephen Vinouze on 09/11/2015.
 */
abstract class RecyclerSectionAdapter<SECTION, MODEL>(context: Context, var section: (MODEL) -> SECTION) : RecyclerAdapter<MODEL>(context) {

    companion object {
        private const val SECTION_VIEW_TYPE = 222
    }

    val sectionCount: Int
        get() = sectionItems.size

    val sections: List<SECTION>
        get() = sectionItems.keys.toList()

    private var sectionItems: LinkedHashMap<SECTION, MutableList<MODEL>> = linkedMapOf()

    override var items: MutableList<MODEL> = mutableListOf()
        set(value) {
            buildSections(value, section)
            field = sectionItems.values.flatten().toMutableList()
            notifyDataSetChanged()
        }

    final override fun addItemsInternal(items: MutableList<MODEL>, position: Int) {
        super.addItemsInternal(items, relativePosition(position))
        buildSections(items, section)
        notifyDataSetChanged()
    }

    final override fun addItemInternal(item: MODEL, position: Int) {
        super.addItemInternal(item, relativePosition(position))
        buildSections(items, section)
        notifyDataSetChanged()
    }

    final override fun removeItemInternal(position: Int) {
        super.removeItemInternal(relativePosition(position))
        buildSections(items, section)
        notifyDataSetChanged()
    }

    final override fun moveItemInternal(from: Int, to: Int) {
        super.moveItemInternal(relativePosition(from), relativePosition(to))
    }

    final override fun handleClick(viewHolder: BaseViewHolder, clickPosition: (BaseViewHolder) -> Int) {
        super.handleClick(viewHolder, { relativePosition(it.layoutPosition) })
    }

    final override fun toggleItemView(position: Int) {
        getSelectedItemViews().forEach {
            notifyItemChanged(absolutePosition(it))
        }
        super.toggleItemView(position)
        notifyItemChanged(absolutePosition(position))
    }

    override fun getItemViewType(position: Int): Int =
            if (isSectionAt(position)) SECTION_VIEW_TYPE else super.getItemViewType(relativePosition(position))

    override fun getItemId(position: Int): Long =
            if (isSectionAt(position)) Long.MAX_VALUE - sectionPosition(position) else super.getItemId(relativePosition(position))

    override fun getItemCount(): Int = super.getItemCount() + sectionCount

    final override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder =
            when (viewType) {
                SECTION_VIEW_TYPE -> BaseViewHolder(onCreateSectionItemView(parent, viewType))
                else -> super.onCreateViewHolder(parent, viewType)
            }

    final override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        if (isSectionAt(position)) {
            onBindSectionItemView(holder.view, sectionPosition(position))
        } else {
            super.onBindViewHolder(holder, relativePosition(position))
        }
    }

    fun itemCountInSection(section: Int): Int = sectionItems[sectionAt(section)]?.size ?: 0

    fun sectionAt(position: Int): SECTION? = sections[position]

    fun buildSections(items: List<MODEL>, section: (MODEL) -> SECTION) {
        sectionItems = linkedMapOf()

        items.forEach {
            val itemSection = section(it)
            val itemsInSection = sectionItems[itemSection] ?: arrayListOf()

            itemsInSection.add(it)

            sectionItems[itemSection] = itemsInSection
        }
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

    protected abstract fun onCreateSectionItemView(parent: ViewGroup, viewType: Int): View
    protected abstract fun onBindSectionItemView(sectionView: View, sectionPosition: Int)

}