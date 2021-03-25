import java.awt.*;

public class Material {
    private Color color;
    private double specular;
    private double reflective;
    public Material(Color color, double specular, double reflective) {
        this.color = color;
        this.specular = specular;
        this.reflective = reflective;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public double getSpecular() {
        return specular;
    }

    public void setSpecular(double specular) {
        this.specular = specular;
    }

    public double getReflective() {
        return reflective;
    }

    public void setReflective(double reflective) {
        this.reflective = reflective;
    }
}
