package ru.smak.ui

import ru.smak.ui.painting.SelectablePanel
import java.awt.Color
import java.awt.Dimension
import javax.swing.*

class AnimationFrame(private val selectablePanel: SelectablePanel) : JFrame() {
    val ctrlPanel : JPanel
    val animLabel : JLabel
    val frameScroll : JScrollPane
    val frameScrollPanel : JPanel
    init {
        defaultCloseOperation = EXIT_ON_CLOSE
        title = "Экскурсия"
        minimumSize = Dimension(1000, 500)
        animLabel = JLabel().apply {
            text = "Создание анимации"
            font = getFont().deriveFont(16.0f)
        }

        frameScrollPanel = JPanel().apply {
            background = Color.GREEN
        }
        frameScroll = JScrollPane(frameScrollPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER).apply {
        }

        ctrlPanel = JPanel().apply {
            background = Color.WHITE
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
                            .addComponent(frameScroll, 250, 250, 250)
                    )
                    .addGap(50)
            )
            setVerticalGroup(
                createSequentialGroup()
                    .addGap(15)
                    .addComponent(animLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE)
                    .addGap(30)
                    .addComponent(frameScroll, 300, GroupLayout.PREFERRED_SIZE , GroupLayout.PREFERRED_SIZE)
                    .addGap(30, 30 , Int.MAX_VALUE)
            )
        }
    }


}
