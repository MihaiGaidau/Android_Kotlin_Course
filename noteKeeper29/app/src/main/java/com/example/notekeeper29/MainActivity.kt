package com.example.notekeeper29

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import com.example.notekeeper29.model.CourseInfo
import com.example.notekeeper29.model.NOTE_POSITION
import com.example.notekeeper29.model.NoteInfo
import com.example.notekeeper29.model.POSITION_NOT_SET

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

//import kotlinx.android.synthetic.main.fragment_first.*

class MainActivity : AppCompatActivity() {

    private var notePosition = POSITION_NOT_SET


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val adapterCourses = ArrayAdapter<CourseInfo>(this,
            android.R.layout.simple_spinner_item, DataManager.courses.values.toList())
        spinnerCourses.adapter = adapterCourses

        notePosition = savedInstanceState?.getInt(NOTE_POSITION) ?:
            intent.getIntExtra(NOTE_POSITION, POSITION_NOT_SET)
        if (notePosition != POSITION_NOT_SET)
            displayNote()
        else{
            DataManager.notes.add(NoteInfo())
            notePosition = DataManager.notes.lastIndex
        }

    }

    private fun displayNote() {
        val note = DataManager.notes.get(notePosition)
        textNoteTitle.setText(note.title)
        textNoteText.setText(note.text)

        val coursePosition = DataManager.courses.values.indexOf(note.course)
        spinnerCourses.setSelection(coursePosition)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            R.id.action_next ->{
                moveNext()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun moveNext() {
        ++notePosition
        displayNote()
        invalidateOptionsMenu()
    }

    override fun onPause() {
        super.onPause()
        saveNote()
    }

    private fun saveNote() {
        val note = DataManager.notes[notePosition]
        note.title = textNoteTitle.text.toString()
        note.text = textNoteText.text.toString()
        note.course = spinnerCourses.selectedItem as CourseInfo
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        if (notePosition >= DataManager.notes.lastIndex){
            val menuItem = menu?.findItem(R.id.action_next)
            if (menuItem != null){
                menuItem.icon = getDrawable(R.drawable.ic_block_white_24)
                menuItem.isEnabled = false
            }
        }
//        p?.name ?: "XX" = if p != null and p.name != null then return p.name else return XX
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(NOTE_POSITION, notePosition)

    }
}