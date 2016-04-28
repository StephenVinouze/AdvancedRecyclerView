package com.github.stephenvinouze.advancedrecyclerview.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.SparseBooleanArray
import android.view.View
import android.view.ViewGroup
import com.github.stephenvinouze.advancedrecyclerview.callbacks.ClickCallback
import com.github.stephenvinouze.advancedrecyclerview.extensions.swap
import com.github.stephenvinouze.advancedrecyclerview.views.BaseViewHolder

/**
 * Created by Stephen Vinouze on 09/11/2015.
 */
abstract class RecyclerAdapter<T>(protected var context: Context): RecyclerView.Adapter<BaseViewHolder>() {
    private val selectedItemViews = SparseBooleanArray()

    var choiceMode = ChoiceMode.SINGLE_CHOICE
    var clickCallback: ClickCallback? = null

    val selectedItemViewCount: Int
        get() = selectedItemViews.size()

    open var items : MutableList<T> = arrayListOf()
        get() = field
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    enum class ChoiceMode {
        SINGLE_CHOICE, MULTIPLE_CHOICE
    }

    open fun handleClick(viewHolder: BaseViewHolder, clickPosition: (BaseViewHolder) -> Int) {
        val itemView = viewHolder.view
        itemView.setOnClickListener {
            toggleItemView(clickPosition(viewHolder))

            clickCallback?.onItemClick(clickPosition(viewHolder))
        }
        itemView.setOnLongClickListener { clickCallback?.onItemLongClick(clickPosition(viewHolder)) ?: false }
    }

    open fun addItems(items: List<T>, position: Int) {
        this.items.addAll(position, items)
        notifyItemRangeInserted(position, items.size)
    }

    open fun addItem(item: T, position: Int) {
        this.items.add(position, item)
        notifyItemInserted(position)
    }

    open fun moveItem(from: Int, to: Int) {
        moveSelectedItemView(from, to)

        this.items.swap(from, to)
        notifyItemMoved(from, to)
    }

    open fun removeItem(position: Int) {
        removeSelectedItemView(position)

        this.items.removeAt(position)
        notifyItemRemoved(position)
    }

    fun clearItems() {
        this.items.clear()
        clearSelectedItemViews()
    }

    fun isItemViewToggled(position: Int): Boolean {
        return selectedItemViews.get(position, false)
    }

    fun getSelectedItemViews(): MutableList<Int> {
        val items: MutableList<Int> = arrayListOf()
        for (i in 0..selectedItemViews.size() - 1) {
            items.add(selectedItemViews.keyAt(i))
        }
        return items
    }

    open fun toggleItemView(position: Int) {
        when (choiceMode) {
            RecyclerAdapter.ChoiceMode.SINGLE_CHOICE -> {
                clearSelectedItemViews()
                selectedItemViews.put(position, true)
            }

            RecyclerAdapter.ChoiceMode.MULTIPLE_CHOICE -> if (isItemViewToggled(position)) {
                selectedItemViews.delete(position)
            } else {
                selectedItemViews.put(position, true)
            }
        }
        notifyItemChanged(position)
    }

    fun clearSelectedItemViews() {
        selectedItemViews.clear()
        notifyDataSetChanged()
    }

    private fun moveSelectedItemView(from: Int, to: Int) {
        if (isItemViewToggled(from) && !isItemViewToggled(to)) {
            selectedItemViews.delete(from)
            selectedItemViews.put(to, true)
        } else if (!isItemViewToggled(from) && isItemViewToggled(to)) {
            selectedItemViews.delete(to)
            selectedItemViews.put(from, true)
        }
    }

    private fun removeSelectedItemView(position: Int) {
        val selectedPositions = getSelectedItemViews()
        if (isItemViewToggled(position)) {
            selectedPositions.removeAt(selectedPositions.indexOf(position))
        }

        selectedItemViews.clear()
        for (selectedPosition in selectedPositions) {
            selectedItemViews.put(if (position > selectedPosition) selectedPosition else selectedPosition - 1, true)
        }
    }

    protected abstract fun onCreateItemView(parent: ViewGroup, viewType: Int): View
    protected abstract fun onBindItemView(v: View, position: Int)

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val itemView = onCreateItemView(parent, viewType)
        val viewHolder = BaseViewHolder(itemView)

        handleClick(viewHolder, { it.layoutPosition })

        return viewHolder
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        onBindItemView(holder.view, position)
    }

}
