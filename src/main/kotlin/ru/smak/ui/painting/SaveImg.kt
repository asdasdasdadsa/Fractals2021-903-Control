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
    private val saveFileChooser: JFileChooser = JFileChooser()
    private val newBI: BufferedImage = BufferedImage()
    private val messageLabel: JLabel=JLabel()


    init {
        saveFileChooser.currentDirectory = File("C:\\Desktop") //Метод определения текущей директории
        saveFileChooser.fileFilter = FileNameExtensionFilter("PNG images", "png") //Метод установки файлового фильтра
    }


    fun saveImage (fractalPanel: SelectablePanel){
        var returnValue = saveFileChooser.showSaveDialog(saveFileChooser)//Функция открытия диалогового окна «Сохранить файл»
        if(returnValue == JFileChooser.APPROVE_OPTION){ //выбор файла в диалоговом окне прошел успешно; выбранный файл можно получить методом getFile()
            try {
                ImageIO.write(newBI,"png",saveFileChooser.selectedFile)
                messageLabel.text = "Изображение успешно загружено"
            } catch (IOException ioe ){
                messageLabel.text = "Не удалось загрузить изображение"
            }
        }else {
            messageLabel.text = "Ошибка"
        }

    }
}