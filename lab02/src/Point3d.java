public class Point3d extends Point2d {
    //z coord
    private double zCoord;
    //initialization
    public Point3d(double x, double y, double z){
        this.setX(x);
        this.setY(y);
        zCoord = z;
    }
    //default construct
    public Point3d(){
        this(0,0,0);
    }
    //return Zcoord
    public double getZ(){
        return zCoord;
    }
    //set z
    public void setZ(double val){
        zCoord = val;
    }
    public static void main(String[] args){
        Point3d myDot = new Point3d(1,3,4);
        Point3d secDot = new Point3d();
        Point3d thirdDot = new Point3d();
        System.out.println(compareDots(myDot,secDot));
        System.out.println(compareDots(thirdDot,secDot));
        System.out.println(myDot.distanceTo(secDot));
    }
    public static boolean compareDots(Point3d d1, Point3d d2){
        return (d1.getX()==d2.getX()&&d1.getY()==d2.getY()&&d1.getZ()==d2.getZ());
    }
    public double distanceTo(Point3d d2){
        double distance = Math.sqrt(Math.pow(this.getX()-d2.getX(), 2)+Math.pow(this.getY()-d2.getY(), 2)
                +Math.pow(this.getZ()-d2.getZ(), 2));
        distance *=100;
        distance = Math.round(distance)/100.d;
        return distance;
    }
}
