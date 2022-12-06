public class ComplexNum {
    public double rl; // ейств
    public double im; // мним

    public ComplexNum(double rl, double im){
        this.rl = rl;
        this.im = im;
    }

    public double getSquaredModule() {
        return (this.rl * this.rl + this.im * this.im);
    } ////////////////////
    // в названии всё - квадрат модуля

    public void makeSquaredInPoint(double x, double y) {
        double real = (rl * rl) - (im * im) + x;
        double imagine = 2 * rl * im + y; // ничего не поняо, но вот википедия и тут я поняв
        // https://ru.wikipedia.org/wiki/Множество_Мандельброта
        // тут написано как происходит итеративная последовательность

        rl = real;
        im = imagine;
    }
}