import java.util.ArrayList;

import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/**
 * The Engineer class of the game
 */
public class Engineer extends Unit {
	private static final String SPRITE = "assets/units/engineer.png";
	private static final float VELOCITY = 0.1f;
	private MovementBehaviour movementBehaviour;
	
	/**
	 * Time it takes to train an Engineer
	 */
	public static final int TIME_TO_TRAIN = 5;
	
	/**
	 * Amount of metal it takes to train an Engineer
	 */
	public static final int COST_TO_TRAIN = 20;
	
	//mining properties
	private static int MAX_CAPACITY = 2;
	private int resourceCount;
	private static final float MIN_MINING_DISTANCE = 32;
	private static final float MIN_COMMAND_CENTRE_DISTANCE = 32;
	private Resource lastMinedResource;
	private boolean returntoResource;
	
	//timer variables
	private int millisecondTimer;
	private static final float MINE_TIME = 5;
	
	/**
	 * Constructs an Engineer at position (x, y)
	 * @param x - x coordinate
	 * @param y - y coordinate
	 * @throws SlickException
	 */
	public Engineer(float x, float y) throws SlickException {
		super(x, y, VELOCITY, SPRITE);
		movementBehaviour = new SimpleMovementBehaviour(getX(), getY());
		resourceCount = 0;
	}
	
	/**
	 * Updates the Engineer
	 * @param delta - Time since last frame in milliseconds
	 */
	public void update(int delta) {
		//handle mining functionality here
		//check distance if not carrying any resource
		if(resourceCount < MAX_CAPACITY) {
			//get the resource gameobjects
			ArrayList<Object> resourceList = App.getActiveScene().getGameObjectList("resources");
			
			//check the distances of each mine
			boolean foundCloseMine = false;
			for(Object object : resourceList) {
				Resource resource = (Resource) object;
				double distance = Math.sqrt(Math.pow(resource.getX()-getX(), 2) + Math.pow(resource.getY()-getY(), 2));
				if(distance < MIN_MINING_DISTANCE) {
					foundCloseMine = true;
					//increment the timer here
					millisecondTimer += delta;
					//when the timer reaches 5s then mine
					if(millisecondTimer / 1000 >= MINE_TIME) {
						resourceCount += resource.mineResource(MAX_CAPACITY);
						//get closest command centre
						lastMinedResource = resource;
						CommandCentre commandCentre = getClosestCommandCentre();
						movementBehaviour.setTarget(commandCentre.getX(), commandCentre.getY());
						returntoResource = true;
					}
				}
			}
			
			//if engineer did not find any close mine than reset the timer
			if(!foundCloseMine) {
				millisecondTimer = 0;
			}
		}
		//if engineer is close to a command centre then drop off resource 
		if(resourceCount > 0) {
			CommandCentre commandCentre = getClosestCommandCentre();
			double distance = Math.sqrt(Math.pow(commandCentre.getX()-getX(), 2) + Math.pow(commandCentre.getY()-getY(), 2));
			if(distance < MIN_COMMAND_CENTRE_DISTANCE && lastMinedResource != null) {
				//drop off the resource at the command centre
				commandCentre.receiveResource(resourceCount, lastMinedResource.getResourceName());
				resourceCount = 0;
				
				//and then head back to mining if not interrupted
				if(returntoResource) {
					movementBehaviour.setTarget(lastMinedResource.getX(), lastMinedResource.getY());
				}
			}
		}
		
		//handle pylon functionality
		checkPylonDistances();
		
		//functionality of clicking and unit moving
		if(activeStatus() == true) {
			Input input = App.getInput();
			//when user clicks, move unit to position of mouse
			if(input.isMousePressed(Input.MOUSE_RIGHT_BUTTON)) {
				//interrupt the mining of the engineer
				returntoResource = false;
				
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
	}
	
	private CommandCentre getClosestCommandCentre() {
		ArrayList<Object> commandCentreList = App.getActiveScene().getGameObjectList("command_centre");
		CommandCentre closestCommandCentre = null;
		double closestDistance = Double.POSITIVE_INFINITY;
		for(Object o : commandCentreList) {
			CommandCentre commandCentre = (CommandCentre)o;
			double distance = Math.sqrt(Math.pow(commandCentre.getX()-getX(), 2) + Math.pow(commandCentre.getY()-getY(), 2));
			if(distance < closestDistance) {
				closestCommandCentre = commandCentre;
				closestDistance = distance;
			}
		}
		
		return closestCommandCentre;
	}
	
	/**
	 * Gets the interaction functions of the Engineer
	 * @return A String made up of the interaction functions to be rendered to the GUI
	 */
	@Override
	public String getInteractionFunctions() {
		return "Currently carrying " + resourceCount + " items // Max carry capacity is: " + MAX_CAPACITY;
	}
	
	/**
	 * Increment the max capacity of all engineers
	 */
	public static void incrementMaxCapacity() {
		MAX_CAPACITY++;
	}
}