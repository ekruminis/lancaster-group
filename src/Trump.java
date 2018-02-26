import org.jsfml.graphics.*;
import org.jsfml.system.Clock;
import org.jsfml.system.Vector2f;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Trump extends  Collision {
    private FloatRect hitbox;
    private FloatRect boom;
    private int eX;
    private int eY;
    Sprite img;
    FloatRect rect;
    private boolean visible=true;
    private Sprite trump;
    private boolean direction;
    RenderWindow window;
    boolean dropped = false;
    boolean shot = false;
    ArrayList<Projectiles> pro = new ArrayList<>(1);
    int n = 2;

    public Trump(int eX,int eY,Hero hero, RenderWindow window2){
        this.eX=eX;
        this.eY=eY;
        this.window = window2;

        SpriteSheet ss = new SpriteSheet();
        trump = ss.getFrame(0, 85, 80, 80, "./graphics/characters/orb/spritesheet.png");
         trump.setPosition(eX,eY);
        //trump.setOrigin(Vector2f.div(new Vector2f(hero.getImgTexture ().getSize ()), 1000000));

        hitbox = new FloatRect(eX - hero.getBg().getBackX(), eY, 80, 80);

    }

    @Override
    public void BasicmovementLeft(Hero hero) {
        SpriteSheet ss = new SpriteSheet();
        if(hero.getCenterX() < eX- hero.getBg().getBackX()){
            direction = false;
            if(n <= 60){
                if (n >= 1 && n <= 15) {
                    trump = ss.getFrame(0, 85, 80, 80, "./graphics/characters/orb/spritesheet.png");
                } else if (n >= 16 && n <= 30) {
                    trump = ss.getFrame(0, 170, 80, 80, "./graphics/characters/orb/spritesheet.png");
                } else if (n >= 31 && n <= 45) {
                    trump = ss.getFrame(85, 0, 80, 80, "./graphics/characters/orb/spritesheet.png");
                } else if (n >= 46 && n <= 60) {
                    trump = ss.getFrame(85, 85, 80, 80, "./graphics/characters/orb/spritesheet.png");
                }
                n++;
            }
            if(n == 60) {
                n = 0;
            }
            trump.setPosition(eX- hero.getBg().getBackX(),eY);
            eX-=2;
            hitbox = new FloatRect(eX - hero.getBg().getBackX(), eY, 96, 96);
        }

    }

    @Override
    public void BasicmovementRight(Hero hero) {
        SpriteSheet ss = new SpriteSheet();
        if(hero.getCenterX() > eX- hero.getBg().getBackX()){
            direction = true;
            if(n <= 60){
                if (n >= 1 && n <= 15) {
                    trump = ss.getFrame(85, 170, 80, 80, "./graphics/characters/orb/spritesheet.png");
                } else if (n >= 16 && n <= 30) {
                    trump = ss.getFrame(0, 0, 80, 80, "./graphics/characters/orb/spritesheet.png");
                } else if (n >= 31 && n <= 45) {
                    trump = ss.getFrame(170, 0, 80, 80, "./graphics/characters/orb/spritesheet.png");
                } else if (n >= 46 && n <= 60) {
                    trump = ss.getFrame(170, 85, 80, 80, "./graphics/characters/orb/spritesheet.png");
                }
                n++;
            }
            if(n == 60) {
                n = 0;
            }
            eX+=2;
            trump.setPosition(eX- hero.getBg().getBackX(),eY);
            hitbox = new FloatRect(eX - hero.getBg().getBackX(), eY, 96, 96);
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
        window.draw(trump);
    }

    public Sprite getTrump() {
        return trump;
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
