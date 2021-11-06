import java.awt.*;
import java.util.LinkedList;

public class Sprite {

    public final static double gravity=10;

    //each sprite will have a gravity multiplyer so that each sprite can have different gravity
    public double gravityMultiplyer;

    //mass of sprite
    public double mass;

    //probably won't use, but just incase
    public double acceleration;

    //final vector for the
    public Vector vector;

    //position of sprite
    public Point position;

    //list of all forces acting on the sprite
    public LinkedList<Vector> forceList = new LinkedList<Vector>();


    Sprite(Point position, double mass, double gravityMultiplyer){
        this.position = position;
        this.mass= mass;
        this.gravityMultiplyer = gravityMultiplyer;
        Physics.spriteList.add(this);
    }

    Sprite(Point position, double mass){
        this.position = position;
        this.mass= mass;
        gravityMultiplyer = 1;
        Physics.spriteList.add(this);
    }


}
