import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2i;

import java.io.IOException;
import java.nio.file.Paths;

public class Background {
    private int backX;
    private int backY;
    private int speedX;
    Texture texture1 = new Texture();

    public Background(int x,int y){
        this.backX=x;
        this.backY=y;

    }

    public Texture loadTextures(){


        try {

                texture1.loadFromFile(Paths.get("background2.png"));

        } catch(IOException ex) {
            //Ouch! something went wrong
            ex.printStackTrace();
        }


        return texture1;
    }

    public Vector2i getTexture1() {
        return texture1.getSize ();
    }

    public void update() {
        backX +=speedX ;
		
        if (backX <= -10){
            backX += 10;
        }
    }
    public int getBackX(){
        return backX;
    }
}
