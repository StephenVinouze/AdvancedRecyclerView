package com.github.stephenvinouze.advancedrecyclerview

import com.github.stephenvinouze.advancedrecyclerview.adapters.RecyclerSectionAdapter
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

    private val adapter : RecyclerSectionAdapter<Int, SectionItem> = mock(Mockito.CALLS_REAL_METHODS)
    private val items = arrayListOf(
            SectionItem(1, 1),SectionItem(2, 1),SectionItem(3, 1),SectionItem(4, 1),
            SectionItem(5, 2),SectionItem(6, 2),SectionItem(7, 2),
            SectionItem(8, 3),SectionItem(9, 3),SectionItem(10, 3),SectionItem(11, 3),SectionItem(12, 3))

    @Before
    fun setup() {
        adapter.buildSections(items, { it.rate })
    }

    @Test
    fun testBuildSections_success() {
        val sections = adapter.sectionItems
        
        Assert.assertEquals(sections.size, 3)
        Assert.assertEquals(sections.get(1)?.size, 4)
        Assert.assertEquals(sections.get(2)?.size, 3)
        Assert.assertEquals(sections.get(3)?.size, 5)
    }

    @Test
    fun testRelativePosition_success() {
        Assert.assertEquals(adapter.relativePosition(4), 3)
    }

}