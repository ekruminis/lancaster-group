
import java.io.IOException;
import java.nio.file.*;

import org.jsfml.system.*;
import org.jsfml.window.*;
import org.jsfml.window.event.*;
import org.jsfml.graphics.*;

class MainMenu {

    private static int fontSize     = 48;
    private static String FontFile  = "font/FreeSans.ttf";

    private static String Title   = "Hello SCC210!";

    private RenderWindow window = new RenderWindow ();
    private int noItemsInMenu=3;
    private int position=0;
    private boolean status=true;
    private int screenWidth = 1600;
    private int screenHeight = 900;
    private class Message  {
        private Text text;

        public Message(int x, int y, int r, String message, Color c) {
            //
            // Load the font
            //
            Font sansRegular = new Font( );
            try {
                sansRegular.loadFromFile(
                        Paths.get(FontFile));
            } catch (IOException ex) {
                ex.printStackTrace( );
            }

            text = new Text (message, sansRegular, fontSize);
            text.setColor(c);
            text.setStyle(Text.BOLD | Text.UNDERLINED);

            FloatRect textBounds = text.getLocalBounds( );
            // Find middle and set as origin/ reference point
            text.setOrigin(textBounds.width / 2,
                    textBounds.height / 2);


        }
    }

    private class Image  {
        private Sprite img;

        public Image(int x, int y, int r, String textureFile) {
            //
            // Load image/ texture
            //
            Texture imgTexture = new Texture( );
            try {
                imgTexture.loadFromFile(Paths.get(textureFile));
            } catch (IOException ex) {
                ex.printStackTrace( );
            }
            imgTexture.setSmooth(true);

            img = new Sprite(imgTexture);
            img.setOrigin(Vector2f.div(
                    new Vector2f(imgTexture.getSize()), 2));


        }
    }

    public Sprite loadBackground(){
        Texture texture1 = new Texture();

        try {
            //Try to load the texture from file "mario.jpg"//change your paths boys/girl"
            if(screenWidth == 1024)
              texture1.loadFromFile(Paths.get("./mainmenu/titleimgbig.png"));
            else if(screenWidth == 1600)
              texture1.loadFromFile(Paths.get("./mainmenu/titleimgmid.png"));
            else if(screenWidth == 1920)
              texture1.loadFromFile(Paths.get("./mainmenu/titleimgsmall.png"));
            //Texture was loaded successfully - retrieve and print size
            Vector2i size = texture1.getSize();

           // System.out.println("The texture is " + size.x + "x" + size.y);
        } catch(IOException ex) {
            //Ouch! something went wrong
            ex.printStackTrace();
        }
        Sprite sprite = new Sprite (texture1);
        return sprite;
    }

    public void run ( ) {
      position = 0;
      noItemsInMenu = 3;
        Text[] text = new Text[noItemsInMenu];

        Font sansRegular = new Font ();
        try {
            sansRegular.loadFromFile (
                    Paths.get (FontFile));
        } catch (IOException ex) {
            ex.printStackTrace ();
        }

        //
        // Create a window
        //
        // RenderWindow window = new RenderWindow ();
        window.create (new VideoMode (screenWidth, screenHeight),
                Title,
                WindowStyle.DEFAULT);
        window.setFramerateLimit (30);
        // Avoid excessive updates

        //Create some texts with different colors, sizes and styles
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
        while (window.isOpen ()) {

            window.clear ();
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

    public void optionMenu ( ) {
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
        //
        // Create a window
        //
        // RenderWindow window = new RenderWindow ();
        window.create (new VideoMode (screenWidth, screenHeight),
                Title,
                WindowStyle.DEFAULT);
        window.setFramerateLimit (30);
        // Avoid excessive updates

        //Create some texts with different colors, sizes and styles
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

//Main loop
        while (window.isOpen ()) {
            window.clear ();
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

    public boolean getStatus(){
        return status;
    }

    public int getHeight() {
      return screenHeight;
    }

    public int getWidth() {
      return screenWidth;
    }

    public static void main (String args[ ]) {
        // Music titlesong = new Music();
        // try {
        //   titlesong.openFromFile(Paths.get("titlesong.ogg"));
        // } catch(IOException ex) {
        //   ex.printStackTrace();
        // }
        // titlesong.setLoop(true);
        // titlesong.play();

        MainMenu x = new MainMenu ();
        x.run ();

    }
}
