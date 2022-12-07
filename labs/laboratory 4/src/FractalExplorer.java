import java.awt.*;
import javax.swing.*;
import java.awt.geom.Rectangle2D;
import java.awt.event.*;
//4 пункт, ваши задачи:
/*
Создайте класс *етотЫ* позволяет исследовать различные обалсти фрактала, путем его создания, отображения через
графический интерфейс Swing и обработки событий, вызванныъ взаимодействием приложения с пользователем
FractalExplorer состоит из Jframe, который в совю очередьб содержит объект JImageDisplay, котоый отображает фрактрал
и объект JButton для соброса изображдения, неоюбхожиоый для отображеиея целого фырактала
* */
public class FractalExplorer {
    private int displaySize; // ширина и высота в пикселях //// по методочике
    private JImageDisplay display; // для обновления отображения в разных методах в процессе вычисления фрактала
    private FractalGenerator fractal; // будет использоватьсая ссылка на базовй класс для отображения других фрактоалов в будущем
    private Rectangle2D.Double range; // указывает комплексный диапазаон комплексной плоскости, которая выводится на экран

    // private - по методичке

    public FractalExplorer(int size) { // у класса должен быть констуктор
        displaySize = size; // который принимает значение размера изображения (аргумент)
        //сохряняет значение в поле

        fractal = new Mandelbrot(); // инициализирует объекты фрактального генератора и
        range = new Rectangle2D.Double(); // диапазона

        fractal.getInitialRange(range); // инициализирует обекъты фрактоального генератора
        display = new JImageDisplay(displaySize, displaySize); // тоже, тут создается дисплей размером displaySize*displaySize
    }

    public void createAndShowGUI() { // инициализация графического интерфейса Swing: JFrame
        display.setLayout(new BorderLayout());
        JFrame myframe = new JFrame("Fractal Explorer"); // даем окну подходящий заголовое

        myframe.add(display, BorderLayout.CENTER); // содержащий объекты JImageDisplay (display)
        //используем border layout (center - плзиция) для содержимого окна
        JButton resetButton = new JButton("Reset Display"); // и кнопку для сброса изображения

        Resetter handler = new Resetter(); // ресеттер описан ниэе
        resetButton.addActionListener(handler); // для кнопки создаем дейставие - ресет

        myframe.add(resetButton, BorderLayout.SOUTH);
        //кнопка эе в своб очередь в позиции south
        Clicker click = new Clicker(); // действие для клика, метод опиан ниже
        display.addMouseListener(click); // действие для клика

        myframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // обеспечиваем операвцию закрыытия окна по умолчанию

        myframe.pack(); /////// по методичке // правильное расположение содержимого окна
        myframe.setVisible(true); // сделают видимым
        myframe.setResizable(false); // и неизменяемый размер

    }

    private void drawFractal() { // реализуйте вспомогательный метод для вывода на экран фрактала
        for (int x = 0; x < displaySize; x++) { // этот метод циклически проходит по пикселям в отображении от 0 до размера х
            for (int y = 0; y < displaySize; y++) { // y

                double xCoord = FractalGenerator.getCoord(range.x, // range - комплесный диапазон плоскости
                        range.x + range.width, displaySize, x); // по методичке

                double yCoord = FractalGenerator.getCoord(range.y,
                        range.y + range.height, displaySize, y);

                int iteration = fractal.numIterations(xCoord, yCoord); //получаем количество итераций

                if (iteration == -1) { // если -1
                    display.drawPixel(x, y, 0); // то устанавливаем пиксель в черный цвет
                } else { // иначе цвет на основе количества итераций
                    float hue = 0.5f + (float) iteration / 50; ////////// фрагмент из методички
                    int rgbColor = Color.HSBtoRGB(hue, 1f, 1f);//////////

                    display.drawPixel(x, y, rgbColor); // рисуем пиксель в цвет выше
                }

            }
        }
        display.repaint(); // после отрисовки обновляем JImageDisplay
    }

    private class Resetter implements ActionListener // обработка кнопки сброса
    {
        public void actionPerformed(ActionEvent e)
        {
            fractal.getInitialRange(range); // сброс диапазона к начальному
            drawFractal(); // перерисуем фрактал
        }
    }

    private class Clicker extends MouseAdapter // по методичке идем как и раньше.............
    {
        @Override
        public void mouseClicked(MouseEvent e)
        {
            int x = e.getX(); // х клика
            double xCoord = FractalGenerator.getCoord(range.x,
                    range.x + range.width, displaySize, x);

            int y = e.getY(); // y клика
            double yCoord = FractalGenerator.getCoord(range.y,
                    range.y + range.height, displaySize, y);

            fractal.recenterAndZoomRange(range, xCoord, yCoord, 0.5); // масштабируем на 0,5 при клике

            drawFractal(); // перерисовывеам
        }
    }

    public static void main(String[] args)
    {
        FractalExplorer displayExplorer = new FractalExplorer(800); // инициализация нового класса с размером 800
        displayExplorer.createAndShowGUI(); // вызываем по методичке
        displayExplorer.drawFractal(); // вызываем для отображения начального представления
    }
}