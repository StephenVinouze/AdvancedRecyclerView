package com.github.stephenvinouze.advancedrecyclerview.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.github.stephenvinouze.advancedrecyclerview.R
import com.github.stephenvinouze.advancedrecyclerview.views.DefaultViewHolder
import java.util.*

/**
 * Created by Stephen Vinouze on 09/11/2015.
 */
abstract class RecyclerSectionAdapter<T>(protected var context: Context) : RecyclerView.Adapter<DefaultViewHolder>() {
    private var isValid = true
    private var sortMethodName: String? = null
    private var baseAdapter: RecyclerAdapter<T>? = null
    private val sections = SparseArray<Section>()

    fun setBaseAdapter(baseAdapter: RecyclerAdapter<T>) {
        this.baseAdapter = baseAdapter
        baseAdapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onChanged() {
                updateSections()
            }

            override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
                isValid = baseAdapter.itemCount > 0
                notifyItemRangeChanged(positionToSectionPosition(positionStart), itemCount)
            }

            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                updateSections()
            }

            override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
                updateSections()
            }

            override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
                isValid = baseAdapter.itemCount > 0
                notifyItemMoved(positionToSectionPosition(fromPosition), positionToSectionPosition(toPosition))
            }
        })
    }

    fun setSortMethodName(sortMethodName: String) {
        this.sortMethodName = sortMethodName
    }

    open fun titleForSection(sectionPosition: Int): String? {
        return null
    }

    open fun onCreateSectionItemView(parent: ViewGroup, viewType: Int): View? {
        return null
    }

    open fun onBindSectionItemView(v: View, sectionPosition: Int) {
    }

    override fun getItemViewType(position: Int): Int {
        return if (isSectionAt(position)) SECTION_TYPE else baseAdapter!!.getItemViewType(sectionPositionToPosition(position)) + 1
    }

    override fun getItemId(position: Int): Long {
        return if (isSectionAt(position)) Long.MAX_VALUE - sections.indexOfKey(position) else baseAdapter!!.getItemId(sectionPositionToPosition(position))
    }

    override fun getItemCount(): Int {
        return if (isValid) baseAdapter!!.itemCount + sections.size() else 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DefaultViewHolder {
        if (viewType == SECTION_TYPE) {
            val customView = onCreateSectionItemView(parent, viewType)
            if (customView != null && titleForSection(0) == null) {
                return DefaultViewHolder(customView)
            } else {
                val view = LayoutInflater.from(context).inflate(R.layout.section_view, parent, false)
                return SectionDefaultViewHolder(view, R.id.section_text)
            }
        } else {
            return baseAdapter!!.onCreateViewHolder(parent, viewType - 1)
        }
    }

    override fun onBindViewHolder(holder: DefaultViewHolder, position: Int) {
        if (isSectionAt(position)) {
            val sectionPosition = sections.indexOfKey(position)
            if (holder is SectionDefaultViewHolder) {
                val sectionTextView = holder.title
                val customTitle = titleForSection(sectionPosition)
                sectionTextView.text = customTitle ?: sections.get(position).title
            } else {
                onBindSectionItemView(holder.view, sectionPosition)
            }
        } else {
            baseAdapter!!.onBindViewHolder(holder, sectionPositionToPosition(position))
        }
    }

    fun isSectionAt(position: Int): Boolean {
        return sections.get(position) != null
    }

    fun getFirstItemInSection(sectionPosition: Int): T? {
        if (sectionPosition < sections.size()) {
            val computedPosition = sectionPositionToPosition(sections.keyAt(sectionPosition) + 1)
            return baseAdapter!!.items[computedPosition]
        }
        return null
    }

    fun positionToSectionPosition(position: Int): Int {
        var offset = 0
        for (i in 0..sections.size() - 1) {
            if (sections.valueAt(i).firstPosition > position) {
                break
            }
            ++offset
        }
        return position + offset
    }

    fun sectionPositionToPosition(sectionPosition: Int): Int {
        if (isSectionAt(sectionPosition)) {
            return RecyclerView.NO_POSITION
        }

        var offset = 0
        for (i in 0..sections.size() - 1) {
            if (sections.valueAt(i).position > sectionPosition) {
                break
            }
            --offset
        }
        return sectionPosition + offset
    }

    private fun buildSections(items: List<T>) {
/*        if (items.size > 0) {
            try {
                val method = items[0]::class .getMethod(sortMethodName)
                if (method != null) {

                    val sections = ArrayList<Section>()
                    var previousTitle = ""
                    for (item in items) {
                        try {
                            val currentTitle = method.invoke(item).toString()
                            if (currentTitle != previousTitle) {
                                sections.add(Section(items.indexOf(item), currentTitle))
                                previousTitle = currentTitle
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }

                    }

                    //Add your adapter to the sectionAdapter
                    setSections(sections.toArray<Section>(arrayOfNulls<Section>(sections.size)))
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }*/
    }

    private fun setSections(sections: Array<Section>) {
        this.sections.clear()

        Arrays.sort(sections) { o, o1 ->
            if (o.firstPosition == o1.firstPosition)
                0
            else
                if (o.firstPosition < o1.firstPosition) -1 else 1
        }

        var offset = 0 // offset positions for the headers we're adding
        for (section in sections) {
            section.position = section.firstPosition + offset
            this.sections.append(section.position, section)
            ++offset
        }

        notifyDataSetChanged()
    }

    private fun updateSections() {
        isValid = baseAdapter!!.itemCount > 0
        buildSections(baseAdapter!!.items)
    }

    class Section(val firstPosition: Int, val title: CharSequence) {
        var position: Int = 0
    }

    class SectionDefaultViewHolder(view: View, textResourceid: Int) : DefaultViewHolder(view) {

        var title: TextView

        init {
            title = view.findViewById(textResourceid) as TextView
        }
    }

    companion object {
        private val SECTION_TYPE = 0
    }

}