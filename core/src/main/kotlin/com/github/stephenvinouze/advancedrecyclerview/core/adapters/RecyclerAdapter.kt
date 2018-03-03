package com.github.stephenvinouze.advancedrecyclerview.core.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.SparseBooleanArray
import android.view.View
import android.view.ViewGroup
import com.github.stephenvinouze.advancedrecyclerview.core.enums.ChoiceMode
import com.github.stephenvinouze.advancedrecyclerview.core.extensions.swap
import com.github.stephenvinouze.advancedrecyclerview.core.views.BaseViewHolder

/**
 * Created by Stephen Vinouze on 09/11/2015.
 */
abstract class RecyclerAdapter<MODEL>(protected val context: Context) : RecyclerView.Adapter<BaseViewHolder>() {

    val selectedItemViewCount: Int
        get() = selectedItemViews.size()
    var choiceMode = ChoiceMode.NONE
        set(value) {
            field = value
            clearSelectedItemViews()
        }
    open var items: MutableList<MODEL> = arrayListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var onClick: ((view: View, position: Int) -> Unit)? = null
    var onLongClick: ((view: View, position: Int) -> Boolean)? = { _: View, _: Int -> false }

    private val selectedItemViews = SparseBooleanArray()

    fun addItems(items: MutableList<MODEL>, position: Int) {
        addItemsInternal(items, position)
        notifyItemRangeInserted(position, items.size)
    }

    fun addItem(item: MODEL, position: Int) {
        addItemInternal(item, position)
        notifyItemInserted(position)
    }

    fun removeItem(position: Int) {
        removeItemInternal(position)
        notifyItemRemoved(position)
    }

    fun moveItem(from: Int, to: Int) {
        moveItemInternal(from, to)
        notifyItemMoved(from, to)
    }

    fun clearItems() {
        clearItemsInternal()
        clearSelectedItemViews()
    }

    fun isItemViewToggled(position: Int): Boolean = selectedItemViews.get(position, false)

    fun getSelectedItemViews(): MutableList<Int> {
        val items: MutableList<Int> = arrayListOf()
        (0 until selectedItemViews.size()).mapTo(items) { selectedItemViews.keyAt(it) }
        return items
    }

    fun clearSelectedItemViews() {
        selectedItemViews.clear()
        notifyDataSetChanged()
    }

    open fun toggleItemView(position: Int) {
        when (choiceMode) {
            ChoiceMode.NONE -> return
            ChoiceMode.SINGLE -> {
                getSelectedItemViews().forEach {
                    selectedItemViews.delete(it)
                    notifyItemChanged(it)
                }
                selectedItemViews.put(position, true)
            }

            ChoiceMode.MULTIPLE -> if (isItemViewToggled(position)) {
                selectedItemViews.delete(position)
            } else {
                selectedItemViews.put(position, true)
            }
        }
        notifyItemChanged(position)
    }

    protected open fun addItemsInternal(items: MutableList<MODEL>, position: Int) {
        this.items.addAll(position, items)
    }

    protected open fun addItemInternal(item: MODEL, position: Int) {
        this.items.add(position, item)
    }

    protected open fun moveItemInternal(from: Int, to: Int) {
        moveSelectedItemView(from, to)

        this.items.swap(from, to)
    }

    protected open fun removeItemInternal(position: Int) {
        removeSelectedItemView(position)

        this.items.removeAt(position)
    }

    protected open fun clearItemsInternal() {
        this.items.clear()
    }

    protected open fun handleClick(viewHolder: BaseViewHolder, clickPosition: (BaseViewHolder) -> Int) {
        val itemView = viewHolder.view
        itemView.setOnClickListener {
            toggleItemView(clickPosition(viewHolder))

            onClick?.invoke(itemView, clickPosition(viewHolder))
        }

        onLongClick?.let {
            itemView.setOnLongClickListener { it(itemView, clickPosition(viewHolder)) }
        }
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

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val itemView = onCreateItemView(parent, viewType)
        val viewHolder = BaseViewHolder(itemView)

        handleClick(viewHolder, { it.layoutPosition })

        return viewHolder
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        onBindItemView(holder.view, position)
    }

    protected abstract fun onCreateItemView(parent: ViewGroup, viewType: Int): View
    protected abstract fun onBindItemView(view: View, position: Int)

}
