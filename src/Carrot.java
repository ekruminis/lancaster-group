import org.jsfml.graphics.*;
import org.jsfml.system.Clock;
import org.jsfml.system.Vector2f;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Carrot extends  Collision {
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


    public Carrot(int eX,int eY,Hero hero, RenderWindow window2){
        this.eX=eX-hero.getBg().getBackX();
        this.eY=eY;
        this.window = window2;
        this.player = hero;
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

            }
        }
    }

    @Override
    public void BasicmovementLeft(Hero hero) {
        if(eX-hero.getBg().getBackX() - hero.getCenterX() > 0){
            direction = false;
            if(((eX- hero.getBg().getBackX()) - hero.getCenterX()-hero.getBg().getBackX()) > 350) {
                closeTo = true;
                carrot.setPosition(eX - hero.getBg().getBackX(), eY);
                eX -= 2;
                hitbox = new FloatRect(eX - hero.getBg().getBackX(), eY, 96, 96);
            }
            else {
                closeTo = false;
            }
        }
    }

    @Override
    public void BasicmovementRight(Hero hero) {
        if(eX-hero.getBg().getBackX() - hero.getCenterX() < 0) {
            direction = true;
            if((eX-hero.getBg().getBackX()) - hero.getCenterX() < -350) {
                closeTo = true;
                carrot.setPosition(eX - hero.getBg().getBackX(), eY);
                eX += 2;
                hitbox = new FloatRect(eX - hero.getBg().getBackX(), eY, 96, 96);
            }
            else {
                closeTo = false;
            }
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

}
