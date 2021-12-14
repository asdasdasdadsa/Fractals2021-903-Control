package ru.smak.ui.painting

import org.intellij.lang.annotations.JdkConstants
import ru.smak.ui.GraphicsPanel
import java.awt.*
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.awt.image.BufferedImage
import javax.swing.*

class KeyFramesPanel : JPanel() {
    private val keyFrames: ArrayList<GraphicsPanel> = arrayListOf()
    val _keyFrames
        get() = keyFrames
    val KFsize = Dimension(300, 100)
    private val gap = 5
    private val borderSz = 1
    private var parallelGr : GroupLayout.ParallelGroup
    private var sequentialGr : GroupLayout.SequentialGroup
    private var layoutMngr :GroupLayout
    init {
        //layout = BoxLayout(this, BoxLayout.Y_AXIS)
        layout = GroupLayout(this).apply {
            parallelGr = createParallelGroup()
            sequentialGr = createSequentialGroup()
            setHorizontalGroup(
                parallelGr
            )
            setVerticalGroup(
                sequentialGr
            )
            layoutMngr = this
        }

        addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(e: MouseEvent?) {
                e?.apply {
                    if (button == MouseEvent.BUTTON1) {

                        val delKF = keyFrames.firstOrNull {
                            (x >= it.x && x <= it.width && y >= it.y && y <= it.y + it.height)
                        }
                        if (delKF != null)
                            deleteKeyFrame(keyFrames.indexOf(delKF))
                    }
                }
            }
        })
    }

    fun addKeyFrame(img: BufferedImage) {
        val keyFrame = GraphicsPanel(ImagePainter(img, KFsize)).apply {
            //minimumSize = KFsize
            //preferredSize = KFsize
            //maximumSize = KFsize
            setSize(KFsize)
        }
        //calcFramePosition(keyFrame)
        //add(keyFrame)
        if (keyFrames.isEmpty()) {
            parallelGr.addComponent(keyFrame, KFsize.width, KFsize.width, KFsize.width)
            sequentialGr.addComponent(keyFrame, KFsize.height, KFsize.height, KFsize.height)
        }
        else {
            parallelGr.addGap(gap).addComponent(keyFrame, KFsize.width, KFsize.width, KFsize.width)
            sequentialGr.addGap(gap).addComponent(keyFrame, KFsize.height, KFsize.height, KFsize.height)
        }
        /*parallelGr.addGroup(layoutMngr.createSequentialGroup().addComponent(keyFrame, KFsize.width, KFsize.width, KFsize.width).addGap(gap))
        sequentialGr.addGroup(layoutMngr.createSequentialGroup().addComponent(keyFrame, KFsize.height, KFsize.height, KFsize.height).addGap(gap))
        sequentialGr.addComponent(keyFrame, KFsize.height, KFsize.height, KFsize.height)*/
        keyFrames.add(keyFrame)
        revalidate()
    }

    fun deleteKeyFrame(index: Int) {
        remove(keyFrames.get(index))
        keyFrames.removeAt(index)
        repaint()
        //reCalcFramesPositions()
    }

 /*   // Производим правильное размещение ключевого кадра, при его добавлении
    private fun calcFramePosition(keyFrame: GraphicsPanel) {
        val indexOfFrame = if (keyFrames.isEmpty()) 0 else keyFrames.lastIndex + 1
        with(keyFrame) {
            setLocation(borderSz, indexOfFrame * (size.height + gap))
        }
    }*/

    private fun reCalcFramesPositions() {
        keyFrames.forEachIndexed { i, kf ->
            kf.setLocation(borderSz, i * (kf.size.height + gap))
        }
        revalidate()
    }


}
