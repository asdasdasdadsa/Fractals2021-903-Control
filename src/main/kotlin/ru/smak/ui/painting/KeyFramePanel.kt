package ru.smak.ui.painting

import ru.smak.ui.GraphicsPanel
import java.awt.Dimension
import java.awt.Graphics
import java.awt.Image
import java.awt.Point
import java.awt.event.MouseAdapter
import java.awt.image.BufferedImage
import javax.swing.JPanel

class KeyFramePanel (private val imagePainter: ImagePainter ) : GraphicsPanel(imagePainter){

    private val countOfFrames = 3
    //val Img : BufferedImage
    val Gap : Int = 2
    init {
        //Img = imagePainter.oldImg
        addMouseListener(object : MouseAdapter() {
        })
        setSize(imagePainter.size)
    }

}

