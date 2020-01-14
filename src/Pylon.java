import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * The Pylon class for the game
 */
public class Pylon extends Building {
	private static final String SPRITE = "assets/buildings/pylon.png";
	private static final String ACTIVE_SPRITE = "assets/buildings/pylon_active.png";
	private Image activeSprite;
	
	private boolean activated = false;
	
	/**
	 * Constructs a Pylon at given location (x, y)
	 * @param x - x coordinate
	 * @param y - y coordinate
	 * @throws SlickException
	 */
	public Pylon (int x, int y) throws SlickException {
		super(x, y, SPRITE);
		activeSprite = new Image(ACTIVE_SPRITE);
	}
	
	/**
	 * Updates the pylon
	 * @param delta - Time since last frame in milliseconds
	 */
	public void update(int delta) {}
	
	public String getInteractionFunctions() {
		return "Pylon status: Active = " + activated;
	}
	
	/**
	 * Activates the Pylon and changes its sprite accordingly
	 */
	public void activate() {
		if(!activated) {
			Engineer.incrementMaxCapacity();
		}
		setSprite(activeSprite);
		activated = true;
	}
}
