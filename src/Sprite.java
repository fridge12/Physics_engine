import java.awt.*;
import java.util.LinkedList;

public class Sprite {



    //each sprite will have a gravity multiplyer so that each sprite can have different gravity
    public double gravityMultiplyer;

    //mass of sprite
    public double mass;

    //probably won't use, but just incase
    public double acceleration;

    //final vector for the sprite
    public Vector spriteVector = new Vector(0,0);

    //position of sprite, from center
    public floatingPoint position;

    //it is for the radius or length/2 of the sprite
    public double radius;

    //list of all forces acting on the sprite
    public LinkedList<Vector> forceList = new LinkedList<Vector>();


    Sprite(floatingPoint position, double mass, double radius,double gravityMultiplyer){
        this.position = position;
        this.mass= mass;
        this.radius= radius;
        this.gravityMultiplyer = gravityMultiplyer;
        Physics.spriteList.add(this);
        forceList.add(Physics.gravity.vectorMultiply(gravityMultiplyer));
    }

    Sprite(floatingPoint position, double mass, double radius){
        this.position = position;
        this.mass= mass;
        this.radius= radius;
        gravityMultiplyer = 1;
        Physics.spriteList.add(this);
        forceList.add(Physics.gravity);
    }


    public void calculateVector(){
        //this is the vector that will be added to sprite vector
    Vector sumVector = new Vector(0,0);

        //adding all the vectors
        for (Vector v: forceList ) {
        sumVector.addVector(v);

        //only adding a vector if it is still applying a force to the sprite
        if(v.lifeTime<=Physics.timeStep&&v.lifeTime>=0){
            forceList.remove(v);
        }
        else if(v.lifeTime>Physics.timeStep){
            //this might cause problems

            v.lifeTime-=Physics.timeStep;
        }


        }

        System.out.println("sprite vector "+ spriteVector.yDirection +" "+ spriteVector.xDirection);
        spriteVector.addVector(sumVector);
        System.out.println("sprite vector "+ spriteVector.yDirection +" "+ spriteVector.xDirection);

    }


    public floatingPoint spriteMove(){
        return spriteVector.vectorMultiply((double)(1/mass)).vectorEndPoint();
    }

    public void newForce(Vector v){
        forceList.add(v);
    }

    public void newForce(double x, double y, long time){
        forceList.add(new Vector(x,y,time));
    }

}
