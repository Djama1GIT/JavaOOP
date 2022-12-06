import java.awt.*;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.geom.Rectangle2D;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class FractalExplorer {
    private int displaySize;
    private JImageDisplay display;
    private FractalGenerator fractal;
    private Rectangle2D.Double range;

    public FractalExplorer(int size) {
        displaySize = size;

        fractal = new Mandelbrot();
        range = new Rectangle2D.Double();

        fractal.getInitialRange(range);
        display = new JImageDisplay(displaySize, displaySize);
    }

    public void createAndShowGUI() {
        display.setLayout(new BorderLayout());

        JButton resetButton = new JButton("Reset"); // добавлем кнопку ресет
        Resetter resetHandler = new Resetter();
        resetButton.addActionListener(resetHandler);

        JButton saveButton = new JButton("Save"); // и сейв
        Saver saveHandler = new Saver();
        saveButton.addActionListener(saveHandler);

        Clicker click = new Clicker();
        display.addMouseListener(click);

        FractalGenerator mandelbrotFractal = new Mandelbrot(); // как в 4 лабе, но теперь три генератора
        FractalGenerator tricornFractal = new Tricorn();
        FractalGenerator burningShipFractal = new BurningShip();

        JComboBox comboBox = new JComboBox(); // создаем comboBox

        comboBox.addItem(mandelbrotFractal); // и выборка через него фракталов идет
        comboBox.addItem(tricornFractal);
        comboBox.addItem(burningShipFractal); // тут типа вызываются эжти туСтринг
        // в кажом файле фрактала есть метод toString(по методичке) для нормального отображения всего этого дела
        Chooser fractalChooser = new Chooser();
        comboBox.addActionListener(fractalChooser);

        JLabel label = new JLabel("Fractal:");

        JPanel panel = new JPanel();
        panel.add(label);
        panel.add(comboBox); // по методичке создаем JPanel и добавляем в него JLabel и JComboBox
        //то была верхняя, теперь нижняя панель
        JPanel myBottomPanel = new JPanel();
        myBottomPanel.add(saveButton); // на ней сейв и ресет
        myBottomPanel.add(resetButton);

        JFrame myFrame = new JFrame("Fractal Explorer"); // создаем окно

        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // и суем туда сё, описываем тута закрывание и ниже как в прошлой лабе
        myFrame.add(myBottomPanel, BorderLayout.SOUTH); // располагаем как нпаисано в методичке
        myFrame.add(display, BorderLayout.CENTER);
        myFrame.add(panel, BorderLayout.NORTH);

        myFrame.pack();
        myFrame.setVisible(true);
        myFrame.setResizable(false);
    }

    private void drawFractal() {
        for (int x = 0; x < displaySize; x++) {
            for (int y = 0; y < displaySize; y++) {

                double xCoord = FractalGenerator.getCoord(range.x,
                        range.x + range.width, displaySize, x);

                double yCoord = FractalGenerator.getCoord(range.y,
                        range.y + range.height, displaySize, y);

                int iteration = fractal.numIterations(xCoord, yCoord);

                if (iteration == -1) {
                    display.drawPixel(x, y, 0);
                } else {
                    float hue = 0.5f + (float) iteration / 50;
                    int rgbColor = Color.HSBtoRGB(hue, 1f, 1f);

                    display.drawPixel(x, y, rgbColor);
                }

            }
        }
        display.repaint();
    }

    private class Resetter implements ActionListener { // ресеттер как в прошлой лабе
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals("Reset")) {
                fractal.getInitialRange(range);
                drawFractal();
            }
        }
    }

    private class Chooser implements ActionListener { // выборка фракталов
        public void actionPerformed(ActionEvent e) {
            Object source = e.getSource();
            if (source instanceof JComboBox) {
                JComboBox comboBox = (JComboBox) source;

                fractal = (FractalGenerator) comboBox.getSelectedItem();
                assert fractal != null;

                fractal.getInitialRange(range);
                drawFractal();
            }
        }
    }

    private class Saver implements ActionListener { // сейвер оаоаоаоаоаоаоаоаоаоаоаоаоаа
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals("Save")) { // нажал сохранить -> сохранил. xD
                JFileChooser fileChooser = new JFileChooser(); // почти все в методичке расписано
                ////////////////

                FileFilter extensionFilter = new FileNameExtensionFilter(
                        "PNG",
                        "png"
                );

                fileChooser.setFileFilter(extensionFilter);

                fileChooser.setAcceptAllFileFilterUsed(false);
                ////////////////// код напрямую из методички
                int userSelection = fileChooser.showSaveDialog(display); // английским понятным написано, showDialog
                //открывает окно сохранения
                if (userSelection == JFileChooser.APPROVE_OPTION) { // если все нормально выбрал, то идем дальше
                    java.io.File file = fileChooser.getSelectedFile(); // файл
                    String filePath = file.getPath(); // путь к файлу.... логично

                    if (!filePath.contains(".png")) file = new File(filePath + ".png"); // должен быть пнг, тут проверяется
                    try {
                        BufferedImage displayImage = display.getImage(); // получаем изображение
                        javax.imageio.ImageIO.write(displayImage, "png", file); // записываем изображение
                    } catch (Exception exception) { // если случилось нехорошее, то
                        JOptionPane.showMessageDialog(display, // отображаем ошибку
                                exception.getMessage(), "Cannot Save Image",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
                else return; // если не выбрал то не сохраняем, логично
            }
        }
    }

    private class Clicker extends MouseAdapter { // аналогично с прошлой лабой
        @Override
        public void mouseClicked(MouseEvent e) {
            int x = e.getX();
            double xCoord = FractalGenerator.getCoord(range.x,
                    range.x + range.width, displaySize, x);

            int y = e.getY();
            double yCoord = FractalGenerator.getCoord(range.y,
                    range.y + range.height, displaySize, y);

            fractal.recenterAndZoomRange(range, xCoord, yCoord, 0.5);

            drawFractal();
        }
    }

    public static void main(String[] args) // аналог
    {
        FractalExplorer displayExplorer = new FractalExplorer(800);
        displayExplorer.createAndShowGUI();
        displayExplorer.drawFractal();
    }
}