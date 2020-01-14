/**
 * Sample Project for SWEN20003: Object Oriented Software Development, 2019, Semester 1
 * by Eleanor McMurtry, University of Melbourne
 * Edited by Elijah Nicol, University of Melbourne
 */

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Input;

/**
 * Main class for the game.
 * Handles initialisation, input and rendering.
 */

public class App extends BasicGame {
    /** window width, in pixels */
    public static final int WINDOW_WIDTH = 1024;
    /** window height, in pixels */
    public static final int WINDOW_HEIGHT = 768;
    
    /**
     * Default size of sprites in pixels
     */
    public static final int SPRITE_SIZE = 64;

    //information for main scene
    private Scene scene;
    private World world;
    private Camera camera;
    
    private static Scene activeScene;
    private static Input currentInput;

    /**
     * Constructs the main game
     */
    public App() {
        super("Shadow Build");
    }

    /**
     * Initializes the game
     * @param gc - The Slick game container object
     */
    @Override
    public void init(GameContainer gc)
            throws SlickException {
    	//initialize world, camera and player objects for main scene
        world = new World("assets/main.tmx");
        camera = new Camera();        
        
        //construct main scene
        scene = new Scene(world, camera, "assets/objects.csv");
        activeScene = scene;
    }

    /** Update the game state for a frame.
     * @param gc The Slick game container object.
     * @param delta Time passed since last frame (milliseconds).
     */
    @Override
    public void update(GameContainer gc, int delta)
            throws SlickException {
        // Get data about the current input
        currentInput = gc.getInput();
        
        //update the active scene
        activeScene.update(delta);
    }

    /** Render the entire screen, so it reflects the current game state.
     * @param gc The Slick game container object.
     * @param g The Slick graphics object, used for drawing.
     */
    public void render(GameContainer gc, Graphics g)
            throws SlickException {
    	//render the active scene
    	activeScene.render(g);
    }
    
    /**
     * Sets the active scene of the game
     * @param scene - Scene to set as active
     */
    public static void setActiveScene(Scene scene) {
    	activeScene = scene;
    }
    
    /**
     * Gets the active scene of the game
     * @return The active scene of the game
     */
    public static Scene getActiveScene() {
    	return activeScene;
    }
    
    /**
     * Gets the current user input to the game
     * @return The current user input
     */
    public static Input getInput() {
    	return currentInput;
    }

    /** Start-up method. Creates the game and runs it.
     * @param args Command-line arguments (ignored).
     */
    public static void main(String[] args)
            throws SlickException {
        AppGameContainer app = new AppGameContainer(new App());
        app.setShowFPS(false);
        app.setDisplayMode(WINDOW_WIDTH, WINDOW_HEIGHT, false);
        app.start();
    }

}