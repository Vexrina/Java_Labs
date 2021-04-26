import java.awt.geom.Rectangle2D;

class BurningShip extends FractalGenerator {

    @Override
    public void getInitialRange(Rectangle2D.Double range) {//установка изначального диапазона
        range.x = -2;
        range.y = -2.5;
        range.width = 3.25;
        range.height = 3.5;
    }

    @Override
    public int numIterations(double x, double y) {//считывается количество итераций
        //за количество которых числа уйдут в бесконечность
        ComplexNumber c = new ComplexNumber(x, y);
        ComplexNumber z = new ComplexNumber(0, 0);
        int counter = 0;
        double Re, Im;
        double temp;
        while (z.getSqrModule() < 4 && counter < MAX_ITERATIONS) {
            z = new ComplexNumber(Math.abs(z.getRe()), Math.abs(z.getIm()));
            z = z.mult(z);
            z = z.plus(c);
            counter++;
        }

        if (counter == MAX_ITERATIONS)
            return -1;//переменная не ушла в бесконечность
        return counter;//за сколько?
    }
}
