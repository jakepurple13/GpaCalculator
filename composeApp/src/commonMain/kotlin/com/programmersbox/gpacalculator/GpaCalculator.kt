package com.programmersbox.gpacalculator

enum class GradePoint(
    val points: Double,
    val letter: String
) {
    APlus(4.3, "A+"),
    A(4.0, "A"),
    AMinus(3.7, "A-"),
    BPlus(3.3, "B+"),
    B(3.0, "B"),
    BMinus(2.7, "B-"),
    CPlus(2.3, "C+"),
    C(2.0, "C"),
    CMinus(1.7, "C-"),
    DPlus(1.3, "D+"),
    D(1.0, "D"),
    DMinus(1.7, "D-"),
    F(0.0, "F")
}

data class GradeForClass(
    val courseName: String,
    val gradePoint: GradePoint,
    val credit: Int
) {
    companion object {
        val Empty = GradeForClass("", GradePoint.F, 0)
    }
}

class GpaCalculator {
    companion object {
        fun calculate(
            list: List<GradeForClass>,
        ): GpaTotals {
            val gradePointTotal = list.sumOf { it.gradePoint.points * it.credit }
            val creditTotal = list.sumOf { it.credit }
            val gpa = gradePointTotal / creditTotal
            return GpaTotals(
                gpa = gpa,
                creditTotal = creditTotal,
                gradePointTotal = gradePointTotal
            )
        }
    }
}

data class GpaTotals(
    val gpa: Double,
    val creditTotal: Int,
    val gradePointTotal: Double,
)