import java.awt.*;
import java.util.LinkedList;
import java.util.ListIterator;

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

    public boolean hasCollision;

    Sprite(boolean hasCollision, floatingPoint position, double mass, double radius,double gravityMultiplyer){
        this.position = position;
        this.mass= mass;
        this.radius= radius;
        this.gravityMultiplyer = gravityMultiplyer;
        Physics.spriteList.add(this);
        forceList.add(Physics.gravity.vectorMultiply(gravityMultiplyer));
        if(hasCollision){
            Physics.collisionList.add(this);
        }
    }

    Sprite(boolean hasCollision,floatingPoint position, double mass, double radius){
        this.position = position;
        this.mass= mass;
        this.radius= radius;
        gravityMultiplyer = 1;
        Physics.spriteList.add(this);
        forceList.add(Physics.gravity);
        if(hasCollision){
            Physics.collisionList.add(this);
        }
    }


    public void calculateVector(){
        //this is the vector that will be added to sprite vector
    Vector sumVector = new Vector(0,0);

        //need to use listIterator to avoid concurrentModificationException
        ListIterator<Vector> it = forceList.listIterator();

        //adding all the vectors
        while (it.hasNext()) {
            Vector v = it.next();
        sumVector.addVector(v);

        //only adding a vector if it is still applying a force to the sprite
        if(v.lifeTime<=Physics.timeStep&&v.lifeTime>=0){
            it.remove();
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

    public double calculateDistance(floatingPoint a, floatingPoint b){

        double x = a.x - b.x;
        double y = a.y - b.y;

        return Math.sqrt((x*x)+(y*y));

    }

    public static void calculateCollisions(){
        for(int i=0; i<Physics.collisionList.size();i++){
             Sprite s = Physics.collisionList.get(i);
            for(int j = i+1;j<Physics.collisionList.size();j++) {
                Sprite s2 = Physics.collisionList.get(j);
                if ((s.calculateDistance(s.position, s2.position) < (s.radius + s2.radius))) {
                        System.out.println(s.calculateDistance(s.position,s2.position)+"\n\n\n");
                    if ((s.calculateDistance(s.position, s2.position) > s.calculateDistance(s.spriteVector.vectorEndPoint(), s2.spriteVector.vectorEndPoint()))){

                        s.newForce((s.position.x - s2.position.x), (s.position.y - s2.position.y), 0);
                        s2.newForce((s2.position.x - s.position.x), (s2.position.y - s.position.y), 0);
                        System.out.println("Collision \n\n\n");
                }
            }
            }

        }
    }
}


