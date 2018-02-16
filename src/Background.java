import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2i;

import java.io.IOException;
import java.nio.file.Paths;

/**
 *Create a scrolling background
 *
 * @version  1.0 Build 1 Feb 12, 2018.
 */
public class Background {
    public void setBackX(int backX) {
        this.backX = backX;
    }

    private int backX=0;
    private int backY;
    private int speedX;
    Texture texture1 = new Texture();

    public Background(int x,int y){
        this.backX=x; //x location
        this.backY=y; //y location
    }

    /**
     * Get the background texture
     *
     * @return texture1 Background
     */
    public Texture loadTextures(){
        try {
            texture1.loadFromFile(Paths.get("./graphics/backgrounds/S3L1.png"));
        } catch(IOException ex) {
            //Ouch! something went wrong
            ex.printStackTrace();
        }
        return texture1;
    }

    /**
     * get Texture as vector
     *
     * @return texture1 size
     */
    public Vector2i getTexture1(){
        return texture1.getSize ();
    }

    /**
     * Update movements Speed
     */
    public void update(){
        backX +=speedX ;
		
        if (backX <= -10){
            backX += 10;
        }
    }

    /**
     * get back x
     *
     * @return backX
     */
    public int getBackX(){
        return backX;
    }
}
