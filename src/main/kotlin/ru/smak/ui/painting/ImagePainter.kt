package ru.smak.ui.painting

import java.awt.Dimension
import java.awt.Graphics
import java.awt.Image
import java.awt.image.BufferedImage
import java.nio.BufferOverflowException

class ImagePainter (private val image : BufferedImage, private var desSize: Dimension) : Painter {
    override var size: Dimension
        get() = desSize
        set(value) {
            desSize = value
        }

    override fun paint(g: Graphics) {
        val resizedImage = BufferedImage(desSize.width, desSize.height, BufferedImage.TYPE_INT_RGB)
        val desImage = image.getScaledInstance(desSize.width, desSize.height, Image.SCALE_SMOOTH)
        with (resizedImage.graphics) {
            drawImage(desImage, 0, 0, null)
        }
        g.drawImage(resizedImage, 0, 0, null)
    }

}