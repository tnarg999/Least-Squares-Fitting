import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        getUserPoints(s);
    }

    public static int[] getCoords(Scanner s) {
        System.out.print("x coordinate: ");
        int x = Integer.parseInt(s.next());
        System.out.print("y coordinate: ");
        int y = Integer.parseInt(s.next());
        int[] point = {x,y};
        return point;
    }

    public static ArrayList getUserPoints(Scanner s)  {
        ArrayList<int[]> allPoints = new ArrayList<int[]>();
        boolean seguir = true;
        while(seguir) {
            System.out.println("Stop?");
            String input = s.next();
            if(input.equals("stop")) {
                seguir = false;
            }
            if(seguir) {
                allPoints.add(getCoords(s));
            }
        }
        for(int[] array: allPoints) {
            for (Object o : array) {
                System.out.println(o);
            }
        }
        return allPoints;
    }
}
