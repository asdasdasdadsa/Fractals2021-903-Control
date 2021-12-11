package ru.smak.ui.painting

import ru.smak.ui.GraphicsPanel
import java.awt.Dimension
import java.awt.Graphics
import java.awt.Image
import java.awt.event.MouseAdapter
import java.awt.image.BufferedImage
import javax.swing.JPanel

class KeyFramePanel (private val indexOfFrame : Int, imagePainter: ImagePainter ) : GraphicsPanel(imagePainter){

    val countOfFrames = 3

    init {
        addMouseListener(object : MouseAdapter() {
        })
        setSize(250, 150)
    }

}