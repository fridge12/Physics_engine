public class Vector {
    //vector will always start at the center point of an object

    //velocity/magnitude of vector
    public double magnitude;

    //
    public double xDirection;


    public double yDirection;

    //I created getter methods but I don't think I need them anymore
    /*
    //getter methods
    public double getMagnitude() {
        return magnitude;
    }

    public double getxDirection() {
        return xDirection;
    }

    public double getyDirection() {
        return yDirection;
    }
    */

    Vector (double x, double y){

        xDirection = x;
        yDirection = y;
        magnitude = vectorMagnitude(x,y);

    }

    public double vectorMagnitude(double x, double y){
        return Math.sqrt(((x*x)+(y*y)));
    }


    public Vector vectorAdd (Vector a, Vector b){
        return new Vector(a.xDirection+b.xDirection,a.yDirection+b.yDirection);
    }

}
