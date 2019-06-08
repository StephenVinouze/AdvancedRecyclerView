package com.github.stephenvinouze.advancedrecyclerview.section.adapters

import android.view.View
import android.view.ViewGroup
import com.github.stephenvinouze.advancedrecyclerview.core.adapters.RecyclerAdapter
import com.github.stephenvinouze.advancedrecyclerview.core.views.BaseViewHolder
import com.github.stephenvinouze.advancedrecyclerview.section.helpers.SectionViewModel

/**
 * Created by Stephen Vinouze on 09/11/2015.
 */
abstract class RecyclerSectionAdapter<SECTION : Comparable<SECTION>, MODEL>(
    var section: (MODEL) -> SECTION
) : RecyclerAdapter<MODEL>() {

    companion object {
        private const val SECTION_VIEW_TYPE = 222
    }

    val viewModel = SectionViewModel<SECTION, MODEL>()

    override var items: MutableList<MODEL> = mutableListOf()
        set(value) {
            viewModel.buildSections(value, section)
            field = viewModel.items.toMutableList()
            notifyDataSetChanged()
        }

    final override fun addItemsInternal(items: MutableList<MODEL>, position: Int) {
        super.addItemsInternal(items, viewModel.relativePosition(position))
        viewModel.buildSections(items, section)
        notifyDataSetChanged()
    }

    final override fun addItemInternal(item: MODEL, position: Int) {
        super.addItemInternal(item, viewModel.relativePosition(position))
        viewModel.buildSections(items, section)
        notifyDataSetChanged()
    }

    final override fun removeItemInternal(position: Int) {
        super.removeItemInternal(viewModel.relativePosition(position))
        viewModel.buildSections(items, section)
        notifyDataSetChanged()
    }

    final override fun moveItemInternal(from: Int, to: Int) {
        super.moveItemInternal(viewModel.relativePosition(from), viewModel.relativePosition(to))
    }

    final override fun handleClick(viewHolder: BaseViewHolder, clickPosition: (BaseViewHolder) -> Int) {
        super.handleClick(viewHolder) { viewModel.relativePosition(it.layoutPosition) }
    }

    final override fun toggleItemView(position: Int) {
        getSelectedItemViews().forEach {
            notifyItemChanged(viewModel.absolutePosition(it))
        }
        super.toggleItemView(position)
        notifyItemChanged(viewModel.absolutePosition(position))
    }

    override fun getItemViewType(position: Int): Int =
        if (viewModel.isSectionAt(position))
            SECTION_VIEW_TYPE
        else
            super.getItemViewType(viewModel.relativePosition(position))

    override fun getItemId(position: Int): Long =
        if (viewModel.isSectionAt(position))
            Long.MAX_VALUE - viewModel.sectionPosition(position)
        else
            super.getItemId(viewModel.relativePosition(position))

    override fun getItemCount(): Int =
        super.getItemCount() + viewModel.sectionCount

    final override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder =
        when (viewType) {
            SECTION_VIEW_TYPE -> BaseViewHolder(onCreateSectionItemView(parent, viewType))
            else -> super.onCreateViewHolder(parent, viewType)
        }

    final override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        if (viewModel.isSectionAt(position)) {
            onBindSectionItemView(holder.view, viewModel.sectionPosition(position))
        } else {
            super.onBindViewHolder(holder, viewModel.relativePosition(position))
        }
    }

    protected abstract fun onCreateSectionItemView(parent: ViewGroup, viewType: Int): View
    protected abstract fun onBindSectionItemView(sectionView: View, sectionPosition: Int)
}