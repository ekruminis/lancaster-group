import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
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
    double time = 0; // s
    private String ImageFile = "./graphics/projectile2.png";
    private Sprite img;
    private boolean stoped=false;
    private int x,y;
    public Sprite getImg() {
        return img;
    }

    public Projectiles(int x, int y, Hero hero) {
        Texture imgTexture = new Texture ();
        try {
            imgTexture.loadFromFile (Paths.get (ImageFile));
        } catch (IOException ex) {
            ex.printStackTrace ();
        }
        imgTexture.setSmooth (true);

        img = new Sprite (imgTexture);
        img.setOrigin (Vector2f.div (new Vector2f (hero.getImgTexture ().getSize ()), 1000000));
        posx = hero.getCenterX();
        posy = hero.getCenterY();
        img.setPosition (x, y);

    }

    public void shoot(RenderWindow window) {
        Clock frameClock = new Clock();

            System.out.println (frameClock.getElapsedTime ().asSeconds ());
                if(!stoped) {
                    window.draw(img);
                    posx += vx * dt;
                    posy -= vy * dt;
                    time += dt;
                    img.setPosition ((int) posx, (int) posy);
                }


                if(posy>=780){
                    stoped=true;
                    img = new Sprite();
                }


            // change speed in y
            vy -= 9.82 * dt; // gravity

    }
    public void draw(RenderWindow window){
        window.draw(img);
    }
}
