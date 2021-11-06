import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

import static java.lang.Math.random;

public class Screen extends Thread {

    static JFrame frame;



    public void run (){
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500,500);

        frame.setVisible(true);


        while(true){
            //using content pane as default for now, will probably add layout and jpanel and use that
            frame.getContentPane().getGraphics().drawImage(draw(),0,0,null);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }


    }
    static int x = 10;
    public static Image draw(){
        BufferedImage image = new BufferedImage(frame.getWidth(), frame.getHeight()-100, BufferedImage.TYPE_INT_RGB);

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
        image.getGraphics().drawRect(x , 250,  10,10);
        x+= 1;

        System.out.println((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory())/1000000);
        return image;
    }

}
