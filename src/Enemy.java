import org.jsfml.audio.Music;
import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Random;

/**
 *Creates an Enemy
 *
 * @version  1.0 Build 1 Feb 12, 2018.
 */

public class Enemy {
    public String name; //name of enemy
    private int x; //position
    private int y;
    FloatRect rect; //collision rectangle
    Vector2f x1; //vector
    private String FontFile  = "font/FreeSans.ttf"; //font
    RectangleShape rectangle;
    private int maxHealth, currentHealth, power, speedX, centerX, centerY;
    Game game;
    protected Drawable obj;
    private String ImageFile="./graphics/characters/boss/miniGeneral/boss1.png"; //get bad guy image

    private Sprite img;
    Random rand = new Random();
    Music s = new Music();

    /**
     * Creates an Enemy
     *
     * @param x x position
     * @param y y position
     * @param hero The hero
     * @param name Name of bad guy
     */
    public Enemy(int x,int y,Hero hero,String name ){
        this.name=name;
        this.x=x;
        this.y=y;

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
        this.currentHealth = 100;
        x1 = (Vector2f.div(new Vector2f(imgTexture.getSize ()), 1));
    }

    /**
     * get image of Enemy
     *
     * @return img
     */
    public Sprite image(){
        return img;
    }

    /**
     * Change image of bad guy for fluid movement
     *
     * @param ImageFile2 image of bad guy
     * @return imgTexture
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
    public void update(Hero hero,RenderWindow window) {
        if(hero==null){
            System.out.println ("I am hero I am null");
        }
        if(hero.getBg ()==null){
            System.out.println ("I am background I dont exist :(");
        }
        if(this.currentHealth > 0) {
            rect = new FloatRect(x - hero.getBg().getBackX(), y, 96, 96);
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
            healthbar.setPosition(x-hero.getBg ().getBackX(), y-15);
            if(this.getCurrentHealth() <= 50) {
                healthbar.setColor(Color.YELLOW);
            }
            if(this.getCurrentHealth() <= 25) {
                healthbar.setColor(Color.RED);
            }
            window.draw(healthbar);
        }
       //  rectangle = new RectangleShape(x1);
       //  rectangle.setPosition (this.x,this.y);
       //  rectangle.setFillColor (Color.RED);
        FloatRect ins = hero.getRect1 ().intersection (rect);
           // System.out.println ("Enemy "+"height "+rect.height+"width "+rect.width+"left "+rect.left+"top "+rect.top);
           // System.out.println ("Hero "+"height "+hero.getRect1 ().height+"width "+hero.getRect1 ().width+"left "+hero.getRect1 ().left+"top "+hero.getRect1 ().top);
        if(hero.getCenterX ()>500){
            img.setPosition (x-hero.getBg ().getBackX (),y);
        }

        window.draw (img);

        if(this.currentHealth <= 0 && this.currentHealth > -9000) {
            try {
                s.openFromFile(Paths.get("./audio/dead.wav"));
            } catch(IOException ex) {
                //"Houston, we have a problem."
                ex.printStackTrace();
            }
            s.play();
            this.rect = new FloatRect(0,0,0,0);
            this.image().rotate(90);
            this.setCurrentHealth(-10000);
        }
    }

    /**
     * get max health
     *
     * @return maxHealth
     */
    public int getMaxHealth() {
        return maxHealth;
    }

    /**
     * get current health
     *
     * @return currentHealth
     */
    public int getCurrentHealth() {
        return currentHealth;
    }

    /**
     * get powers
     *
     * @return power
     */
    public int getPower() {
        return power;
    }

    /**
     * get rectangle for collisions
     *
     * @return rect
     */
    public FloatRect getRect(){
        return rect;
    }

    /**
     * get speed of travel x
     *
     * @return speedX
     */
    public int getSpeedX() {
        return speedX;
    }

    /**
     * get centre X
     *
     * @return centerX
     */
    public int getCenterX() {
        return centerX;
    }

    /**
     * get centre Y
     *
     * @return centerY
     */
    public int getCenterY() {
        return centerY;
    }

    /**
     * Set an object
     *
     * @param obj object
     */
    public void setObj(Drawable obj){
       this.obj=obj;
    }

    /**
     * Set the health
     *
     * @param maxHealth health of Enemy
     */
    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    /**
     * Set the current health
     *
     * @param currentHealth current health of Enemy
     */
    public void setCurrentHealth(int currentHealth) {
        this.currentHealth = currentHealth;
    }

    /**
     * Set powers
     *
     * @param power power enemy has
     */
    public void setPower(int power) {
        this.power = power;
    }

    /**
     * set the speed
     *
     * @param speedX speed of x travel
     */
    public void setSpeedX(int speedX) {
        this.speedX = speedX;
    }

    /**
     * set the x center
     *
     * @param centerX center of X
     */
    public void setCenterX(int centerX) {
        this.centerX = centerX;
    }

    /**
     * set the y center
     *
     * @param centerY center of Y
     */
    public void setCenterY(int centerY) {
        this.centerY = centerY;
    }

    /**
     * draw the window
     *
     * @param window The window
     */
    public void draw(RenderWindow window){
        window.draw(obj);
    }
}
