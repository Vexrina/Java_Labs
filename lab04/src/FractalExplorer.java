import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;

public class FractalExplorer {
    private int width;
    private int height;
    private JImageDisplay display;
    private FractalGenerator fractalGenerator;
    private Rectangle2D.Double range;

    public FractalExplorer(int width, int height) {
        this.width = width;
        this.height = height;
        range = new Rectangle2D.Double();
        fractalGenerator = new Mandelbrot();
        fractalGenerator.getInitialRange(range);
    }

    public void createAndShowGUI(){

        JFrame frame = new JFrame("Fractal Explorer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        display = new JImageDisplay(width,height);
        display.addMouseListener(new MouseButtonClicked());
        frame.add(display, BorderLayout.CENTER);

        JButton btnReset = new JButton("Reset");
        btnReset.addActionListener(new ButtonResetEventListener());
        frame.add(btnReset, BorderLayout.SOUTH);

        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
    }

    private void drawFractal(){
        for (int x = 0; x < width; x++){
            for (int y = 0; y < height; y ++){
                /* Translate screenX to xCoord from fractal space */
                double xCoord = FractalGenerator.getCoord(range.x, range.x+range.width, width, x);
                double yCoord = FractalGenerator.getCoord(range.y, range.y+range.height, height, y);

                /* Calculate number of iterations */
                int numIters = fractalGenerator.numIterations(xCoord, yCoord);

                /* Coloring image */
                if (numIters == -1){
                    display.drawPixel(x, y, Color.BLACK.getRGB());
                }

                else { // Original Lab Color
                    float hue = 0.7f + (float)numIters / 200f;
                    int rgbColor = Color.HSBtoRGB(hue, 1f, 1f);
                    display.drawPixel(x, y, rgbColor);
                }
//                else { // Black and white, only set
//                    display.drawPixel(x, y, Color.WHITE.getRGB());
//                }
                display.repaint();
            }
        }
    }

    class ButtonResetEventListener implements ActionListener {
        /* Reset range ans redraw */
        public void actionPerformed(ActionEvent e){
            fractalGenerator.getInitialRange(range);
            drawFractal();
        }
    }

    class MouseButtonClicked implements MouseListener {
        public void mouseClicked(MouseEvent e) {
            /* Translate screen coordinates */
            double xCoord = FractalGenerator.getCoord(range.x, range.x+range.width, width, e.getX());
            double yCoord = FractalGenerator.getCoord(range.y, range.y+range.height, height, e.getY());
            /* Move and scale fractal */
            fractalGenerator.recenterAndZoomRange(range, xCoord, yCoord, 0.5);
            /* Redraw */
            drawFractal();
        }
        public void mousePressed(MouseEvent e){};

        public void mouseReleased(MouseEvent e){};

        public void mouseEntered(MouseEvent e){};

        public void mouseExited(MouseEvent e){};
    }

    public static void main(String[] args){
        /* Initialize FractalExplorer with window size */
        FractalExplorer fractalExplorer = new FractalExplorer(800, 800);
        fractalExplorer.createAndShowGUI();
        fractalExplorer.drawFractal();
    }

}