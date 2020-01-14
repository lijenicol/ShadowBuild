import org.newdawn.slick.SlickException;

/**
 * Class for the Unobtanium resource deposit
 */
public class Unobtanium extends Resource {
	private static final String RESOURCE_NAME = "Unobtanium";
	private static final int RESOURCE_AMOUNT = 50;
	private static final String SPRITE = "assets/resources/unobtainium_mine.png";
	
	/**
	 * Creates an Unobtanium resource deposit at a specified position (x, y)
	 * @param x - x coordinate
	 * @param y - y coordinate
	 * @throws SlickException
	 */
	public Unobtanium(int x, int y) throws SlickException {
		super(x, y, RESOURCE_AMOUNT, RESOURCE_NAME, SPRITE);
	}
}
