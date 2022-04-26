public class Vector {
    //vector will always start at the center point of an object

    public static int totalVectors;

    //velocity/magnitude of vector
    public double magnitude;

    //the x component of the vector
    public double xDirection;

    //the y component of the vector
    public double yDirection;

    //the time the vector will be affecting the sprite for, it is in millis
    //-1 will mean it lasts forever
    public long lifeTime;

    //name of the vector
    public String name;

    public static final Vector nullVector = new Vector(0,0,0);

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

    //returns the end point of the vector relative to its starting point
    public floatingPoint getVectorEndPoint(){

        return new floatingPoint(xDirection,yDirection);
    }

    //constructor with only x and y
    Vector (double x, double y){
        this(x,y,-1,"Vector "+totalVectors);

    }

    //constructor with x y and name
    Vector (double x, double y, String name){
        this(x,y,-1,name);
    }

    //constructor with x y and time
    Vector (double x, double y, long time){
        this(x,y,time,"Vector "+totalVectors);

    }

    //constructor with x y time and name
    Vector (double x, double y,long time, String name){

        xDirection = x;
        yDirection = y;
        magnitude = getVectorMagnitude(x,y);
        if(time<0) {
            lifeTime = -1;
        }
        else{
            lifeTime = time;
        }

        this.name = name;
        totalVectors++;
    }

    //returns the magnitude of the vector
    public double getVectorMagnitude(double x, double y){
        return Math.sqrt(((x*x)+(y*y)));
    }


    //adds 2 vectors, haven't used it yet
    public static Vector vectorSum (Vector a, Vector b){
        return new Vector(a.xDirection+b.xDirection,a.yDirection+b.yDirection);
    }

    //adds a vectors to the vector that calls this method
    public void addVector (Vector a){
        this.yDirection += a.yDirection;
        this.xDirection += a.xDirection;
        this.magnitude= this.getVectorMagnitude(this.xDirection,this.yDirection);
    }

    //multiplies the vector with a given value
    public Vector vectorMultiply(double value){
        return new Vector(this.xDirection*value,this.yDirection*value, this.lifeTime);
    }



}
