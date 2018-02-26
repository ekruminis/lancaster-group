import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

public abstract class Collision {

    private Texture ourText;
    public String drawMe;
    public  boolean isColliding(ArrayList<Box> sprites, Sprite enemy){
        boolean result = false;
        for(Box sprite : sprites){
            if(enemy.getGlobalBounds().intersection(sprite.image().getGlobalBounds())!= null){
                result = true;
                break;
            }

        }

        return result;
    }


    public abstract void BasicmovementLeft(Hero hero );

    public abstract void BasicmovementRight(Hero hero);

}
