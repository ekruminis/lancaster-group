import org.jsfml.graphics.*;
import org.jsfml.system.Clock;
import org.jsfml.system.Time;
import org.jsfml.system.Vector2f;

import java.io.IOException;
import java.nio.file.Paths;

public class Projectiles {
    int v0 = 60; // m/s
    int angle = 60;
    double dt = 0.2; // s

    double vx = v0 * Math.cos (Math.PI / 180 * angle);
    double vy = v0 * Math.sin (Math.PI / 180 * angle);

    double posx = 200; // m
    double posy = 700;  // m
    double posx2 = 0;
    double time = 0; // s
    private String ImageFile = "./graphics/projectile2.png";
    private Sprite img;
    private boolean stoped=false;
    private int x,y;
    public Sprite getImg() {
        return img;
    }
    private boolean direction = true;
    private boolean drop;
    int typeShot = 0;
    FloatRect rect;
    int n = 0;

    public Projectiles(int x, int y, Hero hero, boolean directionChoice, RenderWindow window, int type) {
        typeShot = type;
        Texture imgTexture = new Texture ();
        try {
            imgTexture.loadFromFile (Paths.get (ImageFile));
        } catch (IOException ex) {
            ex.printStackTrace ();
        }
        imgTexture.setSmooth (true);
        direction = directionChoice;
        img = new Sprite (imgTexture);
        img.setOrigin (Vector2f.div (new Vector2f (hero.getImgTexture ().getSize ()), 1000000));
        posx = hero.getCenterX();
        posy = hero.getCenterY();
        posx2 = hero.getCenterX();
        img.setPosition (x, y);
    }

    public void shoot(RenderWindow window) {
        // 1 -> throws Rock
        if(typeShot == 1) {
            if (!stoped) {
                window.draw(img);
                if (direction == true) // true shoots right
                    posx += vx * dt;
                if (direction == false) // false shoots left/
                    posx -= vx * dt;
                posy -= vy * dt;
                time += dt;
                img.setPosition((int) posx, (int) posy);
                rect = new FloatRect ((float)posx, (float)posy,67,34);
            }


            if (posy >= 780) {
                stoped = true;
                img = null;
                rect = null;
            }


            // change speed in y
            vy -= 9.82 * dt; // gravity
        }
        // 2 -> shoots bullet
        if(typeShot == 2) {
            if (!stoped) {
                window.draw(img);
                if (direction == true) // true shoots right
                    posx += 18;
                if (direction == false) // false shoots left
                    posx -= 18;
                img.setPosition((int) posx, (int) posy);
                rect = new FloatRect ((float)posx, (float)posy,67,34); // 67x34 is just img dimensions
            }


            if (posx >= posx2+1600 || posx <= posx2-600) {
                stoped = true;
                img = null;
                rect = null;
            }
        }

    }

    public void checkCollision(Enemy enemy){
        FloatRect ins = enemy.getRect().intersection (rect);
        if(enemy.getCurrentHealth() > 0 && ins!=null && n == 0) {
            System.out.println("skadaddy");
            img = new Sprite();
            enemy.setCurrentHealth(enemy.getCurrentHealth()-10);
            n = 1;
        }
    }

    public void draw(RenderWindow window){
        window.draw(img);
    }
}
