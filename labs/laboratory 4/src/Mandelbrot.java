import java.awt.geom.Rectangle2D;
//3

// cоздайте  подкласс фракталГенератор с именем этого файла. В нем обеспечиваем getInitialRange и numIterators
public class Mandelbrot extends FractalGenerator {
    public static final int LIMIT = 2000; // максимальное количество итераций - по методочике

    public void getInitialRange(Rectangle2D.Double range) {// метод позволяет генератору
        // фракталов определить наиболее интереснубю облась комплексной плоскости конкретного фрактала
        range.x = -2; // эти переменные - начальный диапазон
        range.y = -1.5;
        range.width = 3;
        range.height = 3;
    }

    public int numIterations(double x, double y) { // этот метод реализует итеративную функцию для фрактала мандаельборта
        ComplexNum cmplx = new ComplexNum(0, 0); // первое число реальная часть, вторая - мнимая
        // нам предложили сделать отдельный класс - ясделяль
        int iterator = 0; // переменная итератор

        while (iterator < LIMIT && cmplx.getSquaredModule() < 4) { ///////////////////// ComplexNum след файл
            cmplx.makeSquaredInPoint(x, y); // итерируемсяяяяяяяяяяя
            // в объект типа ComplexNum записываем что надо как надо при помощи метода выше

            iterator++;
        }

        if (iterator == LIMIT) return -1; // по методоичке

        return iterator;
    }
}
