
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.function.*;

import org.jsfml.system.*;
import org.jsfml.window.*;
import org.jsfml.window.event.*;
import org.jsfml.graphics.*;

class MainMenu {

    //
    // The Java install comes with a set of fonts but these will
    // be on different filesystem paths depending on the version
    // of Java and whether the JDK or JRE version is being used.
    //
    private static String JavaVersion =
            Runtime.class.getPackage( ).getImplementationVersion( );
    private static String JdkFontPath =
            "C:\\Program Files\\Java\\jdk" + JavaVersion +
                    "\\jre\\lib\\fonts\\";
    private static String JreFontPath =
            "C:\\Program Files\\Java\\jre" + JavaVersion +
                    "\\lib\\fonts\\";

    private static int fontSize     = 48;
    private static String FontFile  = "LucidaSansRegular.ttf";
    private static String ImageFile = "team.jpg";

    private static String Title   = "Hello SCC210!";


    private String FontPath;	// Where fonts were found
    private int noItemsInMenu=3;
    private int position=0;
    private boolean status=true;
    private class Message  {
        private Text text;

        public Message(int x, int y, int r, String message, Color c) {
            //
            // Load the font
            //
            Font sansRegular = new Font( );
            try {
                sansRegular.loadFromFile(
                        Paths.get(FontPath+FontFile));
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
    //actually should be called loadbackground :D
    public Sprite loadTextures(){
        Texture texture1 = new Texture();

        try {
            //Try to load the texture from file "mario.jpg"//change your paths boys/girl"
            texture1.loadFromFile(Paths.get("C:\\Users\\LukePc\\Desktop\\210\\src\\Mario.jpg"));

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
        Text[] text = new Text[noItemsInMenu];
        //
        // Check whether we're running from a JDK or JRE install
        // ...and set FontPath appropriately.
        //
        if ((new File (JreFontPath)).exists ()) FontPath = JreFontPath;
        else FontPath = JdkFontPath;

        Font sansRegular = new Font ();
        try {
            sansRegular.loadFromFile (
                    Paths.get (FontPath + FontFile));
        } catch (IOException ex) {
            ex.printStackTrace ();
        }
        //
        // Create a window
        //
        RenderWindow window = new RenderWindow ();
        window.create (new VideoMode (1024, 768),
                Title,
                WindowStyle.DEFAULT);
        window.setFramerateLimit (30);
        // Avoid excessive updates

        //Create some texts with different colors, sizes and styles
        text[0] = new Text ("Play", sansRegular, 32);
        text[0].setStyle (Text.ITALIC);
        text[0].setPosition (400, 200);
        text[0].setColor (Color.RED);

        text[1] = new Text ("Option", sansRegular, 32);
        FloatRect text2bounds = text[1].getLocalBounds ();
        text[1].setStyle (Text.ITALIC);
        text[1].setOrigin (text2bounds.width / 2, text2bounds.height / 2);
        text[1].setPosition (420, 400);
        text[1].setColor (Color.YELLOW);

        text[2] = new Text ("Exit", sansRegular, 32);
        text[2].setStyle (Text.ITALIC);
        text[2].setPosition (380, 600);
        text[2].setColor (Color.YELLOW);

//Main loop
        while (window.isOpen ()) {

            window.clear ();
            window.draw (loadTextures ());

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
                        status = true;
                        window.close ();

                    } else if (position == 2) {
                        window.close ();

                    }
                }

            }
        }
    }
    public boolean getStatus(){
        return status;
    }

    public static void main (String args[ ]) {
        MainMenu x = new MainMenu ();
        x.run ();

    }
}
