import java.util.List;

public abstract class Light {
    protected double intensity;

    public Light(double intensity) {
        this.intensity = intensity;
    }

    public abstract double findLighting(Point point, Point normal,
                                        Point view, double specular, List<Sphere> spheres);
    protected double calculateLighting(double[] lightVec,
                                       Point normal, Point view,
                                       double specular, Scene.ClosestSphere closestSphere) {
        double result = 0.0;


        if (closestSphere.getClosetSphere() == null) {
            double normalDotLightVec = VectorMath.dot(normal.toVec(), lightVec);
            if (normalDotLightVec > 0) {
                result += intensity*normalDotLightVec/(VectorMath.vecAbs(normal.toVec()) * VectorMath.vecAbs(lightVec));
            }

            if (specular != -1) {
                Point R = new Point(VectorMath.findReflectedRay(lightVec, normal.toVec()));
                double rDotView = VectorMath.dot(R.toVec(), view.toVec());
                if (rDotView > 0) {
                    result += intensity*Math.pow(
                            rDotView/ (VectorMath.vecAbs(R.toVec()) * VectorMath.vecAbs(view.toVec())),
                            specular);
                }
            }
        }

        return result;
    }

    public double getIntensity() {
        return intensity;
    }

    public void setIntensity(double intensity) {
        this.intensity = intensity;
    }
}

class AmbientLight extends Light {
    public AmbientLight(double intensity) {
        super(intensity);
    }

    @Override
    public double findLighting(Point point, Point normal, Point view, double specular, List<Sphere> spheres) {
        return intensity;
    }
}

class PointLight extends Light {
    private Point position;
    public PointLight(double intensity, Point position) {
        super(intensity);
        this.position = position;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    @Override
    public double findLighting(Point point, Point normal, Point view, double specular, List<Sphere> spheres) {

        double[] lightVec = VectorMath.minus(position.toVec(), point.toVec());
        Scene.ClosestSphere closestSphere = Scene.findClosestIntersection( new Point(lightVec),point,spheres, 0.001, 1);
        return calculateLighting(lightVec, normal, view, specular, closestSphere);
    }
}

class DirectionalLight extends Light {
    private Point direction;
    public DirectionalLight(double intensity, Point direction) {
        super(intensity);
        this.direction = direction;
    }

    public Point getDirection() {
        return direction;
    }

    public void setDirection(Point direction) {
        this.direction = direction;
    }

    @Override
    public double findLighting(Point point, Point normal, Point view, double specular, List<Sphere> spheres) {
        double[] lightVec = direction.toVec();
        Scene.ClosestSphere closestSphere = Scene.findClosestIntersection( new Point(lightVec),point,spheres, 0.001, Double.MAX_VALUE);
        return calculateLighting(lightVec, normal, view, specular, closestSphere);
    }
}


