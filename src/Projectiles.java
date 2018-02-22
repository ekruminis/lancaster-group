import org.jsfml.audio.Sound;
import org.jsfml.audio.SoundBuffer;
import org.jsfml.audio.Music;
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
    Music s = new Music();
    Music s2 = new Music();
    Music s3 = new Music();
    Hero player;
    Clock boom = new Clock();
    Texture explosion = new Texture();

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
        player = hero;
        this.x = x;
        this.y = y;
        Texture imgTexture = new Texture ();
        if(type == 1) {
            try {
                imgTexture.loadFromFile(Paths.get("./graphics/projectiles/pingpongball.png"));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            try {
                s.openFromFile(Paths.get("./audio/rockthrow.wav"));
            } catch(IOException ex) {
                //"Houston, we have a problem."
                ex.printStackTrace();
            }
            s.play();
        }
        if(type == 2) {
            try {
                imgTexture.loadFromFile(Paths.get("./graphics/projectiles/bullet.png"));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            try {
                s.openFromFile(Paths.get("./audio/bullet.wav"));
            } catch(IOException ex) {
                //"Houston, we have a problem."
                ex.printStackTrace();
            }
            s.play();
        }
        if(type == 3) {
            try {
                imgTexture.loadFromFile(Paths.get("./graphics/projectiles/bombExploding1.png"));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            try {
                s.openFromFile(Paths.get("./audio/bomb.wav"));
            } catch(IOException ex) {
                //"Houston, we have a problem."
                ex.printStackTrace();
            }
            s.play();
            try {
                explosion.loadFromFile(Paths.get("./graphics/projectiles/explosion3.png"));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        if(type == 4) {
            try {
                s.openFromFile(Paths.get("./audio/bomb.wav"));
            } catch(IOException ex) {
                //"Houston, we have a problem."
                ex.printStackTrace();
            }
            s.play();
            try {
                explosion.loadFromFile(Paths.get("./graphics/projectiles/explosion3.png"));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        if(type == 5) {
            try {
                s.openFromFile(Paths.get("./audio/swish.wav"));
            } catch(IOException ex) {
                //"Houston, we have a problem."
                ex.printStackTrace();
            }
            s.play();
        }
        if(type == 6) {
            try {
                imgTexture.loadFromFile(Paths.get("./graphics/projectiles/shuriken.png"));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            try {
                s.openFromFile(Paths.get("./audio/shuriken.wav"));
            } catch(IOException ex) {
                //"Houston, we have a problem."
                ex.printStackTrace();
            }
            s.play();
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

            if (posx >= posx2+1100 || posx <= posx2-1100) {
                stoped = true;
                img = null;
                rect = null;
                s.stop();
            }
        }
        // 3 -> shoots bombs
        if(typeShot == 3) {
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
            if(boom.getElapsedTime().asSeconds() >= 3d) {
                img = new Sprite();
                rect = new FloatRect(0,0,0,0);
                img = null;
                rect = null;
            }
            else if(boom.getElapsedTime().asSeconds() >= 2) {
                rect = new FloatRect((float)posx-80, (float)posy-120, 212,210);
                img = new Sprite(explosion);
                img.setPosition((float)posx-80, (float)posy-120);
                window.draw(img);
            }
            else if (posy >= 680) {
                stoped = true;
                draw(window);
                img.setPosition((float)posx2, (float) posy);
                rect = new FloatRect((float) posx, (float) posy, 50, 50);
            }
            // change speed in y
            vy -= 9.82 * dt; // gravity
        }
        // self-explosion (for bun)
        if(typeShot == 4) {
            if(!stoped) {
                rect = new FloatRect((float) x, (float) y, 50, 50);
                img.setPosition((float) x, (float) y);
            }
            if(boom.getElapsedTime().asSeconds() >= 3) {
                img = new Sprite();
                rect = new FloatRect(0,0,0,0);
                img = null;
                rect = null;
                stoped = true;
            }
            else if(boom.getElapsedTime().asSeconds() >= 2) {
                rect = new FloatRect((float)x-80, (float)y-120, 250,250);
                img = new Sprite(explosion);
                img.setPosition((float)x-80, (float)y-120);
                window.draw(img);
            }
        }
        // bat hitting
        if(typeShot == 5) {
            if(!stoped) {
                rect = new FloatRect((float) x, (float) y, 90, 120); // normal char dimensions +10 each
            }
            if(boom.getElapsedTime().asSeconds() >= 0.5) {
                stoped = true;
                img = null;
            }
        }
        // shuriken throw
        if(typeShot == 6) {
            if (!stoped) {
                window.draw(img);
                if (direction == true) // true shoots right
                    posx += vy * dt;
                if (direction == false) // false shoots left/
                    posx -= vy * dt;
                posy -= vx * dt;
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
            vx -= 9.82 * dt; // gravity
        }
    }

    /**
     * Check for collisions
     *
     * @param enemy Enemy
     */
    public void checkCollision(Enemy enemy){
        FloatRect ins = enemy.getRect().intersection (rect);
        if(ins!=null && n == 0) {
            if(typeShot == 1) {
                img = new Sprite();
                enemy.setCurrentHealth(enemy.getCurrentHealth() - 10);
            }
            else if(typeShot == 2) {
                img = new Sprite();
                enemy.setCurrentHealth(enemy.getCurrentHealth() - 15);
            }
            else if(typeShot == 3) {
                enemy.setCurrentHealth(enemy.getCurrentHealth() - 25);
            }
            else if(typeShot == 4) {
                enemy.setCurrentHealth(enemy.getCurrentHealth() - 35);
            }
            else if(typeShot == 5) {
                enemy.setCurrentHealth(enemy.getCurrentHealth() - 5);
            }
            else if(typeShot == 6) {
                enemy.setCurrentHealth(enemy.getCurrentHealth() - 10);
            }
            n = 1;
            try {
                s2.openFromFile(Paths.get("./audio/hit.wav"));
            } catch(IOException ex) {
                //"Houston, we have a problem."
                ex.printStackTrace();
            }
            s2.play();
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
