package ru.smak.ui.painting

import java.awt.*
import java.awt.image.BufferedImage
import javax.swing.BorderFactory
import javax.swing.JPanel

class KeyFramesPanel : JPanel() {
    private val keyFrames: ArrayList<KeyFramePanel> = arrayListOf()
    val KFsize = Dimension(300, 100)
    private val gap = 2
    private val borderSz = 1

    fun addKeyFrame(keyFrame: KeyFramePanel) {
        calcFramePosition(keyFrame)
        keyFrame.setSize(KFsize)
        keyFrames.add(keyFrame)
    }

    fun deleteKeyFrame(index: Int) {
        keyFrames.forEachIndexed { ind, kf ->
            if (ind == index) keyFrames.remove(kf)
        }
    }

    // Производим правильное размещение ключевого кадра, при его добавлении
    private fun calcFramePosition(keyFrame: KeyFramePanel) {
        val indexOfFrame = if (keyFrames.isEmpty()) 0 else keyFrames.lastIndex + 1
        with(keyFrame) {
            setLocation(borderSz, indexOfFrame * (size.height + gap))
        }
    }

    override fun paintComponent(g: Graphics?) {
        // super.paintComponent(g)
        if (!keyFrames.isEmpty())
            keyFrames.forEach {
                it.repaint()
            }
    }
}
