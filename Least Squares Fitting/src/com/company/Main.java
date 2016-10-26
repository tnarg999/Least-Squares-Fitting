import Jama.Matrix;
import java.util.*;
import java.io.*;

public class Main {

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        //int polyDegree = getDegree(s);
        //ArrayList<double[]>userPoints = getUserPoints(s);
        int polyDegree = 2;
        double[][] coeffecientMatrix = new double[polyDegree+1][polyDegree+1];
        ArrayList<double[]> userPoints = fileIO();
        System.out.println("Size: " + userPoints.size());
        printArrayList(userPoints);
        for(int jj = 0; jj < polyDegree+1; jj++) {
            double[] row = new double[polyDegree+1];
            for(int ii = jj; ii < polyDegree+jj+1; ii++) {
                double element = 0;
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
            double element = 0;
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

        Matrix coefficient = new Matrix(coeffecientMatrix);
        Matrix results = new Matrix(resultMatrix);
        Matrix FINAL = coefficient.solve(results);

        printMatrix(FINAL);
        System.out.println(correlationCoefficient(userPoints, FINAL));

    }

    public static ArrayList<double[]> fileIO() {
        ArrayList<double[]> points = new ArrayList<double[]>();
        String directory = "C:\\Users\\Grant.Hugh\\Desktop\\Least-Squares-Fitting\\Least Squares Fitting\\";
        File f = new File(directory + "least_squares_sample2.txt");
        Scanner fileScan = null;
        try {
            fileScan = new Scanner(f);
        }
        catch(Exception e) {
            System.out.println(e);
        }
        while(fileScan.hasNextLine()) {
            String line = fileScan.nextLine();
            String[] stringPoint = line.split("\t");
            double[] point = new double[2];
            point[0] = Double.parseDouble(stringPoint[0]);
            point[1] = Double.parseDouble(stringPoint[1]);
            points.add(point);
        }
        return points;
    }

    public static double correlationCoefficient(ArrayList<double[]> userPoints, Matrix FINAL) {
        double xMean = meanOfSet(userPoints, 0);
        double yMean = meanOfSet(userPoints, 1);

        double top = 0;
        double bottom = 0;

        for(int ii = 0; ii < userPoints.size(); ii++) {
            top += Math.pow(userPoints.get(ii)[1] - convertFinal(FINAL, userPoints.get(ii)[0]), 2);
            bottom += Math.pow((userPoints.get(ii)[1] - yMean), 2);
        }

        return (1 - (top/bottom));
    }

    public static double convertFinal(Matrix m, double x) {
        double[] result = new double[m.getRowDimension()];
        for(int ii = 0; ii < m.getRowDimension(); ii++) {
            result[ii] = m.get(ii, 0);
        }
        double output = 0;
        for(int jj = 0; jj < result.length; jj++) {
            output += Math.pow(x, result.length-jj-1)*result[jj];
        }
        return output;
    }

    public static double meanOfSet(ArrayList<double[]> points, int coord) {
        double mean = 0;
        for(double[] point : points) {
            mean += point[coord];
        }
        return mean / points.size();
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
                System.out.print(o + " ");
            }
            System.out.println();
        }
    }
}
