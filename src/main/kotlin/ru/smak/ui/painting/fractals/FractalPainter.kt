package ru.smak.ui.painting.fractals

import org.kotlinmath.complex
import ru.smak.math.fractals.AlgebraicFractal
import ru.smak.ui.painting.CartesianPlane
import ru.smak.ui.painting.Painter
import java.awt.Color
import java.awt.Dimension
import java.awt.Graphics
import java.awt.image.BufferedImage
import java.io.FileOutputStream
import java.util.concurrent.Callable
import java.util.concurrent.Executors


class FractalPainter(
    val fractal: AlgebraicFractal,
    val plane: CartesianPlane,
    var colorFunction: (Double)->Color
) : Painter {

    override var size: Dimension
        get() = plane.pixelSize
        set(value) {
            plane.pixelSize = value
        }

    private val threadCount = 16
    private val taskPerThreadCount = 5
    private var fracService = Executors.newFixedThreadPool(threadCount)

    private val list = mutableListOf<BufferedImage>()


    val image: BufferedImage
    get() {
        val image = BufferedImage(plane.width, plane.height, BufferedImage.TYPE_INT_RGB)
        val g1 = image.graphics
        for (i in 0 until  list.size) {
            g1.drawImage(list[i],  list[0].width*i,0, list[i].width,  list[i].height, null)
        }
        return image
    }


    override fun paint(g: Graphics) {
        with(plane){
            if (width <= 0 || height <= 0) return
            if (!(fracService.isShutdown || fracService.isTerminated)) {
                fracService.shutdown()
                fracService = Executors.newFixedThreadPool(threadCount)
            }
            val cnt = width / (threadCount * taskPerThreadCount)
            val wsz = width / cnt
            List(cnt){ i ->
                fracService.submit(Callable {
                    val w = wsz + if (i + 1 == cnt) width % cnt else 0
                    val img = BufferedImage(w, height, BufferedImage.TYPE_INT_RGB)
                    for (k in 0 until w) {
                        for (j in 0..height) {
                            with (img.graphics) {
                                color = colorFunction(
                                    fractal.isInSet(
                                        complex(
                                            xScr2Crt(i * w + k),
                                            yScr2Crt(j)
                                        )
                                    )
                                )
                                fillRect(k, j, 1, 1)
                            }
                        }
                    }
                    //list.add(img)
                    img
                })
            }.forEachIndexed { i, v ->
                val img = v.get()
                list.add(img)
                g.drawImage(img, i * wsz, 0, null)
            }

        }
    }

}