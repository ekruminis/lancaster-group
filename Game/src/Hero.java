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
    private Enemy collidingEntity;



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
    Sprite hero;
    Game game;
    FloatRect heroBounds;
    private boolean collidedLeft = false;
    private boolean collidedRight = false;
    private boolean collidedTop = false;

    private int startScrolling=500;

    public Texture getImgTexture() {
        return imgTexture;
    }

    Texture imgTexture;

    public FloatRect getRect1() {
        return rect1;
    }

    FloatRect rect1 = new FloatRect (100,700,80,110);
    Sprite rects;
    Clock frameClock;
    public boolean isCollide() {
        return collide;
    }

    private boolean collide=false;
    public Hero(int x, int y) {


        this.centerX = x;
        this.centerY = y;

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
        hero.setPosition (0,0);

        heroBounds = img.getGlobalBounds ();
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
    public void shoot() {
        Projectiles pro = new Projectiles (0,0,this);

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
    public void update(RenderWindow window,Enemy x) {
        //checkCollision (x);

       // System.out.println (centerY);
        bg.update ();
        //update X and scroll background accordingly
        updateXPosition ();

        // Updates Y Position
        updateYPosition ();


        // Handles Jumping
        handleJumping ();
      /*  if(collide){
            jumped=false;
        }*/
        // Prevents going beyond X coordinate of 0
        if (centerX + speedX <= 60) {
            centerX = 59;
        }
        window.draw(hero);

    }
    public void updateYPosition(){
        if (centerY + speedY >= 700) {
            centerY = 700;

            rect1= new FloatRect (centerX,centerY,80,110);
        }else if(!collide ){
            System.out.println ("2");
            centerY += speedY;
            System.out.println ("");
            rect1= new FloatRect (centerX,centerY,80,110);
        }else if(jumped && collide && Keyboard.isKeyPressed (Keyboard.Key.W )&& collidedTop){
            System.out.println ("jajajajaajajajaa");
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

                hero.setPosition((-bg.getBackX ()),0);

            }
        }
    }
    public void handleJumping(){

        if ( !collide) {
            speedY += 1;

            if (centerY + speedY >= 700 || collide ) {
                centerY = 700;
                speedY = 0;

                jumped = false;
            }

        }

    }
    public void checkCollision(Enemy enemy){
            if(collidingEntity!=null && collidingEntity!=enemy){
                return;
            }
            FloatRect x = enemy.getRect ();

        //System.out.println ("Enemy" +((int)x.top-x.height));
        //System.out.println (rect1.top+"hero");
            if (rect1==null || x==null){
                return;
            }

            FloatRect ins = rect1.intersection (x);

            if(ins!=null) {
                this.collidingEntity= enemy;
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
    public void noCollision(){
        this.collidingEntity=null;
        collidedTop=false;
        collide=false;

    }

    public void checkLeftCollision(FloatRect x){
        if((int)rect1.left < x.left ) {
            centerX -= 6;
            collidedLeft=true;

        }
    }
    public void checkRightCollision(FloatRect x) {
        if((int)rect1.left+rect1.width > x.left+x.width ){
            centerX+=6;
        }
        }

    public void checkTopCollision(FloatRect x){
        System.out.println (jumped);

        if((int)rect1.top<= (int)x.top-x.height ){
            collide=true;

            collidedTop=true;


        }
    }
        public void setXYPosition(int x,int y){
            centerY=y;
            centerX=x;
        }




    public void setCenterX(int centerX) {
        this.centerX = centerX;
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
    public int getBacky(){
        return backy;
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
