package com.programmersbox.gpacalculator

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class GpaViewModel: ViewModel() {
    val gpaList = mutableStateListOf<GradeForClass>(GradeForClass.Empty)

    fun addGrade() {
        gpaList.add(GradeForClass.Empty)
    }

    fun removeGrade(id: Int) {
        gpaList.removeAt(id)
    }

    fun modifyGradePoint(
        id: Int,
        gradePoint: GradePoint
    ) {
        gpaList[id] = gpaList[id].copy(gradePoint = gradePoint)
    }

    fun modifyCourseName(
        id: Int,
        courseName: String
    ) {
        gpaList[id] = gpaList[id].copy(courseName = courseName)
    }

    fun modifyCredit(
        id: Int,
        credit: Int
    ) {
        gpaList[id] = gpaList[id].copy(credit = credit)
    }
}