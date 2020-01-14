import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * Building abstract class for the game
 */
public abstract class Building extends GameObject implements Interactable{
	private Image activeSprite;
	
	/**
	 * Constructs a Building from a position and the location to its sprite
	 * @param x - x position
	 * @param y - y position
	 * @param imgLocation - Image location of the sprite
	 * @throws SlickException
	 */
	public Building(int x, int y, String imgLocation) throws SlickException{
		super(x, y, imgLocation);
		activeSprite = new Image("assets/highlight_large.png");
	}
	
	/**
	 * Renders the Building
	 */
	public void render() {
		if(super.activeStatus() == true) {
			Camera camera = App.getActiveScene().getCamera();
			activeSprite.draw(super.getX() - camera.getX() - activeSprite.getWidth()/2, super.getY() - camera.getY() - activeSprite.getHeight()/2);
		}
		super.render();
	}
}
