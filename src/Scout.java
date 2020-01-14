import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/**
 * The Scout class for the game
 */
public class Scout extends Unit {
	private static final String SPRITE = "assets/units/scout.png";
	private static final float VELOCITY = 0.3f;
	private MovementBehaviour movementBehaviour;
	
	/**
	 * The time it takes to train a Scout
	 */
	public static final int TIME_TO_TRAIN = 5;
	
	/**
	 * The amount of metal it takes to train a Scout
	 */
	public static final int COST_TO_TRAIN = 5;
	
	/**
	 * Constructs a Scout at a given position (x, y)
	 * @param x - x coordinate
	 * @param y - y coordinate
	 * @throws SlickException
	 */
	public Scout(float x, float y) throws SlickException {
		super(x, y, VELOCITY, SPRITE);
		movementBehaviour = new SimpleMovementBehaviour(getX(), getY());
	}
	
	/**
	 * Updates the Scout
	 */
	public void update(int delta) {
		//handle movement of scout
		if(activeStatus() == true) {
			Input input = App.getInput();
			if(input.isMousePressed(Input.MOUSE_RIGHT_BUTTON)) {
				Camera camera = App.getActiveScene().getCamera();
				float targetX = input.getMouseX() + camera.getX();
				float targetY = input.getMouseY() + camera.getY();
				movementBehaviour.setTarget(targetX, targetY);
			}
		}
		
		//update position
		movementBehaviour.update(delta, VELOCITY, getX(), getY());
		setX(movementBehaviour.getUpdatedX());
		setY(movementBehaviour.getUpdatedY());
		
		//check pylons
		checkPylonDistances();
	}

	@Override
	public String getInteractionFunctions() {
		return null;
	}
}
