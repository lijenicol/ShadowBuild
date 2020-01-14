import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * This class stores information about the game's objects, such as position,
 * size and sprite. Provides general method to render a GameObject
 *
 */
public abstract class GameObject {
	private float x, y;
	private Image sprite;
	private boolean isActive;
	
	/**
	 * Constructs a GameObject based on position (x, y) and sprite image location
	 * @param x - x coordinate
	 * @param y - y coordinate
	 * @param imgLocation - Image location of the sprite
	 * @throws SlickException
	 */
	public GameObject(float x, float y, String imgLocation) throws SlickException{
		this.x = x;
		this.y = y;
		sprite = new Image(imgLocation);
		
		//all objects should not be active on instantiation
		isActive = false;
	}
	
	/**
	 * Abstract method that is called once every frame. Should be overridden
	 * to provide frame to frame functionality for GameObjects
	 * @param delta - Time since last frame in milliseconds
	 */
	public abstract void update(int delta);
	
	/**
	 * Renders the GameObject based upon the scenes' active camera
	 */
	public void render() {
		Camera camera = App.getActiveScene().getCamera();
		sprite.draw(x - camera.getX() - App.SPRITE_SIZE/2, y - camera.getY() - App.SPRITE_SIZE/2);
	}
	
	/**
	 * Sets the GameObjects' sprite to be rendered
	 * @param sprite - The GameObjects' new sprite
	 */
	public void setSprite(Image sprite) {
		this.sprite = sprite;
	}
	 
	/**
	 * Destroys the GameObject and notifies the active scene
	 */
	public void destroy() {
		setActive(false);
		App.getActiveScene().destroyGameObject(this);
	}
	
	/**
	 * Gets the x coordinate of the GameObject
	 * @return The x coordinate
	 */
	public float getX() {
		return x;
	}
	
	/**
	 * Gets the y coordinate of the GameObject
	 * @return The y coordinate
	 */
	public float getY() {
		return y;
	}
	
	/**
	 * Sets the x coordinate of the GameObject
	 * @param x - New x coordinate
	 */
	public void setX(float x) {
		this.x = x;
	}
	
	/**
	 * Sets the y coordinate of the GameObject
	 * @param y - New y coordinate
	 */
	public void setY(float y) {
		this.y = y;
	}
	
	/**
	 * Sets the active status of the GameObject
	 * @param status - The status to set the active state
	 */
	public void setActive(boolean status) {
		isActive = status;
	}
	
	/**
	 * Get the active state of the GameObject
	 * @return The active state of the GameObject
	 */
	public boolean activeStatus() {
		return isActive;
	}
}