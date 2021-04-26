import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Fractal_Explorer {

    private int width;
    private int height;
    private JImageDisplay display;
    private static FractalGenerator fractalGenerator;
    private Rectangle2D.Double range;

    public Fractal_Explorer(int width, int height) {//конструктор окошечка с фрактальчикомм
        this.width = width;
        this.height = height;
        range = new Rectangle2D.Double();
        fractalGenerator = new Mandelbrot();
        fractalGenerator.getInitialRange(range);
    }


    final static Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();

    //create combobox and buttons
    JButton BUT_Reset = new JButton("Reset");//else IDE is angry :C
    JButton BUT_Save = new JButton("Save");
    String[] items = {"Maldebrot", "Tricorn", "Burning Ship"};
    JComboBox comboVombo = new JComboBox(items);

    public void UserInterface(){
        //default
        JFrame frame =  new JFrame("Fractals explorer");//рамочка
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        display = new JImageDisplay(width, height);//картинка в рамочке

        //buttons+mouse
        JPanel BUT_panel = new JPanel();
        display.addMouseListener(new MouseButtonClicked());//добавление возможности кликать на картинку
        frame.add(display, BorderLayout.CENTER);//добавляем картинку с возможностью кликать на рамку
        //frame.add(BUT_Reset, BorderLayout.SOUTH);
        BUT_Reset.addActionListener(new MyListener());
        BUT_Save.addActionListener(new MyListener());
        BUT_panel.add(BUT_Reset);
        BUT_panel.add(BUT_Save);
        frame.add(BUT_panel,BorderLayout.SOUTH);

        //combobox+label
        JPanel panel = new JPanel();
        JLabel lbl = new JLabel("Fractal: ");
        panel.add(lbl);
        panel.add(comboVombo);
        frame.add(panel,BorderLayout.NORTH);
        comboVombo.addActionListener(new MyListener());

        //default
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
    }

    private void drawFractal(FractalGenerator fractalGenerator){
        for (int x = 0; x < width; x++){
            for (int y = 0; y < height; y ++){
                //перевод координат приложухи в координаты комплексной плоскости, чтобы мы могли работать с фракталами
                double xCoord = fractalGenerator.getCoord(range.x, range.x+range.width, width, x);
                double yCoord = fractalGenerator.getCoord(range.y, range.y+range.height, height, y);

                //считаем количество итераций
                int numIters = fractalGenerator.numIterations(xCoord, yCoord);

                //отрисовываем изображение
                if (numIters == -1){
                    display.drawRGB(x, y, Color.BLACK.getRGB());
                }

                else { // задаем цвет для координаты где число ушло в бесконечность
                    float hue = 0.7f + (float)numIters / 200f;
                    int rgbColor = Color.HSBtoRGB(hue, 1f, 1f);
                    display.drawRGB(x, y, rgbColor);
                }
                display.repaint();
            }
        }
    }
    class MyListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            if(e.getSource()==BUT_Reset) {
               ResetAndDraw(fractalGenerator);
            }
            else if (e.getSource()==BUT_Save){
                BufferedImage image = display.buffer;// создаем картинку, которую будем сейвить от дисплея.
                JFileChooser chooser = new JFileChooser();// позволяет выбрать куда сохранять будем
                FileFilter filter = new FileNameExtensionFilter("PNG Images", "png"); //просим сейвить по пнгшке
                chooser.setFileFilter(filter);//для выбора файла устанавливаем фильтр
                chooser.setAcceptAllFileFilterUsed(false);//нельзя пользователю выбрать другие форматы


                int saving = chooser.showSaveDialog(null);//диалоговое окно, позволяющее выбрать файл
							// статус окошка
                //System.out.println(saving);
                if (saving==JFileChooser.APPROVE_OPTION){//методичка, начало сейва прошло успешно
                    File dir = chooser.getSelectedFile();//создаем файл дир
                    if (!(dir.toString().endsWith(".png"))){//проверка, заканчивается ли файл на .пнг
                        dir = new File(dir + ".png");//иначе добавляем пнг
                    }
                    try {
                        ImageIO.write(image,"png", dir);//записываю на комп image, в формате png, по dir
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                        JOptionPane.showMessageDialog(display,ioException.getMessage(),"Не можем сохранить изображение", JOptionPane.ERROR_MESSAGE);//вывод ошибки
                    }
                }
            }
            else if (e.getSource()==comboVombo){
                int item = comboVombo.getSelectedIndex();
                switch (item){
                    case 0:
                        if(fractalGenerator != new Mandelbrot()){
                            fractalGenerator = new Mandelbrot();
                            ResetAndDraw(fractalGenerator);
                        }
                        break;
                    case 1:
                        if(fractalGenerator != new tricorn()){
                            fractalGenerator = new tricorn();
                            ResetAndDraw(fractalGenerator);
                        }
                        break;
                    case 2:
                        if(fractalGenerator != new BurningShip())
                            fractalGenerator = new BurningShip();
                            ResetAndDraw(fractalGenerator);

                }
            }
        }
    }
    private void ResetAndDraw(FractalGenerator fractalGenerator){
        fractalGenerator.getInitialRange(range);//скинуть фрактал и отрисовать заново
        drawFractal(fractalGenerator);
    }
    
    class MouseButtonClicked implements MouseListener{
        public void mouseClicked(MouseEvent e){//увеличение картинки
            double xCoord = FractalGenerator.getCoord(range.x, range.x+range.width, width, e.getX());
            double yCoord = FractalGenerator.getCoord(range.y, range.y+range.height, width, e.getY());
            fractalGenerator.recenterAndZoomRange(range, xCoord, yCoord, 0.5);
            drawFractal(fractalGenerator);
        }
        public void mousePressed(MouseEvent e){};
        public void mouseReleased(MouseEvent e){};
        public void mouseEntered(MouseEvent e){};
        public void mouseExited(MouseEvent e){};
    }
    public static void main(String[] args){
        // Создание окошко, с определенной высотой и шириной
        Fractal_Explorer fractalExplorer = new Fractal_Explorer(800, 800);
        fractalExplorer.UserInterface();
        fractalExplorer.drawFractal(fractalGenerator);
    }
}

