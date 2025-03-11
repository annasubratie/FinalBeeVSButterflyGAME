import java.awt.*;//brings in tools needed to use Rectangle and Image

public class Character {
    //declare variable
    public int xpos;
    public int ypos;
    public int dx;//speed and direction of x
    public int dy;//speed and direction of y
    public int width;
    public int height;
    public boolean isAlive;
    public Rectangle rec;
    public Image pic;

    //have a constructor
    public Character(int pXpos, int pYpos, int pDx, int pDy){
xpos=pXpos;
ypos=pYpos;
dx=pDx;
dy=pDy;
width = 200;
height=100;
isAlive=true;
rec=new Rectangle(xpos,ypos,width,height);
    }// p is parameter

    //printInfo
    //do this now
    public void printInfo(){
        System.out.println("X position: " + xpos);
        System.out.println("Y position: " + ypos);
        System.out.println("x speed: "+dx);
        System.out.println("y speed: " + dy);
        System.out.println("width: " + width);
        System.out.println("height: " + height);
        System.out.println("Is your hero alive: "+isAlive);
    }//do not do same for Rectangle and Image because not done it that way
    public void bounceMove(){
        if (xpos > (1000-width)){
            dx=(-1)*(dx);
        }
        if (xpos<0) {
            dx=(-1)*(dx);
        }
        if(ypos > (700-height)){
            dy=(-1)*(dy);
        }
        if(ypos<0){
            dy=(-1)*(dy);
        }
        xpos = xpos+dx;
        ypos= ypos +dy;
        rec=new Rectangle (xpos,ypos,width,height);

    }

    public void wrapMove(){
        //if character goes off of the screen it comes back on other side of screen
        if(xpos>1000){
            xpos=0-width;
        }
        if(ypos>700){
          ypos=0-height;
        }
        if(xpos<0 &&dx<0){
            xpos=1000;
        }
        if(ypos<0&&dy<0){
            ypos=700;
        }
        xpos = xpos+dx;
        ypos= ypos+dy;
        rec=new Rectangle (xpos,ypos,width,height);

       // if (astro.xpos=star.xpos){

       // }
    }
}
//this class is for creating objects and the GameLand uses them which has all the game mechanics