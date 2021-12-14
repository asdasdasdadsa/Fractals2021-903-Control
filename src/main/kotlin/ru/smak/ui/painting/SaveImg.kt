package ru.smak.ui.painting
import CartesianPlane
import javax.swing.JFileChooser
import java.awt.image.BufferedImage
import java.io.File
import java.io.IOException
import javax.imageio.ImageIO
import javax.swing.filechooser.FileNameExtensionFilter
import javax.swing.JLabel


class SaveImg (private val plane: CartesianPlane){
    private val  saveFileChooser: JFileChooser = JFileChooser()
    private val newBI: BufferedImage = BufferedImage()
    private val messageLabel: JLabel=JLabel()


    init {
        saveFileChooser.currentDirectory = File("C:\\Desktop")
        saveFileChooser.fileFilter = FileNameExtensionFilter("PNG images", "png")
    }


    fun saveImage (fractalPanel: SelectablePanel){
        var returnValue = saveFileChooser.showSaveDialog()
        if(returnValue == JFileChooser.APPROVE_OPTION){
            try {
                ImageIO.write(newBI,"png",saveFileChooser.selectedFile)
                messageLabel.text = "Изображение успешно загружено"
            } catch (IOException e ){
                messageLabel.text = "Не удалось загрузить изображение"
            }
        }else {
            messageLabel.text = "Ошибка"
        }




    }
}