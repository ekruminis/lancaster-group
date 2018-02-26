
import org.jsfml.graphics.IntRect;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import java.io.IOException;

import java.nio.file.Paths;


public class SpriteSheet {
    Texture spriteTexture = new Texture(); //Sprite texture
    public Texture getSprites(String dest){
        try {
            spriteTexture.loadFromFile(Paths.get(dest));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        spriteTexture.setSmooth (true);
        return spriteTexture;
    }

    public Sprite getFrame(int x, int y, int w, int h, String dest){
        getSprites(dest);
        Sprite frame = new Sprite(spriteTexture);
        frame.setTextureRect(new IntRect(x, y, w, h));
        return frame;
    }
}



