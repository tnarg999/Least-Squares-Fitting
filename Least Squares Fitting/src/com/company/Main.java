//The two files that can be tested are least_squares_sample1.txt and least_squares_sample2.txt. If you want to test
//another tab separated text file, please put it in the same folder as the other two text files.

//There is basically zero error validation, so if you mess up typing the filename, it will throw an error and you will
//have to restart the program. Sorry for the inconvenience. 

import Jama.Matrix;
import java.util.*;
import java.io.*;

public class Main {

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int polyDegree = getDegree(s);
        double[][] coeffecientMatrix = new double[polyDegree+1][polyDegree+1];
        ArrayList<double[]> userPoints = fileIO(s);
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

        Matrix coefficient = new Matrix(coeffecientMatrix);
        Matrix results = new Matrix(resultMatrix);
        Matrix FINAL = coefficient.solve(results);

        if(coefficient.det() != 0) {
            System.out.println();
            functionForm(polyDegree);
            printMatrix(FINAL);
            System.out.println();
            System.out.println("R^2: " + correlationCoefficient(userPoints, FINAL));
        }
        else {
            System.out.println("Solution does not exist (determinant is zero). Try a different degree polynomial.");
        }

    }


    public static ArrayList<double[]> fileIO(Scanner s) {
        ArrayList<double[]> points = new ArrayList<double[]>();
        System.out.print("File Name: ");
        String fileName = s.next();
        File f = new File(fileName);
        Scanner fileScan = null;
        try {
            fileScan = new Scanner(f);
        } catch (Exception e) {
            System.out.println("File doesn't exist.");
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

    public static void functionForm(int x) {
        System.out.print("Your function will be in the form ");
        char co = 'A';
        for(int ii = x; ii >= 0; ii--) {
            System.out.print(co);
            if(ii != 0) {
                System.out.print("x^"+ii);
            }
            if(ii != 0) {
                System.out.print("+");
            }
            co = (char) (co+1);
        }
        System.out.println();
    }

    public static void printMatrix(Matrix m) {
        //System.out.println(m.getRowDimension() + "x" + m.getColumnDimension());
        char coName = 'A';
        for(int ii = 0; ii < m.getRowDimension(); ii++) {
            for(int jj = 0; jj < m.getColumnDimension(); jj++) {
                System.out.print(coName + ": " + m.get(ii, jj) + " ");
                coName = (char) (coName + 1);
            }
            System.out.println();
        }
    }

    public static int getDegree(Scanner s) {
        System.out.print("Polynomial Degree: ");
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

    public static void printArrayList(ArrayList<double[]> allPoints) {
        for(double[] array: allPoints) {
            for (Object o : array) {
                System.out.print(o + " ");
            }
            System.out.println();
        }
    }
}
