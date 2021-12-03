package ru.smak.math.fractals

import org.kotlinmath.Complex

interface AlgebraicFractal {
    fun isInSet(c: Complex): Double
}
