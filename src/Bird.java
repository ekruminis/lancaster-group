import org.jsfml.graphics.*;
import org.jsfml.system.Clock;
import org.jsfml.system.Vector2f;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Bird extends Collision {
    private FloatRect hitbox;

    private int eX;
    private int eY;
    boolean side = true;
    private int speedX = 0;  //speed
    private Clock time = new Clock();
    private boolean exploded = false;
    Sprite img;
    int n = 1;
    boolean dropped = true;
    boolean shot = false;
    ArrayList<Projectiles> pro = new ArrayList<>(1);
    Hero player;
    boolean cracked = false;
    boolean direction = false;
    Clock timer = new Clock();
    boolean closeTo = false;
    public Sprite getBird() {
        return bird;
    }

    public boolean isVisible() {
        return visible;
    }

    private boolean visible = true;

    private Sprite bird;

    public Bird(int eX, int eY, Hero hero, RenderWindow window) {
        this.eY = eY;
        this.eX = eX;
        SpriteSheet ss = new SpriteSheet();
        img = ss.getFrame(29, 18, 44, 59, "./graphics/characters/bird/sprites.png");
        img.setPosition(eX, eY);
        //bird.setOrigin(Vector2f.div(new Vector2f(hero.getImgTexture ().getSize ()), 1000000));
        hitbox = new FloatRect(eX - hero.getBg().getBackX(), eY, 96, 96);

        Texture imgTexture = new Texture();
        try {
            imgTexture.loadFromFile(Paths.get("./graphics/projectiles/pingpongball.png"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        img = new Sprite(imgTexture);
        pro.add(0, new Projectiles(90000, 90000, player, direction, window, 2));
    }


    public Sprite changeImg(Sprite x, String e) {
        Texture imgTexture = new Texture();
        try {
            imgTexture.loadFromFile(Paths.get(e));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        imgTexture.setSmooth(true);
        x = new Sprite(imgTexture);
        return x;
    }

    public void hatching() {
        SpriteSheet ss = new SpriteSheet();
        if (n <= 120) {
            if (n >= 1 && n <= 10) {
                img = ss.getFrame(29, 18, 44, 59, "./graphics/characters/bird/sprites.png");
            } else if (n >= 11 && n <= 20) {
                img = ss.getFrame(239, 333, 44, 59, "./graphics/characters/bird/sprites.png");
            } else if (n >= 21 && n <= 30) {
                img = ss.getFrame(344, 18, 44, 59, "./graphics/characters/bird/sprites.png");
            } else if (n >= 31 && n <= 40) {
                img = ss.getFrame(344, 123, 44, 59, "./graphics/characters/bird/sprites.png");
            } else if (n >= 41 && n <= 50) {
                img = ss.getFrame(344, 228, 44, 59, "./graphics/characters/bird/sprites.png");
            } else if (n >= 51 && n <= 60) {
                img = ss.getFrame(344, 326, 44, 66, "./graphics/characters/bird/sprites.png");
            } else if (n >= 61 && n <= 70) {
                img = ss.getFrame(29, 430, 44, 67, "./graphics/characters/bird/sprites.png");
            } else if (n >= 71 && n <= 80) {
                img = ss.getFrame(134, 429, 46, 68, "./graphics/characters/bird/sprites.png");
            } else if (n >= 81 && n <= 90) {
                img = ss.getFrame(239, 428, 52, 69, "./graphics/characters/bird/sprites.png");
            } else if (n >= 91 && n <= 100) {
                img = ss.getFrame(344, 428, 61, 69, "./graphics/characters/bird/sprites.png");
            } else if (n >= 101 && n <= 110) {
                img = ss.getFrame(449, 11, 65, 66, "./graphics/characters/bird/sprites.png");
            } else if (n >= 111 && n <= 120) {
                img = ss.getFrame(134, 23, 44, 54, "./graphics/characters/bird/sprites.png");
            }
            n++;
        }
        if (n == 120) {
            cracked = true;
        }
        img.setPosition(eX, eY);
    }

    public void draw(RenderWindow window) {
        window.draw(img);
    }

    @Override
    public void BasicmovementLeft(Hero hero) {
        if(eX-hero.getBg().getBackX() - hero.getCenterX() > 0){
            direction = false;
            if(((eX- hero.getBg().getBackX()) - hero.getCenterX()-hero.getBg().getBackX()) > 350) {
                closeTo = true;
                SpriteSheet ss = new SpriteSheet();
                img = ss.getFrame(134, 23, 44, 54, "./graphics/characters/bird/sprites.png");
                bird.setPosition(eX - hero.getBg().getBackX(), eY);
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
        if(eX-hero.getBg().getBackX() - hero.getCenterX() < 0){
            direction = false;
            if(((eX- hero.getBg().getBackX()) - hero.getCenterX()-hero.getBg().getBackX()) > 350) {
                closeTo = true;
                SpriteSheet ss = new SpriteSheet();
                img = ss.getFrame(132, 128, 44, 54, "./graphics/characters/bird/sprites.png");
                bird.setPosition(eX - hero.getBg().getBackX(), eY);
                eX -= 2;
                hitbox = new FloatRect(eX - hero.getBg().getBackX(), eY, 96, 96);
            }
            else {
                closeTo = false;
            }
        }
    }

    public void peck(Hero hero, RenderWindow window, boolean cracked) {
        if(hero.getCenterX() - hero.getBg().getBackX() > eX) {
            direction = true;
            SpriteSheet ss = new SpriteSheet();
            img = ss.getFrame(132, 128, 44, 54, "./graphics/characters/bird/sprites.png");
            img.setPosition(eX, eY);
        }
        if(hero.getCenterX() - hero.getBg().getBackX() < eX) {
            direction = false;
            SpriteSheet ss = new SpriteSheet();
            img = ss.getFrame(134, 28, 44, 54, "./graphics/characters/bird/sprites.png");
            img.setPosition(eX, eY);
        }
        if(cracked == true && dropped == true) {
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
                        pro.add(0, new Projectiles(eX, eY, player, direction, window, 2));
                        shot = false;
                        dropped = false;
                    }
                }

            }
        }
        if(timer.getElapsedTime().asSeconds() > 2) {
            dropped = true;
            timer.restart();
        }


    }

    public boolean isCracked() {
        return cracked;
    }
}