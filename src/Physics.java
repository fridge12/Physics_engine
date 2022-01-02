import java.util.LinkedList;

public class Physics extends Thread {

    public static final Vector gravity = new Vector(0,.1);

    //list for all the sprites
    public static LinkedList<Sprite> spriteList = new LinkedList<Sprite>();

    //list for all objects with collision
    public static LinkedList<Sprite> collisionList = new LinkedList<Sprite>();

    //this is for the number of physics frames calculated per second
    public static long physicsFrames = 50;

    //this is the amount of time between each physics frame in milliseconds
    public static long timeStep = 1000/physicsFrames;

    @Override
    public void run() {
        long startTime;


        while (true) {
            //time the loop started
            startTime= System.currentTimeMillis();

            //calculating collisions
            Sprite.calculateCollisions();

            //for loop for calculating the vector for each sprite
            for (Sprite s : spriteList) {

                s.calculateVector();
                s.position.floatingPointAdd(s.spriteMove());
                //System.out.println(s.position.y);

            }

            //putting the thread to sleep
            try {
                //putting it to sleep for the amount of time left
                Thread.sleep(timeStep-(System.currentTimeMillis()-startTime));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            catch(IllegalArgumentException e ){

                e.printStackTrace();
                System.out.println("in the try catch \n\n\n");
            }
        }
    }
}
