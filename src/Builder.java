import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/**
 * The Builder class for the game.
 *
 */
public class Builder extends Unit {
	private static final String SPRITE = "assets/units/builder.png";
	private static final float VELOCITY = 0.1f;
	private MovementBehaviour movementBehaviour;
	
	/**
	 * The time it takes to train a Builder
	 */
	public static final int TIME_TO_TRAIN = 5;
	
	/**
	 * The amount of metal it takes to train a Builder
	 */
	public static final int COST_TO_TRAIN = 10;
	
	//building and timing attributes
	private int millisecondTimer;
	private int endTimerTime;
	private boolean isBuilding;
	
	/**
	 * Constructs a new Builder given a position
	 * @param x - x location
	 * @param y - y location
	 * @throws SlickException
	 */
	public Builder(float x, float y) throws SlickException {
		super(x, y, VELOCITY, SPRITE);
		movementBehaviour = new SimpleMovementBehaviour(getX(), getY());
		millisecondTimer = 0;
		endTimerTime = 0;
	}
	
	/**
	 * Updates the Builder.
	 * @param delta - The time since the last frame in milliseconds
	 */
	public void update(int delta) {
		if(activeStatus() == true) {
			Input input = App.getInput();
			
			//build factory and check if tile is occupied
			if(input.isKeyDown(Input.KEY_1) && !isBuilding
					&& !App.getActiveScene().getWorld().isOccupiedTile(getX(), getY())) {
				//stop the builder from moving by setting its target to its current position
				movementBehaviour.setTarget(getX(), getY());
				
				if(App.getActiveScene().removeResource(Factory.COST_TO_BUILD, "Metal")) {
					millisecondTimer = 0;
					endTimerTime = Factory.TIME_TO_BUILD * 1000;
					isBuilding = true;
				}
			}
	
			//handle right mouse click only if not building
			if(input.isMousePressed(Input.MOUSE_RIGHT_BUTTON) && !isBuilding) {
				Camera camera = App.getActiveScene().getCamera();
				float targetX = input.getMouseX() + camera.getX();
				float targetY = input.getMouseY() + camera.getY();
				movementBehaviour.setTarget(targetX, targetY);
			}
		}
		
		//keep the builder building and increment the timer
		if(isBuilding) {
			millisecondTimer +=  delta;
			if(millisecondTimer >= endTimerTime) {
				//instantiate factory at end of timer
				App.getActiveScene().instantiateGameObject("factory", (int)getX(), (int)getY());
				isBuilding = false;
			}
		}
		
		//update position
		movementBehaviour.update(delta, VELOCITY, getX(), getY());
		setX(movementBehaviour.getUpdatedX());
		setY(movementBehaviour.getUpdatedY());
		
		//check pylons
		checkPylonDistances();
	}
	
	/**
	 * Gets the Builders' interaction functions
	 * @return A String made of the Builder's interaction functions
	 */
	@Override
	public String getInteractionFunctions() {
		return "1- Build Factory";
	}
}
