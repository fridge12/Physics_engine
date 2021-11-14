public class Vector {
    //vector will always start at the center point of an object

    //velocity/magnitude of vector
    public double magnitude;

    //
    public double xDirection;


    public double yDirection;

    //the time the vector will be affecting the sprite for, it is in millis
    //-1 will mean it lasts forever
    public long lifeTime;

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

    public floatingPoint vectorEndPoint(){

        return new floatingPoint(xDirection,yDirection);
    }

    Vector (double x, double y){

        xDirection = x;
        yDirection = y;
        magnitude = vectorMagnitude(x,y);
        lifeTime = -1;

    }

    Vector (double x, double y, long time){

        xDirection = x;
        yDirection = y;
        magnitude = vectorMagnitude(x,y);
        if(time<0) {
            lifeTime = -1;
        }
        else{
            lifeTime = time;
        }
    }

    public double vectorMagnitude(double x, double y){
        return Math.sqrt(((x*x)+(y*y)));
    }


    public Vector vectorSum (Vector a, Vector b){
        return new Vector(a.xDirection+b.xDirection,a.yDirection+b.yDirection);
    }

    public void addVector (Vector a){
        this.yDirection += a.yDirection;
        this.xDirection += a.xDirection;
        this.magnitude= this.vectorMagnitude(this.xDirection,this.yDirection);
    }

    public Vector vectorMultiply(double value){
        return new Vector(this.xDirection*value,this.yDirection*value, this.lifeTime);
    }



}
