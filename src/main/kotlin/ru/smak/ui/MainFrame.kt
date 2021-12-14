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

    init{
        defaultCloseOperation = EXIT_ON_CLOSE
        minimumSize = Dimension(600, 400)
        val painter = FractalPainter(
            Mandelbrot(),
            CartesianPlane(-2.0, 1.0, -1.0, 1.0),
            colorizers[Random.nextInt(colorizers.size)])

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

                    stat.add(Pair(Pair(xMin,xMax),Pair(yMin,yMax)))
                }
                repaint()
            }
        }


     /*   val action: AbstractAction = object : AbstractAction() {
            override fun actionPerformed(e: ActionEvent) {
                if (e.source is JButton) {
                    val button = e.source as JButton
                    button.doClick()
                } else if (e.source is JComponent) {
                    val component = e.source as JComponent
                    component.transferFocus()
                }
            }
        }
*/

     //   val button1 = JButton()
    //    button1.getActionMap().put("foo", action);
        //fractalPanel.getActionForKeyStroke()
        //fractalPanel.getInputMap().put(KeyStroke.getKeyStroke(Character.valueOf('control A'), 0), "foo")
        fractalPanel.getInputMap().put(KeyStroke.getKeyStroke("control A"),"foo")

        fractalPanel.addKeyListener(object : KeyListener{
            override fun keyTyped(e: KeyEvent?) {

            }

            override fun keyPressed(e: KeyEvent?) {
                e?.let {
                    if(e.keyChar=='z')
                    println("asdasdasdasd")
                }
            }

            override fun keyReleased(e: KeyEvent?) {

            }
        })


        fractalPanel.addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(e: MouseEvent?) {
                super.mouseClicked(e)
                e?.let {
                    if(stat.size!=1)
                        stat.removeAt(stat.size-1)
                    with (painter.plane){
                        xSegment = stat[stat.size-1].first
                        ySegment = stat[stat.size-1].second
                    }
                }
                repaint()
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