import java.util.LinkedList;

public class Physics extends Thread {

    public static final Vector gravity = new Vector(0,10);

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
            startTime= System.currentTimeMillis();

            Sprite.calculateCollisions();

            for (Sprite s : spriteList) {

                s.calculateVector();

                s.position.floatingPointAdd(s.spriteMove());
                System.out.println(s.position.y);

            }

            try {
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
