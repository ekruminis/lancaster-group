import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;
import org.jsfml.window.Keyboard;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Random;

/**
 *Creates a box that can be used for platforms
 *
 * @version  1.0 Build 1 Feb 12, 2018.
 */
public class Box {
    public String name;
    private int x;  //positions
    private int y;
    FloatRect rect; //rectangle
    Vector2f x1;    //vector
    private int height;
    private int width;

    private String ImageFile="./graphics/objects/box.png"; //get box graphic

    private Sprite img;
    Random rand = new Random();

    /**
     * set rectangle
     *
     * @param rect rectangle
     */
    public void setRect(FloatRect rect) {
        this.rect = rect;
    }

    /**
     * Creates a box
     *
     * @param x x location
     * @param y y location
     * @param hero Hero
     */
    public Box(int x,int y,Hero hero){
        this.x=x;
        this.y=y;

        Texture imgTexture = new Texture ();
        try {
            imgTexture.loadFromFile (Paths.get (ImageFile));
        } catch (IOException ex) {
            ex.printStackTrace ();
        }
        imgTexture.setSmooth (true);
        img = new Sprite (imgTexture);
        img.setOrigin(Vector2f.div(new Vector2f(hero.getImgTexture ().getSize ()), 1000000));
        img.setPosition (x,y);

        x1 = (Vector2f.div(new Vector2f(imgTexture.getSize ()), 1));
    }

    public Sprite image(){
        return img;
    }
    public Texture changeImg(String ImageFile2) {
        Texture imgTexture = new Texture ();
        try {
            imgTexture.loadFromFile (Paths.get (ImageFile2));
        } catch (IOException ex) {
            ex.printStackTrace ();
        }
        imgTexture.setSmooth (true);
        return imgTexture;
    }
    public void update(Hero hero,RenderWindow window) {
        rect = new FloatRect (x-hero.getBg ().getBackX (),y,90,80);
        FloatRect u = hero.getRect1();
        FloatRect ins = hero.getRect1 ().intersection (rect);

        if(hero==null){
            System.out.println ("I am hero I am null");
        }
        if(hero.getBg ()==null){
            System.out.println ("I am background I dont exist :(");
        }


        if(hero.getCenterX ()>500){
            img.setPosition (x-hero.getBg ().getBackX (),y);
        }
        window.draw (img);
    }

    /**
     *get rectangle
     *
     * @return rect
     */
    public FloatRect getRect(){
        return rect;
    }
}
