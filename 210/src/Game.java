import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.function.*;

import org.jsfml.system.*;
import org.jsfml.window.*;
import org.jsfml.window.event.*;
import org.jsfml.graphics.*;

public class Game {
    private static Background bg1, bg2;
    private Enemy x;
    enum State {
        MENU, GAME
    }

    State playerChoice = State.GAME;

    public State getState() {
        return playerChoice;
    }

    private String Title = "Let's Play!";
    private static int fontSize = 48;
    private int screenWidth = 1600;
    private int screenHeight = 900;
    private RenderWindow window = new RenderWindow ();
    public int xPosition;
    public int yPosition;
    Hero player = new Hero (100, 700);


    public void run(int width, int height) {
        int screenWidth = width;
        int screenHeight = height;

        bg1 = new Background (0, 0);
        bg2 = new Background (0, 0);

        window.create (new VideoMode (screenWidth, screenHeight),
                Title,
                WindowStyle.DEFAULT);

        window.setFramerateLimit (60);
         x = new Enemy (1200,700,player);

        while (true) {
            // Clear the screen
            window.clear (Color.WHITE);

            //window.draw(bg1.loadTextures ());

            // Move all the actors around
            player.idle ();

            player.image ().setPosition (player.getCenterX (), player.getCenterY ());

            if (Keyboard.isKeyPressed (Keyboard.Key.A)) {
                player.moveLeft ();
                player.image ().setPosition (player.getCenterX (), player.getCenterY ());
            }
            if (Keyboard.isKeyPressed (Keyboard.Key.D)) {
                player.moveRight();
                player.image ().setPosition (player.getCenterX (), player.getCenterY ());
            }
            if (Keyboard.isKeyPressed (Keyboard.Key.W)) {
                player.jump();
                player.image ().setPosition (player.getCenterX (), player.getCenterY ());
            }


            player.update (window,x);
            player.image ().draw (window, RenderStates.DEFAULT);
            x.update (player,window);
            x.image ().draw (window,RenderStates.DEFAULT);

            // Update the display with any changes
            window.display ();

            // Handle any events
            for (Event event : window.pollEvents ()) {
                if (event.type == Event.Type.CLOSED) {
                    // the user pressed the close button
                    System.exit (0);
                }


            }
        }

    }

    public Hero getHero() {
        return player;
    }
    public Enemy getEnemy(){
        return x;
    }
}



