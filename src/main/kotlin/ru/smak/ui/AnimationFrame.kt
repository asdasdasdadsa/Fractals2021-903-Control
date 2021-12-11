package ru.smak.ui

import ru.smak.ui.painting.ImagePainter
import ru.smak.ui.painting.KeyFramePanel
import ru.smak.ui.painting.SelectablePanel
import java.awt.Color
import java.awt.Dimension
import java.awt.Image
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.awt.image.BufferedImage
import java.io.BufferedReader
import javax.swing.*

class AnimationFrame(private val selectablePanel: SelectablePanel) : JFrame() {
    val ctrlPanel : JPanel
    val animLabel : JLabel
    val frameScroll : JScrollPane
    val keyFramesPanel : JPanel
    val addKeyFrame : JButton
    init {
        defaultCloseOperation = EXIT_ON_CLOSE
        title = "Экскурсия"
        minimumSize = Dimension(1200, 700)
        val keyFrames = arrayListOf<JPanel>()

        animLabel = JLabel().apply {
            text = "Создание анимации"
            font = getFont().deriveFont(16.0f)
        }
        keyFramesPanel = JPanel().apply {
            background = Color.GRAY
        }

        frameScroll = JScrollPane(keyFramesPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER).apply {
        }
        addKeyFrame = JButton().apply {
            text = "Добавить ключевой кадр"
        }

        addKeyFrame.addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(e: MouseEvent?) {
                val img = BufferedImage(selectablePanel.width, selectablePanel.height, BufferedImage.TYPE_INT_RGB)
                val imgGr = img.createGraphics()
                selectablePanel.paint(imgGr)
                val keyFrame = KeyFramePanel(keyFrames.lastIndex,
                    ImagePainter(img, Dimension(300-frameScroll.verticalScrollBar.width, 100)))
                frameScroll.add(keyFrame)
                keyFrames.add(keyFrame)
                frameScroll.revalidate()
            }
        })
        ctrlPanel = JPanel().apply {
            background = Color.WHITE
            border = BorderFactory.createLineBorder(Color.BLACK)
        }
        layout = GroupLayout(contentPane).apply {
            setHorizontalGroup(
                createSequentialGroup()
                    .addGap(4)
                    .addComponent(selectablePanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE)
                    .addComponent(ctrlPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addGap(4)
            )
            setVerticalGroup(
                createSequentialGroup()
                    .addGap(4)
                    .addGroup(createParallelGroup()
                        .addComponent(selectablePanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE)
                        .addComponent(ctrlPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE)
                    )
                    .addGap(4)
            )
        }
        ctrlPanel.layout = GroupLayout(ctrlPanel).apply {
            setHorizontalGroup(
                createSequentialGroup()
                    .addGap(50)
                    .addGroup(
                        createParallelGroup(GroupLayout.Alignment.CENTER)
                            .addComponent(animLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE)
                            .addGap(30)
                            .addComponent(frameScroll, 300, 300, 300)
                            .addGap(10)
                            .addComponent(addKeyFrame, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                    )
                    .addGap(50)
            )
            setVerticalGroup(
                createSequentialGroup()
                    .addGap(15)
                    .addComponent(animLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE)
                    .addGap(30)
                    .addComponent(frameScroll, 400, 400 , 400)
                    .addGap(10)
                    .addComponent(addKeyFrame, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addGap(30, 30 , Int.MAX_VALUE)
            )
        }

    }


}
