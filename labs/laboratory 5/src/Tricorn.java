import java.awt.geom.Rectangle2D;
//Лаба 5 реализация почти идентичная мандельборту, кроме двух изменений. всё то же самое кроме
public class Tricorn extends FractalGenerator {
    public static final int LIMIT = 2000;

    public void getInitialRange(Rectangle2D.Double range) {
        range.x = -2; // другие значения начального диапазона
        range.y = -2;
        range.width = 4;
        range.height = 4;
    }

    public int numIterations(double x, double y) {
        ComplexNum cmplx = new ComplexNum(0, 0);
        int iterator = 0;

        while (iterator < LIMIT && cmplx.getSquaredModule() < 4) {
            cmplx.makeSquaredWithConjInPoint(x, y); // тут другое уравнение, согласно методичке

            iterator++;
        }

        if (iterator == LIMIT) return -1;

        return iterator;
    }

    @Override
    public String toString() { return "Tricorn"; }
}
