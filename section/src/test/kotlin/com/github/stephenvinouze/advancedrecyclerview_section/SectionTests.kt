package com.github.stephenvinouze.advancedrecyclerview

import android.support.v7.widget.RecyclerView
import com.github.stephenvinouze.advancedrecyclerview_section.adapters.RecyclerSectionAdapter
import com.nhaarman.mockito_kotlin.mock
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

/**
 * Created by stephenvinouze on 28/04/16.
 */
class SectionTests {

    private data class SectionItem(val id: Int, val rate: Int)

    private val adapter: RecyclerSectionAdapter<Int, SectionItem> = mock(Mockito.CALLS_REAL_METHODS)
    private val items = arrayListOf(
            SectionItem(1, 1), SectionItem(2, 1), SectionItem(3, 1), SectionItem(4, 1),
            SectionItem(5, 2), SectionItem(6, 2), SectionItem(7, 2),
            SectionItem(8, 3), SectionItem(9, 3), SectionItem(10, 3), SectionItem(11, 3), SectionItem(12, 3))

    @Before
    fun setup() {
        adapter.buildSections(items, { it.rate })
    }

    @Test
    fun testNumberOfSections() {
        val numberOfSections = adapter.numberOfSections()

        Assert.assertEquals(numberOfSections, 3)
    }

    @Test
    fun testNumberOfItemsInSection() {
        Assert.assertEquals(adapter.numberOfItemsInSection(0), 4)
        Assert.assertEquals(adapter.numberOfItemsInSection(1), 3)
        Assert.assertEquals(adapter.numberOfItemsInSection(2), 5)
    }

    @Test
    fun testIsSectionAt() {
        // Match section position
        Assert.assertEquals(adapter.isSectionAt(0), true)
        Assert.assertEquals(adapter.isSectionAt(5), true)
        Assert.assertEquals(adapter.isSectionAt(9), true)

        // Inside section position
        Assert.assertEquals(adapter.isSectionAt(4), false)
        Assert.assertEquals(adapter.isSectionAt(7), false)
        Assert.assertEquals(adapter.isSectionAt(10), false)
    }

    @Test
    fun testAllSections() {
        val sections = adapter.allSections()

        Assert.assertEquals(sections, listOf(1, 2, 3))
    }

    @Test
    fun testSectionAt() {
        Assert.assertEquals(adapter.sectionAt(0), 1)
        Assert.assertEquals(adapter.sectionAt(1), 2)
        Assert.assertEquals(adapter.sectionAt(2), 3)
    }

    @Test
    fun testSectionPosition() {
        // Match section position
        Assert.assertEquals(adapter.sectionPosition(0), 0)
        Assert.assertEquals(adapter.sectionPosition(5), 1)
        Assert.assertEquals(adapter.sectionPosition(9), 2)

        // Inside section position
        Assert.assertEquals(adapter.sectionPosition(4), 0)
        Assert.assertEquals(adapter.sectionPosition(7), 1)
        Assert.assertEquals(adapter.sectionPosition(10), 2)
    }

    @Test
    fun testRelativePosition() {
        // Both absolute and relative positions share same section
        Assert.assertEquals(adapter.relativePosition(2), 1)
        Assert.assertEquals(adapter.relativePosition(7), 5)
        Assert.assertEquals(adapter.relativePosition(10), 7)

        // Absolute and relative positions between two sections
        Assert.assertEquals(adapter.relativePosition(4), 3)
        Assert.assertEquals(adapter.relativePosition(8), 6)

        // Absolute position matches section position
        Assert.assertEquals(adapter.relativePosition(0), RecyclerView.NO_POSITION)
        Assert.assertEquals(adapter.relativePosition(5), RecyclerView.NO_POSITION)
        Assert.assertEquals(adapter.relativePosition(9), RecyclerView.NO_POSITION)
    }

    @Test
    fun testAbsolutePosition() {
        // Both absolute and relative positions share same section
        Assert.assertEquals(adapter.absolutePosition(1), 2)
        Assert.assertEquals(adapter.absolutePosition(5), 7)
        Assert.assertEquals(adapter.absolutePosition(7), 10)

        // Absolute and relative positions between two sections
        Assert.assertEquals(adapter.absolutePosition(3), 4)
        Assert.assertEquals(adapter.absolutePosition(4), 6)
        Assert.assertEquals(adapter.absolutePosition(6), 8)
    }

}