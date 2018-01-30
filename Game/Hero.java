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

    private static Background bg1 = Game.getBg1 ();
    private static Background bg2 = Game.getBg2 ();

    private int speedX = 0;
    private int speedY = 1;
    private Sprite img;
    private int xx;
    private int yy;
    private String ImageFile = "graphics/player/idle.png";
    private int a;
    Drawable obj;
    BiConsumer<Float, Float> setPosition;
    Background bg = new Background (0,0);
    private int backy=0;
    Sprite hero;



    public Hero(int x, int y) {

        xPosition = x;
        yPosition = y;

        Texture imgTexture = new Texture ();
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
        speedX = -6;
        Texture r1 = changeImg("graphics/player/leftWalk1.png");
        img = new Sprite(r1);
    }

    public void moveRight() {
        speedX = 6;
        Texture r1 = changeImg("graphics/player/rightWalk1.png");
        img = new Sprite(r1);
        backy+=6;

    }

    public void idle() {
        speedX = 0;
        Texture i = changeImg("graphics/player/idle.png");
        img = new Sprite(i);

    }





    public void jump() {
        if (jumped == false) {
            speedY = -15;
            jumped = true;
        }

    }
    public void update(RenderWindow window) {

        bg.update ();

        if (speedX < 0) {
            centerX += speedX;
        } 

		else {
            if (centerX <= 500) {
                centerX += speedX;
            } else {
                hero.setPosition (-backy,0);

            }
        }

        // Updates Y Position

        if (centerY + speedY >= 700) {
            centerY = 700;
        }else{
            centerY += speedY;
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

    public static void main(String[] args){
        Hero x = new Hero(700,200);
    }

}
