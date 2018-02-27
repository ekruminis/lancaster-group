import org.jsfml.graphics.*;
import org.jsfml.system.Clock;
import org.jsfml.system.Vector2f;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Carrot {
    private FloatRect hitbox;
    private FloatRect boom;
    private int eX;
    private int eY;
    Sprite img;
    FloatRect rect;
    private boolean visible=true;
    private Sprite carrot;
    private boolean direction;
    RenderWindow window;
    boolean dropped = false;
    boolean shot = false;
    ArrayList<Projectiles> pro = new ArrayList<>(1);
    Hero player;
    boolean closeTo = false;
    private String FontFile  = "font/FreeSans.ttf";  //get font(used for health bar)


    public Carrot(int eX,int eY,Hero hero, RenderWindow window2){
        this.eX=eX;
        this.eY=eY;
        this.window = window2;
        this.player = hero;
        this.currenthealth = 50;
        carrot = changeImg(carrot,"graphics/characters/carrot/carrot.png");
        carrot.setPosition(eX,eY);
//        carrot.setOrigin(Vector2f.div(new Vector2f(hero.getImgTexture ().getSize ()), 1000000));

        hitbox = new FloatRect(eX - hero.getBg().getBackX(), eY, 96, 96);
        Texture imgTexture = new Texture();
        try {
            imgTexture.loadFromFile(Paths.get("./graphics/projectiles/pingpongball.png"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        img = new Sprite(imgTexture);
        pro.add(0, new Projectiles(90000, 90000, player, direction, window, 1));
    }

    public void stun(Hero hero,RenderWindow window){
        if(!closeTo || dropped == true) {
            for (Projectiles proj : pro) {
                dropped = true;
                shot = true;

                    if (shot == true) {
                        Projectiles b = pro.get(0);
                        b.shoot(window);
                        // when projectile has finished its route, the img is set to null, so this checks for that..
                        if (b.getImg() == null) {
                            System.out.println("dropped by carrot");
                            pro.clear();
                            pro.add(0, new Projectiles(eX, eY, player, direction, window, 1));
                            shot = false;
                            dropped = false;
                        }
                    }
                // if floatrect makes intersection, save coordinates of hero, and setCenterX(saved) and setCenterY(saved) of hero for stun
            }
        }
    }

    public void BasicmovementLeft(Hero hero) {
        if(eX-hero.getBg().getBackX() - hero.getCenterX() > 0){
            direction = false;
            if(((eX- hero.getBg().getBackX()) - hero.getCenterX()-hero.getBg().getBackX()) > 350) {
                closeTo = true;
                carrot.setPosition(eX - hero.getBg().getBackX(), eY);
                eX -= 2;
                hitbox = new FloatRect(eX - hero.getBg().getBackX(), eY, 1000, 1000);
            }
            else {
                closeTo = false;
            }
        }
    }

    public void BasicmovementRight(Hero hero) {
        if(eX-hero.getBg().getBackX() - hero.getCenterX() < 0) {
            direction = true;
            if((eX-hero.getBg().getBackX()) - hero.getCenterX() < -350) {
                closeTo = true;
                carrot.setPosition(eX - hero.getBg().getBackX(), eY);
                eX += 2;
                hitbox = new FloatRect(eX - hero.getBg().getBackX(), eY, 1000, 1000);
            }
            else {
                closeTo = false;
            }
        }
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
    public void draw(RenderWindow window){
        window.draw(carrot);
    }

    public Sprite getCarrot() {
        return carrot;
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

    public FloatRect getRect() {
        return hitbox;
    }

    int currenthealth;
    public void setCurrentHealth(int currenthealth) {
        this.currenthealth = currenthealth;
    }

    public int getCurrentHealth() {
        return currenthealth;
    }

}
