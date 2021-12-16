package ru.smak.ui

import ru.smak.math.fractals.Mandelbrot
import ru.smak.ui.painting.CartesianPlane
import ru.smak.ui.painting.SelectablePanel
import ru.smak.ui.painting.fractals.FractalPainter
import ru.smak.ui.painting.fractals.colorizers
import java.awt.Color
import java.awt.Dimension
import javax.swing.*
import kotlin.random.Random


class MainFrame : JFrame() {

    val fractalPanel: SelectablePanel
    var frame: JFrame //= null
    val menu: JMenu
    val menuBar: JMenuBar

    init{
        defaultCloseOperation = EXIT_ON_CLOSE
        minimumSize = Dimension(600, 400)
        val painter = FractalPainter(
            Mandelbrot(),
            CartesianPlane(-2.0, 1.0, -1.0, 1.0),
            colorizers[Random.nextInt(colorizers.size)])

        frame = JFrame()
        menu = JMenu()
        menuBar = JMenuBar()
        menuBar.setBounds(0, 0, 350, 30)
        val fileMenu = JMenu("Файл")
        menuBar.add(fileMenu)
        val loadMenu = JMenuItem("Загрузить")
        fileMenu.add(loadMenu)
        val SourceAreaMenu = JMenuItem("Исходная область")
        fileMenu.add(SourceAreaMenu)
        val OpenMenu = JMenuItem("Открыть...")
        fileMenu.add(OpenMenu)
        val SaveMenu = JMenu("Сохранить как")
        val fracMenu = JMenuItem("Фрактал")
        val imageMenu = JMenuItem("Изображение")
        SaveMenu.add(fracMenu)
        SaveMenu.add(imageMenu)
        fileMenu.add(SaveMenu)

        val FractalMenu = JMenu("Фрактал")
        menuBar.add(FractalMenu)
        val ColorSitemMenu = JMenu("Цветовая схема")
        val ColorSitem1Menu = JMenuItem("чб")
        val ColorSitem2Menu = JMenuItem("сз")
        val ColorSitem3Menu = JMenuItem("кз")
        val TypeFracMenu = JMenu("Тип фрактала")
        val DynamicMenu = JCheckBoxMenuItem("Динамические итерации")
        val ExcursionMenu = JMenuItem("Создание экскурсии")
        ColorSitemMenu.add(ColorSitem1Menu)
        ColorSitemMenu.add(ColorSitem2Menu)
        ColorSitemMenu.add(ColorSitem3Menu)
        FractalMenu.add(ColorSitemMenu)
        FractalMenu.add(TypeFracMenu)
        FractalMenu.add(DynamicMenu)
        FractalMenu.add(ExcursionMenu)

        fractalPanel = SelectablePanel(painter).apply {
            background = Color.WHITE
            addSelectListener{
                with (painter.plane){
                    val xMin = xScr2Crt(it.x)
                    val yMin = yScr2Crt(it.y)
                    val xMax = xScr2Crt(it.x + it.width)
                    val yMax = yScr2Crt(it.y + it.height)
                    xSegment = Pair(xMin, xMax)
                    ySegment = Pair(yMin, yMax)
                }
                repaint()
            }
        }

        layout = GroupLayout(contentPane).apply{
            setHorizontalGroup(
                createSequentialGroup()
                    .addGap(4)
                    .addGroup(
                        createParallelGroup()
                            .addComponent(menuBar, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE)
                            .addComponent(fractalPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE)
                    )
                    .addGap(4)

            )

            setVerticalGroup(
                createSequentialGroup()
                    .addGap(4)
                    .addComponent(menuBar, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addGap(4)
                    .addComponent(fractalPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE)
                    .addGap(4)
            )
        }
    }

}