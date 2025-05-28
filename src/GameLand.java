//Basic Game Application
// Basic Object, Image, Movement
// Threaded
//******************************************************************************
//Import Section
//Add Java libraries needed for the game
//import java.awt.Canvas;

//to do: change zombies to bees or flowers, change background to a garden, add game over, fix lives text!

//Graphics Libraries
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;

//*******************************************************************************

public class GameLand implements Runnable, KeyListener {

    //Variable Definition Section
    //Declare the variables used in the program
    //You can set their initial values too
    public Character butterfly;
    public Image butterflyPic;
    //public Character star;
    public Image starPic;
    public Character moon;
    public Image moonPic;
    public Image backgroundImage;
    public Image gameOver;
    //public boolean butterflyIsintersectionstar;
    public Character[] stars;
    // public boolean[] isIntersecting;

    //Sets the width and height of the program window
    final int WIDTH = 1000;
    final int HEIGHT = 700;

    //Declare the variables needed for the graphics
    public JFrame frame;
    public Canvas canvas;
    public JPanel panel;
    public int lives = 5;
    public boolean butterflyCollision = false;

    public BufferStrategy bufferStrategy;

    // Main method definition
    // This is the code that runs first and automatically
    public static void main(String[] args) {
        GameLand ex = new GameLand();   //creates a new instance of the game
        new Thread(ex).start();                 //creates a threads & starts up the code in the run( ) method
    }


    // This section is the setup portion of the program
    // Initialize your variables and construct your program objects here.
    public GameLand() { // BasicGameApp constructor
        setUpGraphics();

        //variable and objects
        //create (construct) the objects needed for the game
        butterfly = new Character(18, 19, 0, 0);
        butterfly.printInfo();
        butterflyPic = Toolkit.getDefaultToolkit().getImage("butterflyPic.png");

//star = new Character(30,45,2,3);
//star.printInfo();
        starPic = Toolkit.getDefaultToolkit().getImage("starPic.png");

        stars = new Character[20];
        for (int c = 0; c < stars.length; c++) {
            int randx = (int) (900 * Math.random());
            int randy = (int) (300 * Math.random() + 300);
            stars[c] = new Character(randx, randy, 1, 1);
            stars[c].width = 30;
            stars[c].height = 50;
        }

        backgroundImage = Toolkit.getDefaultToolkit().getImage("backgroundImage.jpg");
        gameOver = Toolkit.getDefaultToolkit().getImage("gameOver.jpg");
    } // end BasicGameApp constructor

    //creating and constructing images

//*******************************************************************************
//User Method Section
//
// put your code to do things here.

    // main thread
    // this is the code that plays the game after you set things up
    public void run() {
        //for the moment we will loop things forever.
        while (true) {
            moveThings();  //move all the game objects
            checkIntersections();
            render();  // paint the graphics
            pause(10); // sleep for 10 ms
        }
    }


    public void moveThings() {
        butterfly.controlMove();
        // star.bounceMove();
        for (int r = 0; r < stars.length; r = r + 1) {
            stars[r].bounceMove();
        }
        //call the move() code for each object
    }

    public void checkIntersections() {
        for (int c = 0; c < stars.length; c++) {
            for (int d = 0; d < stars.length; d++) {
                if (stars[c].rec.intersects(stars[d].rec) && c != d && stars[c].isIntersecting == false && stars[d].isIntersecting == false) {
                    stars[c].isIntersecting = true;
                    stars[c].dx = (-1) * stars[c].dx;
                    stars[c].dy = (-1) * stars[c].dy;
                    stars[d].dx = (-1) * stars[d].dx;
                    stars[d].dy = (-1) * stars[d].dy;
                }
            }
        }
        for (int c = 0; c < stars.length; c++) {
            boolean starCollision = false;
            for (int d = 0; d < stars.length; d++) {
                if (stars[c].rec.intersects(stars[d].rec) == true && c != d) {
                    starCollision = true;
                }
            }
            if (starCollision == false) {
                stars[c].isIntersecting = false;
            }

            for (int y = 0; y < stars.length; y = y + 1) {
                if (stars[y].rec.intersects(butterfly.rec) && butterflyCollision == false) {
                    System.out.println("ouch x" + butterfly.xpos);
                    lives = lives - 1;
                    butterflyCollision = true;
                }
            }
            boolean butterflyColliding = false;
            for (int y = 0; y < stars.length; y = y + 1) {
                if (stars[y].rec.intersects(butterfly.rec)) {
                    butterflyColliding = true;
                }
            }
            if (butterflyColliding == false) {
                butterflyCollision = false;
            }
        }
    }
        //Paints things on the screen using bufferStrategy

        public void render () {
            Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
            g.clearRect(0, 0, WIDTH, HEIGHT);
            //draw the images
            g.drawImage(backgroundImage, 0, 0, WIDTH, HEIGHT, null);
            // g.drawImage(starPic, star.xpos, star.ypos, star.width, butterfly.height, null, null);
            //g.drawImage(moonPic, moon.xpos, moon.ypos, moon.width, moon.height, null, null);

            for (int h = 0; h < stars.length; h = h + 1) {
                //g.drawRect(stars[h].rec.x, stars[h].rec.y, stars[h].rec.width, stars[h].rec.height);
                g.drawImage(starPic, stars[h].xpos, stars[h].ypos, stars[h].width, stars[h].height, null, null);
            }

            if (butterfly.isAlive == true) {
               g.drawImage(butterflyPic, butterfly.xpos, butterfly.ypos, butterfly.width, butterfly.height, null, null);
                //g.drawRect(butterfly.rec.x, butterfly.rec.y, butterfly.rec.width, butterfly.rec.height);
            }
            g.drawString("Avoid the Bees!",500,30);

            g.setColor(Color.BLACK);
            g.drawString("Lives: " + lives, 930, 30);
            //do overpicture (game over here)
            if (lives <= 0) {
                g.drawImage(gameOver, 0, 0, WIDTH, HEIGHT, null);
            }
            g.dispose();
            bufferStrategy.show();
        }

        //Pauses or sleeps the computer for the amount specified in milliseconds
        public void pause ( int time){
            try {
                Thread.sleep(time);
            } catch (InterruptedException e) {
            }
        }


        //Graphics setup method
        private void setUpGraphics () {
            frame = new JFrame("Application Template");   //Create the program window or frame.  Names it.
            panel = (JPanel) frame.getContentPane();  //sets up a JPanel which is what goes in the frame
            panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));  //sizes the JPanel
            panel.setLayout(null);   //set the layout

            // creates a canvas which is a blank rectangular area of the screen onto which the application can draw
            // and trap input events (Mouse and Keyboard events)
            canvas = new Canvas();
            canvas.setBounds(0, 0, WIDTH, HEIGHT);
            canvas.setIgnoreRepaint(true);

            panel.add(canvas);  // adds the canvas to the panel.

            // frame operations
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //makes the frame close and exit nicely
            frame.pack();  //adjusts the frame and its contents so the sizes are at their default or larger
            frame.setResizable(false);   //makes it so the frame cannot be resized
            frame.setVisible(true);      //IMPORTANT!!!  if the frame is not set to visible it will not appear on the screen!

            canvas.addKeyListener(this);
            // sets up things so the screen displays images nicely.
            canvas.createBufferStrategy(2);
            bufferStrategy = canvas.getBufferStrategy();
            canvas.requestFocus();
            System.out.println("DONE graphic setup");
        }


        @Override
        public void keyTyped (KeyEvent e){
//we can ignore method but DO NOT delete it
        }

        @Override
        public void keyPressed (KeyEvent e){
            char key = e.getKeyChar();//this gets the character of the button you press and saves it under "key"
            int keyCode = e.getKeyCode();//this gets number associated with the button you press
            System.out.println("Key Pressed: " + key + ", Code: " + keyCode);

            if (keyCode == 39) { //right arrow
                butterfly.dx = 2;
            }
            if (keyCode == 38) { //up arrow
                butterfly.dy = -2;
            }
            if (keyCode == 37) { // left arrow
                butterfly.dx = -2;

            }
            if (keyCode == 40) { //down arrow =40
                butterfly.dy = 2;
            }
        }
        @Override
        public void keyReleased (KeyEvent e){
            char key = e.getKeyChar();//this gets the character of the button you press and saves it under "key"
            int keyCode = e.getKeyCode();//this gets number associated with the button you press
            System.out.println("Key Released: " + key + ", Code: " + keyCode);
            if (keyCode == 39) {
                butterfly.dx = 0;
            }
            if (keyCode == 40) {
                butterfly.dy = 0;
            }
            if (keyCode == 37) {
                butterfly.dx = 0;
            }
            if (keyCode == 38) {
                butterfly.dy = 0;
            }
        }
    }