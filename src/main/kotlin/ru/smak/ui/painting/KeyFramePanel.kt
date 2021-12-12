package ru.smak.ui.painting

import ru.smak.ui.GraphicsPanel
import java.awt.Dimension
import java.awt.Graphics
import java.awt.Image
import java.awt.Point
import java.awt.event.MouseAdapter
import java.awt.image.BufferedImage
import javax.swing.JPanel

class KeyFramePanel (private val indexOfFrame : Int, private val imagePainter: ImagePainter ) : GraphicsPanel(imagePainter){

    private val countOfFrames = 3
    private val gap = 2
    init {
        addMouseListener(object : MouseAdapter() {
        })
        setSize(imagePainter.size)
        // Производим правильное размещение ключевого кадра, при его добавлении
        setLocation(Point(0, imagePainter.size.height * indexOfFrame + indexOfFrame * gap))

    }

}