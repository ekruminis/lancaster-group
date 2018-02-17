import org.jsfml.graphics.*;
import org.jsfml.system.Clock;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;
import org.jsfml.window.Keyboard;
import org.jsfml.window.VideoMode;
import org.jsfml.window.WindowStyle;
import org.jsfml.window.event.Event;
import org.jsfml.audio.*;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.function.BiConsumer;

/**
 *Layers a square over a selection of textures to give the hero fluid movement and collection detection
 *
 * @version  1.0 Build 1 Feb 12, 2018.
 */

public class Hero {

    private int centerX;            //hero X and Y coordinates
    private int centerY;
    private boolean jumped = false; //initiate jumped as false
    private Enemy collidingEntity;  //collisions
    private Box collidingEntity2;
    private String FontFile  = "font/FreeSans.ttf";  //get font(used for health bar)
    private int speedX = 0;  //speed
    private int speedY = 1;
    private Sprite img;
    private String ImageFile = "./graphics/characters/player/idle.png"; //get hero graphic
    private Drawable obj;
    private BiConsumer<Float, Float> setPosition;
    private Background bg = new Background (0,0);
    private int backy=0;
    private Sprite background;
    private boolean collidedTop = false;
    private int startScrolling=500;
    private int maxHealth, currentHealth;
    private Texture imgTexture;
    private boolean collide=false;
    Music s = new Music();
    Music s2 = new Music();

    /**
     *get the textures
     *
     * @return imgTexture
     */
    public Texture getImgTexture() {
        return imgTexture;
    }

    /**
     *get a rectangle
     *
     * @return rect1
     */
    public FloatRect getRect1() {
        return rect1;
    }

    //Rectangle for collisions (surrounds hero)
    FloatRect rect1 = new FloatRect (0,0,0,0);

    /**
     *Loads the textures and position of the Hero
     *
     * @param x  x co-ordinates
     * @param y  y co-ordinates
     */
    public Hero(int x, int y) {
        this.centerX = x;
        this.centerY = y;
        this.currentHealth = 100;
        rect1 = new FloatRect (100,630,80,110);
         imgTexture = new Texture ();
        try {
            imgTexture.loadFromFile (Paths.get (ImageFile));
        } catch (IOException ex) {
            ex.printStackTrace ();
        }
        imgTexture.setSmooth (true);

        img = new Sprite (imgTexture);
        img.setOrigin (Vector2f.div (new Vector2f (imgTexture.getSize ()), 2));
        img.setPosition (x, y);
        // Store references to object and key methods
        obj = img;
        setPosition = img::setPosition;

        background = new Sprite (bg.loadTextures ());
        background.setOrigin(Vector2f.div(new Vector2f(bg.getTexture1 ()), 1000000));
        background.setPosition (0,0);
    }

    //never used..ignore
    public void draw(RenderWindow window){
        window.draw(obj);
    }


    /**
     * Change the texture for Hero
     *
     * @param ImageFile2 file location
     * @return imgTexture return the image
     */
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

    /**
     *Return sprite image
     *
     * @return img
     */
    public Sprite image() {
        return img;
    }

    /**
     * Move the Hero left
     *
     */
    public void moveLeft() {
           speedX = -6;
           Texture r1 = changeImg ("./graphics/characters/player/leftWalk1.png");
           img = new Sprite (r1);
    }

    /**
     *Move the Hero right
     *
     */
    public void moveRight() {
            speedX = 6;
            Texture r1 = changeImg ("./graphics/characters/player/rightWalk1.png");
            img = new Sprite (r1);
            if (centerX > startScrolling)
                bg.setBackX (bg.getBackX () + 6);
            bg.update ();
    }

    /**
     *Hero stays still
     *
     */
    public void idle() {
        speedX = 0;
        Texture i = changeImg("./graphics/characters/player/idle.png");
        img = new Sprite(i);
    }

    /**
     *Hero Jumps
     *
     */
    public void jump() {
        if (jumped == false) {
            speedY = -20;
            jumped = true;
        }
    }

    /**
     *Update the window about the Hero's position
     *
     * @param window the current window
     */
    public void update(RenderWindow window) {
        bg.update (); //update X and scroll background accordingly
        updateXPosition ();
        updateYPosition ();  // Updates Y Position
        handleJumping (); // Handles Jumping

        Font fontStyle = new Font();  //load font
        try {
            fontStyle.loadFromFile(Paths.get(FontFile));
        } catch (IOException ex) {
            ex.printStackTrace( );
        }

        Text healthbar = new Text(("health: " + String.valueOf(getCurrentHealth())), fontStyle, 15);

        healthbar.setColor(Color.GREEN);
        healthbar.setStyle(Text.BOLD | Text.UNDERLINED);
        healthbar.setPosition(centerX, centerY-15);

        if(this.getCurrentHealth() <= 50) {
            healthbar.setColor(Color.YELLOW);
        }
        if(this.getCurrentHealth() <= 25) {
            healthbar.setColor(Color.RED);
        }

        window.draw(background);
        window.draw(healthbar);
    }

    /**
     *Update the Y position of the hero
     *
     */
    public void updateYPosition(){
        if (centerY + speedY >= 630) {
            centerY = 630;

            rect1= new FloatRect (centerX,centerY,80,110);
        }else if(!collide){

            centerY += speedY;

            rect1= new FloatRect (centerX,centerY,80,110);
        }
        else if((jumped && collide && Keyboard.isKeyPressed (Keyboard.Key.W )&& collidedTop)){
            speedY=-20;
            centerY +=speedY;
        }
    }

    /**
     *Update the X position of the hero
     *
     */
    public void updateXPosition(){
        if (centerX + speedX <= 60) {
            centerX = 60;
        }
        if(centerX + bg.getBackX() > 4890) {
            bg.setBackX(4890-startScrolling);
            if(centerX + speedX >= 1500) {
                centerX = 1500;
                rect1= new FloatRect (centerX,centerY,80,110);
            }
            if(centerX < 1504) {
                centerX+=speedX;
                rect1= new FloatRect (centerX,centerY,80,110);
            }
        }
        else if (speedX < 0) {
            centerX += speedX;
            rect1= new FloatRect (centerX,centerY,80,110);
        }
        else {
            if (centerX <= startScrolling) {
                centerX += speedX;
                rect1= new FloatRect (centerX,centerY,80,110);
            } else {
                background.setPosition((-bg.getBackX ()),0);
            }
        }
    }

    /**
     *Allows the Hero to Jump
     *
     */
    public void handleJumping(){

        if ( !collide) {
            speedY += 1;
            if ((centerY + speedY >= 630 || collide )) {
                centerY = 630;
                speedY = 0;
                jumped = false;
            }
        }
    }

    /**
     *Check for anything that collides with the hero
     *
     * @param box collision box over character
     */
    public void checkCollision(Box box){
        if(collidingEntity2!=null && collidingEntity2!=box){
            return;
        }
        FloatRect x = box.getRect ();

        if (rect1==null || x==null){
            return;
        }

        FloatRect ins = rect1.intersection (x);

        if(ins!=null) {
            this.collidingEntity2= box;
            collide=true;
            checkTopCollision (x);
            if(collidedTop==false) {
                checkRightCollision (x);
                checkLeftCollision (x);
            }
        } else {
            noCollision ();
        }
    }

    /**
     *Check for collisions with enemy
     *
     * @param enemy the enemy
     */
    public void checkCollision(Enemy enemy){
        if(collidingEntity!=null && collidingEntity!=enemy){
            return;
        }
        FloatRect x = enemy.getRect ();

        if (rect1==null || x==null){
            return;
        }

        FloatRect ins = rect1.intersection (x);

        if(ins!=null) {
            this.collidingEntity= enemy;
            collide=true;
            checkRightCollision (x);
            checkLeftCollision (x);
            bounce(enemy);
        } else {
            noCollision ();
        }
    }

    /**
     *Set parameters for no collision
     *
     */
    public void noCollision(){
        this.collidingEntity2=null;
        this.collidingEntity=null;
        collidedTop=false;
        collide=false;
    }

    /**
     *Check for collisions on left
     *
     * @param x the rectangle
     */
    public void checkLeftCollision(FloatRect x){
        if((int)rect1.left < x.left ) {
            centerX -= 6;
        }
    }

    /**
     *Check for collisions on right
     *
     * @param x the rectangle
     */
    public void checkRightCollision(FloatRect x) {
        if((int)rect1.left+rect1.width > x.left+x.width ){
            centerX+=6;
        }
    }

    /**
     *Check for collisions on top
     *
     * @param x the rectangle
     */
    public void checkTopCollision(FloatRect x){
        if((int)rect1.top<= (int)x.top-x.height ){
            collide=true;
            collidedTop=true;
        }
    }

    /**
     *Bounce on Enemy
     *
     * @param e Enemy
     */
    public void bounce(Enemy e) {
        FloatRect x = e.getRect();
        FloatRect ins = rect1.intersection (x);
        if(((int)getCenterY() <= (int)e.getRect().top-(int)e.getRect().height) && ((int)getCenterX()+((int)e.getRect().width/2) <= (int)e.getRect().left+(int)e.getRect().width ) && ((int)getCenterX()+((int)e.getRect().width/2) >= (int)e.getRect().left) ){
            if(ins!=null) {
                try {
                    s.openFromFile(Paths.get("./audio/jumphit.wav"));
                } catch(IOException ex) {
                    //"Houston, we have a problem."
                    ex.printStackTrace();
                }
                s.play();
                e.setCurrentHealth(e.getCurrentHealth()-5); // 5 is dmg dealt
                try {
                    s2.openFromFile(Paths.get("./audio/hit.wav"));
                } catch(IOException ex) {
                    //"Houston, we have a problem."
                    ex.printStackTrace();
                }
                s2.play();
                speedY = -20;
                centerY += speedY;
            }
        }
    }

    /**
     *Get the Center x of the quadrilateral
     *
     * @return centerX
     */
    public int getCenterX(){
        return centerX;
    }

    public void setSpeedY(int a) {
        speedY = a;
    }

    public int getSpeedY() {
        return speedY;
    }

    public void setCenterY(int a) {
        centerY = a;
    }

    /**
     *Get the Center y of the quadrilateral
     *
     * @return centerY
     */
    public int getCenterY(){
        return centerY;
    }

    /**
     *Get the background
     *
     * @return bg
     */
    public Background getBg(){
        return bg;
    }

    /**
     * Get the Health of Hero
     *
     * @return maxHealth
     */
    public int getMaxHealth() {
        return maxHealth;
    }

    /**
     *Get the current health of the Hero
     *
     * @return currentHealth
     */
    public int getCurrentHealth() {
        return currentHealth;
    }
}
