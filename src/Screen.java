import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

import static java.lang.Math.random;

public class Screen extends Thread {

    static JFrame frame;

    static BufferedImage image;

    static final int frames =60;

    static final long timeBetweenFrames = 1000/frames;

    static final double timeBetweenFramesSeconds = 1/(double)(frames);

    public static long totalFrames =0;

    public void run (){
        //creating frame and setting size, close operation etc
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        frame.setExtendedState( JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);

        //setting size of buffered image, this is what will be displayed
        image = new BufferedImage(frame.getWidth(), frame.getHeight()-100, BufferedImage.TYPE_INT_RGB);

        //setting frame visible
        frame.setVisible(true);
        System.out.println("created frame");

        //this will be used to store when the while loop starts
        long startTime ;
        while(true) {
            startTime = System.currentTimeMillis();
            //System.out.println(++totalFrames + "frames");
            //using content pane as default for now, will probably add layout and jpanel and use that
            frame.getContentPane().getGraphics().drawImage(draw(), 0, 0, null);

            try {
                //puts the thread to sleep for the difference in timebetweenframes and the time taken for the frame to be drawn
                Thread.sleep(timeBetweenFrames - (System.currentTimeMillis() - startTime));

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {

                System.out.println("took too long");
            }

        }
    }
    static int x = 10;
    //method to draw everything on screen
    public static Image draw(){
        //this clears the image
       image.getGraphics().clearRect(0,0,image.getWidth(), image.getHeight());


        /*
        BufferedImage layer1 = image;
        layer1.getGraphics().dispose();
        layer1.getGraphics().drawRect(10,10,10,10);

        BufferedImage layer2 = image;
        layer2.getGraphics().dispose();

        layer2.getGraphics().fillOval(0,0,10,10);

        image.getGraphics().drawImage(layer1,0,0,null);
        image.getGraphics().drawImage(layer2,0,0,null);
        */

        //iterating through every sprite to render it
        for(Sprite s : Physics.spriteList){

            //not rendering sprites if they aren't on screen
            if((s.position.x>image.getWidth()+s.radius)||(s.position.x< -s.radius)||(s.position.y>image.getHeight()+s.radius)||(s.position.y< -s.radius)) {
            System.out.println("didn't draw sprite");
            }
                else{
                    //rendering the sprites only if they are on screen
                image.getGraphics().drawOval((int) ((s.position.x + (s.spriteMove().x * timeBetweenFramesSeconds)) - s.radius), (int) ((s.position.y + (s.spriteMove().y * timeBetweenFramesSeconds)) - s.radius), (int) (s.radius * 2), (int) (s.radius * 2));
            }
        }

        return image;
    }

}
