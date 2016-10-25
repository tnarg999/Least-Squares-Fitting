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
        double[] point3 = {2, 6};
        userPoints.add(point1);
        userPoints.add(point2);
        userPoints.add(point3);

        for(int jj = 0; jj < polyDegree+1; jj++) {
            double[] row = new double[polyDegree+1];
            for(int ii = jj; ii < polyDegree+jj+1; ii++) {
                int element = 0;
                for(int kk = 0; kk < userPoints.size(); kk++) {
                    double x = userPoints.get(kk)[0];
                    if(ii != 2*polyDegree) {
                        element += Math.pow(x, 2*polyDegree - ii);
                    }
                    else {
                        element = 1;
                    }
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

        double[] resultMatrix = new double[polyDegree+1];
        for(int ii = 0; ii < polyDegree + 1; ii++) {
            int element = 0;
            for(int jj = 0; jj < userPoints.size(); jj++) {
                double x = userPoints.get(jj)[0];
                double y = userPoints.get(jj)[1];
                element += Math.pow(x, polyDegree-ii)*y;
            }
            resultMatrix[ii] = element;
        }

        for(double p: resultMatrix) {
            System.out.println(p);
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
