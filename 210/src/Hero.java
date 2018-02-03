import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;
import org.jsfml.window.Keyboard;
import org.jsfml.window.VideoMode;
import org.jsfml.window.WindowStyle;
import org.jsfml.window.event.Event;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.function.BiConsumer;

public class Hero {

    enum State {
        MENU, GAME
    }

    State playerChoice = State.GAME;

    public State getState() {
        return playerChoice;
    }

    private String Title = "Let's Play!";
    private static int fontSize = 48;
    private int screenWidth = 1600;
    private int screenHeight = 900;
    private RenderWindow window = new RenderWindow ();
    public int xPosition;
    public int yPosition;
    private int centerX = 100;
    private int centerY = 748;
    private boolean jumped = false;



    private int speedX = 0;

    public int getSpeedX() {
        return speedX;
    }

    public int getSpeedY() {
        return speedY;
    }

    private int speedY = 1;
    private Sprite img;
    private int xx;
    private int yy;
    private String ImageFile = "./graphics/player/idle.png";
    private int a;
    Drawable obj;
    BiConsumer<Float, Float> setPosition;
    Background bg = new Background (0,0);
    private int backy=0;
    private int backy2=0;
    Sprite hero;
    Game game;

    public Texture getImgTexture() {
        return imgTexture;
    }

    Texture imgTexture;

    public FloatRect getRect1() {
        return rect1;
    }

    FloatRect rect1 = new FloatRect (0,0,0,0);
    Sprite rects;

    public Hero(int x, int y) {


        this.centerX = x;
        this.centerY = y;
        rect1 = new FloatRect (x,y,80,110);

         imgTexture = new Texture ();
        try {
            imgTexture.loadFromFile (Paths.get (ImageFile));
        } catch (IOException ex) {
            ex.printStackTrace ();
        }
        imgTexture.setSmooth (true);

        img = new Sprite (imgTexture);
        img.setOrigin (Vector2f.div (
                new Vector2f (imgTexture.getSize ()), 2));

        img.setPosition (x, y);
        //
        // Store references to object and key methods
        //
        obj = img;
        setPosition = img::setPosition;
        System.out.println (x+"x"+y+"y");
        if(check(x+1,y-1)){
            System.out.println ("COllsion ffs");
        }
        hero = new Sprite (bg.loadTextures ());
        hero.setOrigin(Vector2f.div(new Vector2f(bg.getTexture1 ()), 1000000));
    }
    public void draw(RenderWindow window){

        window.draw(obj);
    }



    public Texture changeImg(String ImageFile2) {
        Texture imgTexture = new Texture ();
        try {
            imgTexture.loadFromFile (Paths.get (ImageFile2));
        } catch (IOException ex) {
            ex.printStackTrace ();
        }
        imgTexture.setSmooth (true);
        return imgTexture;
    }

    public Sprite image() {
        return img;
    }

    public void moveLeft() {
        if(backy2 >= 0) {
            backy2 -= 6;
        }
        speedX = -6;
        Texture r1 = changeImg("./graphics/player/leftWalk1.png");
        img = new Sprite(r1);
        System.out.println("1.backy2 = " + backy2 + ", backy = " + backy);
    }

    public void moveRight() {
        // maximum distance allowed to go
        if(backy < 4200) {
            speedX = 6;
            Texture r1 = changeImg("./graphics/player/rightWalk1.png");
            img = new Sprite(r1);
            if(backy2 <= 420) {
                backy2 += 6;
            }
            else {
                backy += 6;
            }
            System.out.println("2.backy2 = " + backy2 + ", backy = " + backy);
            bg.update();
        }
    }

    public void idle() {
        speedX = 0;
        Texture i = changeImg("./graphics/player/idle.png");
        img = new Sprite(i);
        backy-=0;
    }





    public void jump() {
        if (jumped == false) {
            speedY = -15;
            jumped = true;
        }

    }
    public void update(RenderWindow window,Enemy x) {
        FloatRect ins = rect1.intersection (x.getRect ());


        bg.update ();

        if (speedX < 0) {
            centerX += speedX;
            rect1= new FloatRect (centerX,centerY,80,110);
        } 

		else {
            if (centerX <= 500) {
                centerX += speedX;
                rect1= new FloatRect (centerX,centerY,80,110);
            } else {

                hero.setPosition(-backy,0);

            }
        }

        // Updates Y Position

        if (centerY + speedY >= 700) {
            centerY = 700;
            rect1= new FloatRect (centerX,centerY,80,110);
        }else{
            centerY += speedY;
            rect1= new FloatRect (centerX,centerY,80,110);
        }

        // Handles Jumping
        if (jumped == true) {
            speedY += 1;

            if (centerY + speedY >= 700) {
                centerY = 700;
                speedY = 0;

                jumped = false;
            }

        }

        // Prevents going beyond X coordinate of 0
        if (centerX + speedX <= 60) {
            centerX = 59;
        }
        window.draw(hero);

    }
    public int getCenterX(){
        return centerX;
    }
    public int getCenterY(){
        return centerY;
    }

    public int setCenterX(int ab) {
        centerX = ab;
        return centerX;
    }

    public int setCenterY(int ab2) {
        centerY = ab2;
        return centerY;
    }

    public Background getBg(){
        return bg;
    }
    public int getBacky(){
        return backy;
    }

    public int getBacky2(){
        return backy2;
    }

    public void setBacky(int ab) {
        backy = ab;
    }

    public void setBacky2(int ab) {
        backy2 = ab;
    }

    public boolean check(int x,int y){
        if(rect1.contains (centerX,centerY)){
            return true;
        }
        return false;
    }
    public static void main(String[] args){
        Hero x = new Hero(700,200);
    }

}
