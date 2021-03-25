import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Scene {
    private static double kw = 1; //Размер "рамки" по х
    private static double kh = 1; //Размер "рамки" по у
    private static double kz = 1; //Расстояние "рамки" от камеры по z
    private int weight;
    private int height;
    private BufferedImage bufferedImage;
    private Point camera;
    private List<Sphere> spheres = new ArrayList<>();
    private List<Light> lights = new ArrayList<>();
    private double[][] rotationMatrix =
            {{0.7, 0,-0.7},
             {0, 1, 0},
             {0.7, 0, 0.7}};

    //Устанавливаем размер сцены и камеру
    private Scene(int weight, int height, Point camera) {
        this.weight = weight;
        this.height = height;
        this.camera = camera;
        bufferedImage = new BufferedImage(weight, height, BufferedImage.TYPE_INT_RGB);
    }

    public static Scene getInstance(int weight, int height, Point camera) {
        return new Scene(weight, height, camera);
    }

    //Красим пиксели в белый, если луч не пересекает сферу
    //Красим в цвет сферы, если пересекает
    private Color traceRay(Point camera,Point ray, int recursion, double xMin, double xMax) {
        ClosestSphere closestSphereData = findClosestIntersection(ray,  camera, this.spheres, xMin, xMax);
        Sphere closetSphere = closestSphereData.closetSphere;
        double closetX = closestSphereData.closestX;

        if (closetSphere == null) {
            return new Color(83, 68, 68);
        }
        Point crossing = new Point(VectorMath.plus(camera.toVec(),
                VectorMath.mult(ray.toVec(), closetX)));
        Point normal = new Point(VectorMath.minus(crossing.toVec(), closetSphere.getCenter()));
        normal = new Point(VectorMath.normalization(normal.toVec()));
        double lighting = countAllLights(crossing, normal, new Point(VectorMath.preMinus(ray.toVec()))
                , closetSphere.getMaterial().getSpecular());
        double[] color = (new PixelColor(closetSphere.getMaterial().getColor()).toVec()); //Цвет нужной окружности превращаем в массив
        color = VectorMath.mult(color, lighting);

        double sphereReflective = closetSphere.getMaterial().getReflective();

        if (recursion <= 0 || sphereReflective <= 0) {
            repairColor(color);
            return VectorMath.toColor(color);
        }
        //System.out.println(sphereReflective);
        Point reflectedRay = new Point(VectorMath.findReflectedRay(VectorMath.preMinus(ray.toVec()), normal.toVec()));
        Color reflectedColor = traceRay(crossing, reflectedRay,recursion - 1, 0.001, Double.MAX_VALUE);
        double[] reflectedColorArray = new PixelColor(reflectedColor).toVec();
        double[] finalColor = VectorMath.plus(VectorMath.mult(color, (1 - sphereReflective)),
                VectorMath.mult(reflectedColorArray, sphereReflective));
        repairColor(finalColor);
        return VectorMath.toColor(finalColor);
    }

    public static ClosestSphere findClosestIntersection(Point point,Point camera, List<Sphere> spheres, double xMin, double xMax) {
        //Выбор сферы, которую видим
        Sphere closetSphere = null;
        QuadEquation quadEquation;
        double closetX = Double.MAX_VALUE;
        for (Sphere sphere : spheres) {
            quadEquation = intersectRay(point, sphere, camera, xMin, xMax); //Находим точки пересечения
            if (quadEquation.x1 < closetX && quadEquation.checkX1()) {
                closetX = quadEquation.x1;
                closetSphere = sphere;
            }
            if (quadEquation.x2 < closetX && quadEquation.checkX2()) {
                closetX = quadEquation.x2;
                closetSphere = sphere;
            }
        }
        return new ClosestSphere(closetSphere, closetX);
    }

    private double[] repairColor(double[] color) {
        double max = Math.max(color[0], Math.max(color[1], color[2]));
        if (max > 255) {
            color[0] *= 255/max;
            color[1] *= 255/max;
            color[2] *= 255/max;
        }
        return  color;
    }
    private double countAllLights(Point crossing, Point normal, Point view, double specular) {
        double result = 0.0;
        for (Light light:
                lights) {
            result += light.findLighting(crossing, normal, view, specular, spheres);
        }
        return result;
    }


    //Решение квадратного уравнения
    private static QuadEquation intersectRay(Point point, Sphere sphere, Point camera, double xMin, double xMax) {
        double[] sphereCenter = sphere.getCenter(); //Получаем центр сферы
        double sphereRadius = sphere.getRadius(); //Получаем ее радиус
        double[] cameraCenterVec = VectorMath.minus(camera.toVec(), sphereCenter); //Находим вектор от камеры до центра сферы

        //Коэффициента квадратного уравнения
        double a = VectorMath.dot(point.toVec(), point.toVec());
        double b = 2 * VectorMath.dot(cameraCenterVec, point.toVec());
        double c = VectorMath.dot(cameraCenterVec, cameraCenterVec) - sphereRadius*sphereRadius;

        double d = b*b - 4*a*c; //Дискрименант
        if (d < 0) {
            return new QuadEquation(Double.MAX_VALUE, Double.MIN_VALUE, xMin, xMax);
        } else {
            return new QuadEquation((-b + Math.sqrt(d)) / (2 * a),
                    (-b - Math.sqrt(d)) / (2 * a), xMin, xMax);
        }
    }

    public BufferedImage getBufferedImage() {
        Color color = null;
        Point direction = null;
        Point cameraPosition = null;
        for (int i = -weight/2; i < weight/2; i ++) {
            for (int j = -height/2; j < height/2; j ++) {
                double x = (double)i / weight * kw;
                double y = (double)j / height * kh;
                direction = new Point(x,y,kz);

                cameraPosition = new Point(VectorMath.matrixToVecMul(rotationMatrix, camera.toVec()));
                color = traceRay(cameraPosition,
                         direction,5,
                        1, Double.MAX_VALUE);
                bufferedImage.setRGB(weight / 2 + i,height / 2 - j - 1, color.getRGB());
            }
        }
        return bufferedImage;
    }

    public void addSphere(Sphere sphere) {
        spheres.add(sphere);
    }

    public void addLight(Light light) {
        lights.add(light);
    }


    //Класс квадратного уравнения
    public static class QuadEquation {
        private final double x1;
        private final double x2;
        private final double xMin;
        private final double xMax;

        public QuadEquation(double x1, double x2, double xMin, double xMax) {
            this.x1 = x1;
            this.x2 = x2;
            this.xMin = xMin;
            this.xMax = xMax;
        }

        boolean checkX1() {
            return x1 > xMin && x1 < xMax;
        }

        boolean checkX2() {
            return x2 > xMin && x2 < xMax;
        }
    }

    public static class ClosestSphere {
        private final Sphere closetSphere;
        private final double closestX;

        public ClosestSphere(Sphere closetSphere, double closestX) {
            this.closetSphere = closetSphere;
            this.closestX = closestX;
        }

        public Sphere getClosetSphere() {
            return closetSphere;
        }

        public double getClosestX() {
            return closestX;
        }
    }
}
