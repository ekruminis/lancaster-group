import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;

import java.io.IOException;
import java.nio.file.Paths;

public class MrEqq  extends Collision{
    private FloatRect hitbox;

    private double eX;
    private double eY;
    private boolean jumped=false;
    private Sprite eqq;
    private String FontFile  = "font/FreeSans.ttf";  //get font(used for health bar)

    double v0 = 50; // m/s
    double angle = 60;
    double dt = (int) 1; // s

    double vx = (int) (v0 * Math.cos(Math.PI / 180 * angle));
    double vy = (int) (v0 * Math.sin(Math.PI / 180 * angle));

    double posx; // m
    double posy;  // m
    double time = 0; // s

    RenderWindow window;


    public MrEqq(double eX,double eY,Hero hero, RenderWindow window){
        this.eX=eX;
        this.eY=eY;
        this.currenthealth = 50;
        this.window = window;
        posx=eX;
        posy=eY;
        eqq = changeImg(eqq,"graphics/characters/bird/birdAttack.png");
        eqq.setPosition((int)eX,(int)eY);
//        eqq.setOrigin(Vector2f.div(new Vector2f(hero.getImgTexture ().getSize ()), 1000000));

        jumped=false;

        //hitbox = new FloatRect(eX - hero.getBg().getBackX(), eY, 96, 96);
    }
    @Override
    public void BasicmovementLeft(Hero hero) {


        posx -= vx * 0.2;
        posy -= vy * 0.2;
        time += dt;

        //System.out.println(vx + " " + vy);
        //System.out.println(posy+ " " +posx);
        // change speed in y
        vy -= 1.5 * dt; // gravity
        eqq.setPosition((int)posx,(int)posy);
        Font fontStyle = new Font();  //load font
        try {
            fontStyle.loadFromFile(Paths.get(FontFile));
        } catch (IOException ex) {
            ex.printStackTrace( );
        }

        Text healthbar = new Text(("health: " + String.valueOf(getCurrentHealth())), fontStyle, 15);

        healthbar.setColor(Color.GREEN);
        healthbar.setStyle(Text.BOLD | Text.UNDERLINED);
        healthbar.setPosition((float)eX-hero.getBg().getBackX(), (float)eY-15);

        if(this.getCurrentHealth() <= 50) {
            healthbar.setColor(Color.YELLOW);
        }
        if(this.getCurrentHealth() <= 25) {
            healthbar.setColor(Color.RED);
        }
        if(this.getCurrentHealth() <= 0) {
            //gameover();
            healthbar.setString("You're dead");
        }
        window.draw(healthbar);
    }

    @Override
    public void BasicmovementRight(Hero hero) {
        posx += vx * 0.2;
        posy -= vy * 0.2;
        time += dt;

        //System.out.println(vx + " " + vy);
        //System.out.println(posy+ " " +posx);
        // change speed in y
        vy -= 1.5 * dt; // gravity
        eqq.setPosition((int)posx,(int)posy);
        Font fontStyle = new Font();  //load font
        try {
            fontStyle.loadFromFile(Paths.get(FontFile));
        } catch (IOException ex) {
            ex.printStackTrace( );
        }

        Text healthbar = new Text(("health: " + String.valueOf(getCurrentHealth())), fontStyle, 15);

        healthbar.setColor(Color.GREEN);
        healthbar.setStyle(Text.BOLD | Text.UNDERLINED);
        healthbar.setPosition((float)eX, (float)eY-15);

        if(this.getCurrentHealth() <= 50) {
            healthbar.setColor(Color.YELLOW);
        }
        if(this.getCurrentHealth() <= 25) {
            healthbar.setColor(Color.RED);
        }
        if(this.getCurrentHealth() <= 0) {
            //gameover();
            healthbar.setString("You're dead");
        }
    }

    public void jump(Hero hero){









    }

    public Sprite changeImg(Sprite x,String e) {
        Texture imgTexture = new Texture ();
        try {
            imgTexture.loadFromFile (Paths.get (e));
        } catch (IOException ex) {
            ex.printStackTrace ();
        }
        imgTexture.setSmooth (true);
        x = new Sprite (imgTexture);
        return x;
    }
    public void draw(RenderWindow window){window.draw(eqq);}

    public FloatRect getRect() {
        return hitbox;
    }

    int currenthealth;

    public void setCurrentHealth(int currenthealth) {
        this.currenthealth = currenthealth;
    }

    public int getCurrentHealth() {
        return currenthealth;
    }
}
