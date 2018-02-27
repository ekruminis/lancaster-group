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
public class Game {


    private boolean shot = false; //declare shot as false (shot not fired)
    private String Title = "Let's Play!";
    private RenderWindow window = new RenderWindow (); //Create the window
    private Enemy boss; //declare enemy
    private Enemy boss2;
    private Enemy boss3;
    private Box box1; //declare box for player to jump on
    private Box box2;
    enum State {MENU, GAME}
    Music s = new Music();
    Music s2 = new Music();
    Music s3 = new Music();
    int lvl;
    boolean end = false;
    private String FontFile  = "font/FreeSans.ttf";
    Clock gamestart = new Clock();
    Clock fulltime = new Clock();
    float finaltime;
    int finalscore;
    double enemyScore;
    double levelScore;
    Text stage1;
    Text stage2;
    Text stage3;
    Text stage4;
    int score1 = 0;
    int score2 = 0;
    int score3 = 0;
    int score4 = 0;
    int endScore = 0;
    int n = 1;
    Carrot carrot;
    MrEqq eqq;
    Trump trump;
    Egg egg;
    TheBun bun;

    State playerChoice = State.GAME; //Players choice is game
    public State getState() {
        return playerChoice;
    } // return the players choice

    public  ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    ArrayList<Enemy> enemies = new ArrayList<> (); //create an array of enemies

    public ArrayList<Box> getBoxes() {
        return boxes;
    }

    static ArrayList<Box> boxes = new ArrayList<>(); //create an array of boxes

    static Hero player  = new Hero (100, 640, "./graphics/backgrounds/S3L3.png", "./graphics/characters/player/playableCharacterIdle.png", 4890, 1500);; //Call Hero and set co-ordinates

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
    public void go(int width, int height) {
        int screenWidth = width;
        int screenHeight = height;
        window.create (new VideoMode (screenWidth, screenHeight), Title, WindowStyle.DEFAULT); //create window
        window.setFramerateLimit (120); //set frame limit

        ArrayList<Projectiles> pro = new ArrayList<>(1);
        boolean dropped = true;
        level12();
        Font fontStyle = new Font();  //load font
        try {
            fontStyle.loadFromFile(Paths.get(FontFile));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

            while (true) {

                Text gameTime = new Text(("Time Elapsed: " + gamestart.getElapsedTime().asSeconds()), fontStyle, 15);

                gameTime.setColor(Color.RED);
                gameTime.setStyle(Text.BOLD | Text.UNDERLINED);
                gameTime.setPosition(1380, 20);

                Text endTime = new Text(("Total time taken: " + finaltime), fontStyle, 40);
                endTime.setColor(Color.GREEN);
                endTime.setStyle(Text.BOLD | Text.UNDERLINED);

                Text stage1 = new Text(("Stage 1 score:" + score1), fontStyle, 15);

                stage1.setColor(Color.RED);
                stage1.setStyle(Text.BOLD | Text.UNDERLINED);
                stage1.setPosition(20, 20);

                Text stage2 = new Text(("Stage 2 score:" + score2), fontStyle, 15);

                stage2.setColor(Color.RED);
                stage2.setStyle(Text.BOLD | Text.UNDERLINED);
                stage2.setPosition(20, 50);

                Text stage3 = new Text(("Stage 3 score:" + score3), fontStyle, 15);

                stage3.setColor(Color.RED);
                stage3.setStyle(Text.BOLD | Text.UNDERLINED);
                stage3.setPosition(20, 80);

                Text stage4 = new Text(("Stage 4 score:" + score4), fontStyle, 15);

                stage4.setColor(Color.RED);
                stage4.setStyle(Text.BOLD | Text.UNDERLINED);
                stage4.setPosition(20, 110);

                Text gameScore = new Text(("Total score:" + endScore), fontStyle, 15);

                gameScore.setColor(Color.RED);
                gameScore.setStyle(Text.BOLD | Text.UNDERLINED);
                gameScore.setPosition(20, 140);

                if(player.getBg().getBackX() + player.getCenterX() >= 3700 && lvl == 11) {
                    s2.stop();
                    window.clear();
                    System.out.println(getScore());
                    score1 = getScore();
                    endScore = score1;
                    for(int i = 0;i < enemies.size(); i++) {
                        enemies.get(i).resetScore();
                    }
                    level12();
                    gamestart.restart();
                }
                if(player.getBg().getBackX() + player.getCenterX() >= 5500 && lvl == 12) {
                    s2.stop();
                    System.out.println(getScore());
                    window.clear();
                    score1 = getScore();
                    endScore = score1;
                    for(int i = 0;i < enemies.size(); i++) {
                        enemies.get(i).resetScore();
                    }
                    level13();
                    gamestart.restart();
                }
                if(player.getBg().getBackX() + player.getCenterX() >= 5500 && lvl == 13) {
                    s2.stop();
                    System.out.println(getScore());
                    window.clear();
                    score1 = getScore();
                    endScore = score1;
                    for(int i = 0;i < enemies.size(); i++) {
                        enemies.get(i).resetScore();
                    }
                    level21();
                    gamestart.restart();
                }
                if(player.getBg().getBackX() + player.getCenterX() >= 5500 && lvl == 21) {
                    s2.stop();
                    System.out.println(getScore());
                    finalscore = 0;
                    score2 = getScore();
                    endScore = score1 + score2;
                    window.clear();
                    for(int i = 0;i < enemies.size(); i++) {
                        enemies.get(i).resetScore();
                    }
                    level22();
                    gamestart.restart();
                }
                if(player.getBg().getBackX() + player.getCenterX() >= 5500 && lvl == 22) {
                    s2.stop();
                    System.out.println(getScore());
                    window.clear();
                    score2 = getScore();
                    endScore = score1 + score2;
                    for(int i = 0;i < enemies.size(); i++) {
                        enemies.get(i).resetScore();
                    }
                    level23();
                    gamestart.restart();
                }
                if(player.getBg().getBackX() + player.getCenterX() >= 5500 && lvl == 23) {
                    s2.stop();
                    System.out.println(getScore());
                    window.clear();
                    score2 = getScore();
                    endScore = score1 + score2;
                    for(int i = 0;i < enemies.size(); i++) {
                        enemies.get(i).resetScore();
                    }
                    level31();
                    gamestart.restart();
                }
                if(player.getBg().getBackX() + player.getCenterX() >= 5500 && lvl == 31) {
                    s2.stop();
                    System.out.println(getScore());
                    finalscore = 0;
                    score3 = getScore();
                    endScore = score1 + score2 + score3;
                    window.clear();
                    for(int i = 0;i < enemies.size(); i++) {
                        enemies.get(i).resetScore();
                    }
                    level32();
                    gamestart.restart();
                }
                if(player.getBg().getBackX() + player.getCenterX() >= 5500 && lvl == 32) {
                    s2.stop();
                    System.out.println(getScore());
                    window.clear();
                    score3 = getScore();
                    endScore = score1 + score2 + score3;
                    for(int i = 0;i < enemies.size(); i++) {
                        enemies.get(i).resetScore();
                    }
                    level33();
                    gamestart.restart();
                }
                if(player.getBg().getBackX() + player.getCenterX() >= 5500 && lvl == 33) {
                    s2.stop();
                    System.out.println(getScore());
                    window.clear();
                    score3 = getScore();
                    endScore = score1 + score2 + score3;
                    for(int i = 0;i < enemies.size(); i++) {
                        enemies.get(i).resetScore();
                    }
                    level34();
                    gamestart.restart();
                }
                if(player.getBg().getBackX() + player.getCenterX() >= 1400 && lvl == 34) {
                    s2.stop();
                    System.out.println(getScore());
                    finalscore = 0;
                    score4 = getScore();
                    endScore = score1 + score2 + score3 + score4;
                    window.clear();
                    for(int i = 0;i < enemies.size(); i++) {
                        enemies.get(i).resetScore();
                    }
                    level35();
                    gamestart.restart();
                }
                if(player.getBg().getBackX() + player.getCenterX() >= 1900 && lvl == 35) {
                    s2.stop();
                    window.clear();
                    score4 = getScore();
                    endScore = score1 + score2 + score3 + score4;
                    for(int i = 0;i < enemies.size(); i++) {
                        enemies.get(i).resetScore();
                    }
                    stageEnd();
                }
            // Clear the screen as white
            window.clear (Color.WHITE);

            // Following handles player movement

                    player.idle();
                    player.image().setPosition(player.getCenterX(), player.getCenterY());
                    if (Keyboard.isKeyPressed(Keyboard.Key.A)) {
                        if(n <= 61) {
                            if (n == 60) {
                                n = 0;
                            }
                            player.moveLeft(n);
                            player.image().setPosition(player.getCenterX(), player.getCenterY());
                            n++;
                        }
                    }
                    if (Keyboard.isKeyPressed(Keyboard.Key.D)) {
                        if(n <= 61) {
                            if (n == 60) {
                                n = 0;
                            }
                                player.moveRight(n);
                                player.image().setPosition(player.getCenterX(), player.getCenterY());
                                n++;
                        }
                    }
                    if (Keyboard.isKeyPressed(Keyboard.Key.W)) {
                        player.jump();
                        player.image().setPosition(player.getCenterX(), player.getCenterY());
                    }

            // 'dropped' checks if the projectile has finished its route first, before allowing to shoot again
            // 1 refers to throwing animation, 2 refers to shooting animation
            if (Keyboard.isKeyPressed (Keyboard.Key.O) && dropped == true) {
                dropped = false;
                pro.add(0, new Projectiles (getHero().getCenterX(),getHero().getCenterY(), player, false, window, 6));
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
                //System.out.println("X=" + (player.getCenterX() + player.getBg().getBackX()));
                //System.out.println("Y=" + player.getCenterY());
            }

                player.update(window, this);
                player.image().draw(window, RenderStates.DEFAULT);

                for (Box box : boxes) {
                    player.checkCollision(box);
                    //System.out.println("-- Checked Collision --");
                    box.image().draw(window, RenderStates.DEFAULT);
                    box.update(player, window);
                    //System.out.println("-- Updated Screen --");
                }

                if(lvl != 36) {
                    window.draw(gameTime);
                    window.draw(stage1);
                    window.draw(stage2);
                    window.draw(stage3);
                    window.draw(stage4);
                    window.draw(gameScore);
                }

                if(lvl == 36) {
                    stage1.setPosition(140,160);
                    stage2.setPosition(140, 190);
                    stage3.setPosition(140, 220);
                    stage4.setPosition(140, 250);
                    gameScore.setCharacterSize(40);
                    gameScore.setColor(Color.BLACK);
                    gameScore.setPosition(330, 190);

                    endTime.setPosition(140, 350);

                    window.draw(stage1);
                    window.draw(stage2);
                    window.draw(stage3);
                    window.draw(stage4);
                    window.draw(gameScore);
                    window.draw(endTime);
                }

                if(shot == true) {
                    Projectiles b = pro.get(0);
                    b.shoot(window);
                    // when projectile has finished its route, the img is set to null, so this checks for that..
                    if(b.getImg() == null) {
                        dropped = true;
                    }
                    if(b.getImg() != null) {
                        b.checkCollision(egg);
                        //b.checkCollision(trump);
                        //b.checkCollision(eqq);
                        //b.checkCollision(bun);
                        //b.checkCollision(carrot);
                    }
                }

                if(carrot!=null){

                    carrot.update(player);
                    carrot.stun(player, window);
                    if(carrot.isVisible()) {
                        player.checkCollision(carrot);
                        carrot.draw(window);
                    }

                    player.checkCollision(carrot);
                }
                if(egg!=null) {
                    //System.out.println("I am here");
                    egg.update(player);
                    egg.shoot(player, window);
                    player.checkCollision(egg);
                    egg.draw(window);
                }



                if(player.getCurrentHealth() <= 0) {
                    Texture t = new Texture();
                    try {
                        t.loadFromFile(Paths.get("./graphics/backgrounds/death.png"));
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    Sprite death = new Sprite(t);
                    s.pause();
                    s2.pause();
                    window.clear();
                    death.setPosition(400,300);
                    window.draw(death);
                    if (Keyboard.isKeyPressed (Keyboard.Key.RETURN)) {
                        if( lvl == 11) {
                            level11();
                        }
                        if( lvl == 12) {
                            level12();
                        }
                        if( lvl == 13) {
                            level13();
                        }
                        if( lvl == 21) {
                            level21();
                        }
                        if( lvl == 22) {
                            level22();
                        }
                        if( lvl == 23) {
                            level23();
                        }
                        if( lvl == 31) {
                            level31();
                        }
                        if( lvl == 32) {
                            level32();
                        }
                        if( lvl == 33) {
                            level33();
                        }
                        if( lvl == 34) {
                            level34();
                        }
                        if( lvl == 35) {
                            level35();
                        }

                    }
                }

            // activated when a projectile is shot, checks for collisions and whether the projectile has finished its route

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
    private void level11() {
        lvl = 11;
        enemies.clear();
        boxes.clear();
        player = null;
        player = new Hero (100, 740, "./graphics/backgrounds/S1L1.png", "./graphics/characters/player/playableCharacterIdle.png", 3000, 1500);
        //boss = new Enemy(1000,760,player,"general"); //create boss from enemy class
        //enemies.add(boss); //add boss to window
        //egg = new Egg(700,740,player, window);
        //carrot = new Carrot(1300, 740, player, window);
        //position of the boxes

        try {
            s2.openFromFile(Paths.get("./audio/b2.wav"));
        } catch(IOException ex) {
            //"Houston, we have a problem."
            ex.printStackTrace();
        }
        s2.setVolume(20);
        s2.setLoop(true);
        s2.play();
    }

    private void level12() {
        lvl = 12;
        enemies.clear();
        boxes.clear();
        player = null;
        player = new Hero (100, 690, "./graphics/backgrounds/S1L2.png", "./graphics/characters/player/playableCharacterIdle.png", 4890, 1500);
        egg = new Egg(1000,650,player, window);
        //eqq = new MrEqq(1500,650,player,window);
        //carrot = new Carrot(2000, 650, player, window);

        //position of the boxes
        try {
            s2.openFromFile(Paths.get("./audio/b1.wav"));
        } catch(IOException ex) {
            //"Houston, we have a problem."
            ex.printStackTrace();
        }
        s2.setVolume(20);
        s2.setLoop(true);
        s2.play();
    }

    private void level13() {
        lvl = 13;
        enemies.clear();
        boxes.clear();
        player = null;
        player = new Hero (100, 690, "./graphics/backgrounds/S1L3.png", "./graphics/characters/player/playableCharacterIdle.png", 4890, 1500);
        boss = new Enemy(1000,720,player,"carrot"); //create boss from enemy class
        enemies.add(boss); //add boss to window

        //position of the boxes
        try {
            s2.openFromFile(Paths.get("./audio/b4.wav"));
        } catch(IOException ex) {
            //"Houston, we have a problem."
            ex.printStackTrace();
        }
        s2.setVolume(20);
        s2.setLoop(true);
        s2.play();
    }

    private void level21() {
        lvl = 21;
        enemies.clear();
        boxes.clear();
        player = null;
        player = new Hero (100, 680, "./graphics/backgrounds/S2L1.png", "./graphics/characters/player/playableCharacterIdle.png", 4890, 1500);
        boss = new Enemy(1000,710,player,"carrot"); //create boss from enemy class
        enemies.add(boss); //add boss to window

        //position of the boxes

        try {
            s2.openFromFile(Paths.get("./audio/b6.ogg"));
        } catch(IOException ex) {
            //"Houston, we have a problem."
            ex.printStackTrace();
        }
        s2.setVolume(20);
        s2.setLoop(true);
        s2.play();
    }

    private void level22() {
        lvl = 22;
        enemies.clear();
        boxes.clear();
        player = null;
        player = new Hero (100, 680, "./graphics/backgrounds/S2L2.png", "./graphics/characters/player/playableCharacterIdle.png", 4890, 1500);
        boss = new Enemy(1000,710,player,"carrot"); //create boss from enemy class
        enemies.add(boss); //add boss to window

        //position of the boxes

        try {
            s2.openFromFile(Paths.get("./audio/b5.wav"));
        } catch(IOException ex) {
            //"Houston, we have a problem."
            ex.printStackTrace();
        }
        s2.setVolume(20);
        s2.setLoop(true);
        s2.play();
    }

    private void level23() {
        lvl = 23;
        enemies.clear();
        boxes.clear();
        player = null;
        player = new Hero (100, 680, "./graphics/backgrounds/S2L3.png", "./graphics/characters/player/playableCharacterIdle.png", 4890, 1500);
        boss = new Enemy(1000,710,player,"bun"); //create boss from enemy class
        enemies.add(boss); //add boss to window

        //position of the boxes

        try {
            s2.openFromFile(Paths.get("./audio/b1.wav"));
        } catch(IOException ex) {
            //"Houston, we have a problem."
            ex.printStackTrace();
        }
        s2.setVolume(20);
        s2.setLoop(true);
        s2.play();
    }

    private void level31() {
        lvl = 31;
        enemies.clear();
        boxes.clear();
        player = null;
        player = new Hero (100, 645, "./graphics/backgrounds/S3L1.png", "./graphics/characters/player/playableCharacterIdle.png", 4890, 1500);
        boss = new Enemy(2000,660,player,"carrot"); //create boss from enemy class
        Enemy boss2 = new Enemy(900,660,player,"carrot");
        enemies.add(boss); //add boss to window
        enemies.add(boss2);

        //position of the boxes

        try {
            s2.openFromFile(Paths.get("./audio/b3.wav"));
        } catch(IOException ex) {
            //"Houston, we have a problem."
            ex.printStackTrace();
        }
        s2.setVolume(20);
        s2.setLoop(true);
        s2.play();
    }

    private void level32() {
        lvl = 32;
        enemies.clear();
        boxes.clear();
        player = null;
        player = new Hero (100, 645, "./graphics/backgrounds/S3L2.png", "./graphics/characters/player/playableCharacterIdle.png", 4890, 1500);
        boss = new Enemy(1000,660,player,"bun"); //create boss from enemy class
        enemies.add(boss); //add boss to window

        //position of the boxes
        ;
        try {
            s2.openFromFile(Paths.get("./audio/b5.wav"));
        } catch(IOException ex) {
            //"Houston, we have a problem."
            ex.printStackTrace();
        }
        s2.setVolume(20);
        s2.setLoop(true);
        s2.play();
    }

    private void level33() {
        lvl = 33;
        enemies.clear();
        boxes.clear();
        player = null;
        player = new Hero (100, 645, "./graphics/backgrounds/S3L3.png", "./graphics/characters/player/playableCharacterIdle.png", 4890, 1500);
        boss = new Enemy(1000,660,player,"Main Boss"); //create boss from enemy class
        Enemy boss2 = new Enemy(1300,660,player,"carrot"); //create boss from enemy class
        enemies.add(boss2);
        enemies.add(boss); //add boss to window

        //position of the boxes

        try {
            s2.openFromFile(Paths.get("./audio/b4.wav"));
        } catch(IOException ex) {
            //"Houston, we have a problem."
            ex.printStackTrace();
        }
        s2.setVolume(20);
        s2.setLoop(true);
        s2.play();
    }

    private void level34() {
        lvl = 34;
        enemies.clear();
        boxes.clear();
        player = null;
        player = new Hero (100, 645, "./graphics/backgrounds/S3L4.png", "./graphics/characters/player/playableCharacterIdle.png", 1100, 1500);
        boss = new Enemy(1000,660,player,"general"); //create boss from enemy class
        enemies.add(boss); //add boss to window

        //position of the boxes

        try {
            s2.openFromFile(Paths.get("./audio/b6.ogg"));
        } catch(IOException ex) {
            //"Houston, we have a problem."
            ex.printStackTrace();
        }
        s2.setVolume(20);
        s2.setLoop(true);
        s2.play();
    }

    private void level35() {
        lvl = 35;
        enemies.clear();
        boxes.clear();
        player = null;
        player = new Hero (100, 645, "./graphics/backgrounds/endlevel.png", "./graphics/characters/player/playableCharacterIdle.png", 1200, 1500);
        boss = new Enemy(1000,620,player,"trump"); //create boss from enemy class
        enemies.add(boss); //add boss to window

        try {
            s2.openFromFile(Paths.get("./audio/b6.ogg"));
        } catch(IOException ex) {
            //"Houston, we have a problem."
            ex.printStackTrace();
        }
        s2.setVolume(20);
        s2.setLoop(true);
        s2.play();
    }

    private void stageEnd() {
        lvl = 36;
        enemies.clear();
        boxes.clear();
        window.clear();
        player = null;
        player = new Hero (100, 720, "./graphics/backgrounds/endBillboard.png", "./graphics/characters/player/playableCharacterIdle.png", 0, 780);
        try {
            s2.openFromFile(Paths.get("./audio/b6.ogg"));
        } catch(IOException ex) {
            //"Houston, we have a problem."
            ex.printStackTrace();
        }
        finaltime = fulltime.getElapsedTime().asSeconds();
        s2.setVolume(20);
        s2.setLoop(true);
        s2.play();
    }

    public int getScore() {
        for(int i = 0;i < enemies.size(); i++) {
            enemyScore += enemies.get(i).getScore();
        }
        levelScore = ((enemyScore * 2) / (gamestart.getElapsedTime().asSeconds() * 4) * 1000);
        finalscore += levelScore;
        enemyScore = 0;
        return finalscore;
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