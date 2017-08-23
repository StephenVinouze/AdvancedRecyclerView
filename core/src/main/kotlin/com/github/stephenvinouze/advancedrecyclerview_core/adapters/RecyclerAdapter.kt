package com.github.stephenvinouze.advancedrecyclerview_core.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.SparseBooleanArray
import android.view.View
import android.view.ViewGroup
import com.github.stephenvinouze.advancedrecyclerview_core.callbacks.ClickCallback
import com.github.stephenvinouze.advancedrecyclerview_core.extensions.swap
import com.github.stephenvinouze.advancedrecyclerview_core.views.BaseViewHolder

/**
 * Created by Stephen Vinouze on 09/11/2015.
 */
abstract class RecyclerAdapter<MODEL>(protected var context: Context) : RecyclerView.Adapter<BaseViewHolder>() {

    private val selectedItemViews = SparseBooleanArray()

    var clickCallback: ClickCallback? = null

    var choiceMode = ChoiceMode.NONE
        set(value) {
            field = value
            clearSelectedItemViews()
        }

    val selectedItemViewCount: Int
        get() = selectedItemViews.size()

    open var items: MutableList<MODEL> = arrayListOf()
        get() = field
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    enum class ChoiceMode {
        NONE, SINGLE, MULTIPLE
    }

    open fun handleClick(viewHolder: BaseViewHolder, clickPosition: (BaseViewHolder) -> Int) {
        val itemView = viewHolder.view
        itemView.setOnClickListener {
            toggleItemView(clickPosition(viewHolder))

            clickCallback?.onItemClick(itemView, clickPosition(viewHolder))
        }
        itemView.setOnLongClickListener { clickCallback?.onItemLongClick(itemView, clickPosition(viewHolder)) ?: false }
    }

    open fun addItems(items: MutableList<MODEL>, position: Int) {
        this.items.addAll(position, items)
        notifyItemRangeInserted(position, items.size)
    }

    open fun addItem(item: MODEL, position: Int) {
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

    open fun clearItems() {
        this.items.clear()
        clearSelectedItemViews()
    }

    fun isItemViewToggled(position: Int): Boolean = selectedItemViews.get(position, false)

    fun getSelectedItemViews(): MutableList<Int> {
        val items: MutableList<Int> = arrayListOf()
        (0 until selectedItemViews.size()).mapTo(items) { selectedItemViews.keyAt(it) }
        return items
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

}
