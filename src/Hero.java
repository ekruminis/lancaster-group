import org.jsfml.graphics.*;
import org.jsfml.system.Clock;
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








    // hero X and Y coordinates
    private int centerX = 100;
    private int centerY = 748;


    private boolean jumped = false;


    private Enemy collidingEntity;
    private Box collidingEntity2;


    private String FontFile  = "font/FreeSans.ttf";


    private int speedX = 0;
    private int speedY = 1;

    private Sprite img;

    private String ImageFile = "./graphics/player/idle.png";

    private Drawable obj;
    private BiConsumer<Float, Float> setPosition;
    private Background bg = new Background (0,0);
    private int backy=0;
    private Sprite background;



    private boolean collidedTop = false;

    private int startScrolling=500;
    private int maxHealth, currentHealth;
    public Texture getImgTexture() {
        return imgTexture;
    }

    private Texture imgTexture;

    public FloatRect getRect1() {
        return rect1;
    }

    //Rectangle for collisions (surrounds hero)

    FloatRect rect1 = new FloatRect (100,700,80,110);



    private boolean collide=false;

    public Hero(int x, int y) {


        this.centerX = x;
        this.centerY = y;
        this.currentHealth = 100;
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



        background = new Sprite (bg.loadTextures ());
        background.setOrigin(Vector2f.div(new Vector2f(bg.getTexture1 ()), 1000000));
        background.setPosition (0,0);


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
           Texture r1 = changeImg ("./graphics/player/leftWalk1.png");
           img = new Sprite (r1);


    }

    public void moveRight() {

            speedX = 6;
            Texture r1 = changeImg ("./graphics/player/rightWalk1.png");
            img = new Sprite (r1);

            if (centerX > startScrolling)
                bg.setBackX (bg.getBackX () + 6);

            bg.update ();

    }

    public void idle() {
        speedX = 0;
        Texture i = changeImg("./graphics/player/idle.png");
        img = new Sprite(i);

    }





    public void jump() {
        if (jumped == false) {
            speedY = -20;
            jumped = true;
        }

    }
    public void update(RenderWindow window) {



        bg.update ();
        //update X and scroll background accordingly
        updateXPosition ();

        // Updates Y Position
        updateYPosition ();


        // Handles Jumping
        handleJumping ();

        // Prevents going beyond X coordinate of 0
        if (centerX + speedX <= 60) {
            centerX = 60;
        }

        Font fontstyle = new Font();
        try {
            fontstyle.loadFromFile(
                    Paths.get(FontFile));
        } catch (IOException ex) {
            ex.printStackTrace( );
        }
        Text healthbar = new Text(("health: " + String.valueOf(getCurrentHealth())), fontstyle, 15);

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
    public void updateYPosition(){
        if (centerY + speedY >= 700) {
            centerY = 700;

            rect1= new FloatRect (centerX,centerY,80,110);
        }else if(!collide){

            centerY += speedY;

            rect1= new FloatRect (centerX,centerY,80,110);
        }else if((jumped && collide && Keyboard.isKeyPressed (Keyboard.Key.W )&& collidedTop)){

            speedY=-20;
            centerY +=speedY;
        }
    }
    public void updateXPosition(){
        if (speedX < 0) {
            centerX += speedX;
            rect1= new FloatRect (centerX,centerY,80,110);
        } else {
            if (centerX <= startScrolling) {
                centerX += speedX;
                rect1= new FloatRect (centerX,centerY,80,110);
            } else {

                background.setPosition((-bg.getBackX ()),0);

            }
        }
    }
    public void handleJumping(){

        if ( !collide) {
            speedY += 1;

            if ((centerY + speedY >= 700 || collide )) {
                centerY = 700;
                speedY = 0;

                jumped = false;
            }

        }

    }
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

    public void noCollision(){
        this.collidingEntity2=null;
        this.collidingEntity=null;
        collidedTop=false;
        collide=false;

    }

    public void checkLeftCollision(FloatRect x){
        if((int)rect1.left < x.left ) {
            centerX -= 6;


        }
    }
    public void checkRightCollision(FloatRect x) {
        if((int)rect1.left+rect1.width > x.left+x.width ){
            centerX+=6;
        }
        }

    public void checkTopCollision(FloatRect x){
        //System.out.println (jumped);

        if((int)rect1.top<= (int)x.top-x.height ){
            collide=true;
            collidedTop=true;
            //System.out.println(collidedTop);

        }
    }

    public void bounce(Enemy e) {
        FloatRect x = e.getRect();
        FloatRect ins = rect1.intersection (x);
        // ???????? yeah i know.. this basically checks if the player is above the enemy but between him.. then it automatically jumps and deals dmg.. i have no idea why i divided width by 2 and
        // then added it to player, but it makes the collision more accurate..
        if(((int)getCenterY() <= (int)e.getRect().top-(int)e.getRect().height) && ((int)getCenterX()+((int)e.getRect().width/2) <= (int)e.getRect().left+(int)e.getRect().width ) && ((int)getCenterX()+((int)e.getRect().width/2) >= (int)e.getRect().left) ){
            if(ins!=null) {
                // for some reason it stops jumping after jumping 15 or so times????? idk why.. it also slides on x-axis a tiny bit when close to the very corners
                // probably not an issue as enemy will be moving, so getting 10+ jumps on him should be impossible and sliding is actually good.
                e.setCurrentHealth(e.getCurrentHealth()-5); // 5 is dmg dealt
                speedY = -20;
                centerY += speedY;
            }
        }
    }


    public int getCenterX(){
        return centerX;
    }
    public int getCenterY(){
        return centerY;
    }

    public Background getBg(){
        return bg;
    }


    public int getMaxHealth() {
        return maxHealth;
    }


    public int getCurrentHealth() {
        return currentHealth;
    }
    public static void main(String[] args){
        Hero x = new Hero(700,200);
    }

}
