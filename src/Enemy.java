import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Random;

public class Enemy {


    public String name;
    private int x;
    private int y;
    int position = 0;
    FloatRect rect;
    Vector2f x1;
    private String FontFile  = "font/FreeSans.ttf";
    public RectangleShape getRectangle() {
        return rectangle;
    }

    RectangleShape rectangle;
    private int maxHealth, currentHealth, power, speedX, centerX, centerY;
    Game game;
    protected Drawable obj;
    private String ImageFile="./graphics/heliboy.png";

    private Sprite img;
    Random rand = new Random();

    public void setRect(FloatRect rect) {
        this.rect = rect;
    }



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
            this.rect = new FloatRect(0,0,0,0);
            this.image().rotate(90);
            this.setCurrentHealth(-10000);
        }
    }

    public int getMaxHealth() {
        return maxHealth;
    }


    public int getCurrentHealth() {
        return currentHealth;
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