import java.awt.*;

public class PixelColor {
    private double[] color = new double[3];

    public PixelColor () {

    }


    public PixelColor (Color color) {
        this.color[0] = color.getRed() ;
        this.color[1] = color.getGreen();
        this.color[2] = color.getBlue();
    }

    public void setColor(double[] color) {
        this.color = color;
    }

    public void setColor(Color color) {
        this.color[0] = color.getRed() ;
        this.color[1] = color.getGreen() ;
        this.color[2] = color.getBlue() ;
    }

    public int[] getArrayColor() {
        int[] result = new int[3];
        result[0] = (int) color[0] * 255;
        result[1] = (int) color[1] * 255;
        result[2] = (int) color[2] * 255;
        return result;
    }

    public double[] toVec() {
        return color;
    }

    public void mulToNumber(double number) {
        color = VectorMath.mult(color, number);
        double max = Math.max(color[0], Math.max(color[1], color[2]));
        if (max > 1){
            color = VectorMath.mult(color, 255.0/max);
        }
    }

    public Color getColor() {
        System.out.println(color[0] );
        return new Color((int) (color[0] ), (int)(color[0] ), (int) (color[0] ));

    }
}
