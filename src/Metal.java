import org.newdawn.slick.SlickException;
/**
 * Class for the Metal resource type
 */
public class Metal extends Resource {
	private static final String RESOURCE_NAME = "Metal";
	private static final int RESOURCE_AMOUNT = 500;
	private static final String SPRITE = "assets/resources/metal_mine.png";
	
	/**
	 * Constructs a Metal deposit at given location (x, y)
	 * @param x - x coordinate
	 * @param y - y coordinate
	 * @throws SlickException
	 */
	public Metal(int x, int y) throws SlickException {
		super(x, y, RESOURCE_AMOUNT, RESOURCE_NAME, SPRITE);
	}
}
