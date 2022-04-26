import java.awt.*;

public class Screen extends Thread {

    static final int frames =60;

    static final long timeBetweenFrames = 1000/frames;

    static final double timeBetweenFramesSeconds = 1/(double)(frames);

    public static long totalFrames =0;

    public static final String screenThreadName = "Screen thread";

    public static boolean runScreenThread = true;

    public static boolean drawVectors = false;

    public void run (){
        setName(screenThreadName);

        //this will be used to store when the while loop starts
        long startTime ;
        while(true) {

            if (runScreenThread) {


                startTime = System.currentTimeMillis();
                //System.out.println(++totalFrames + "frames");
                UI.imagePanel.getGraphics().drawImage(draw(UI.image.getGraphics()), 0, 0, null);

                try {
                    //puts the thread to sleep for the difference in timebetweenframes and the time taken for the frame to be drawn
                    Thread.sleep(timeBetweenFrames - (System.currentTimeMillis() - startTime));

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {

                    System.out.println("took too long");
                }

            }
            else{
                synchronized (this){
                    try {
                        wait();
                        runScreenThread = true;
                        System.out.println("screen thread notified in try");

                    } catch (InterruptedException e) {
                        runScreenThread = true;
                        System.out.println("screen thread notified in catch");
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    static int x = 10;
    //method to draw everything on screen
    public static Image draw(Graphics g){
        //this clears the image
        g.clearRect(0,0,UI.image.getWidth(), UI.image.getHeight());

        //iterating through every sprite to render it
        for(Sprite s : Physics.spriteList){

            //not rendering sprites if they aren't on screen
            if((s.position.x>UI.image.getWidth()+s.radius)||(s.position.x< -s.radius)||(s.position.y>UI.image.getHeight()+s.radius)||(s.position.y< -s.radius)) {
            //System.out.println("didn't draw sprite");
            }
                else{
                    //rendering the sprites only if they are on screen
                g.drawOval((int) ((s.position.x + (s.spriteMove().x * timeBetweenFramesSeconds)) - s.radius), (int) ((s.position.y + (s.spriteMove().y * timeBetweenFramesSeconds)) - s.radius), (int) (s.radius * 2), (int) (s.radius * 2));
                if(drawVectors) {
                    g.drawLine((int) (s.position.x), (int) (s.position.y), (int) (s.position.x + s.spriteVector.getVectorEndPoint().x), (int) (s.position.y + s.spriteVector.getVectorEndPoint().y));
                }
                }

        }

        return UI.image;
    }


    public static void update( ){
        //this clears the image


        Graphics g = UI.image.getGraphics();

        g.clearRect(0,0,UI.image.getWidth(), UI.image.getHeight());

        //iterating through every sprite to render it
        for(Sprite s : Physics.spriteList){

            //not rendering sprites if they aren't on screen
            if((s.position.x>UI.image.getWidth()+s.radius)||(s.position.x< -s.radius)||(s.position.y>UI.image.getHeight()+s.radius)||(s.position.y< -s.radius)) {
                //System.out.println("didn't draw sprite");
            }
            else{
                //rendering the sprites only if they are on screen
                g.drawOval((int) (s.position.x  - s.radius), (int) (s.position.y  - s.radius), (int) (s.radius * 2), (int) (s.radius * 2));
                if(drawVectors) {
                    g.drawLine((int) (s.position.x), (int) (s.position.y), (int) (s.position.x + s.spriteVector.getVectorEndPoint().x), (int) (s.position.y + s.spriteVector.getVectorEndPoint().y));
                }
            }

        }

        UI.imagePanel.getGraphics().drawImage(UI.image, 0, 0, null);

    }

    public void threadNotifyAll (){
        synchronized (this){
            notifyAll();
        }
    }

}
