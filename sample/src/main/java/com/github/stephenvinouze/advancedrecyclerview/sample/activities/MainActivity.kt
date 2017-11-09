package com.github.stephenvinouze.advancedrecyclerview.sample.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.github.stephenvinouze.advancedrecyclerview.sample.R
import com.github.stephenvinouze.advancedrecyclerview.sample.fragments.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        displaySingleChoiceRecyclerFragment()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.single_choice_action -> displaySingleChoiceRecyclerFragment()
            R.id.multiple_choice_action -> displayMultipleChoiceRecyclerFragment()
            R.id.section_action -> displaySectionRecyclerFragment()
            R.id.gesture_action -> displayGestureRecyclerFragment()
            R.id.gesture_section_action -> displayGestureSectionRecyclerFragment()
            R.id.pagination_action -> displayPaginationRecyclerFragment()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun displaySingleChoiceRecyclerFragment() {
        title = getString(R.string.single_choice_recycler_name)
        supportFragmentManager.beginTransaction().replace(R.id.main_container, SingleChoiceRecyclerFragment()).commit()
    }

    private fun displayMultipleChoiceRecyclerFragment() {
        title = getString(R.string.multiple_choice_recycler_name)
        supportFragmentManager.beginTransaction().replace(R.id.main_container, MultipleChoiceRecyclerFragment()).commit()
    }

    private fun displaySectionRecyclerFragment() {
        title = getString(R.string.section_recycler_name)
        supportFragmentManager.beginTransaction().replace(R.id.main_container, SectionRecyclerFragment()).commit()
    }

    private fun displayGestureRecyclerFragment() {
        title = getString(R.string.gesture_recycler_name)
        supportFragmentManager.beginTransaction().replace(R.id.main_container, GestureRecyclerFragment()).commit()
    }

    private fun displayGestureSectionRecyclerFragment() {
        title = getString(R.string.gesture_sections_recycler_name)
        supportFragmentManager.beginTransaction().replace(R.id.main_container, GestureSectionRecyclerFragment()).commit()
    }

    private fun displayPaginationRecyclerFragment() {
        title = getString(R.string.pagination_recycler_name)
        supportFragmentManager.beginTransaction().replace(R.id.main_container, PaginationRecyclerFragment()).commit()
    }

}
