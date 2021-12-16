package ru.smak.ui

import ru.smak.math.fractals.Mandelbrot
import ru.smak.ui.painting.CartesianPlane
import ru.smak.ui.painting.SelectablePanel
import ru.smak.ui.painting.fractals.FractalPainter
import ru.smak.ui.painting.fractals.colorizers
import java.awt.Color
import java.awt.Dimension
import java.awt.event.*
import javax.swing.*
import kotlin.random.Random


class MainFrame : JFrame() {

    val fractalPanel: SelectablePanel

    val stat = mutableListOf(Pair(Pair(-2.0,1.0),Pair(-1.0,1.0)) )

    var prop = 0.0

    init{
        defaultCloseOperation = EXIT_ON_CLOSE
        minimumSize = Dimension(600, 400)
        val mand = Mandelbrot()
        val painter = FractalPainter(
            mand,
            CartesianPlane(-2.0, 1.0, -1.0, 1.0),
            colorizers[Random.nextInt(colorizers.size)])

        with(painter.plane){
            prop = (xMax - xMin) / (yMax - yMin)
        }

        fractalPanel = SelectablePanel(painter).apply {
            background = Color.WHITE
            addSelectListener{
                with (painter.plane){
                    var xMin = xScr2Crt(it.x)
                    var yMin = yScr2Crt(it.y + it.height)
                    var xMax = xScr2Crt(it.x + it.width)
                    var yMax = yScr2Crt(it.y)



                    if (xMax - xMin > yMax - yMin){
                        yMax = yMin + (xMax - xMin) / prop
                    } else{
                        xMax = xMin + (yMax - yMin) * prop
                    }


                    mand.changeIterations(xSegment.first, xSegment.second, ySegment.first, ySegment.second, xMin, yMin, xMax, yMax)
                    xSegment = Pair(xMin, xMax)
                    ySegment = Pair(yMin, yMax)

                    stat.add(Pair(Pair(xMin,xMax),Pair(yMin,yMax)))
                }
                repaint()
            }
        }

        fractalPanel.getInputMap().put(KeyStroke.getKeyStroke("control A"),"foo")

        fractalPanel.addKeyListener(object : KeyListener{
            override fun keyTyped(e: KeyEvent?) {

            }

            override fun keyPressed(e: KeyEvent?) {
            }

            override fun keyReleased(e: KeyEvent?) {
                e?.let{
                    if(e.keyChar=='\u001A'){
                        if(stat.size!=1)
                            stat.removeAt(stat.size-1)
                        with (painter.plane){
                            xSegment = stat[stat.size-1].first
                            ySegment = stat[stat.size-1].second
                        }
                    }
                    repaint()
                }
            }
        })

        layout = GroupLayout(contentPane).apply {
            setHorizontalGroup(
                createSequentialGroup()
                    .addGap(4)
                    .addComponent(fractalPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE)
                    .addGap(4)
            )
            setVerticalGroup(
                createSequentialGroup()
                    .addGap(4)
                    .addComponent(fractalPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE)
                    .addGap(4)
            )
        }
    }

    //fun onSelectArea(r: Rectangle)
}