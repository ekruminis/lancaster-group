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
    private Enemy Thankyou;
    enum State {
        MENU, GAME
    }

    State playerChoice = State.GAME;

    public State getState() {
        return playerChoice;
    }


    private boolean collidedLeft = false;
    private boolean collidedRight = false;
    private boolean collidedTop = false;
    ArrayList<Enemy> enemies = new ArrayList<> ();

    private Projectiles pro;
    private boolean shooted=false;


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

        window.setFramerateLimit (100);
         x = new Enemy (300,730,player,"1");
         Thankyou = new Enemy(800,730,player,"2");
        enemies.add(x);
        enemies.add(Thankyou);
        while (true) {
            // Clear the screenwd
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
                player.moveRight ();
                player.image ().setPosition (player.getCenterX (), player.getCenterY ());
            }
            if (Keyboard.isKeyPressed (Keyboard.Key.W)) {
                player.jump ();
                player.image ().setPosition (player.getCenterX (), player.getCenterY ());
            }
            if (Keyboard.isKeyPressed (Keyboard.Key.SPACE)) {
                pro = new Projectiles (getHero().getCenterX(),getHero().getCenterY(),player);
                shooted=true;

            }

            for( Enemy ENEMIES : enemies ){
                player.checkCollision (ENEMIES);
            }


            x.image ().draw (window,RenderStates.DEFAULT);
            x.update (player,window);
            Thankyou.update (player,window);
            player.update (window,x);
            player.image ().draw (window, RenderStates.DEFAULT);
            Thankyou.image ().draw (window,RenderStates.DEFAULT);
            x.image ().draw (window,RenderStates.DEFAULT);
            if(shooted) {
                pro.shoot (window);
                pro.draw (window);
            }


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



