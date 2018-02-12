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
    private Box box1;
    private Box box2;
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
    ArrayList<Box> boxes = new ArrayList<>();

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
        //x = new Enemy (400,730,player,"1");
        Thankyou = new Enemy(800,730,player,"2");
        enemies.add(Thankyou);
        //enemies.add(x);
        box1 = new Box(1500, 730, player);
        box2 = new Box(1800, 730, player);
        boxes.add(box1);
        boxes.add(box2);
        ArrayList<Projectiles> pro = new ArrayList<>(1);
        boolean dropped = true;
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
                player.moveRight ();
                player.image ().setPosition (player.getCenterX (), player.getCenterY ());
            }
            if (Keyboard.isKeyPressed (Keyboard.Key.W)) {
                player.jump ();
                player.image ().setPosition (player.getCenterX (), player.getCenterY ());
            }
            if (Keyboard.isKeyPressed (Keyboard.Key.Q) && dropped == true) {
                dropped = false;
                pro.add(0, new Projectiles (getHero().getCenterX(),getHero().getCenterY(),player, false, window, 1));
                shooted = true;
            }
            if (Keyboard.isKeyPressed (Keyboard.Key.E) && dropped == true) {
                dropped = false;
                pro.add(0, new Projectiles (getHero().getCenterX(),getHero().getCenterY(),player, true, window, 1));
                shooted = true;
            }
            if (Keyboard.isKeyPressed (Keyboard.Key.LBRACKET) && dropped == true) {
                dropped = false;
                pro.add(0, new Projectiles (getHero().getCenterX(),getHero().getCenterY(),player, false, window, 2));
                shooted = true;
            }
            if (Keyboard.isKeyPressed (Keyboard.Key.RBRACKET) && dropped == true) {
                dropped = false;
                pro.add(0, new Projectiles (getHero().getCenterX(),getHero().getCenterY(),player, true, window, 2));
                shooted = true;
            }

            player.checkCollision(box1);
            player.checkCollision(box2);
            //x.image ().draw (window,RenderStates.DEFAULT);
            //x.update (player,window);
            box1.image ().draw (window,RenderStates.DEFAULT);
            box1.update (player,window);
            box2.update(player, window);
            player.update (window);
            player.image ().draw (window, RenderStates.DEFAULT);
            Thankyou.image ().draw (window,RenderStates.DEFAULT);
            //x.image ().draw (window,RenderStates.DEFAULT);
            box2.image ().draw (window,RenderStates.DEFAULT);
            box1.image ().draw (window,RenderStates.DEFAULT);
            Thankyou.update (player,window);
            if(shooted) {
                Projectiles b = pro.get(0);
                b.shoot(window);
                if(b.getImg() == null) {
                    dropped = true;
                }
                // if projectile hits an enemy, you still need to wait for it to cross all the way to the end before shooting again..
                // not really noticeable(only on bullet shooting when in close range to an enemy), but something to note..
                // lets just pretend the gun will be a revolver or something so shooting again takes time :)
                if(b.getImg() != null) {
                    //b.checkCollision(x);
                    b.checkCollision(Thankyou);
                }
            }
            //player.checkCollision(x);
            player.checkCollision(Thankyou);

            //System.out.println("x has = " + x.getCurrentHealth());
            //System.out.println("thankyou has = " + Thankyou.getCurrentHealth());

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
        return Thankyou;
    }
}



