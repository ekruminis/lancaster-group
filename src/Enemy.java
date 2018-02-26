import org.jsfml.audio.Music;
import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;

/**
 *Creates an Enemy
 *
 * @version  1.0 Build 1 Feb 12, 2018.
 */

public class Enemy  extends Animator {
    public int getX() {
        return x;
    }

    private int x; //position

    public int getY() {
        return y;
    }

    private int y;
    FloatRect rect; //collision rectangle
    Vector2f x1; //vector
    private String FontFile  = "font/FreeSans.ttf"; //font
    RectangleShape rectangle;
    private int maxHealth, currentHealth, power, speedX, centerX, centerY;
    Game game;
    protected Drawable obj;
    ArrayList<Projectiles> pro = new ArrayList<>(1);
    Texture imgTexture = new Texture ();

    public FloatRect getRay() {
        return Ray;
    }



    private Sprite img;
    Random rand = new Random();
    Music s = new Music();
    FloatRect circle;
    boolean alive = true;
    private String ImageFile;
    private boolean shot = false;
    private boolean dropped = true;
    Enemy enemy;
    int backx;
    int projType;
    int inity;
    int score = 1;

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }

    private boolean active=false;

    /**
     * Creates an Enemy
     *
     * @param x x position
     * @param y y position
     * @param hero The hero
     */
    FloatRect Ray;
    public Enemy(int x,int y,Hero hero,String bossname){

        this.x=x;
        this.y=y;
        this.inity=y;

        enemy = this;
        getCharInfo(bossname);

        img = new Sprite (getCharImg(bossname));
        img.setOrigin(Vector2f.div(new Vector2f(hero.getImgTexture ().getSize ()), 1000000));
        img.setPosition (x,y);
        //x1 = (Vector2f.div(new Vector2f(imgTexture.getSize ()), 1));
        rect = new FloatRect(x - hero.getBg().getBackX(), y, 96, 96);
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

   public void move(Hero hero,RenderWindow window,Game game){
       if(active) {
           if (alive == true) {
               if (y > inity) {
                   y--;
               }






               img.setPosition(x - hero.getBg().getBackX(), y);
               if (hero.getCenterX() > this.x - hero.getBg().getBackX()) {
                   x += 2;
               } else if (hero.getCenterX() < this.x - hero.getBg().getBackX())
                   x -= 2;
               if (isColliding(game.getBoxes(), img)) {
                   //System.out.println("Oh no Box!");
                   y -= 20;
               } else if (y < inity) {
                   y++;




               }

           } else {
               Ray = null;
               img.setPosition(x - hero.getBg().getBackX(), y);
           }
       } else {

       }
   }
    public void update(Hero hero,RenderWindow window) {
        int k = x;
        int l = y;
        if (active) {
            if (alive == true) {
                if (hero.getCenterX() < this.x - hero.getBg().getBackX() && dropped == true) {
                    dropped = false;
                    pro.add(0, new Projectiles(this.getCenterX(), this.getCenterY(), enemy, false, window, projType));
                    shot = true;
                }

                if (hero.getCenterX() > this.x - hero.getBg().getBackX() && dropped == true) {
                    dropped = false;
                    pro.add(0, new Projectiles(this.getCenterX(), this.getCenterY(), enemy, true, window, projType));
                    shot = true;
                }

                if (shot == true) {
                    Projectiles b = pro.get(0);
                    b.shoot(window);
                    // when projectile has finished its route, the img is set to null, so this checks for that..
                    if (b.getImg() == null) {
                        System.out.println("dropped");
                        dropped = true;
                    }
                    if (b.getImg() != null) {
                        b.checkCollision(hero);
                    }
                }
            }

            if (this.currentHealth > 0) {
                rect = new FloatRect(x - hero.getBg().getBackX(), y, 96, 96);
                Font fontstyle = new Font();
                try {
                    fontstyle.loadFromFile(
                            Paths.get(FontFile));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                Text healthbar = new Text(("health: " + String.valueOf(getCurrentHealth())), fontstyle, 15);
                healthbar.setColor(Color.GREEN);
                healthbar.setStyle(Text.BOLD | Text.UNDERLINED);
                healthbar.setPosition(x - hero.getBg().getBackX(), y - 15);
                if (this.getCurrentHealth() <= 50) {
                    healthbar.setColor(Color.YELLOW);
                }
                if (this.getCurrentHealth() <= 25) {
                    healthbar.setColor(Color.RED);
                }
                if (this.getCurrentHealth() <= 0) {
                    healthbar = null;
                    rect = null;
                }
                window.draw(healthbar);
            }
            //  rectangle = new RectangleShape(x1);
            //  rectangle.setPosition (this.x,this.y);
            //  rectangle.setFillColor (Color.RED);
            // FloatRect ins = hero.getRect1 ().intersection (rect);
            // System.out.println ("Enemy "+"height "+rect.height+"width "+rect.width+"left "+rect.left+"top "+rect.top);
            // System.out.println ("Hero "+"height "+hero.getRect1 ().height+"width "+hero.getRect1 ().width+"left "+hero.getRect1 ().left+"top "+hero.getRect1 ().top);
            if (hero.getCenterX() > 500) {
                //img.setPosition (x-hero.getBg ().getBackX (),y);
            }

            window.draw(img);

            if (this.currentHealth <= 0 && this.currentHealth > -9000) {
                try {
                    s.openFromFile(Paths.get("./audio/dead.wav"));
                } catch (IOException ex) {
                    //"Houston, we have a problem."
                    ex.printStackTrace();
                }
                score = score + 50;
                s.play();
                this.rect = new FloatRect(0, 0, 0, 0);
                this.image().rotate(90);
                this.setCurrentHealth(-10000);
                alive = false;
            }
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

    public static boolean isColliding(ArrayList<Box> sprites, Sprite enemy){
        boolean result = false;
        for(Box sprite : sprites){
            if(enemy.getGlobalBounds().intersection(sprite.image().getGlobalBounds())!= null){
                result = true;
                break;
            }

        }
        return result;
    }


    public Texture getCharImg(String name) {
        if(name == "miniGeneral") {
            ImageFile = "./graphics/characters/miniGeneral/miniGeneral.png";
        }
        else if(name == "bird") {
            ImageFile = "./graphics/characters/bird/bird.png";
        }
        else if(name == "bun") {
            ImageFile = "./graphics/characters/bun/bunExploding1.png";
        }
        else if(name == "carrot") {
            ImageFile = "./graphics/characters/carrot/carrot.png";
        }
        else if(name == "general") {
            ImageFile = "./graphics/characters/general/general.png";
        }
        else if(name == "ghost") {
            ImageFile = "./graphics/characters/ghost/ghost.png";
        }
        else if(name == "trump") {
            ImageFile = "./graphics/characters/trump/trumpIdle.png";
        }
        else {
            ImageFile = "./graphics/characters/miniGeneral/miniGeneral.png";
        }
        try {
            imgTexture.loadFromFile (Paths.get (ImageFile));
        } catch (IOException ex) {
            ex.printStackTrace ();
        }
        imgTexture.setSmooth (true);
        return imgTexture;
    }

    public void getCharInfo(String name) {
        if(name == "miniGeneral") {
            currentHealth = 5;
            projType = 2;
        }
        else if(name == "bird") {
            currentHealth = 90;
            projType = 1;
        }
        else if(name == "bun") {
            currentHealth = 85;
            projType = 4;
        }
        else if(name == "carrot") {
            currentHealth = 75;
            projType = 1;
        }
        else if(name == "general") {
            currentHealth = 200;
            projType = 2;
        }
        else if(name == "ghost") {
            currentHealth = 60;
            projType = 5;
        }
        else if(name == "trump") {
            currentHealth = 500;
            projType = 7;
        }
        //random character
        else {
            currentHealth = 60;
            projType = 1;
        }
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
        return x-backx;
    }

    /**
     * get centre Y
     *
     * @return centerY
     */
    public int getCenterY() {
        return y;
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
     *
     */

    public int getScore() {
        return score;
    }

    public void resetScore() {
        score = 1;
    }
    public void draw(RenderWindow window){
        window.draw(obj);
    }

    public Texture getImgTexture() {
        return imgTexture;
    }

    @Override
    public void moveLeft() {

    }

    @Override
    public void moveRight() {

    }

    @Override
    public void jump() {

    }
}
