package ru.smak.ui.painting

import ru.smak.math.eq
import ru.smak.ui.painting.fractals.FractalPainter
import java.awt.image.BufferedImage
import java.io.BufferedReader
import java.nio.BufferOverflowException
import kotlin.math.abs

class VideoMaker(private val painter: FractalPainter, private val selectablePanel: SelectablePanel) {
    private var keyFrames : ArrayList<Pair<BufferedImage, CartesianPlane>> = arrayListOf<Pair<BufferedImage, CartesianPlane>>()
    private var frames : ArrayList<BufferedImage> = arrayListOf<BufferedImage>()
    private val FrameRate = 30
    var secBetweenFrames : Int = 5
        get () = field
        set (value) {
            field = value
        }
    private val CountOfFrames
        get() = FrameRate * secBetweenFrames
    fun addKeyFrames (_keyFrames : ArrayList<Pair<BufferedImage, CartesianPlane>>) {
        keyFrames = _keyFrames
    }
    fun createFrames() {
        keyFrames.forEachIndexed{ i, kf ->
            if (i != keyFrames.size - 1) {
                val XMaxRateOfChange = abs((kf.second.xMax - keyFrames.get(i + 1).second.xMax)) / CountOfFrames
                val XMinRateOfChange = abs((kf.second.xMin - keyFrames.get(i + 1).second.xMin)) / CountOfFrames
                val YMaxRateOfChange = abs((kf.second.yMax - keyFrames.get(i + 1).second.yMax)) / CountOfFrames
                val YMinRateOfChange = abs((kf.second.yMin - keyFrames.get(i + 1).second.yMin)) / CountOfFrames
                val _kf = kf
                for (frameNum in 0 until CountOfFrames) {
                    _kf.second.xSegment = Pair( _kf.second.xMin - frameNum*XMinRateOfChange,_kf.second.xMax - frameNum * XMaxRateOfChange)
                    _kf.second.ySegment = Pair( _kf.second.yMin + frameNum*YMinRateOfChange,_kf.second.yMax - frameNum * YMaxRateOfChange)
                    val _painter = painter
                    _painter.plane.xSegment = _kf.second.xSegment
                    _painter.plane.ySegment = _kf.second.ySegment
                    val bufImg = BufferedImage(_painter.plane.width, _painter.plane.height, BufferedImage.TYPE_INT_RGB)
                    val bufGraphics = bufImg.createGraphics()
                    _painter.paint(bufGraphics)
                    selectablePanel.graphics.drawImage(bufImg,0,0,null)
                    frames.add(bufImg)
                }
            }
        }
    }
}