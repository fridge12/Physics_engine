public class ThreadController {

    public static void main (String args[]){

        Screen screen = new Screen();
        screen.start();

        memoryManager manager = new memoryManager();
        manager.start();

    }

}
