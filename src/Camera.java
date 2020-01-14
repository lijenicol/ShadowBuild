import org.newdawn.slick.Input;

/** This class stores positional information about a camera.
 * Restricts its position to stay within bounds of the current 
 * active world and can either be moved by the user or be made 
 * to follow a GameObject. */ 

public class Camera {

	private float x, y;
	private static final float MOVEMENT_VELOCITY = 0.4f;
	private GameObject prevFollowedObject;
	private boolean toggleFollowMode;
	
	/**
	 * Constructs camera at a default position of (0,0)
	 */
	public Camera() {
		x = 0;
		y = 0;
	}
	
	/**
	 * Constructs camera at position (x, y)
	 * @param x - x position
	 * @param y - y position
	 */
	public Camera(float x, float y) {	
		this.x = x;
		this.y = y;
		prevFollowedObject = null;
	}
	
	/**
	 * Move camera to follow specified game object or follow user input, 
	 * however do not go out of world bounds
	 * @param delta - Time since last frame in milliseconds
	 * @param objectToFollow - The GameObject for the camera to follow
	 */
	public void update(int delta, GameObject objectToFollow) {
		//signifies that user has selected a new object and that the camera should follow
		if(objectToFollow != prevFollowedObject) {
			toggleFollowMode = true;
		}
		
		//positions player in the center of screen
		if(objectToFollow != null && toggleFollowMode) {
			x = objectToFollow.getX() - App.WINDOW_WIDTH/2;
			y = objectToFollow.getY() - App.WINDOW_HEIGHT/2;
		}
		
		//Handle moving the camera through user input
		Input input = App.getInput();
		if(input.isKeyDown(Input.KEY_W)) {
			y -= MOVEMENT_VELOCITY * delta;
			toggleFollowMode = false;
		}
		if(input.isKeyDown(Input.KEY_S)) {
			y += MOVEMENT_VELOCITY * delta;	
			toggleFollowMode = false;
		}
		if(input.isKeyDown(Input.KEY_A)) {
			x -= MOVEMENT_VELOCITY * delta;
			toggleFollowMode = false;
		}
		if(input.isKeyDown(Input.KEY_D)) {
			x += MOVEMENT_VELOCITY * delta;
			toggleFollowMode = false;
		}
		
		//keep camera within bounds of world
		World world = App.getActiveScene().getWorld();
		x = Math.min(x, world.getWidth() - App.WINDOW_WIDTH);
		x = Math.max(x,0);
		y = Math.min(y, world.getHeight() - App.WINDOW_HEIGHT);
		y = Math.max(y,0);
		
		//keep record of the previously followed object
		prevFollowedObject = objectToFollow;
	}
	
	/**
	 * Sets the camera to follow a GameObject (follow mode = true) 
	 * or to follow user input (follow mode = false)
	 * @param state - The state to set the follow mode
	 */
	public void setFollowMode(boolean state) {
		toggleFollowMode = state;
	}

	/**
	 * Gets the x coordinate of the camera
	 * @return The x coordinate
	 */
	public float getX() {
		return x;
	}
	
	/**
	 * Gets the y coordinate of the camera
	 * @return The y coordinate
	 */
	public float getY() {
		return y;
	}
}
