public class ThreadController {

    public static void main (String args[]){

        Sprite sprite = new Sprite(true,new floatingPoint(200,200),10,15,0.1);
        sprite.newForce(1,-3,250);

        Sprite sprite2 = new Sprite(true,new floatingPoint(400 ,200),10,15,0.1);
        sprite2.newForce(-1,-3,250);

        Screen screen = new Screen();
        screen.setName("Screen thread");
        screen.start();

        memoryManager manager = new memoryManager();
        manager.setName("Memory Manager thread");
        manager.start();

        Physics physics = new Physics();
        physics.setName("Physics thread");
        physics.start();



    }

}
