import java.awt.geom.Rectangle2D;

public class tricorn extends FractalGenerator {
    @Override
    public void getInitialRange(Rectangle2D.Double range) {//установка изначального диапазона
        range.x = -2;
        range.y = -1.5;
        range.width = 3;
        range.height = 3;
    }
    public int numIterations(double x, double y) {//считывается количество итераций
        //за количество которых числа уйдут в бесконечность
        ComplexNumber c = new ComplexNumber(x, y);
        ComplexNumber z = new ComplexNumber(0,0);
        int counter = 0;
        while (z.getSqrModule() < 4 && counter < MAX_ITERATIONS){
            z=z.conj();
            z = z.mult(z);
            z = z.plus(c);
            counter++;
        }

        if (counter == MAX_ITERATIONS)
            return -1;//переменная не ушла в бесконечность
        return counter;//за сколько?
    }
}