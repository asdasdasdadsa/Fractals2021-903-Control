package ru.smak.ui.painting

import java.awt.Dimension
import java.awt.Graphics
import java.awt.Image
import java.awt.image.BufferedImage
import java.nio.BufferOverflowException

class ImagePainter (private val image : BufferedImage, private var desSize: Dimension) : Painter {

    val resizedBufImg : BufferedImage
    //var oldImg : BufferedImage = BufferedImage(0,0,BufferedImage.TYPE_INT_RGB)
    private val resizedImage : Image
    override var size: Dimension
        get() = desSize
        set(value) {
            desSize = value
        }

    init {
        resizedBufImg = BufferedImage(desSize.width, desSize.height, BufferedImage.TYPE_INT_RGB)
        resizedImage = image.getScaledInstance(desSize.width, desSize.height, Image.SCALE_SMOOTH)
    }

    override fun paint(g: Graphics) {
        with (resizedBufImg.graphics) {
            drawImage(resizedImage, 0, 0, null)
        }
        g.drawImage(resizedBufImg, 0, 0, null)
    }

}