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
    int lvl;
    boolean end = false;
    private String FontFile  = "font/FreeSans.ttf";
    Clock gamestart = new Clock();
    Clock fulltime = new Clock();
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
        level11();
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
                        player.moveLeft();
                        player.image().setPosition(player.getCenterX(), player.getCenterY());
                    }
                    if (Keyboard.isKeyPressed(Keyboard.Key.D)) {
                        player.moveRight();
                        player.image().setPosition(player.getCenterX(), player.getCenterY());
                    }
                    if (Keyboard.isKeyPressed(Keyboard.Key.W)) {
                        player.jump();
                        player.image().setPosition(player.getCenterX(), player.getCenterY());
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

                player.update(window, this);
                player.image().draw(window, RenderStates.DEFAULT);

                for (Enemy bosses : enemies) {
                    //  System.out.println(bosses.getX()+ "  " +bosses.getY());
                    if (player.getScreen().contains(bosses.getX(), 650)) {
                        //   System.out.println("I am in");
                        bosses.setActive(true);
                        player.checkCollision(bosses);
                        bosses.image().draw(window, RenderStates.DEFAULT);
                        bosses.update(player, window);
                        bosses.move(player, window, this);
                    } else {
                        bosses.setActive(false);
                    }
                    if(shot == true) {
                        Projectiles b = pro.get(0);
                        b.shoot(window);
                        // when projectile has finished its route, the img is set to null, so this checks for that..
                        if(b.getImg() == null) {
                            dropped = true;
                        }
                        if(b.getImg() != null) {
                            b.checkCollision(bosses);
                        }
                    }
                }

                for (Box box : boxes) {
                    player.checkCollision(box);
                    //System.out.println("-- Checked Collision --");
                    box.image().draw(window, RenderStates.DEFAULT);
                    box.update(player, window);
                    //System.out.println("-- Updated Screen --");
                }

                window.draw(gameTime);
                window.draw(stage1);
                window.draw(stage2);
                window.draw(stage3);
                window.draw(stage4);
                window.draw(gameScore);

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
        boss = new Enemy(1000,760,player,"bun"); //create boss from enemy class
        enemies.add(boss); //add boss to window
        //initialising the boxes
        Box box2;
        Box carBumper;
        Box carRoof;
        Box wall;
        Box bushes;

        //position of the boxes

        box2 = new Box(1587, 560, 80,90, player,1);
        carBumper = new Box(997,683,89,90,player,0);
        carRoof = new Box(1159,603,113,90,player,0);
        wall = new Box(1226,486,663,90,player,0);
        bushes = new Box(1990,597,272,90,player,0);

        //adding the boxes
        boxes.add(box2);
        boxes.add(carBumper);
        boxes.add(carRoof);
        boxes.add(wall);
        boxes.add(bushes);

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
        boss = new Enemy(1000,720,player,"bun"); //create boss from enemy class
        enemies.add(boss); //add boss to window


        //initialising boxes
        Box chairs;
        Box flowers;
        Box shelf;
        Box bin1;
        Box coffeeTable;


        //position of the boxes
        chairs = new Box(1570,560,1020,90,player,0);
        flowers  = new Box(2590,435,225,90,player,0);
        shelf = new Box(2825,245,690,90,player,0);
        bin1 = new Box(3740,575,193,90,player,0);
        coffeeTable = new Box(4584,686,661,90,player,0);


        //adding boxes to map
        boxes.add(chairs);
        boxes.add(flowers);
        boxes.add(shelf);
        boxes.add(bin1);
        boxes.add(coffeeTable);

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
        boss = new Enemy(1000,720,player,"bun"); //create boss from enemy class
        enemies.add(boss); //add boss to window

        //initialising boxes
        Box bin1;
        Box stand1;
        Box pole1;
        Box stand2;
        Box pole2;

        //position of the boxes
        bin1 = new Box(4658,655,104,90,player,0);
        pole1 = new Box(4678,489,36,90,player,0);
        stand1 = new Box(4702,533,378,90,player,0);
        pole2 = new Box(5060,493,32,90,player,0);
        stand2 = new Box(5020,665,124,90,player,0);

        //adding boxes
        boxes.add(bin1);
        boxes.add(pole1);
        boxes.add(stand1);
        boxes.add(pole2);
        boxes.add(stand2);

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
        boss = new Enemy(1000,710,player,"bun"); //create boss from enemy class
        enemies.add(boss); //add boss to window

        //initialising the boxes
        Box desk;
        Box SofaEnd;
        Box SofaEnd2;

        //position of the boxes
        desk = new Box(2292,595,482,90,player,0);
        SofaEnd= new Box(5438,621,314,90,player,0);
        SofaEnd2 = new Box(5438,621,240,90,player,0);
        //adding the boxes

        boxes.add(desk);
        boxes.add(SofaEnd);
        boxes.add(SofaEnd2);


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
        boss = new Enemy(1000,710,player,"bun"); //create boss from enemy class
        enemies.add(boss); //add boss to window

        //initialising them boxesss
        Box Bin1;
        Box sponges;
        Box Sofa1;
        Box Sofa2;

        //position of the boxes

        Bin1 = new Box(2272,580,150,90,player,0);
        sponges = new Box(4088,649,802,90,player,0);
        Sofa1 = new Box(4892,630,414,90,player,0);
        Sofa2 = new Box(4936,517,328,90,player,0);

        boxes.add(Bin1);
        boxes.add(sponges);
        boxes.add(Sofa1);
        boxes.add(Sofa2);



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


        //starting the boxes
        Box BoxA;
        Box BoxB;
        Box BoxC;
        Box BoxD;
        Box BoxE;
        Box BoxF;
        Box BoxG;
        Box BoxH;
        Box BoxI;


        //position of the boxes

        BoxA = new Box(318,654,288,90,player,0);
        BoxB = new Box(954,669,288,90,player,0);
        BoxC = new Box(1580,672,42,90,player,0);
        BoxD = new Box(4822,680,232,90,player,0);
        BoxE = new Box(4870,604,130,90,player,0);
        BoxF = new Box(5167,676,227,90,player,0);
        BoxG = new Box(5205,574,133,90,player,0);
        BoxH = new Box(5535,675,239,90,player,0);
        BoxI = new Box(5576,580,150,90,player,0);

        boxes.add(BoxA);
        boxes.add(BoxB);
        boxes.add(BoxC);
        boxes.add(BoxD);
        boxes.add(BoxE);
        boxes.add(BoxF);
        boxes.add(BoxG);
        boxes.add(BoxH);
        boxes.add(BoxI);


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





        //initialising the boxes
        Box level1Sink1,level1Sink2,level1Sink3;
        Box level1Sofa1_Arm1,level1Sofa1_Seat, level1Sofa1_Arm2;
        Box level1Table1,level1TablePlant1;

        //position of the boxes
        box2 = new Box(1800, 660, 80,90, player,1);
        level1Sink1 = new Box(455,555, 440,85,player,0);
        level1Sink2 = new Box(2040,555, 440,85,player,0);
        level1Sofa1_Arm1 = new Box(4010,600, 30,85,player,0);
        level1Sofa1_Arm2 = new Box(4330,600, 30,85,player,0);
        level1Sofa1_Seat = new Box(4067,658, 240,85,player,0);
        level1Sink3 = new Box(5502,566, 100,85,player,0);
        level1Table1 = new Box (4389,649,145,85,player,0);
        level1TablePlant1 = new Box(4435,617,47,85,player,0);

        //adding the boxes
        boxes.add(box2);boxes.add(level1Sink1);boxes.add(level1Sink2);boxes.add(level1Sink3);
        boxes.add(level1Sofa1_Arm1);boxes.add(level1Sofa1_Arm2); boxes.add(level1Sofa1_Seat);
        boxes.add(level1Table1); boxes.add(level1TablePlant1);








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


        //starting the boxes
        Box BoxA;
        Box BoxB;
        Box BoxC;
        Box BoxD;
        Box BoxE;
        //position of the boxes
        BoxA = new Box(361,654,161,90,player,0);
        BoxB = new Box(1400,669,161,90,player,0);
        BoxC = new Box(2690,605,58,90,player,0);
        BoxD = new Box(2757,657,309,90,player,0);
        BoxE = new Box(3066,599,66,90,player,0);


        boxes.add(BoxA);
        boxes.add(BoxB);
        boxes.add(BoxC);
        boxes.add(BoxD);
        boxes.add(BoxE);
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