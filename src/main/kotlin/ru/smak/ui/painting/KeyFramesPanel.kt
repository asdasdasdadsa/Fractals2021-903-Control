package ru.smak.ui.painting

import org.intellij.lang.annotations.JdkConstants
import ru.smak.ui.GraphicsPanel
import java.awt.*
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.awt.image.BufferedImage
import javax.swing.BorderFactory
import javax.swing.BoxLayout
import javax.swing.JPanel

class KeyFramesPanel : JPanel() {
    private val keyFrames: ArrayList<GraphicsPanel> = arrayListOf()
    val _keyFrames
        get() = keyFrames
    val KFsize = Dimension(300, 100)
    private val gap = 2
    private val borderSz = 1


    init {
        layout = BoxLayout(this, BoxLayout.Y_AXIS)
        addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(e: MouseEvent?) {
                e?.apply {
                    if (button == MouseEvent.BUTTON1) {

                        val delKF = keyFrames.firstOrNull {
                            (x >= it.x && x <= it.width && y >= it.y && y <= it.y + it.height)
                        }
                        if (delKF != null) {
                            print("Keyframe ${keyFrames.indexOf(delKF)}")
                            deleteKeyFrame(keyFrames.indexOf(delKF))
                        }
                    }
                }
            }
        })
    }

    fun addKeyFrame(img: BufferedImage) {
        val keyFrame = GraphicsPanel(ImagePainter(img, KFsize)).apply {
            minimumSize = KFsize
            preferredSize = KFsize
            maximumSize = KFsize
        }
        calcFramePosition(keyFrame)
        keyFrame.paint(graphics)
        keyFrames.add(keyFrame)
        add(keyFrame)
        revalidate()
    }

    fun deleteKeyFrame(index: Int) {
        keyFrames.removeAt(index)
        reCalcFramesPositions()
    }

    // Производим правильное размещение ключевого кадра, при его добавлении
    private fun calcFramePosition(keyFrame: GraphicsPanel) {
        val indexOfFrame = if (keyFrames.isEmpty()) 0 else keyFrames.lastIndex + 1
        with(keyFrame) {
            setLocation(borderSz, indexOfFrame * (size.height + gap))
        }
    }

    private fun reCalcFramesPositions() {
        keyFrames.forEachIndexed { i, kf ->
            kf.setLocation(borderSz, i * (kf.size.height + gap))
        }
        repaint()
    }

    /*override fun paint(g: Graphics?) {
        if (keyFrames.isNotEmpty())
        keyFrames.forEach{
            it.repaint()
        }
    }*/
}
