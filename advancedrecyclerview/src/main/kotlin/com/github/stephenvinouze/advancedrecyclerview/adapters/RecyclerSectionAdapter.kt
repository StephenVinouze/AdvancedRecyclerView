package com.github.stephenvinouze.advancedrecyclerview.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.github.stephenvinouze.advancedrecyclerview.extensions.isSectionAt
import com.github.stephenvinouze.advancedrecyclerview.extensions.numberOfSections
import com.github.stephenvinouze.advancedrecyclerview.extensions.relativePosition
import com.github.stephenvinouze.advancedrecyclerview.extensions.sectionPosition
import com.github.stephenvinouze.advancedrecyclerview.views.BaseViewHolder
import java.util.*

/**
 * Created by Stephen Vinouze on 09/11/2015.
 */
abstract class RecyclerSectionAdapter<K, T>(context: Context): RecyclerAdapter<T>(context) {

    var sectionItems = LinkedHashMap<K, List<T>>()

    private val SECTION_TYPE = 0

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

    fun buildSections(items : List<T>, section: (T) -> K): LinkedHashMap<K, List<T>> {
        this.items = items.toMutableList()

        sectionItems.clear()

        if (!items.isEmpty()) {

            var itemsPerSection: MutableList<T> = arrayListOf()
            var currentSection : K = section(items[0])

            for (item in items) {
                val itemSection = section(item)

                if (currentSection != itemSection || items.indexOf(item) == items.size - 1) {
                    if (!itemsPerSection.isEmpty()) {
                        sectionItems.put(currentSection, ArrayList<T>(itemsPerSection))
                    }
                    itemsPerSection.clear()
                }
                else {
                    itemsPerSection.add(item)
                }

                currentSection = itemSection
            }
        }

        return sectionItems
    }

}