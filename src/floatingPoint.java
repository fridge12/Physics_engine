public class floatingPoint {

    public double x;
    public double y;

    floatingPoint(double x, double y){
        this.x=x;
        this.y=y;
    }

    public void floatingPointAdd(floatingPoint a){
        x+=a.x;
        y+=a.y;
    }

}
