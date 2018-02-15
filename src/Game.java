import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.function.*;

import org.jsfml.system.*;
import org.jsfml.window.*;
import org.jsfml.window.event.*;
import org.jsfml.graphics.*;

/**
 *Creates a new window with an environment for the player to move and explore.
 *
 *Creates the background, calls the Hero class, Box and Enemy. Collision detection is also included
 *to detect contacts between projectiles and enemies.
 *
 * @version  1.0 Build 1 Feb 12, 2018.
 */
public class Game{

    private boolean shot = false; //declare shot as false (shot not fired)
    private String Title = "Let's Play!";
    private RenderWindow window = new RenderWindow (); //Create the window
    private Enemy boss; //declare enemy
    private Box box1; //declare box for player to jump on
    private Box box2;
    enum State {MENU, GAME}

    State playerChoice = State.GAME; //Players choice is game
    public State getState() {
        return playerChoice;
    } // return the players choice

    ArrayList<Enemy> enemies = new ArrayList<> (); //create an array of enemies
    ArrayList<Box> boxes = new ArrayList<>(); //create an array of boxes

    Hero player = new Hero (100, 700); //Call Hero and set co-ordinates

    /**
     *Creates a window with a fixed frame rate and allows for the player to move
     *
     *Player can move using the w,a,s,d keys, the background will also move with the player
     *
     * @param width width of the window
     * @param height height of the window
     */
    public void run(int width, int height) {
        int screenWidth = width;
        int screenHeight = height;

        window.create (new VideoMode (screenWidth, screenHeight), Title, WindowStyle.DEFAULT); //create window
        window.setFramerateLimit (60); //set frame limit
        boss = new Enemy(800,730,player,"Main Boss"); //create boss from enemy class
        enemies.add(boss); //add boss to window

        //position of the boxes
        box1 = new Box(1500, 730, player);
        box2 = new Box(1800, 730, player);
        boxes.add(box1);
        boxes.add(box2);

        ArrayList<Projectiles> pro = new ArrayList<>(1);
        boolean dropped = true;
        while (true) {
            // Clear the screen as white
            window.clear (Color.WHITE);

            // Following handles player movement
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

            // 'dropped' checks if the projectile has finished its route first, before allowing to shoot again
            // 1 refers to throwing animation, 2 refers to shooting animation
            if (Keyboard.isKeyPressed (Keyboard.Key.Q) && dropped == true) {
                dropped = false;
                pro.add(0, new Projectiles (getHero().getCenterX(),getHero().getCenterY(),player, false, window, 1));
                shot = true;
            }
            if (Keyboard.isKeyPressed (Keyboard.Key.E) && dropped == true) {
                dropped = false;
                pro.add(0, new Projectiles (getHero().getCenterX(),getHero().getCenterY(),player, true, window, 1));
                shot = true;
            }
            if (Keyboard.isKeyPressed (Keyboard.Key.LBRACKET) && dropped == true) {
                dropped = false;
                pro.add(0, new Projectiles (getHero().getCenterX(),getHero().getCenterY(),player, false, window, 2));
                shot  = true;
            }
            if (Keyboard.isKeyPressed (Keyboard.Key.RBRACKET) && dropped == true) {
                dropped = false;
                pro.add(0, new Projectiles (getHero().getCenterX(),getHero().getCenterY(),player, true, window, 2));
                shot = true;
            }

            //calls checkCollision for collisions
            player.checkCollision(box1);
            player.checkCollision(box2);

            player.update (window);
            player.image().draw (window, RenderStates.DEFAULT);

            boss.image ().draw (window,RenderStates.DEFAULT);
            boss.update (player,window);

            box1.image ().draw (window,RenderStates.DEFAULT);
            box1.update (player,window);
            box2.update(player, window);
            box2.image ().draw (window,RenderStates.DEFAULT);
            box1.image ().draw (window,RenderStates.DEFAULT);

            // activated when a projectile is shot, checks for collisions and whether the projectile has finished its route
            if(shot == true) {
                Projectiles b = pro.get(0);
                b.shoot(window);
                // when projectile has finished its route, the img is set to null, so this check for that..
                if(b.getImg() == null) {
                    dropped = true;
                }
                if(b.getImg() != null) {
                    b.checkCollision(boss);
                }
            }

            player.checkCollision(boss);
            window.display ();   // Update the display with any changes

            // Handle any events
            for (Event event : window.pollEvents ()) {
                if (event.type == Event.Type.CLOSED) {
                    // the user pressed the close button
                    System.exit (0);
                }
            }
        }
    }

    /**
     * Return the hero
     *
     * @return hero as the player
     */
    public Hero getHero() {
        return player;
    }

    /**
     * Return an enemy
     *
     * @return enemy as boss
     */
    public Enemy getEnemy(){
        return boss;
    }
}