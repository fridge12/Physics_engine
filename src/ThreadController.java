public class ThreadController {

    public static void main (String args[]){

        Sprite sprite = new Sprite(new floatingPoint(5,200),100,15);
        sprite.newForce(10,-30,500);

        Sprite sprite2 = new Sprite(new floatingPoint(450 ,200),100,15);
        sprite2.newForce(-10,-20,500);

        Screen screen = new Screen();
        screen.start();

        memoryManager manager = new memoryManager();
        manager.start();

        Physics physics = new Physics();
        physics.start();



    }

}
