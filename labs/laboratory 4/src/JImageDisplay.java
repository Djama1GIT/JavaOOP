import javax.swing.JComponent;
import java.awt.*;
import java.awt.image.BufferedImage;
//1
/*
* Создайте класс JImageDisplay, производный от
javax.swing.JComponent. Класс должен иметь одно поле с типом доступа
private, экземрляр java.awt.image.BufferedImage. Класс BufferedImage представляет
изображением, содержимое которого можно записать
* */
public class JImageDisplay extends JComponent {
    private final BufferedImage image;

    /*Конструктор принимает ширину и высоту*/
    public JImageDisplay(int w, int h){
        if (w <= 0)
            throw new IllegalArgumentException("w must be > 0; got " + w);

        if (h <= 0)
            throw new IllegalArgumentException("h must be > 0; got " + h);
        /*и инициализирует объект BufferedImage новым изображением с этой высотой и шириной и типом typeintrgb */
        image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);


        Dimension dimension = new Dimension(w, h);
        /* по условию конструктор должен вызвать то что ниже и поместить туда то, что выше */
        super.setPreferredSize(dimension);
        /*Так компонент включается в пользовательский интерфейс, он отобразит на экране все изображение*/
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // по условию из методички: чтобы все объекьы отображалось правилньо

        g.drawImage (image, 0, 0, image.getWidth(), image.getHeight(), null); // код из методички: рисует изображение в компоненте
    }
    /* по методичке: нада переполнить метод выше*/


    /*
    * Такэе нгам надо создаьл два метода для записи данны=ъ в изоюоражеие
    * */
    public void clearImage() { // все в черный красит
        Graphics2D imageGraphics = image.createGraphics();
        imageGraphics.setColor(new Color(0, 0, 0));

        imageGraphics.fillRect(0, 0, image.getWidth(), image.getHeight());
    }

    public void drawPixel (int x, int y, int rgbColor){
        image.setRGB(x, y, rgbColor);
    }
    // иьак плгяьтно что красит пиксель xy в цвет rgbColor


}
