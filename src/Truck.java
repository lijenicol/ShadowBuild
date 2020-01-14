import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
/**
 * Defines a Truck unit for the game
 */
public class Truck extends Unit {
	private static final String SPRITE = "assets/units/truck.png";
	private static final float VELOCITY = 0.25f;
	private MovementBehaviour movementBehaviour;
	
	/**
	 * Time it takes to train a truck
	 */
	public static final int TIME_TO_TRAIN = 5;
	
	/**
	 * The amount of Metal required to train the truck
	 */
	public static final int COST_TO_TRAIN = 150;
	
	private int millisecondTimer;
	private int endTimerTime;
	private boolean isBuilding;
	
	/**
	 * Constructs a Truck at position (x, y)
	 * @param x - x coordinate
	 * @param y - y coordinate
	 * @throws SlickException
	 */
	public Truck(float x, float y) throws SlickException {
		super(x, y, VELOCITY, SPRITE);
		movementBehaviour = new SimpleMovementBehaviour(getX(), getY());
		millisecondTimer = 0;
		endTimerTime = 0;
	}
	
	/**
	 * Updates the Truck
	 */
	public void update(int delta) {
		if(activeStatus() == true) {
			Input input = App.getInput();
			
			//build command centre and check if tile is occupied
			if(input.isKeyDown(Input.KEY_1) && !isBuilding) {
				//stop the truck from moving by setting the target position to its current position
				movementBehaviour.setTarget(getX(), getY());
				
				if(App.getActiveScene().removeResource(CommandCentre.COST_TO_BUILD, "Metal") 
						&& !App.getActiveScene().getWorld().isOccupiedTile(getX(), getY())) {
					millisecondTimer = 0;
					endTimerTime = CommandCentre.TIME_TO_BUILD * 1000;
					isBuilding = true;
				}
			}
			
			//move truck towards mouse if right button is clicked only if not building
			if(input.isMousePressed(Input.MOUSE_RIGHT_BUTTON) && !isBuilding) {
				Camera camera = App.getActiveScene().getCamera();
				float targetX = input.getMouseX() + camera.getX();
				float targetY = input.getMouseY() + camera.getY();
				movementBehaviour.setTarget(targetX, targetY);
			}
		}
		
		//increment the timer if building a command centre
		if(isBuilding) {
			millisecondTimer +=  delta;
			if(millisecondTimer >= endTimerTime) {
				//instantiate the command centre then destroy the truck
				App.getActiveScene().instantiateGameObject("command_centre", (int)getX(), (int)getY());
				isBuilding = false;
				destroy();
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
		return "1- Build Command Centre";
	}
}
