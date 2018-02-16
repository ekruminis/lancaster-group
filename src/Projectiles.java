import org.jsfml.graphics.*;
import org.jsfml.system.Clock;
import org.jsfml.system.Time;
import org.jsfml.system.Vector2f;

import java.io.IOException;
import java.nio.file.Paths;

/**
 *Creates projectiles and their movements
 *
 * @version  1.0 Build 1 Feb 12, 2018.
 */

public class Projectiles {
    int v0 = 60; // m/s
    int angle = 60;
    double dt = 0.2; // s

    double vx = v0 * Math.cos (Math.PI / 180 * angle);
    double vy = v0 * Math.sin (Math.PI / 180 * angle);
    double posx; // m
    double posy;  // m
    double posx2 = 0;
    double time = 0; // s
    private Sprite img;
    private boolean stoped=false;
    private int x,y;

    /**
     * get image
     *
     * @return img
     */
    public Sprite getImg() {
        return img;
    }

    private boolean direction = true;
    private boolean drop;
    int typeShot = 0;
    FloatRect rect;
    int n = 0;

    /**
     * Creates a projectile
     *
     * @param x x position
     * @param y y position
     * @param hero hero
     * @param directionChoice direction
     * @param window the current window
     * @param type type of projectile
     */
    public Projectiles(int x, int y, Hero hero, boolean directionChoice, RenderWindow window, int type) {
        typeShot = type;
        Texture imgTexture = new Texture ();
        if(type == 1) {
            try {
                imgTexture.loadFromFile(Paths.get("./graphics/projectiles/pingpongball.png"));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        if(type == 2) {
            try {
                imgTexture.loadFromFile(Paths.get("./graphics/projectiles/bullet.png"));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
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

    /**
     * Shoot the projectile
     *
     * @param window current window
     */
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
                rect = new FloatRect ((float)posx, (float)posy,50,50);
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
				// true shoots right
                if (direction == true) {
                    posx += 18;
				}
				// false shoots left
                if (direction == false) {
                    posx -= 18;
				}
                img.setPosition((int) posx, (int) posy);
                rect = new FloatRect ((float)posx, (float)posy,50,50); // 67x34 is just img dimensions
            }

            if (posx >= posx2+1600 || posx <= posx2-600) {
                stoped = true;
                img = null;
                rect = null;
            }
        }
    }

    /**
     * Check for collisions
     *
     * @param enemy Enemy
     */
    public void checkCollision(Enemy enemy){
        FloatRect ins = enemy.getRect().intersection (rect);
        if(enemy.getCurrentHealth() > 0 && ins!=null && n == 0) {
            img = new Sprite();
            enemy.setCurrentHealth(enemy.getCurrentHealth()-10);
            n = 1;
        }
    }

    /**
     * Refresh the  window
     *
     * @param window
     */
    public void draw(RenderWindow window){
        window.draw(img);
    }
}
