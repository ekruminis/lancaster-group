import org.jsfml.graphics.*;
import org.jsfml.system.Clock;
import org.jsfml.system.Vector2f;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Egg {
    private FloatRect hitbox;
    private FloatRect boom;
    private int eX;
    private int eY;
    Sprite img;
    FloatRect rect;
    private boolean visible=true;
    private Sprite egg;
    private boolean direction;
    RenderWindow window;
    boolean dropped;
    boolean shot = false;
    ArrayList<Projectiles> pro = new ArrayList<>(1);
    Hero player;
    boolean closeTo = false;
    Clock timer = new Clock();
    int currenthealth;
    Egg games;
    private String FontFile  = "font/FreeSans.ttf";  //get font(used for health bar)

    public Egg(int eX,int eY,Hero hero, RenderWindow window2){
        this.eX=eX;
        this.eY=eY;
        this.window = window2;
        this.player = hero;
        this.currenthealth = 150;

        SpriteSheet ss = new SpriteSheet();
        egg = ss.getFrame(0, 0, 100, 100, "./graphics/characters/egg/spritesheet.png");
        egg.setPosition(eX,eY);
        //trump.setOrigin(Vector2f.div(new Vector2f(hero.getImgTexture ().getSize ()), 1000000));

        hitbox = new FloatRect(eX - hero.getBg().getBackX(), eY, 100, 100);
        pro.add(0, new Projectiles(eX-player.getBg().getBackX(), eY, player, direction, window, 2));
        dropped = false;
    }

    public void shoot(Hero hero,RenderWindow window) {
        if(dropped == false) {
            for (Projectiles proj : pro) {
                shot = true;

                if (shot == true) {
                    Projectiles b = pro.get(0);
                    b.shoot(window);
                    // when projectile has finished its route, the img is set to null, so this checks for that..
                    if (b.getImg() == null) {
                        //System.out.println("shot by egg");
                        pro.clear();
                        pro.add(0, new Projectiles(eX-player.getBg().getBackX(), eY,new Egg(eX, eY, player, window), direction, window, 2));
                        shot = false;
                        dropped = true;
                    }
                    if (b.getImg() != null) {
                        b.checkCollision(player);
                    }
                }
                // if floatrect makes intersection, save coordinates of hero, and setCenterX(saved) and setCenterY(saved) of hero for stun
            }
        }
        if(timer.getElapsedTime().asSeconds() > 2) {
            dropped = false;
            timer.restart();
        }
    }

    public void BasicmovementLeft(Hero hero) {
        SpriteSheet ss = new SpriteSheet();
        if(hero.getCenterX() < eX- hero.getBg().getBackX()){
            direction = false;
            egg = ss.getFrame(0, 0, 100, 100, "./graphics/characters/egg/spritesheet.png");
            egg.setPosition(eX- hero.getBg().getBackX(),eY);
            eX-=1;
            hitbox = new FloatRect(eX - hero.getBg().getBackX(), eY, 100, 100);
        }

    }

    public void BasicmovementRight(Hero hero) {
        SpriteSheet ss = new SpriteSheet();
        if(hero.getCenterX() > eX- hero.getBg().getBackX()){
            direction = true;
            egg = ss.getFrame(0, 105, 100, 100, "./graphics/characters/egg/spritesheet.png");
            egg.setPosition(eX- hero.getBg().getBackX(),eY);
            eX+=1;
            hitbox = new FloatRect(eX - hero.getBg().getBackX(), eY, 100, 100);
        }
    }

    public Sprite changeImg(Sprite x,String e) {
        Texture imgTexture = new Texture ();
        try {
            imgTexture.loadFromFile (Paths.get (e));
        } catch (IOException ex) {
            ex.printStackTrace ();
        }
        imgTexture.setSmooth (true);
        x = new Sprite (imgTexture);
        return x;
    }

    public Sprite getEgg() {
        return egg;
    }

    public boolean isVisible() {
        return visible;
    }

    public int geteX() {
        return eX;
    }

    public int geteY() {
        return eY;
    }

    public int getCenterX() {
        return eX;
    }

    public int getCenterY() {
        return eY;
    }

    public FloatRect getRect() {
        return hitbox;
    }

    public void draw(RenderWindow window){
        window.draw(egg);
    }

    public void update(Hero player) {
        BasicmovementLeft(player);
        BasicmovementRight(player);
        Font fontStyle = new Font();  //load font
        try {
            fontStyle.loadFromFile(Paths.get(FontFile));
        } catch (IOException ex) {
            ex.printStackTrace( );
        }

        Text healthbar = new Text(("health: " + String.valueOf(getCurrentHealth())), fontStyle, 15);

        healthbar.setColor(Color.GREEN);
        healthbar.setStyle(Text.BOLD | Text.UNDERLINED);
        healthbar.setPosition(eX- player.getBg().getBackX(), eY-15);

        if(this.getCurrentHealth() <= 50) {
            healthbar.setColor(Color.YELLOW);
        }
        if(this.getCurrentHealth() <= 25) {
            healthbar.setColor(Color.RED);
        }
        if(this.getCurrentHealth() <= 0) {
            //gameover();
            healthbar.setString("You're dead");
        }
        window.draw(healthbar);
    }

    public void setCurrentHealth(int currenthealth) {
        this.currenthealth = currenthealth;
    }

    public int getCurrentHealth() {
        return currenthealth;
    }

    public Sprite getImg() {
        return egg;
    }

    public void setCenterX(int xx) {
        eX = xx;
    }
}
