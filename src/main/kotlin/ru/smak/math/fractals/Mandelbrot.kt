package ru.smak.math.fractals

import org.kotlinmath.Complex
import org.kotlinmath.ONE
import org.kotlinmath.ZERO
import org.kotlinmath.ln
import ru.smak.math.mod2
import kotlin.math.absoluteValue

class Mandelbrot() : AlgebraicFractal{
    var deg:Int=2
    /**
     * Количество итераций, которые необходимы для проверки принедлежности
     * точки множеству Мандельброта
     */
    var maxIterations = 200

    var fixedIterations = 200

    var changeableIterations = 200

    /**
     * Квадрат радиуса принадлежности
     * @see R
     */
    private var R2: Double = 4.0

    /**
     * Радиус окрестности, где должна оказаться точка после maxIterations итераций,
     * чтобы считаться принадлежащей множеству Мандельброта
     * @see maxIterations
     */
    var R: Double = 2.0
        set(value) {
            field = value
            R2 = value * value
        }

    /**
     * Определяет тип возвращаемого значения для точек из множества Мандельброта.
     * true  - для точек из множества будут возвращаться различные значения,
     *         зависящие от модуля комплексного числа z, полученного после maxIterations итераций.
     * false - для точек из множества будет выдаваться значение 1F
     * @see maxIterations
     */
    var colorizedSet: Boolean = false

    var prop = 0.0

    /**
     * Метод определения принадлежности точки множеству Мандельброта
     * @param c точка комплексной плоскости, для которой определяется принадлежность
     * множеству Мандельброта
     * @return значение доли успешно проведенных итераций,
     * в течение которых точка могла бы считаться принадлежащей множеству,
     * либо значение 1F для точек, принадлежащий множеству в случае colorizedSet = false,
     * либо величина модуля полученного в ходе итераций комплексного числа в случае colorizeDSet = true
     * @see colorizedSet
     */
    fun changeIterations(X1: Double, X2: Double, Y1: Double, Y2: Double, xMin: Double, yMin: Double, xMax: Double, yMax: Double) {
        val c = kotlin.math.ln((((X2 - X1) * (Y2 - Y1)) * 1.0 / ((xMax - xMin) * (yMax - yMin))).absoluteValue)
        changeableIterations += c.toInt()*30
    }

    fun isDynamic(flag: Boolean){
        if (flag) maxIterations = changeableIterations
        else maxIterations = fixedIterations
    }

    override fun isInSet(c: Complex): Double {
        var z = ZERO
        for(i in 0 until maxIterations){
            var a = ONE
            for( i in 1..deg)
                a*=z
            z = a + c
            if (z.mod2 > R2) return i.toDouble() / maxIterations
        }
        return if (colorizedSet) z.mod else 1.0
    }
}