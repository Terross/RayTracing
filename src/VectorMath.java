import java.awt.*;

public class VectorMath {
    public static double[] minus(double[] firstVec, double[] secondVec) {
        double[] result = new double[firstVec.length];
        for (int i = 0; i < firstVec.length; i++) {
            result[i] = firstVec[i] - secondVec[i];
        }
    return result;
    }

    public static double[] plus(double[] firstVec, double[] secondVec) {
        double[] result = new double[firstVec.length];
        for (int i = 0; i < firstVec.length; i++) {
            result[i] = firstVec[i] + secondVec[i];
        }
        return result;
    }

    public static double[] mult(double[] firstVec, double number) {
        double[] result = new double[firstVec.length];
        for (int i = 0; i < firstVec.length; i++) {
            result[i] = firstVec[i] * number;
        }
        return result;
    }

    public static double dot(double[] firstVec, double[] secondVec) {
        double result = 0.0;
        for (int i = 0; i < firstVec.length; i++) {
            result += firstVec[i]*secondVec[i];
        }
        return result;
    }


    public static double vecAbs(double[] vec) {
        return Math.sqrt(vec[0]*vec[0] + vec[1]*vec[1] + vec[2]*vec[2]);
    }

    public static  double[] normalization(double[] vec) {
        double result[] = new double[vec.length];
        double length = vecAbs(vec);
        for (int i = 0; i < vec.length; i++) {
            result[i] = vec[i]/length;
        }
        return result;
    }

    public static double[] preMinus(double[] vec) {
        double result[] = new double[vec.length];
        double length = vecAbs(vec);
        for (int i = 0; i < vec.length; i++) {
            result[i] = -vec[i];
        }
        return result;
    }

    public static Color toColor(double[] vec) {
        return new Color((int)vec[0],(int)vec[1],(int)vec[2] );
    }

    public static double[] findReflectedRay(double[] ray, double[] normal) {
        return minus(mult(normal, 2* dot(normal, ray)),ray);
    }

    public static double[] matrixToVecMul(double[][] matrix, double[] vec) {
        double[] result = new double[3];

        for (int i = 0; i < 3; i ++) {
            for (int j = 0; j < 3; j++) {
                result[i] += vec[j] * matrix[i][j];
            }
        }
        return result;
    }
}
