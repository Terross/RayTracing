public class Sphere {
    private double[] center = new double[]{0, 0, 0};
    private double radius;
    private Material material;

    public Sphere () {
        radius = 0;
    }

    public Sphere (double[] center, double radius, Material material) {
        this.center = center;
        this.radius = radius;
        this.material = material;
    }

    public double[] getCenter() {
        return center;
    }

    public void setCenter(double[] center) {
        this.center = center;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }
}
