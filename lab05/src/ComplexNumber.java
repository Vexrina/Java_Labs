public class ComplexNumber {
    //мнимое число состоит из двух частей поэтому
    public double re;//реальная часть числа
    public double im;//мнимая часть числа

    public ComplexNumber(double re, double im) {
        this.re = re;
        this.im = im;
    }

    public double getRe() {
        return re;
    }

    public double getIm() {
        return im;
    }

    public double getSqrModule() {
        return re * re + im * im;
    }

    public ComplexNumber plus(ComplexNumber complexNumber) {
        return new ComplexNumber(this.re + complexNumber.re, this.im + complexNumber.im);
    }

    public ComplexNumber mult(ComplexNumber complexNumber) {
        return new ComplexNumber(this.re * complexNumber.re - this.im * complexNumber.im,
                this.im * complexNumber.re + this.re * complexNumber.im);
    }

    public ComplexNumber conj() {
        return new ComplexNumber(this.re, this.im * (-1));
    }
}