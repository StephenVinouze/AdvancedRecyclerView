package com.github.stephenvinouze.advancedrecyclerview.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.SparseBooleanArray
import android.view.View
import android.view.ViewGroup
import com.github.stephenvinouze.advancedrecyclerview.callbacks.ClickCallback
import com.github.stephenvinouze.advancedrecyclerview.views.DefaultViewHolder
import java.util.*

/**
 * Created by Stephen Vinouze on 09/11/2015.
 */
abstract class RecyclerAdapter<T>(protected var context: Context) : RecyclerView.Adapter<DefaultViewHolder>() {
    private val selectedItemViews = SparseBooleanArray()
    private var clickCallback: ClickCallback? = null

    var choiceMode = ChoiceMode.SINGLE_CHOICE

    var items: MutableList<T>
        get() = items
        set(items) {
            this.items = items
            notifyDataSetChanged()
        }

    val selectedItemViewCount: Int
        get() = selectedItemViews.size()

    enum class ChoiceMode {
        SINGLE_CHOICE, MULTIPLE_CHOICE
    }

    fun setClickCallback(callback: ClickCallback) {
        clickCallback = callback
    }

    fun getItemAt(position: Int): T {
        return items[position]
    }

    fun addItems(items: List<T>?, position: Int) {
        if (items != null) {
            this.items.addAll(position, items)
            notifyItemRangeInserted(position, items.size)
        }
    }

    fun addItem(item: T?, position: Int) {
        if (item != null) {
            items.add(position, item)
            notifyItemInserted(position)
        }
    }

    fun moveItem(from: Int, to: Int) {
        moveSelectedItemView(from, to)

        Collections.swap(items, from, to)
        notifyItemMoved(from, to)
    }

    fun remoteItems(items: List<T>, position: Int) {
        val selectedPositions = ArrayList<Int>()
        for (item in items) {
            selectedPositions.add(items.indexOf(item))
        }

        removeSelectedItemViews(selectedPositions)

        this.items.removeAll(items)
        notifyItemRangeRemoved(position, items.size)
    }

    fun removeItem(position: Int) {
        removeSelectedItemView(position)

        items.removeAt(position)
        notifyItemRemoved(position)
    }

    fun clearItems() {
        items.clear()
        clearSelectedItemViews()
    }

    fun isItemViewToggled(position: Int): Boolean {
        return selectedItemViews.get(position, false)
    }

    fun getSelectedItemViews(): MutableList<Int> {
        val items = ArrayList<Int>()
        for (i in 0..selectedItemViews.size() - 1) {
            items.add(selectedItemViews.keyAt(i))
        }
        return items
    }

    fun toggleItemView(position: Int) {
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

    fun moveSelectedItemView(from: Int, to: Int) {
        if (isItemViewToggled(from) && !isItemViewToggled(to)) {
            selectedItemViews.delete(from)
            selectedItemViews.put(to, true)
        } else if (!isItemViewToggled(from) && isItemViewToggled(to)) {
            selectedItemViews.delete(to)
            selectedItemViews.put(from, true)
        }
    }

    fun removeSelectedItemViews(positions: List<Int>) {
        val selectedPositions = getSelectedItemViews()
        selectedPositions.removeAll(positions)

        selectedItemViews.clear()
        for (selectedPosition in selectedPositions) {
            selectedItemViews.put(selectedPosition - 1, true)
        }
    }

    fun removeSelectedItemView(position: Int) {
        val selectedPositions = getSelectedItemViews()
        selectedPositions.removeAt(selectedPositions.indexOf(position))

        selectedItemViews.clear()
        for (selectedPosition in selectedPositions) {
            selectedItemViews.put(if (position > selectedPosition) selectedPosition else selectedPosition - 1, true)
        }
    }

    fun clearSelectedItemViews() {
        selectedItemViews.clear()
        notifyDataSetChanged()
    }

    protected abstract fun onCreateItemView(parent: ViewGroup, viewType: Int): View
    protected abstract fun onBindItemView(v: View, position: Int)

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DefaultViewHolder {
        return DefaultViewHolder(onCreateItemView(parent, viewType))
    }

    override fun onBindViewHolder(holder: DefaultViewHolder, position: Int) {
        val itemView = holder.view

        itemView.setOnClickListener {
            toggleItemView(position)

            if (clickCallback != null) {
                clickCallback!!.onItemClick(position)
            }
        }
        itemView.setOnLongClickListener { clickCallback != null && clickCallback!!.onItemLongClick(position) }

        onBindItemView(itemView, position)
    }

}
