import java.util.LinkedList;
import java.util.ListIterator;

public class Sprite {



    //each sprite will have a gravity multiplyer so that each sprite can have different gravity
    public double gravityMultiplyer;

    //mass of sprite
    public double mass;

    //final vector for the sprite
    public Vector spriteVector = new Vector(0,0);

    //position of sprite, from center
    public floatingPoint position;

    //it is for the radius or length/2 of the sprite
    public double radius;

    //list of all forces acting on the sprite
    public LinkedList<Vector> forceList = new LinkedList<Vector>();

    //String for name
    String name="";

    public boolean hasCollision;

    //constructor with gravity Multiplyer
    Sprite(boolean hasCollision, floatingPoint position, double mass, double radius,double gravityMultiplyer){
        this.position = position;
        this.mass= mass;
        this.radius= radius;
        this.gravityMultiplyer = gravityMultiplyer;
        Physics.spriteList.add(this);
        name ="Sprite "+Physics.spriteList.size();
        //need to multiply the gravity multiplyer by mass because if it isn't then gravity affects each object differently depending on it's mass
        forceList.add(Physics.gravity.vectorMultiply(gravityMultiplyer*mass));
        this.hasCollision = hasCollision;
        if(hasCollision){
            Physics.collisionList.add(this);
        }
    }

    //consturctor without gravity multiplyer
    Sprite(boolean hasCollision,floatingPoint position, double mass, double radius){
        this.position = position;
        this.mass= mass;
        this.radius= radius;
        gravityMultiplyer = 0;
        Physics.spriteList.add(this);
        name ="Sprite "+Physics.spriteList.size();
        forceList.add(Physics.gravity.vectorMultiply(mass*gravityMultiplyer));
        //if hasCollision is true then it is added to the collisionlist
        this.hasCollision = hasCollision;
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
        //only keeping a vector if it is still applying a force to the sprite
        if(v.lifeTime<=Physics.timeStep&&v.lifeTime>=0){
            it.remove();
        }
        else if(v.lifeTime>Physics.timeStep){
            //this might cause problems
            v.lifeTime-=Physics.timeStep;
        }
        }
        spriteVector.addVector(sumVector);
    }


    public void calculateVectorWithoutTime(){
        //this is the vector that will be added to sprite vector
        Vector sumVector = new Vector(0,0);

        //need to use listIterator to avoid concurrentModificationException
        ListIterator<Vector> it = forceList.listIterator();

        //adding all the vectors
        while (it.hasNext()) {
            Vector v = it.next();
            sumVector.addVector(v);
        }
        spriteVector.addVector(sumVector);
    }

    //returns amount the sprite has to move
    public floatingPoint spriteMove(){
        return spriteVector.vectorMultiply((double)(1/mass)).getVectorEndPoint();
    }

    public void newForce(Vector v){
        forceList.add(v);
    }

    public void newForce(Vector v,long time){
        v.lifeTime=time;
        forceList.add(v);
    }

    //to add a new force/vector
    public void newForce(double x, double y, long timeMillis){
        forceList.add(new Vector(x,y,timeMillis));
    }

    //calculates distance between two points
    public double calculateDistance(floatingPoint a, floatingPoint b){

        double x = a.x - b.x;
        double y = a.y - b.y;

        return Math.sqrt((x*x)+(y*y));

    }


    public static void calculateCollisions(){
        //iterates through all the sprites in the collision list
        for(int i=0; i<Physics.collisionList.size();i++){

             Sprite s = Physics.collisionList.get(i);

             //iterating through all the other remaining sprites see if there are any collisions
            for(int j = i+1;j<Physics.collisionList.size();j++) {


                Sprite s2 = Physics.collisionList.get(j);

                //if the distance between the centers of the two sprites is less than the sum of their radii then go to the next step to see if a collission is needed
                if ((s.calculateDistance(s.position, s2.position) < (s.radius + s2.radius))) {
                        System.out.println(s.calculateDistance(s.position,s2.position)+" distance \n\n\n");

                        //if the distance between their centers is greater than the distance between their vectors (after being divided by the greatest vector direction) then a collision has happened
                    double max = Math.max(Math.max(s.spriteVector.xDirection,s.spriteVector.yDirection),Math.max(s2.spriteVector.xDirection,s2.spriteVector.yDirection));
                    if ((s.calculateDistance(s.position, s2.position) > s.calculateDistance(s.position.floatingPointAdd(s.position,s.spriteVector.vectorMultiply((1/max)).getVectorEndPoint()), s2.position.floatingPointAdd(s2.position,s2.spriteVector.vectorMultiply((1/max)).getVectorEndPoint())))){
                        //TODO: write comments

                        //
                        double distanceBetweenSprites = s.calculateDistance(s.position, s2.position);
                        //cos
                        double xMultiplyer = Math.abs(s2.position.x - s.position.x)/distanceBetweenSprites;
                        //sin
                        double yMultiplyer = Math.abs(s2.position.y - s.position.y)/distanceBetweenSprites;


                        Vector v= new Vector(s.spriteVector.xDirection*xMultiplyer,s.spriteVector.yDirection*yMultiplyer,0);
                        Vector v2 = new Vector(s2.spriteVector.xDirection*xMultiplyer, s2.spriteVector.yDirection*yMultiplyer,0);

                        System.out.println(xMultiplyer + "  xy multiplyer  "+ yMultiplyer);

                        double multiplyer =  (2/((s.mass+s2.mass)/Math.min(s.mass,s2.mass)));
                        System.out.println(multiplyer +" multiplyer");

                        if(s.mass>s2.mass) {
                            s.newForce(Vector.vectorSum(v.vectorMultiply(-multiplyer), v2.vectorMultiply(2-multiplyer)), 0);
                            s2.newForce(Vector.vectorSum(v2.vectorMultiply(-2+multiplyer), v.vectorMultiply(multiplyer)), 0);
                        }
                        else if(s.mass<s2.mass) {
                            s.newForce(Vector.vectorSum(v.vectorMultiply(-2+multiplyer), v2.vectorMultiply(multiplyer)), 0);
                            s2.newForce(Vector.vectorSum(v2.vectorMultiply(-multiplyer), v.vectorMultiply(2-multiplyer)), 0);
                        }
                        else{
                            s.newForce(Vector.vectorSum(v.vectorMultiply(-1), v2), 0);
                            s2.newForce(Vector.vectorSum(v2.vectorMultiply(-1), v), 0);
                        }
                        System.out.println("Collision "+i+ " "+s.forceList.size()+"   "+j+" "+s2.forceList.size()+"\n\n\n");

                }
            }
            }

        }
    }

    //sets the object to null so that the gc can collect it
    public void delete(){
        Sprite s = this;
        s = null;
    }
}


