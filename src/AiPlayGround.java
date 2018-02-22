import org.jsfml.graphics.RenderWindow;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.function.*;

import org.jsfml.audio.Music;
import org.jsfml.system.*;
import org.jsfml.window.*;
import org.jsfml.window.event.*;
import org.jsfml.graphics.*;

public class AiPlayGround {
    private String ImageFile = "./graphics/characters/player/idle.png";
    private RenderWindow window = new RenderWindow ();

    public AiPlayGround(){

        window.create (new VideoMode (1600, 900), "Let's play", WindowStyle.DEFAULT); //create window
        window.setFramerateLimit (120); //set frame limit
        Hero hero = new Hero(100,684,"./graphics/backgrounds/S3L4.png",ImageFile,5000,5000);
        while(window.isOpen()){
            window.clear();
            window.draw(hero.image());



            window.display();
        }
    }
    public static void main (String args[ ]) {
        AiPlayGround play = new AiPlayGround();
    }
}
