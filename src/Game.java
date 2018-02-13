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
    private Enemy boss;
    private Box box1;
    private Box box2;
    enum State {
        MENU, GAME
    }

    State playerChoice = State.GAME;

    public State getState() {
        return playerChoice;
    }

    ArrayList<Enemy> enemies = new ArrayList<> ();
    ArrayList<Box> boxes = new ArrayList<>();

    private Projectiles pro;
    private boolean shot=false;

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
        //x = new Enemy (400,730,player,"1");
        boss = new Enemy(800,730,player,"Main Boss");
        enemies.add(boss);

        box1 = new Box(1500, 730, player);
        box2 = new Box(1800, 730, player);
        boxes.add(box1);
        boxes.add(box2);

        ArrayList<Projectiles> pro = new ArrayList<>(1);
        boolean dropped = true;
        while (true) {
            // Clear the screen
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

            player.checkCollision(box1);
            player.checkCollision(box2);

            player.update (window);
            player.image ().draw (window, RenderStates.DEFAULT);

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
        return boss;
    }
}