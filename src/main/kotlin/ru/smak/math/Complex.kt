package ru.smak.math

import org.kotlinmath.Complex

val Complex.mod2: Double
    get() = re * re + im * im