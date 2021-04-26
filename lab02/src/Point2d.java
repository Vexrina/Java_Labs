public class Point2d {
    //x coord
    private double xCoord;
    //y coord
    private double yCoord;
    //initialization
    public Point2d(double x, double y){
        xCoord = x;
        yCoord = y;
    }
    //default construct
    public Point2d(){
        this(0,0);
    }
    //return Xcoord
    public double getX(){
        return xCoord;
    }
    //return Ycoord
    public double getY(){
        return yCoord;
    }
    //set X and Y coord
    public void setX(double val){
        xCoord = val;
    }
    public void setY(double val){
        yCoord = val;
    }
    public static void main(String[] args){
        Point2d myPoint = new Point2d();
        Point2d myOtherPoint = new Point2d(5,3);
        Point2d aThirdPoint = new Point2d();
        System.out.println(aThirdPoint==myPoint);
        System.out.println(compareDots(aThirdPoint, myPoint));
    }
    public static boolean compareDots(Point2d d1, Point2d d2){

        return (d1.getX()==d2.getX()&&d1.getY()==d2.getY());
    }
}
