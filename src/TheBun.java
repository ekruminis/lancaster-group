import org.jsfml.graphics.*;
import org.jsfml.system.Clock;
import org.jsfml.system.Vector2f;

import java.io.IOException;
import java.nio.file.Paths;

public class TheBun extends  Collision {
    private FloatRect hitbox;
    private FloatRect boom;
    private int eX;
    private int eY;










    private Clock time = new Clock();
    private boolean exploded=false;
    public Sprite getBun() {
        return bun;
    }

    public boolean isVisible() {
        return visible;
    }

    private boolean visible=true;

    private Sprite bun;
    public TheBun(int eX,int eY,Hero hero){
        this.eX=eX;
        this.eY=eY;

        bun = changeImg(bun,"graphics/characters/bun/bunExplosion1.png");
        bun.setPosition(eX,eY);
        bun.setOrigin(Vector2f.div(new Vector2f(hero.getImgTexture ().getSize ()), 1000000));

        hitbox = new FloatRect(eX - hero.getBg().getBackX(), eY, 96, 96);
    }


    public void explosion(Hero hero,RenderWindow window){
        if(bun != null) {


            System.out.println(time.getElapsedTime().asSeconds());
            if (Math.abs(hero.getCenterX() - eX) < 40) {


                boom = new FloatRect(eX - hero.getBg().getBackX(), eY, 120, 120);
                FloatRect ins = boom.intersection(hero.getRect1());
                if (ins != null) {

                    bun = changeImg(bun, "graphics/characters/bun/bunExplosion3.png");

                    exploded = true;


                    System.out.println("got ya bitch");
                    time.restart();
                }
            }
            bun.setPosition(eX - hero.getBg().getBackX(), eY);
            if (time.getElapsedTime().asSeconds() > 1 && exploded) {
                visible = false;
                bun = null;
            }


        }



        }


    @Override
    public void BasicmovementLeft(Hero hero) {
        if(hero.getCenterX() < eX && !exploded){
            bun.setPosition(eX- hero.getBg().getBackX(),eY);
            eX-=2;
            hitbox = new FloatRect(eX - hero.getBg().getBackX(), eY, 96, 96);
        } else if(eY==hero.getCenterY() && hero.getCenterX() < eX){
            //shoor ot smth
        }

    }

    @Override
    public void BasicmovementRight(Hero hero) {

        if(hero.getCenterX() > eX && !exploded){
            bun.setPosition(eX- hero.getBg().getBackX(),eY);
            eX+=2;
            hitbox = new FloatRect(eX - hero.getBg().getBackX(), eY, 96, 96);
        } else if(eY==hero.getCenterY() && hero.getCenterX() > eX){
            //shoor ot smth
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
        window.draw(bun);
    }

}



