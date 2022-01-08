import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.sql.SQLOutput;

public class UI extends Thread{
    static JFrame frame;

    static BufferedImage image;

    static JPanel imagePanel;

    static JPanel UIPanel;

    static FlowLayout layout;

    static Physics physics = new Physics();

    static Screen screen = new Screen();

    public static final String UIThreadName = "UI Thread";

    public void run(){
        setName(UIThreadName);
        createFrame();


    }


    public static void createFrame(){

        layout = new FlowLayout();
        layout.setHgap(0);
        layout.setVgap(0);

        //creating frame and setting size, close operation etc
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        frame.setExtendedState( JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
        frame.getContentPane().setLayout(layout);

        createImagePanel();
        createUIPanel();




        //setting frame visible
        frame.setVisible(true);
        System.out.println("created frame");

    }

    public static void createImagePanel(){

        imagePanel = new JPanel();
        imagePanel.setSize(new Dimension(frame.getWidth(), frame.getHeight()-100));
        imagePanel.setPreferredSize(new Dimension(frame.getWidth(), frame.getHeight()-100));
        imagePanel.setBackground(Color.BLACK);
        imagePanel.setVisible(true);
        frame.add(imagePanel);

        //setting size of buffered image, this is what will be displayed
        image = new BufferedImage(imagePanel.getWidth(), imagePanel.getHeight(), BufferedImage.TYPE_INT_RGB);
        System.out.println("image height" + imagePanel.getSize());

    }

    public static void createUIPanel(){

        //TODO: need to correct alignment
        UIPanel = new JPanel();
        UIPanel.setPreferredSize(new Dimension(frame.getWidth(), 100));
        UIPanel.setBackground(Color.RED);
        UIPanel.setLayout(layout);
        UIPanel.setVisible(true);

        JButton startButton = new JButton("START");
        startButton.setPreferredSize(new Dimension(200,40));

        startButton.addActionListener(e -> {

            if(startButton.getText().equals("START")) {
                startButton.setText("STOP");
                physics.start();

                screen.start();


            }
            else if(startButton.getText().equals("STOP")){
                startButton.setText("RESUME");
                // making the threads wait.
                Physics.runPhysicsThread = false;
                Screen.runScreenThread = false;
            }
            else{
                startButton.setText("STOP");
                System.out.println("notifying threads");

                physics.threadNotifyAll();
                screen.threadNotifyAll();

            }


        });
        UIPanel.add(startButton);



        frame.add(UIPanel);


    }
}
