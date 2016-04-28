package com.github.stephenvinouze.advancedrecyclerview.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.github.stephenvinouze.advancedrecyclerview.extensions.*
import com.github.stephenvinouze.advancedrecyclerview.views.BaseViewHolder
import java.util.*

/**
 * Created by Stephen Vinouze on 09/11/2015.
 */
abstract class RecyclerSectionAdapter<K, T>(context: Context, section: (T) -> K): RecyclerAdapter<T>(context) {

    var section: (T) -> K
    var sectionItems = LinkedHashMap<K, List<T>>()

    private val SECTION_TYPE = 0

    init {
        this.section = section
    }

    abstract fun onCreateSectionItemView(parent: ViewGroup, viewType: Int): View
    abstract fun onBindSectionItemView(v: View, section: Int)

    override var items: MutableList<T> = arrayListOf()
        get() = field
        set(value) {
            buildSections(value, section)

            var reorderedItems: MutableList<T> = arrayListOf()
            for (items in sectionItems.values) {
                reorderedItems.addAll(items)
            }

            field = reorderedItems
        }

    override fun handleClick(viewHolder: BaseViewHolder, clickPosition: (BaseViewHolder) -> Int) {
        super.handleClick(viewHolder, { relativePosition(it.layoutPosition) })
    }

    override fun addItems(items: List<T>, position: Int) {
        this.items.addAll(relativePosition(position), items)
        buildSections(items, section)
    }

    override fun addItem(item: T, position: Int) {
        super.addItem(item, relativePosition(position))
        buildSections(items, section)
    }

    override fun moveItem(from: Int, to: Int) {
        super.moveItem(relativePosition(from), relativePosition(to))
        buildSections(items, section)
    }

    override fun removeItem(position: Int) {
        super.removeItem(relativePosition(position))
        buildSections(items, section)
    }

    override fun toggleItemView(position: Int) {
        super.toggleItemView(position)
        notifyItemChanged(absolutePosition(position))
    }

    override fun getItemViewType(position: Int): Int {
        return if (isSectionAt(position)) SECTION_TYPE else super.getItemViewType(relativePosition(position)) + 1
    }

    override fun getItemId(position: Int): Long {
        return if (isSectionAt(position)) Long.MAX_VALUE - sectionPosition(position) else super.getItemId(relativePosition(position))
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + numberOfSections()
    }

    final override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        if (viewType == SECTION_TYPE) {
            return BaseViewHolder(onCreateSectionItemView(parent, viewType))
        } else {
            return super.onCreateViewHolder(parent, viewType - 1)
        }
    }

    final override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        if (isSectionAt(position)) {
            onBindSectionItemView(holder.view, sectionPosition(position))
        } else {
            super.onBindViewHolder(holder, relativePosition(position))
        }
    }

    private fun buildSections(items : List<T>, section: (T) -> K) {
        sectionItems.clear()

        for (item in items) {
            val itemSection = section(item)
            val itemsInSection = sectionItems[itemSection]?.toMutableList() ?: arrayListOf()

            itemsInSection.add(item)

            sectionItems.put(itemSection, itemsInSection.toList())
        }

        notifyDataSetChanged()
    }

}