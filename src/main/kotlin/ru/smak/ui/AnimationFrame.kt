package ru.smak.ui

import ru.smak.ui.painting.KeyFramesPanel
import ru.smak.ui.painting.SelectablePanel
import ru.smak.ui.painting.VideoMaker
import ru.smak.ui.painting.fractals.FractalPainter
import java.awt.Color
import java.awt.Dimension
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.awt.image.BufferedImage
import javax.swing.*
import javax.swing.filechooser.FileNameExtensionFilter

class AnimationFrame( private val painter: FractalPainter) : JFrame() {
    val ctrlPanel : JPanel
    val kfLabel : JLabel
    val frameScroll : JScrollPane
    val keyFramesPanel : JPanel
    val addKeyFrame : JButton
    val createVideoBtn : JButton
    val frameTimeLbl : JLabel
    val frameTimeSpinner : JSpinner
    val frameTimeModel : SpinnerNumberModel
    var prop = 0.0

    init {

        with(painter.plane){
            prop = (xMax - xMin) / (yMax - yMin)
        }

    val fracPanel = SelectablePanel(painter).apply {
        background = Color.WHITE
        addSelectListener {
            with(painter.plane) {
                var xMin = xScr2Crt(it.x)
                var yMin = yScr2Crt(it.y + it.height)
                var xMax = xScr2Crt(it.x + it.width)
                var yMax = yScr2Crt(it.y)

                /*if(SaveRationMenu.state){
                        if (xMax - xMin > yMax - yMin){
                            yMax = yMin + (xMax - xMin) / prop
                        } else{
                            xMax = xMin + (yMax - yMin) * prop
                        }
                    }*/

                //mand.flag = DynamicMenu.state

                /*mand.changeIterations(xSegment.first, xSegment.second, ySegment.first, ySegment.second, xMin, yMin, xMax, yMax)
                    //mand.isDynamic(DynamicMenu.state)
                    mand.isDynamic(DynamicMenu.isSelected)*/
                    xSegment = Pair(xMin, xMax)
                    ySegment = Pair(yMin, yMax)

                    //stat.add(Pair(Pair(xMin,xMax), Pair(yMin,yMax)))
                }
                repaint()
            }
            addMoveListener {
                with(painter.plane) {
                    val xMin = xMin + xScr2Crt(0) - xScr2Crt(it.first)
                    val yMin = yMin + yScr2Crt(0) - yScr2Crt(it.second)
                    val xMax = xMax + xScr2Crt(0) - xScr2Crt(it.first)
                    val yMax = yMax + yScr2Crt(0) - yScr2Crt(it.second)
                    xSegment = Pair(xMin, xMax)
                    ySegment = Pair(yMin, yMax)

                    // stat.add(Pair(Pair(xMin,xMax), Pair(yMin,yMax)))
                        }
                    repaint()
                }
            }
        defaultCloseOperation = DISPOSE_ON_CLOSE
        title = "Экскурсия"
        minimumSize = Dimension(1200, 700)
        kfLabel = JLabel().apply {
            text = "Ключевые кадры"
            font = getFont().deriveFont(16.0f)
        }
        frameTimeLbl = JLabel().apply {
            text = "Время смены ключевых кадров (с):"
        }
        frameTimeModel = SpinnerNumberModel(5, 1, 10, 1)
        frameTimeSpinner = JSpinner(frameTimeModel)
        createVideoBtn = JButton().apply {
            text = "Создать видео"
        }
        keyFramesPanel = KeyFramesPanel().apply {
            background = Color.GRAY
        }
        frameScroll = JScrollPane(keyFramesPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER).apply {
            preferredSize = Dimension(300, 400)
        }

        createVideoBtn.addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(e: MouseEvent?) {
                val fileChooser = JFileChooser().apply {
                    fileFilter = FileNameExtensionFilter("Видео-файлы анимации фракталов", "avi")
                }
                fileChooser.dialogTitle = "Сохранение видео"
                fileChooser.fileSelectionMode = JFileChooser.FILES_AND_DIRECTORIES
                val result = fileChooser.showSaveDialog(this@AnimationFrame)
                val pathToFile = fileChooser.selectedFile.absolutePath + ".${(fileChooser.fileFilter as FileNameExtensionFilter).extensions[0]}"
                val videoMaker = VideoMaker(painter, fracPanel, pathToFile)
                videoMaker.secBetweenFrames = frameTimeSpinner.value as Int
                videoMaker.addKeyFrames(keyFramesPanel.keyFrames)
                videoMaker.createVideo()
            }
        })

        addKeyFrame = JButton().apply {
            text = "Добавить ключевой кадр"
        }

        addKeyFrame.addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(e: MouseEvent?) {
                val img = BufferedImage(fracPanel.width, fracPanel.height, BufferedImage.TYPE_INT_RGB)
                val imgGr = img.createGraphics()
                fracPanel.paint(imgGr)
                keyFramesPanel.addKeyFramePanel(img, painter.plane)
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
                    .addComponent(fracPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE)
                    .addComponent(ctrlPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addGap(4)
            )
            setVerticalGroup(
                createSequentialGroup()
                    .addGap(4)
                    .addGroup(createParallelGroup()
                        .addComponent(fracPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE)
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
                            .addComponent(kfLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE)
                            .addGap(30)
                            .addComponent(frameScroll, 300, 300, 300)
                            .addGap(10)
                            .addComponent(addKeyFrame, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addGap(15)
                            .addComponent(frameTimeLbl, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addGap(5)
                            .addComponent(frameTimeSpinner, 100, GroupLayout.PREFERRED_SIZE, Int.MAX_VALUE)
                            .addGap(5)
                            .addComponent(createVideoBtn, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                    )
                    .addGap(50)
            )
            setVerticalGroup(
                createSequentialGroup()
                    .addGap(15)
                    .addComponent(kfLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE)
                    .addGap(30)
                    .addComponent(frameScroll, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE , 400)
                    .addGap(10)
                    .addComponent(addKeyFrame, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addGap(15)
                    .addComponent(frameTimeLbl, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addGap(5)
                    .addComponent(frameTimeSpinner, 20, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addGap(5)
                    .addComponent(createVideoBtn, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addGap(30, 30 , Int.MAX_VALUE)
            )
        }


    }


}
