package ru.smak.ui.painting

import com.xuggle.mediatool.ToolFactory
import com.xuggle.xuggler.Global.DEFAULT_TIME_UNIT
import com.xuggle.xuggler.ICodec
import ru.smak.ui.painting.fractals.FractalPainter
import java.awt.image.BufferedImage
import java.util.concurrent.TimeUnit
import kotlin.math.abs

class VideoMaker(private val painter: FractalPainter, private val selectablePanel: SelectablePanel, private val outputFilePath : String) {
    private var keyFrames : ArrayList<Pair<BufferedImage, CartesianPlane>> = arrayListOf<Pair<BufferedImage, CartesianPlane>>()
    private var frames : ArrayList<BufferedImage> = arrayListOf<BufferedImage>()
    private val frameRate = 30
    private val frameTime : Long
        get() = (1000 / frameRate).toLong()
    var secBetweenFrames : Int = 5
        get () = field
        set (value) {
            field = value
        }
    private val CountOfFrames
        get() = frameRate * secBetweenFrames
    fun addKeyFrames (_keyFrames : ArrayList<Pair<BufferedImage, CartesianPlane>>) {
        keyFrames = _keyFrames
    }
    fun createVideo() {
        val writer = ToolFactory.makeWriter(outputFilePath)
        writer.addVideoStream(0,0, ICodec.ID.CODEC_ID_MPEG4, painter.plane.width, painter.plane.height)
        //val encoder = AWTSequenceEncoder.createSequenceEncoder(File(outputFilePath), FrameRate)

        var frameNum = 0
        keyFrames.forEachIndexed{ i, kf ->
            if (i != keyFrames.size - 1) {
                val currXmin = kf.second.xMin
                val currXmax = kf.second.xMax
                val currYmin = kf.second.yMin
                val currYmax = kf.second.yMax
                val xMaxRateOfChange = (currXmax- keyFrames.get(i + 1).second.xMax) / CountOfFrames
                val xMinRateOfChange = abs(currXmin - keyFrames.get(i + 1).second.xMin) / CountOfFrames
                val yMaxRateOfChange = (currYmax - keyFrames.get(i + 1).second.yMax) / CountOfFrames
                val yMinRateOfChange = abs(currYmin - keyFrames.get(i + 1).second.yMin) / CountOfFrames
                val _kf = kf
                var subFrameNum = 0
                while (frameNum <= CountOfFrames * (i+1)) {
                    _kf.second.xSegment = Pair( currXmin + subFrameNum * xMinRateOfChange, currXmax - subFrameNum * xMaxRateOfChange)
                    _kf.second.ySegment = Pair( currYmin + subFrameNum * yMinRateOfChange, currYmax - subFrameNum * yMaxRateOfChange)
                    val _painter = painter
                    _painter.plane.xSegment = _kf.second.xSegment
                    _painter.plane.ySegment = _kf.second.ySegment
                    val bufImg = BufferedImage(_painter.plane.width, _painter.plane.height, BufferedImage.TYPE_INT_RGB)
                    val bufGraphics = bufImg.createGraphics()
                    _painter.paint(bufGraphics)
                    val convBufImg = BufferedImage(bufImg.width, bufImg.height, BufferedImage.TYPE_3BYTE_BGR)
                    convBufImg.graphics.drawImage(bufImg,0,0,null)
                    selectablePanel.graphics.drawImage(bufImg,0,0,null)
                    writer.encodeVideo(0, convBufImg, DEFAULT_TIME_UNIT.convert(frameNum * frameTime,TimeUnit.MILLISECONDS), DEFAULT_TIME_UNIT )
                    frameNum++
                    subFrameNum++
                }
            }
        }
        writer.close()
        //NIOUtils.closeQuietly(out)
       // muxer.close()
    }
}