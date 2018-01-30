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
  private static Background bg1,bg2;

  enum State{
		MENU,GAME
	}

  State playerChoice = State.GAME;
	public State getState(){
		return playerChoice;
	}

  private String Title = "Let's Play!";
  private static int fontSize = 48;
  private int screenWidth = 1600;
  private int screenHeight = 900;
  private RenderWindow window = new RenderWindow();
  public int xPosition;
  public int yPosition;

  private ArrayList<Actor> actors = new ArrayList<Actor>( );

  private abstract class Actor {
    Drawable obj;
    BiConsumer<Float, Float> setPosition;

    int x  = 0;	// Current X-coordinate
    int y  = 0;	// Current Y-coordinate

    //
    // Render the object at its new position
    //

    void draw(RenderWindow w) {
      w.draw(obj);
    }
  }

  private class Image extends Actor {
	private Sprite img;
    private int xx;
    private int yy;
    private String ImageFile = "graphics/player/idle.png";
    private int a;

		public Image(int x, int y) {
      xPosition = x;
      yPosition = y;
			//
			// Load image/ texture
			//
			Texture imgTexture = new Texture( );
			try {
				imgTexture.loadFromFile(Paths.get(ImageFile));
			} catch (IOException ex) {
				ex.printStackTrace( );
			}
			imgTexture.setSmooth(true);

			img = new Sprite(imgTexture);
      img.setOrigin(Vector2f.div(
					new Vector2f(imgTexture.getSize()), 2));

      img.setPosition(x,y);
			//
			// Store references to object and key methods
			//
			obj = img;
			setPosition = img::setPosition;
		}

    public Texture changeImg(String ImageFile2) {
      Texture imgTexture = new Texture( );
			try {
				imgTexture.loadFromFile(Paths.get(ImageFile2));
			} catch (IOException ex) {
				ex.printStackTrace( );
			}
			imgTexture.setSmooth(true);
      return imgTexture;
    }

    public Sprite image() {
      return img;
    }

	}

  public void run(int width, int height) {
    int screenWidth = width;
    int screenHeight = height;

    bg1 = new Background (0,0);
    bg2 = new Background (0,0);

    window.create(new VideoMode(screenWidth, screenHeight),
                  Title,
                  WindowStyle.DEFAULT);

    window.setFramerateLimit(60);

    Hero player = new Hero(100,700);

    while (playerChoice==State.GAME) {
      // Clear the screen
      window.clear(Color.WHITE);

      //window.draw(bg1.loadTextures ());

      // Move all the actors around
      player.idle();
      player.image ().setPosition (player.getCenterX (),player.getCenterY ());

      if (Keyboard.isKeyPressed (Keyboard.Key.A)) {
        player.moveLeft();
        player.image ().setPosition (player.getCenterX (),player.getCenterY ());
      }
      if (Keyboard.isKeyPressed (Keyboard.Key.D)) {
        player.moveRight();
        player.image ().setPosition (player.getCenterX (),player.getCenterY ());
      }
      if (Keyboard.isKeyPressed (Keyboard.Key.W)) {
        player.jump ();
        player.image ().setPosition (player.getCenterX (),player.getCenterY ());
      }


      player.update(window);
      player.image().draw(window, RenderStates.DEFAULT);

      // Update the display with any changes
      window.display( );

      // Handle any events
      for (Event event : window.pollEvents( )) {
        if (event.type == Event.Type.CLOSED) {
          // the user pressed the close button
          System.exit(0);
        }

        if (Keyboard.isKeyPressed (Keyboard.Key.ESCAPE)) {
          OptionMenu om = new OptionMenu();
          om.show();

        }
      }
    }
  }

  private class OptionMenu {
    public void show() {

      int noItemsInMenu = 4;
      Text[] text = new Text[noItemsInMenu];
      int position = 0;

      Texture texture1 = new Texture();

      try {
          //Try to load the texture from file "mario.jpg"//change your paths boys/girl"
          if(screenWidth == 1024)
            texture1.loadFromFile(Paths.get("mainmenu/titleimgsmall.png"));
          else if(screenWidth == 1600)
            texture1.loadFromFile(Paths.get("mainmenu/titleimgmid.png"));
          else if(screenWidth == 1920)
            texture1.loadFromFile(Paths.get("mainmenu/titleimgbig.png"));
          //Texture was loaded successfully - retrieve and print size
          Vector2i size = texture1.getSize();

         // System.out.println("The texture is " + size.x + "x" + size.y);
      } catch(IOException ex) {
          //Ouch! something went wrong
          ex.printStackTrace();
      }
      Sprite sprite = new Sprite (texture1);

      RenderWindow window = new RenderWindow ();

      window.create (new VideoMode (screenWidth, screenHeight),
              Title,
              WindowStyle.DEFAULT);
      window.setFramerateLimit (30);

      Font sansRegular = new Font ();
      try {
          sansRegular.loadFromFile (Paths.get ("FreeSans.ttf"));
      } catch (IOException ex) {
          ex.printStackTrace ();
      }

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
          window.draw(sprite);

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
                      show();

                  }  else if (position == 1) {
                    screenWidth = 1600;
                    screenHeight = 900;
                    window.close();
                    show();
                  } else if (position == 2) {
                    screenWidth = 1920;
                    screenHeight = 1080;
                    window.close();
                    show();
                  } else if (position == 3) {
                    window.close();
                    run(screenWidth, screenHeight);
                  }
              }

          }
      }
    }
  }
  public static Background getBg1(){
    return bg1;
  }

  public static Background getBg2() {
    return bg2;
  }
}
