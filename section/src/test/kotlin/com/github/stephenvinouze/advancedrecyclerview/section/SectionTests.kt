package com.github.stephenvinouze.advancedrecyclerview.section

import android.support.v7.widget.RecyclerView
import com.github.stephenvinouze.advancedrecyclerview.section.adapters.RecyclerSectionAdapter
import com.nhaarman.mockito_kotlin.mock
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

/**
 * Created by stephenvinouze on 28/04/16.
 */
class SectionTests {

    private data class SectionItem(val id: Int, val rate: Int)

    private val adapter: RecyclerSectionAdapter<Int, SectionItem> = mock()
    private val items = arrayListOf(
            SectionItem(1, 1), SectionItem(2, 1), SectionItem(3, 1), SectionItem(4, 1), // Section 1
            SectionItem(5, 2), SectionItem(6, 2), SectionItem(7, 2), // Section 2
            SectionItem(8, 3), SectionItem(9, 3), SectionItem(10, 3), SectionItem(11, 3), SectionItem(12, 3)) // Section 3

    @Before
    fun setup() {
        adapter.buildSections(items, { it.rate })
    }

    @Test
    fun testSectionCount() {
        val numberOfSections = adapter.sectionCount

        assertEquals(numberOfSections, 3)
    }

    @Test
    @Throws(Exception::class)
    fun testItemCountInSection() {
        assertEquals(adapter.itemCountInSection(0), 4)
        assertEquals(adapter.itemCountInSection(1), 3)
        assertEquals(adapter.itemCountInSection(2), 5)
    }

    @Test
    @Throws(Exception::class)
    fun testIsSectionAt() {
        // Match section position
        assertEquals(adapter.isSectionAt(0), true)
        assertEquals(adapter.isSectionAt(5), true)
        assertEquals(adapter.isSectionAt(9), true)

        // Inside section position
        assertEquals(adapter.isSectionAt(4), false)
        assertEquals(adapter.isSectionAt(7), false)
        assertEquals(adapter.isSectionAt(10), false)
    }

    @Test
    @Throws(Exception::class)
    fun testSections() {
        val sections = adapter.sections

        assertEquals(sections, listOf(1, 2, 3))
    }

    @Test
    @Throws(Exception::class)
    fun testSectionAt() {
        assertEquals(adapter.sectionAt(0), 1)
        assertEquals(adapter.sectionAt(1), 2)
        assertEquals(adapter.sectionAt(2), 3)
    }

    @Test
    @Throws(Exception::class)
    fun testSectionPosition() {
        // Match section position
        assertEquals(adapter.sectionPosition(0), 0)
        assertEquals(adapter.sectionPosition(5), 1)
        assertEquals(adapter.sectionPosition(9), 2)

        // Inside section position
        assertEquals(adapter.sectionPosition(4), 0)
        assertEquals(adapter.sectionPosition(7), 1)
        assertEquals(adapter.sectionPosition(10), 2)
    }

    @Test
    @Throws(Exception::class)
    fun testRelativePosition() {
        // Both absolute and relative positions share same section
        assertEquals(adapter.relativePosition(2), 1)
        assertEquals(adapter.relativePosition(7), 5)
        assertEquals(adapter.relativePosition(10), 7)

        // Absolute and relative positions between two sections
        assertEquals(adapter.relativePosition(4), 3)
        assertEquals(adapter.relativePosition(8), 6)

        // Absolute position matches section position
        assertEquals(adapter.relativePosition(0), RecyclerView.NO_POSITION)
        assertEquals(adapter.relativePosition(5), RecyclerView.NO_POSITION)
        assertEquals(adapter.relativePosition(9), RecyclerView.NO_POSITION)
    }

    @Test
    @Throws(Exception::class)
    fun testAbsolutePosition() {
        // Both absolute and relative positions share same section
        assertEquals(adapter.absolutePosition(1), 2)
        assertEquals(adapter.absolutePosition(5), 7)
        assertEquals(adapter.absolutePosition(7), 10)

        // Absolute and relative positions between two sections
        assertEquals(adapter.absolutePosition(3), 4)
        assertEquals(adapter.absolutePosition(4), 6)
        assertEquals(adapter.absolutePosition(6), 8)
    }

}