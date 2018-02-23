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
    Music s = new Music();
    Music s2 = new Music();
    int lvl;
    boolean end = false;


    State playerChoice = State.GAME; //Players choice is game
    public State getState() {
        return playerChoice;
    } // return the players choice

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    ArrayList<Enemy> enemies = new ArrayList<> (); //create an array of enemies

    public ArrayList<Box> getBoxes() {
        return boxes;
    }

    ArrayList<Box> boxes = new ArrayList<>(); //create an array of boxes

    Hero player; //Call Hero and set co-ordinates

    public Sprite[] getCharacters() {
        return characters;
    }

    Sprite[] characters = new Sprite[20];
    public static boolean isColliding(ArrayList<Sprite> sprites, Sprite spriteA){
        boolean result = false;
        for(Sprite sprite : sprites){
            if(spriteA.getGlobalBounds().intersection(sprite.getGlobalBounds())!= null){
                result = true;
                break;
            }

        }
        return result;
    }

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
        window.setFramerateLimit (120); //set frame limit

        //level1();
        ArrayList<Projectiles> pro = new ArrayList<>(1);
        boolean dropped = true;
        myBitchLevel();
        while (true) {

           /* System.gc();
            if(player.getBg().getBackX() + player.getCenterX() >= 5800 && lvl == 1) {
                s2.stop();
                window.clear();
               // level2();
                myBitchLevel();
            }
            if(player.getBg().getBackX() + player.getCenterX() >= 5800 && lvl == 2) {
                s2.stop();
                window.clear();
                level3();
            }

            if(player.getBg().getBackX() + player.getCenterX() >= 5800 && lvl == 3) {
                s2.stop();
                window.clear();
                level4();
            }
            if(player.getBg().getBackX() + player.getCenterX() >= 1400 && lvl == 4) {
                s2.stop();
                window.clear();
                stageEnd();
            }*/
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
            if (Keyboard.isKeyPressed (Keyboard.Key.O) && dropped == true) {
                dropped = false;
                pro.add(0, new Projectiles (getHero().getCenterX(),getHero().getCenterY(),player, false, window, 6));
                shot = true;
            }
            if (Keyboard.isKeyPressed (Keyboard.Key.P) && dropped == true) {
                dropped = false;
                pro.add(0, new Projectiles (getHero().getCenterX(),getHero().getCenterY(),player, true, window, 6));
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
            if (Keyboard.isKeyPressed (Keyboard.Key.M) && dropped == true) {
                dropped = false;
                pro.add(0, new Projectiles (getHero().getCenterX(),getHero().getCenterY(),player, true, window, 4));
                shot = true;
            }
            if (Keyboard.isKeyPressed (Keyboard.Key.K) && dropped == true) {
                dropped = false;
                pro.add(0, new Projectiles (getHero().getCenterX(),getHero().getCenterY(),player, false, window, 5));
                shot = true;
            }
            if (Keyboard.isKeyPressed (Keyboard.Key.L) && dropped == true) {
                dropped = false;
                pro.add(0, new Projectiles (getHero().getCenterX(),getHero().getCenterY(),player, true, window, 5));
                shot = true;
            }

            // prints current co-ordinates
            if (Keyboard.isKeyPressed (Keyboard.Key.I)) {
                System.out.println("X=" + (player.getCenterX() + player.getBg().getBackX()));
                System.out.println("Y=" + player.getCenterY());
            }

            player.update (window,this);
            player.image().draw (window, RenderStates.DEFAULT);

            for(Enemy bosses : enemies) {
                player.checkCollision(bosses);
                bosses.image().draw(window, RenderStates.DEFAULT);
                bosses.update(player, window);
                bosses.move(player,window,this);
            }
            for(Box box : boxes) {
                player.checkCollision(box);
                //System.out.println("-- Checked Collision --");
                box.image().draw(window, RenderStates.DEFAULT);
                box.update(player,window);
                //System.out.println("-- Updated Screen --");
            }


            // activated when a projectile is shot, checks for collisions and whether the projectile has finished its route
            if(shot == true) {
                Projectiles b = pro.get(0);
                b.shoot(window);
                // when projectile has finished its route, the img is set to null, so this checks for that..
                if(b.getImg() == null) {
                    dropped = true;
                }
                if(b.getImg() != null) {
                    b.checkCollision(boss);
                }
            }

            window.display ();   // Update the display with any changes
            // Handle any events
            for (Event event : window.pollEvents ()) {
                if (event.type == Event.Type.CLOSED) {
                    // the user pressed the close button
                    System.exit (0);
                    s2.stop();
                }
            }
        }
    }
    public void myBitchLevel(){

        lvl = 5;
        enemies.clear();
        boxes.clear();
        player = null;
        player = new Hero (100, 640, "./graphics/backgrounds/S3L3.png", "./graphics/characters/player/playableCharacterIdle.png", 4890, 1500);
        boss = new Enemy(900,660,player,"carrot"); //create boss from enemy class
        enemies.add(boss); //add boss to window

        //position of the boxes
        box2 = new Box(1800, 660, 80,90, player);
        boxes.add(box2);
        try {
            s.openFromFile(Paths.get("./audio/dead.wav"));
        } catch(IOException ex) {
            //"Houston, we have a problem."
            ex.printStackTrace();
        }

    }

    private void level1() {
        lvl = 1;
        enemies.clear();
        boxes.clear();
        player = null;
        player = new Hero (100, 625, "./graphics/backgrounds/S3L1.png", "./graphics/characters/player/idle.png", 4890, 1500);
        boss = new Enemy(2000,660,player,"general"); //create boss from enemy class
        enemies.add(boss); //add boss to window

        //position of the boxes
        box2 = new Box(1800, 660, 80,90, player);
        boxes.add(box2);
     //   try {
            //s2.openFromFile(Paths.get("./audio/b3.wav"));
       // } catch(IOException ex) {
            //"Houston, we have a problem."
        //    ex.printStackTrace();
      //  }
       // s2.setVolume(40);
       // s2.play();
    }

    private void level2() {
        lvl = 2;
        enemies.clear();
        boxes.clear();
        player = null;
        player = new Hero (100, 625, "./graphics/backgrounds/S3L2.png", "./graphics/characters/player/idle.png", 4890, 1500);
        boss = new Enemy(1000,660,player,"bun"); //create boss from enemy class
        enemies.add(boss); //add boss to window

        //position of the boxes
        box2 = new Box(3300, 660, 80,90, player);
        Box box3 = new Box(500, 660, 80, 90, player);
        boxes.add(box2);
        try {
            s2.openFromFile(Paths.get("./audio/b5.wav"));
        } catch(IOException ex) {
            //"Houston, we have a problem."
            ex.printStackTrace();
        }
        s2.setVolume(40);
        s2.play();
    }

    private void level3() {
        lvl = 3;
        enemies.clear();
        boxes.clear();
        player = null;
        player = new Hero (100, 625, "./graphics/backgrounds/S3L3.png", "./graphics/characters/player/idle.png", 4890, 1500);
        boss = new Enemy(1000,660,player,"Main Boss"); //create boss from enemy class
        Enemy boss2 = new Enemy(1300,660,player,"carrot"); //create boss from enemy class
        enemies.add(boss2);
        enemies.add(boss); //add boss to window

        //position of the boxes
        box2 = new Box(3300, 660, 80,90, player);
        Box box3 = new Box(500, 660, 80, 90, player);
        boxes.add(box2);
        try {
            s2.openFromFile(Paths.get("./audio/b4.wav"));
        } catch(IOException ex) {
            //"Houston, we have a problem."
            ex.printStackTrace();
        }
        s2.setVolume(40);
        s2.play();
    }

    private void level4() {
        lvl = 4;
        enemies.clear();
        boxes.clear();
        player = null;
        player = new Hero (100, 625, "./graphics/backgrounds/S3L4.png", "./graphics/characters/player/idle.png", 900, 1000);
        boss = new Enemy(1000,660,player,"general"); //create boss from enemy class
        enemies.add(boss); //add boss to window

        //position of the boxes
        box2 = new Box(3300, 660, 80,90, player);
        Box box3 = new Box(500, 660, 80, 90, player);
        boxes.add(box2);
        try {
            s2.openFromFile(Paths.get("./audio/b6.ogg"));
        } catch(IOException ex) {
            //"Houston, we have a problem."
            ex.printStackTrace();
        }
        s2.setVolume(40);
        s2.play();
    }

    private void stageEnd() {
        lvl = 5;
        enemies.clear();
        boxes.clear();
        window.clear();
        window.close();
        System.gc();
        Texture endt = new Texture();
        try {
            endt.loadFromFile(Paths.get("./graphics/backgrounds/end.png"));
        } catch (IOException ex) {
            //Ouch! something went wrong
            ex.printStackTrace();
        }
        Sprite img = new Sprite(endt);
        RenderWindow window2 = new RenderWindow();
        window2.create (new VideoMode (1600, 900), Title, WindowStyle.DEFAULT);
        window2.setFramerateLimit(5);
        try {
            s2.openFromFile(Paths.get("./audio/end.wav"));
        } catch (IOException ex) {
            //"Houston, we have a problem."
            ex.printStackTrace();
        }
        s2.setLoop(true);
        s2.setVolume(100);
        s2.play();
        while(true) {
            System.gc();
            window2.clear();
            window2.draw(img);
            window2.display();
            for (Event event : window.pollEvents ()) {
                if (event.type == Event.Type.CLOSED) {
                    // the user pressed the close button
                    System.exit (0);
                    s2.stop();
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