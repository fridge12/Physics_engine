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

    public void run (){
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500,500);

        image = new BufferedImage(frame.getWidth(), frame.getHeight()-100, BufferedImage.TYPE_INT_RGB);

        frame.setVisible(true);
        System.out.println("created frame");

        long startTime ;
        while(true){
            startTime= System.currentTimeMillis();
            //using content pane as default for now, will probably add layout and jpanel and use that
            frame.getContentPane().getGraphics().drawImage(draw(),0,0,null);
            try {
                if(timeBetweenFrames-(System.currentTimeMillis()-startTime)<0){
                    Thread.sleep(0);
                }
                else{
                Thread.sleep(timeBetweenFrames-(System.currentTimeMillis()-startTime));
            }
            }catch (InterruptedException e) {
                e.printStackTrace();
            }

        }


    }
    static int x = 10;
    public static Image draw(){
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

        for(Sprite s : Physics.spriteList){

            image.getGraphics().drawOval((int)(s.position.x +(s.spriteMove().x*timeBetweenFramesSeconds)),(int)(s.position.y +(s.spriteMove().y*timeBetweenFramesSeconds)),(int)s.radius,(int)s.radius);
            System.out.println((s.spriteMove().y*timeBetweenFramesSeconds)+" "+s.spriteVector.yDirection);
        }

        //System.out.println((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory())/1000000);
        return image;
    }

}
