import java.util.Scanner;
public class Lab1 {
    public static void main(String args[]){
        Scanner in = new Scanner(System.in);
        Point3d d1 = new Point3d(in.nextDouble(),in.nextDouble(),in.nextDouble());
        Point3d d2 = new Point3d(in.nextDouble(),in.nextDouble(),in.nextDouble());
        Point3d d3 = new Point3d(in.nextDouble(),in.nextDouble(),in.nextDouble());
        if(Point3d.compareDots(d1,d2)||Point3d.compareDots(d2,d3)||Point3d.compareDots(d1,d3))
            System.out.println("Some dots is equaled");
        else
            System.out.println(computerArea(d1,d2,d3));
    }
    public static double computerArea(Point3d A, Point3d B, Point3d C){
        double a,b,c;
        a=B.distanceTo(C);
        b=A.distanceTo(C);
        c=B.distanceTo(A);
        double p=a+b+c;
        p/=2;
        double area=Math.sqrt(p*(p-a)*(p-b)*(p-c));
        return area;
    }
}
