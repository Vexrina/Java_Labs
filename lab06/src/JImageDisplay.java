import java.awt.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;

public class JImageDisplay extends JComponent {

    public BufferedImage buffer;
    public JImageDisplay(int w, int h){
        buffer = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);//методичка
        super.setPreferredSize(new java.awt.Dimension(w, h));//делает определенное разрешение картинки
        // в зависимости от выбранных высоты и ширины
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);//super == указатель на родительский класс
        g.drawImage(buffer, 0, 0, buffer.getWidth(), buffer.getHeight(), null);//вызывается метод род. класса
    }
    public void clearImage(){
        for (int i = 0; i<buffer.getWidth();i++){
            for (int j = 0; j<buffer.getHeight();j++){
                drawRGB(i,j,0);
            }
        }
    }
    public void drawRGB(int Wpxl, int Hpxl, int Color){
        buffer.setRGB(Wpxl,Hpxl,Color);//пиксель на [Wpxl, Hpxl] получает Color цвет
    }
}