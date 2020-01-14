import java.util.ArrayList;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * Abstract class for Unit
 */
public abstract class Unit extends GameObject implements Interactable {
	private static final int MIN_PYLON_DISTANCE = 32; 
	
	private Image activeSprite;
	
	/**
	 * Constructs a Unit based on a position (x, y), velocity and sprite
	 * @param x - x coordinate
	 * @param y - y coordinate
	 * @param velocity - Movement velocity
	 * @param imgLocation - Sprite image location
	 * @throws SlickException
	 */
	public Unit(float x, float y, float velocity, String imgLocation) throws SlickException {
		super(x, y, imgLocation);		
		activeSprite = new Image("assets/highlight.png");
	}
	
	/**
	 * Updates a Unit
	 */
	public abstract void update(int delta);
	
	/**
	 * Checks the distances to each pylon. If the unit is within
	 * MIN_PYLON_DISTANCE of a pylon then activate that pylon
	 */
	public void checkPylonDistances() {
		ArrayList<Object> pylonList = App.getActiveScene().getGameObjectList("pylon");
		for(Object o : pylonList) {
			Pylon pylon = (Pylon) o;
			double distance = Math.sqrt(Math.pow(pylon.getX()-getX(), 2) + Math.pow(pylon.getY()-getY(), 2));
			if(distance < MIN_PYLON_DISTANCE) {
				pylon.activate();
			}
		}
	}
	
	/**
	 * Renders the unit
	 */
	public void render() {
		if(super.activeStatus() == true) {
			Camera camera = App.getActiveScene().getCamera();
			activeSprite.draw(super.getX() - camera.getX() - App.SPRITE_SIZE/2, super.getY() - camera.getY() - App.SPRITE_SIZE/2);
		}
		super.render();
	}
}