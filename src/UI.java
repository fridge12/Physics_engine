import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.sql.SQLOutput;
import java.util.Iterator;

public class UI extends Thread{
    static JFrame frame;

    static BufferedImage image;

    static JPanel imagePanel;

    static JPanel UIPanel;

    static int  ctr=0;


    static Physics physics = new Physics();

    static Screen screen = new Screen();

    public static final String UIThreadName = "UI Thread";

    public static final LineBorder BlackBorder = new LineBorder(Color.BLACK,2);

    public void run(){
        setName(UIThreadName);
        createFrame();


    }


    public static void createFrame(){

        FlowLayout layout = new FlowLayout();
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

        //adding mouselistener so that I can intereact with sprites
        imagePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent event) {
                //if a there was a double click
                if (event.getClickCount() == 2 && event.getButton() == MouseEvent.BUTTON1) {
                    System.out.println("double clicked");
                    System.out.println(event.getLocationOnScreen());

                    try {
                        //getting the sprite that was clicked on
                        createSpriteInfoFrame(Physics.getSpriteOnClick(floatingPoint.convertPoint(event.getLocationOnScreen())));
                    }
                    catch(Exception e){
                        e.printStackTrace();
                        System.out.println("no sprite clicked");
                    }
                }
            }
        });

    }

    public static void createUIPanel(){

        //create a panel for different ui elements
        UIPanel = new JPanel();
        UIPanel.setPreferredSize(new Dimension(frame.getWidth(), 100));
        UIPanel.setBackground(Color.RED);

        FlowLayout layout= new FlowLayout(FlowLayout.CENTER);
        layout.setVgap(30);

        UIPanel.setLayout(layout);
        UIPanel.setVisible(true);

        //button that will start stop and resume the simulation
        JButton startButton = new JButton("START");
        startButton.setPreferredSize(new Dimension(200,40));
        startButton.addActionListener(e -> {

            //starting the simulation
            if(startButton.getText().equals("START")) {
                startButton.setText("STOP");
                physics.start();
                screen.start();
            }
            //stopping the simulation
            else if(startButton.getText().equals("STOP")){
                startButton.setText("RESUME");
                // making the threads wait.
                Physics.runPhysicsThread = false;
                //Screen.runScreenThread = false;
            }
            //resuming the simulation
            else{
                startButton.setText("STOP");
                System.out.println("notifying threads");
                physics.threadNotifyAll();
                //screen.threadNotifyAll();
            }
        });
        UIPanel.add(startButton);

        //button to draw vectors
        JButton drawVectorsButton = new JButton("Draw Vectors");
        drawVectorsButton.setPreferredSize(new Dimension(200,40));
        drawVectorsButton.addActionListener(e->{
            if(Screen.drawVectors){
                Screen.drawVectors = false;
            }
            else{
                Screen.drawVectors = true;
            }
            //Screen.update();
        });

        UIPanel.add(drawVectorsButton);


        frame.add(UIPanel);
    }

    //this is to show the info of the sprite so that it can be edited
    public static void createSpriteInfoFrame(Sprite s){

        //new frame with all the sprite info
        JFrame spriteFrame = new JFrame(s.name);
        spriteFrame.setFocusable(true);
        spriteFrame.setSize(250,500);

        //adding mouselistener to the content pane so that when the any open space is clicked the focused component loses focus
        spriteFrame.getContentPane().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("mouse clicked on frame");
                spriteFrame.getContentPane().requestFocus();
            }
        });


        //layout for the frame
        FlowLayout flowLayout = new FlowLayout();
        flowLayout.setAlignment(FlowLayout.CENTER);
        spriteFrame.getContentPane().setLayout(flowLayout);


        //label
        spriteFrame.add(label(100,"Name"));

        //textfield where the name of the sprite can be edited
        JTextField nameField = new JTextField(s.name);
        nameField.setPreferredSize(new Dimension(100, (int) nameField.getPreferredSize().getHeight()));
        nameField.setBorder(BlackBorder);
        nameField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {}
            @Override
            //setting the name of the sprite when the textfield loses focus
            public void focusLost(FocusEvent e) {
                s.name = nameField.getText();
            }
        });
        nameField.setVisible(true);
        spriteFrame.add(nameField);

        //position label
        spriteFrame.add(label(100,"Position"));

        //textfield where the x position is displayed
        JTextField xPositionField = new JTextField((int) (s.position.x)+"");
        xPositionField.setPreferredSize(new Dimension(47, (int) xPositionField.getPreferredSize().getHeight()));
        xPositionField.setBorder(BlackBorder);
        xPositionField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {}
            @Override
            //tries to set the position when it loses focus, if it can't it resets the text to the position
            public void focusLost(FocusEvent e) {
               try{
                   s.position.x=Double.parseDouble(xPositionField.getText());
                   //Screen.update();
               }
               catch(Exception exception){
                   xPositionField.setText((int) (s.position.x)+"");
                   exception.printStackTrace();
               }
            }
        });
        spriteFrame.add(xPositionField);

        //textfield where the y position is displayed
        JTextField yPositionField = new JTextField((int) (s.position.y)+"");
        yPositionField.setPreferredSize(new Dimension(47, (int) yPositionField.getPreferredSize().getHeight()));
        yPositionField.setBorder(BlackBorder);
        yPositionField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {}
            @Override
            //tries to set the position when it loses focus, if it can't it resets the text to the position

            public void focusLost(FocusEvent e) {
                try{
                    s.position.y=Double.parseDouble(yPositionField.getText());
                    //Screen.update();
                }
                catch(Exception exception){
                    yPositionField.setText((int)(s.position.y)+"");
                    exception.printStackTrace();
                }
            }
        });
        spriteFrame.add(yPositionField);


        //gravity label
        spriteFrame.add(label(100,"Gravity"));

        //textfield where the gravity of the sprite can be edited
        JTextField gravityField = new JTextField((int)(s.gravityMultiplyer)+"");
        gravityField.setPreferredSize(new Dimension(100, (int) gravityField.getPreferredSize().getHeight()));
        gravityField.setBorder(BlackBorder);
        gravityField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {}
            @Override
            //setting the gravity of the sprite when the textfield loses focus
            public void focusLost(FocusEvent e) {
                try{
                    //to remove the effect of gravity on the sprite so that the new gravity isn't changed by the new gravity
                    s.forceList.set(0,s.forceList.getFirst().vectorMultiply(-1));
                    s.calculateVectorWithoutTime();
                    System.out.println("gravity updated");

                    //adding new gravity to the sprite
                    s.forceList.set(0,Physics.gravity.vectorMultiply(Double.parseDouble(gravityField.getText())*s.mass));
                    s.calculateVectorWithoutTime();
                    //Screen.update();
                }
                catch(Exception exception){
                    gravityField.setText((int)(s.gravityMultiplyer)+"");
                    exception.printStackTrace();
                }

            }
        });
        gravityField.setVisible(true);
        spriteFrame.add(gravityField);




        //label
        spriteFrame.add(label(100,"Mass"));

        //textfield where the gravity of the sprite can be edited
        JTextField massField = new JTextField((int)(s.mass)+"");
        massField.setPreferredSize(new Dimension(100, (int) massField.getPreferredSize().getHeight()));
        massField.setBorder(BlackBorder);
        massField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {}
            @Override
            //setting the gravity of the sprite when the textfield loses focus
            public void focusLost(FocusEvent e) {
                //TODO: make mass changes work
                try{
                    //to remove the effect of gravity, so that the new adjusted gravity can be added without problem
                    s.forceList.set(0,s.forceList.getFirst().vectorMultiply(-1));
                    s.calculateVectorWithoutTime();

                    //need to add gravity since it is dependent on mass
                    s.mass = Double.parseDouble(massField.getText());
                    s.forceList.set(0,Physics.gravity.vectorMultiply(s.gravityMultiplyer*s.mass));
                    s.calculateVectorWithoutTime();
                    //Screen.update();
                }
                catch(Exception exception){
                    massField.setText((int)(s.mass)+"");
                    exception.printStackTrace();
                }

            }
        });
        massField.setVisible(true);
        spriteFrame.add(massField);

        //has collision label
        spriteFrame.add(label(100,"Has collision"));

        //radio button that will turn on or off collision
        JRadioButton hasCollisionRadioButton = new JRadioButton();
        //if the collision of the sprite is on then the radio button is selected
        if(s.hasCollision){
            hasCollisionRadioButton.setSelected(true);
            hasCollisionRadioButton.setText("collision on");
        }
        else{
            hasCollisionRadioButton.setSelected(false);
            hasCollisionRadioButton.setText("collision off");
        }
        hasCollisionRadioButton.setPreferredSize(new Dimension(100,(int)(hasCollisionRadioButton.getPreferredSize().getHeight())));
        //adding functionality
        hasCollisionRadioButton.addActionListener(e -> {
            if(s.hasCollision){
                //removing from collision list
                s.hasCollision= false;
                Physics.collisionList.remove(s);
                hasCollisionRadioButton.setText("collision off");
            }
            else{
                //adding to collision list
                s.hasCollision = true;
                Physics.collisionList.add(s);
                hasCollisionRadioButton.setText("collision on");
            }
        });
        spriteFrame.add(hasCollisionRadioButton);


        //radius label
        spriteFrame.add(label(100,"Radius"));


        //textfield where the raius
        JTextField radiusField = new JTextField((int) (s.radius)+"");
        radiusField.setPreferredSize(new Dimension(100, (int) radiusField.getPreferredSize().getHeight()));
        radiusField.setBorder(BlackBorder);
        radiusField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {}
            @Override
            //tries to set the radius when it loses focus, if it can't it resets the text to the old radius
            public void focusLost(FocusEvent e) {
                try{
                    s.radius=Double.parseDouble(radiusField.getText());
                    //Screen.update();
                }
                catch(Exception exception){
                    radiusField.setText((int)(s.radius)+"");
                    exception.printStackTrace();
                }
            }
        });
        spriteFrame.add(radiusField);

        //vectors label
        spriteFrame.add(label(100,"Vectors"));

        //hold the scrollpane which holds vectors
        JPanel scrollPanePanel = new JPanel();
        scrollPanePanel.setPreferredSize(new Dimension(spriteFrame.getWidth()-10,280));

        //holds vectors, goes into the scrollpane
        JPanel vectorPanel = new JPanel();
        vectorPanel.setBorder(BlackBorder);
        vectorPanel.setSize(new Dimension(spriteFrame.getWidth()-10-2,280));
        vectorPanel.setPreferredSize(new Dimension(spriteFrame.getWidth()-10-2,280));

        Iterator<Vector> it = s.forceList.listIterator();
        ctr=-1;

        while(it.hasNext()){
            ctr++;
            Vector v = it.next();
            System.out.println("in for loop for vectors");
            if(!v.equals(s.forceList.getFirst())) {
                System.out.println("adding vector");
                vectorPanel.add(label(100,v.name));
                JTextField vectorX = new JTextField((int) (v.xDirection)+"");
                vectorX.setPreferredSize(new Dimension(30, (int) vectorX.getPreferredSize().getHeight()));
                vectorX.setBorder(BlackBorder);
                vectorX.addFocusListener(new FocusListener() {
                    @Override
                    public void focusGained(FocusEvent e) {}
                    @Override
                    //tries to set the position when it loses focus, if it can't it resets the text to the position
                    public void focusLost(FocusEvent e) {
                        try{

                            s.spriteVector.addVector(v.vectorMultiply(-1));

                            v.xDirection=Double.parseDouble(vectorX.getText());
                            s.spriteVector.addVector(v);
                            //Screen.update();
                        }
                        catch(Exception exception){
                            vectorX.setText((int) (v.xDirection)+"");
                            exception.printStackTrace();
                        }
                    }
                });
                vectorPanel.add(vectorX);

                //textfield where the y position is displayed
                JTextField vectorY = new JTextField((int) (v.yDirection)+"");
                vectorY.setPreferredSize(new Dimension(30, (int) vectorY.getPreferredSize().getHeight()));
                vectorY.setBorder(BlackBorder);
                vectorY.addFocusListener(new FocusListener() {
                    @Override
                    public void focusGained(FocusEvent e) {}
                    @Override
                    //tries to set the position when it loses focus, if it can't it resets the text to the position

                    public void focusLost(FocusEvent e) {
                        try{
                            s.spriteVector.addVector(v.vectorMultiply(-1));

                            v.yDirection=Double.parseDouble(vectorY.getText());
                            s.spriteVector.addVector(v);
                        }
                        catch(Exception exception){
                            vectorY.setText((int)(v.yDirection)+"");
                            exception.printStackTrace();
                        }
                    }
                });
                vectorPanel.add(vectorY);



                //textfield where the lifetime is displayed
                JTextField vectorTime = new JTextField((int) (v.lifeTime)+"");
                vectorTime.setPreferredSize(new Dimension(30, (int) vectorTime.getPreferredSize().getHeight()));
                vectorTime.setBorder(BlackBorder);
                vectorTime.addFocusListener(new FocusListener() {
                    @Override
                    public void focusGained(FocusEvent e) {}
                    @Override
                    //tries to set the position when it loses focus, if it can't it resets the text to the position

                    public void focusLost(FocusEvent e) {
                        try{
                            v.lifeTime= Long.parseLong(vectorTime.getText());
                        }
                        catch(Exception exception){
                            vectorTime.setText((int)(v.lifeTime)+"");
                            exception.printStackTrace();
                        }
                    }
                });
                vectorPanel.add(vectorTime);
            }
            else{
                System.out.println("skipped gravity vector");
            }


            }


        JScrollPane vectorPane = new JScrollPane(vectorPanel);
        vectorPane.setPreferredSize(scrollPanePanel.getPreferredSize());
        vectorPane.setLayout(new ScrollPaneLayout());
        vectorPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPanePanel.add(vectorPane);
        spriteFrame.add(scrollPanePanel);

        spriteFrame.setVisible(true);
    }


    public static JLabel label(int width,String text){
        JLabel Label =  new JLabel(text,JLabel.CENTER);
        Label.setBorder(BlackBorder);
        Label.setPreferredSize(new Dimension(width,Label.getPreferredSize().height));
        Label.setVisible(true);

        return Label;
    }

}
