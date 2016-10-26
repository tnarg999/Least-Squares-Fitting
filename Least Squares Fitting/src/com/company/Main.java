import Jama.Matrix;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
//        int polyDegree = getDegree(s);
//        ArrayList<double[]>userPoints = getUserPoints(s);
        int polyDegree = 3;
        double[][] coeffecientMatrix = new double[polyDegree+1][polyDegree+1];
        ArrayList<double[]> userPoints = new ArrayList<double[]>();
        double[] point1 = {2, 1};
        double[] point2 = {3, 4};
        double[] point3 = {4, 6};
        double[] point4 = {-3, 5};
        userPoints.add(point1);
        userPoints.add(point2);
        userPoints.add(point3);
        userPoints.add(point4);

        for(int jj = 0; jj < polyDegree+1; jj++) {
            double[] row = new double[polyDegree+1];
            for(int ii = jj; ii < polyDegree+jj+1; ii++) {
                int element = 0;
                for(int kk = 0; kk < userPoints.size(); kk++) {
                    double x = userPoints.get(kk)[0];
                    element += Math.pow(x, 2*polyDegree - ii);
                }
                row[ii-jj] = element;
            }
            coeffecientMatrix[jj] = row;
        }

        for(double[] x : coeffecientMatrix) {
            for(double y : x) {
                System.out.print(y + " ");
            }
            System.out.println();
        }

        double[][] resultMatrix = new double[polyDegree+1][1];
        for(int ii = 0; ii < polyDegree + 1; ii++) {
            int element = 0;
            for(int jj = 0; jj < userPoints.size(); jj++) {
                double x = userPoints.get(jj)[0];
                double y = userPoints.get(jj)[1];
                element += Math.pow(x, polyDegree-ii)*y;
            }
            resultMatrix[ii][0] = element;
        }

        for(double[] p: resultMatrix) {
            for(double x : p) {
                System.out.println(x);
            }
        }

        Matrix coeffecient = new Matrix(coeffecientMatrix);
        Matrix results = new Matrix(resultMatrix);
        Matrix FINAL = coeffecient.solve(results);

        printMatrix(FINAL);
    }

    public static void printMatrix(Matrix m) {
        System.out.println(m.getRowDimension() + "x" + m.getColumnDimension());
        for(int ii = 0; ii < m.getRowDimension(); ii++) {
            for(int jj = 0; jj < m.getColumnDimension(); jj++) {
                System.out.print(m.get(ii, jj) + " ");
            }
            System.out.println();
        }
    }

    public static int getDegree(Scanner s) {
        System.out.println("Polynomial Degree: ");
        return s.nextInt();
    }

    public static double[] getCoords(Scanner s) {
        System.out.print("x coordinate: ");
        double x = s.nextFloat();
        System.out.print("y coordinate: ");
        double y = s.nextFloat();
        double[] point = {x,y};
        return point;
    }

    public static ArrayList getUserPoints(Scanner s)  {
        ArrayList<double[]> allPoints = new ArrayList<double[]>();
        boolean seguir = true;
        while(seguir) {
            System.out.println("Stop?");
            String input = s.next();
            if (input.equals("stop")) {
                seguir = false;
            }
            if (seguir) {
                allPoints.add(getCoords(s));
            }
        }
        //printArrayList(allPoints);
        return allPoints;
    }

    public static void printArrayList(ArrayList<double[]> allPoints) {
        for(double[] array: allPoints) {
            for (Object o : array) {
                System.out.println(o);
            }
        }
    }
}
