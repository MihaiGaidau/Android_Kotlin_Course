package com.example.notekeeper29

import com.example.notekeeper29.model.CourseInfo
import com.example.notekeeper29.model.NoteInfo

class DataManager {
    val courses = HashMap<String, CourseInfo>()
    val notes = ArrayList<NoteInfo>()

    init {
        initializeCourses()
    }

    private fun initializeCourses(){
        var course = CourseInfo("android_intents","android with intents")
        courses.set(course.courseId, course)
        course = CourseInfo(courseId = "android_async",title = "android async")
        courses.set(course.courseId, course)
        course = CourseInfo(title = "java finda",courseId = "java_fundamentals")
        courses.set(course.courseId, course)
        course = CourseInfo("java_core","java wi core")
        courses.set(course.courseId, course)
    }
}