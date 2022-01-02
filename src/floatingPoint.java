//this class is so that i can have poisitions with decimals
public class floatingPoint {

    //the x and y coordinates
    public double x;
    public double y;

    //constructor
    floatingPoint(double x, double y){
        this.x=x;
        this.y=y;
    }

    //this is for adding a point to the point that calls this  method
    public void floatingPointAdd(floatingPoint a){
        x+=a.x;
        y+=a.y;

    }

    //this is for adding 2 different floating points and returning the answer
    public floatingPoint floatingPointAdd(floatingPoint a, floatingPoint b){
        return new floatingPoint(a.x+b.x, a.y+b.y);
    }

}
