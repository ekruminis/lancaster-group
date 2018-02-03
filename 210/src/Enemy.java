import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Random;

public class Enemy {
    private int x;
    private int y;
    int position = 0;
    private boolean collidedLeft = false;
    private boolean collidedRight = false;
    private boolean collidedTop = false;

    private int maxHealth, currentHealth, power, speedX, centerX, centerY;
    Game game;
    protected Drawable obj;
    private String ImageFile="./src/heliboy.png";

    private Sprite img;
    Random rand = new Random();
    FloatRect rect;

    public Enemy(int x,int y,Hero hero ){
        this.x=x;
        this.y=y;
        rect = new FloatRect (x,y,200,200);
        Texture imgTexture = new Texture ();
        try {
            imgTexture.loadFromFile (Paths.get (ImageFile));
        } catch (IOException ex) {
            ex.printStackTrace ();
        }
        imgTexture.setSmooth (true);
        img = new Sprite (imgTexture);
        img.setOrigin(Vector2f.div(new Vector2f(hero.getImgTexture ().getSize ()), 1000000));
        img.setPosition (x,y);





    }

    public Sprite image(){
        return img;
    }
    //

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
    public void update(Hero hero,RenderWindow window) {

        /*if(hero.getCenterX () < x){
            position-=3;
        } else if(hero.getCenterX () > x){
            position+=3;
        } else {

        }*/
            FloatRect ins = hero.getRect1 ().intersection (rect);

           //System.out.println ("Enemy "+"height "+rect.height+"width "+rect.width+"left "+rect.left+"top "+rect.top);
           //System.out.println ("Hero "+"height "+hero.getRect1 ().height+"width "+hero.getRect1 ().width+"left "+hero.getRect1 ().left+"top "+hero.getRect1 ().top);

            if(ins!=null){
                // if hero Y coordinate is above (well.. below) the rect Y cord minus its height.. ie. character jump is higher than rect height.
                if(hero.getRect1().top < (rect.top - rect.height)) {
                    collidedTop = true;
                }
                // if approached from the right side and touches the block wall at -75 (why 75? background moves in 6 or -6 because that's the char speed, and character is closest to the rect size of 80 at 75..
                else if(rect.left > hero.getRect1().left && ((hero.getRect1().left - rect.left) == -75)) {
                    collidedLeft = true;
                    hero.setCenterX(hero.getCenterX() - 6);
                }
                // if approached from the left and ....
                else if(rect.left < hero.getRect1().left && ((hero.getRect1().left - rect.left) == 75)) {  // -75 and +75 is because rect.width is 100
                    collidedRight = true;
                    hero.setCenterX(hero.getCenterX() + 6);
                }
            }

            if(hero.getCenterX ()>500) {
                img.setPosition(x - hero.getBacky() + 1, y);
                rect = new FloatRect(x - hero.getBacky() + 1, y, 100, 100);
            }

            if(collidedLeft == true) {
                // so colliding doesn't affect the background scrolling
                hero.setBacky(hero.getBacky() - 6);
                collidedLeft = false;
            }

            if(collidedRight == true) {
                // so colliding doesn't affect the background scrolling
                hero.setBacky2(hero.getBacky2() + 6);
                collidedRight = false;
            }
            if(collidedTop == true) {
                if((-75 <= (hero.getRect1().left - rect.left))) {
                    // set character on top of the box
                    hero.setCenterY((int)rect.top-(int)rect.height);
                }
                else {
                    // return character to initial position
                    hero.setCenterY((int)rect.top+(int)rect.height);
                    collidedTop = false;
                }

                if((75 >= (hero.getRect1().left - rect.left))) {
                    // set character on top of the box
                    hero.setCenterY((int)rect.top-(int)rect.height);
                }
                else {
                    // return character to initial position
                    hero.setCenterY((int)rect.top+(int)rect.height);
                    collidedTop = false;
                }

            }

            //System.out.println(hero.getCenterY());
            window.draw (img);
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public boolean isCollidedLeft() {
        return collidedLeft;
    }

    public boolean isCollidedRight() {
        return collidedRight;
    }

    public int getPower() {
        return power;
    }
    public FloatRect getRect(){
        return rect;
    }

    public int getSpeedX() {
        return speedX;
    }


    public int getCenterX() {
        return centerX;
    }


    public int getCenterY() {
        return centerY;
    }

    public void setObj(Drawable obj){
       this.obj=obj;
    }



    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }


    public void setCurrentHealth(int currentHealth) {
        this.currentHealth = currentHealth;
    }


    public void setPower(int power) {
        this.power = power;
    }


    public void setSpeedX(int speedX) {
        this.speedX = speedX;
    }


    public void setCenterX(int centerX) {
        this.centerX = centerX;
    }


    public void setCenterY(int centerY) {
        this.centerY = centerY;
    }
    public void draw(RenderWindow window){
        window.draw(obj);
    }
}
