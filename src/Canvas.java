import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Canvas extends JPanel {
    private Scene scene;

    //Настройка сцены
    public Canvas() {
        scene = Scene.getInstance(900,900, new Point(-4,1,-5));


        scene.addSphere(new Sphere(new double[] {2, 0, 3.1},
                1.5, new Material(new Color(0, 0, 255), 10, 0.3)));

        scene.addSphere(new Sphere(new double[] {-2, 0, 4},
                1, new Material(new Color(0, 255, 0), 10, 0.4)));

        scene.addSphere(new Sphere(new double[] {0, -1, 3},
                1, new Material(new Color(255, 0, 0), 500, 0.2)));
        scene.addSphere(new Sphere(new double[] {-1,0, 2},
               1, new Material(new Color(255, 255, 0), 200, 0.5)));
        scene.addSphere(new Sphere(new double[] {0, 5, 3},
                2, new Material(new Color(1, 253, 224), 2000, 0.6)));
        scene.addSphere(new Sphere(new double[] {0, -5001, 0},
                5000, new Material(new Color(241, 246, 4), 1000, 0.5)));

        scene.addLight(new AmbientLight(0.2));
        scene.addLight(new PointLight(0.6, new Point(2, 1, 0)));
        scene.addLight(new DirectionalLight(0.2, new Point(1, 4, 4)));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        BufferedImage bufferedImage = scene.getBufferedImage();
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(bufferedImage, null, null);
    }
}
