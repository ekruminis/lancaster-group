
import java.io.IOException;
import java.nio.file.*;

import org.jsfml.system.*;
import org.jsfml.window.*;
import org.jsfml.window.event.*;
import org.jsfml.graphics.*;

/**
 *This class implements a simple main menu for the player to select three options: Play, Options and Exit.
 *Test the commit.
 *
 * @version  1.0 Build 1 Feb 12, 2018.
 */
class MainMenu {
    private int noItemsInMenu;                              //Number of items in the window
    private int position;                                   //Position
    private static String FontFile  = "font/FreeSans.ttf";  //Font used for Main Menu text
    private static String Title     = "Bad | Reflection";   //Title of the Window

    private int screenWidth         = 1600;                 //Screen Width
    private int screenHeight        = 900;                  //Screen Height
    private RenderWindow window     = new RenderWindow ();  //Instance of Window

    /**
     *Load the appropriate background depending on the screenWidth: 1024(big), 1600(medium) or 1920(small).
     *
     * @return the background used for the Main Menu
     */
    private Sprite loadBackground(){
        Texture texture1 = new Texture();
        try {
            //Try to load the texture from folder 'mainMenu'"
            if(screenWidth == 1024)
              texture1.loadFromFile(Paths.get("./mainMenu/titleimgbig.png"));
            else if(screenWidth == 1600)
              texture1.loadFromFile(Paths.get("./mainMenu/titleimgmid.png"));
            else if(screenWidth == 1920)
              texture1.loadFromFile(Paths.get("./mainMenu/titleimgsmall.png"));
            //Texture was loaded successfully
        } catch(IOException ex) {
            //"Houston, we have a problem."
            ex.printStackTrace();
        }
        Sprite sprite = new Sprite (texture1);
        return sprite;
    }

    /**
     *Creates the first window and populates it with the three text options: Play, Options and Exit with different colours
     *
     *Creates text with arrays, adds colour, font and size to the three text options and then runs a main loop which allows the
     *player to select the option they want.
     *
     */
    public void run ( ) {
        position = 0; //set the position to start
        noItemsInMenu = 3;  //set number of items
        Text[] text = new Text[noItemsInMenu]; //Create the array for text
        Font sansRegular = new Font (); //set the font to sansRegular and then load it
        try {
            sansRegular.loadFromFile (Paths.get (FontFile));
        } catch (IOException ex) {
            ex.printStackTrace ();
        }
        // Create a window and set frame cap
        window.create (new VideoMode (screenWidth, screenHeight), Title, WindowStyle.DEFAULT);
        window.setFramerateLimit (30);

        //Create texts with different colors, sizes and styles
        text[0] = new Text ("Play", sansRegular, 32);
        text[0].setStyle (Text.ITALIC);
        text[0].setPosition (screenWidth/3, screenHeight-(screenHeight-200));
        text[0].setColor (Color.RED);

        text[1] = new Text ("Option", sansRegular, 32);
        FloatRect text2bounds = text[1].getLocalBounds ();
        text[1].setStyle (Text.ITALIC);
        text[1].setOrigin (text2bounds.width / 2, text2bounds.height / 2);
        text[1].setPosition (screenWidth/3, screenHeight-(screenHeight-350));
        text[1].setColor (Color.YELLOW);

        text[2] = new Text ("Exit", sansRegular, 32);
        text[2].setStyle (Text.ITALIC);
        text[2].setPosition (screenWidth/3, screenHeight-(screenHeight-500));
        text[2].setColor (Color.YELLOW);

        //Main loop
        while (window.isOpen ()){window.clear ();
        window.draw (loadBackground());

            for(int i=0;i<3;i++)
            window.draw (text[i]);
            window.display ();

            for (Event event : window.pollEvents ()) {
                if (event.type == Event.Type.CLOSED) {
                    // the user pressed the close button
                    window.close ();
                }
                if (Keyboard.isKeyPressed (Keyboard.Key.W)) {
                    if (position-1 >=0) {
                        text[position].setColor (Color.YELLOW);
                        position--;
                        text[position].setColor (Color.RED);
                        break;
                    }
                }
                if (Keyboard.isKeyPressed (Keyboard.Key.S)) {
                    if (position+1 < noItemsInMenu) {
                        text[position].setColor (Color.YELLOW);
                        position++;
                        text[position].setColor (Color.RED);
                        break;
                    }
                }
                if (Keyboard.isKeyPressed (Keyboard.Key.SPACE)) {
                    if (position == 0) {
                      window.close ();
                      Game g = new Game( );
                      g.run(getWidth(), getHeight());
                    } else if (position == 1) {
                      window.close();
                      optionMenu();

                    }  else if (position == 2) {
                        System.exit(0);
                    }
                }
            }
        }
    }

    /**
     *Creates the option window and populates it with resolution settings
     *
     *Creates text with arrays, adds colour, font and size to the three text options and then runs a main loop which allows the
     *player to select the option they want.
     *
     */
    private void optionMenu() {
        position = 0;
        noItemsInMenu = 4;
        Text[] text = new Text[noItemsInMenu];
        Font sansRegular = new Font ();
        try {
            sansRegular.loadFromFile (
                    Paths.get (FontFile));
        } catch (IOException ex) {
            ex.printStackTrace ();
        }
        window.create (new VideoMode (screenWidth, screenHeight), Title, WindowStyle.DEFAULT);
        window.setFramerateLimit (30);

        text[0] = new Text ("1024x768", sansRegular, 32);
        text[0].setStyle (Text.ITALIC);
        text[0].setPosition (screenWidth/3, screenHeight-(screenHeight-200));
        text[0].setColor (Color.RED);

        text[1] = new Text ("1600x900", sansRegular, 32);
        FloatRect text2bounds = text[1].getLocalBounds ();
        text[1].setStyle (Text.ITALIC);
        text[1].setOrigin (text2bounds.width / 2, text2bounds.height / 2);
        text[1].setPosition (screenWidth/3, screenHeight-(screenHeight-350));
        text[1].setColor (Color.YELLOW);

        text[2] = new Text ("1920x1080", sansRegular, 32);
        text[2].setStyle (Text.ITALIC);
        text[2].setPosition (screenWidth/3, screenHeight-(screenHeight-500));
        text[2].setColor (Color.YELLOW);

        text[3] = new Text ("Exit", sansRegular, 32);
        text[3].setStyle (Text.ITALIC);
        text[3].setPosition (screenWidth/3, screenHeight-(screenHeight-650));
        text[3].setColor (Color.YELLOW);

        //Main loop for option menu
        while (window.isOpen ()) { window.clear ();
            window.draw (loadBackground());

            for(int i=0;i<4;i++)
            window.draw (text[i]);
            window.display ();

            for (Event event : window.pollEvents ()) {
                if (event.type == Event.Type.CLOSED) {
                    // the user pressed the close button
                    window.close ();
                }
                if (Keyboard.isKeyPressed (Keyboard.Key.W)) {
                    if (position-1 >=0) {
                        text[position].setColor (Color.YELLOW);
                        position--;
                        text[position].setColor (Color.RED);
                        break;
                    }
                }
                if (Keyboard.isKeyPressed (Keyboard.Key.S)) {
                    if (position+1 < noItemsInMenu) {
                        text[position].setColor (Color.YELLOW);
                        position++;
                        text[position].setColor (Color.RED);
                        break;
                    }
                }
                if (Keyboard.isKeyPressed (Keyboard.Key.SPACE)) {
                    if (position == 0) {
                        screenWidth = 1024;
                        screenHeight = 768;
                        window.close();
                        optionMenu();

                    }  else if (position == 1) {
                      screenWidth = 1600;
                      screenHeight = 900;
                      window.close();
                      optionMenu();
                    } else if (position == 2) {
                      screenWidth = 1920;
                      screenHeight = 1080;
                      window.close();
                      optionMenu();
                    } else if (position == 3) {
                      window.close();
                      window.close();
                      run();
                    }
                }
            }
        }
    }

    /**
     * This returns the status
     * @return status
     */


    /**
     * This returns the height of the screen
     * @return Screen Height
     */
    private int getHeight() {
      return screenHeight;
    }

    /**
     * This returns the width of the screen
     * @return Screen width
     */
    private int getWidth() {
      return screenWidth;
    }

    //Main function calls run function within MainMenu
    public static void main (String args[ ]) {
        MainMenu mm = new MainMenu ();
        mm.run ();
    }
}