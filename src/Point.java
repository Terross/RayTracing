public class Point {
    private double[] coordinates = new double[3];

    public Point() {

    }

    public Point(double x, double y, double z) {
        this.coordinates[0] = x;
        this.coordinates[1] = y;
        this.coordinates[2] = z;
    }

    public Point(double[] vec) {
        this.coordinates[0] = vec[0];
        this.coordinates[1] = vec[1];
        this.coordinates[2] = vec[2];
    }

    public void mul(double number) {
        coordinates = VectorMath.mult(coordinates, number);
    }

    public void minus(double[] vec) {
        coordinates = VectorMath.minus(coordinates, vec);
    }

    public double[] toVec() {
        return coordinates;
    }

    public int[] toIntVec() {
        return new int[]{(int) coordinates[0],
                         (int) coordinates[1],
                         (int) coordinates[2]
        };
    }
}
