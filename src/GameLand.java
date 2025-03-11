
    //Basic Game Application
// Basic Object, Image, Movement
// Threaded

//******************************************************************************
//Import Section
//Add Java libraries needed for the game
//import java.awt.Canvas;

//Graphics Libraries
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;

//*******************************************************************************

    public class GameLand implements Runnable {

        //Variable Definition Section
        //Declare the variables used in the program
        //You can set their initial values too
        public Character butterfly;
        public Image butterflyPic;
        public Character star;
        public Image starPic;
        public Character moon;
        public Image moonPic;
        public Character sunflower;
        public Image sunflowerPic;
        public Image backgroundImage;
        public boolean butterflyIsintersectionStar;
        public boolean moonIsintersectionsunflower;
        public boolean sunflowerIsIntersectionstar;

        //Sets the width and height of the program window
        final int WIDTH = 1000;
        final int HEIGHT = 700;

        //Declare the variables needed for the graphics
        public JFrame frame;
        public Canvas canvas;
        public JPanel panel;

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
butterfly = new Character(20,21,-5,-6);
butterfly.printInfo();
butterflyPic = Toolkit.getDefaultToolkit().getImage("butterflyPic.png");

star = new Character(30,45,2,3);
star.printInfo();
starPic = Toolkit.getDefaultToolkit().getImage("starPic.png");

moon = new Character(100,100,4,-6);
moon.printInfo();
moonPic = Toolkit.getDefaultToolkit().getImage("moonPic.png");

sunflower = new Character(200,300,1,2);
sunflower.printInfo();
sunflowerPic = Toolkit.getDefaultToolkit().getImage("sunflowerPic.png");


backgroundImage = Toolkit.getDefaultToolkit().getImage("backgroundImage.jpg");
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
            butterfly.wrapMove();
            star.wrapMove();
            moon.bounceMove();
            sunflower.bounceMove();
            //call the move() code for each object
        }

        public void checkIntersections(){// MAKE TWO MORE INTERACTIONS
            //rec has a method called intersects which takes an input of another rec
            if(butterfly.rec.intersects(star.rec)&& butterflyIsintersectionStar==false){
                butterflyIsintersectionStar=true;
                System.out.println("ouch");
                butterfly.height=butterfly.height+30;
                star.width=star.width+100;

            }
            if (butterfly.rec.intersects(star.rec)==false){
                butterflyIsintersectionStar=false;
            }
            System.out.println("sunflower "+sunflower.rec);
            System.out.println("moon "+moon.rec);

            if (moon.rec.intersects(sunflower.rec)&&moonIsintersectionsunflower==false){
                moonIsintersectionsunflower=true;
                System.out.println("oops");
                moon.dx=-moon.dx;
            }
            if (moon.rec.intersects(sunflower.rec)==false){
                moonIsintersectionsunflower=false;
            }
            if (sunflower.rec.intersects(star.rec)&&sunflowerIsIntersectionstar==false){
                sunflowerIsIntersectionstar=true;
                System.out.println("oopsie");
                sunflower.xpos=sunflower.xpos+20;
                star.ypos=sunflower.ypos-20;
            }
        }

        //Paints things on the screen using bufferStrategy
        private void render() {
            Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
            g.clearRect(0, 0, WIDTH, HEIGHT);

            //draw the images
            g.drawImage(backgroundImage,0,0,WIDTH,HEIGHT, null);
            g.drawImage(butterflyPic,butterfly.xpos,butterfly.ypos,butterfly.width,butterfly.height,null,null);
g.drawImage(starPic,star.xpos,star.ypos,star.width,butterfly.height,null,null);
g.drawImage(moonPic,moon.xpos,moon.ypos,moon.width,moon.height,null,null);
g.drawImage(sunflowerPic,sunflower.xpos,sunflower.ypos,sunflower.width,sunflower.height,null,null);
            g.dispose();
            bufferStrategy.show();
        }

        //Pauses or sleeps the computer for the amount specified in milliseconds
        public void pause(int time ) {
            try {
                Thread.sleep(time);
            } catch (InterruptedException e) {
            }
        }

        //Graphics setup method
        private void setUpGraphics() {
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

            // sets up things so the screen displays images nicely.
            canvas.createBufferStrategy(2);
            bufferStrategy = canvas.getBufferStrategy();
            canvas.requestFocus();
            System.out.println("DONE graphic setup");
        }

    }


