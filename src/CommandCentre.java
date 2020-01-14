import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/**
 * Command Centre class of the game
 *
 */
public class CommandCentre extends Building{
	private static final String SPRITE = "assets/buildings/command_centre.png";
	private int millisecondTimer;
	private int endTimerTime;
	private boolean trainingUnit;
	private String trainingUnitType;
	
	/**
	 * Time it takes to build a Command Centre
	 */
	public static final int TIME_TO_BUILD = 15;
	
	/**
	 * Amount of metal it takes to build a Command Centre
	 */
	public static final int COST_TO_BUILD = 0;
	
	/**
	 * Constructs a Command Centre at position (x, y)
	 * @param x - The x coordinate
	 * @param y - The y coordinate
	 * @throws SlickException
	 */
	public CommandCentre (int x, int y) throws SlickException {
		super(x, y, SPRITE);
		millisecondTimer = 0;
		endTimerTime = 0;
	}
	
	/**
	 * Updates the Command Centre.
	 * @param delta - The time since last frame in milliseconds
	 */
	public void update(int delta) {
		if(activeStatus() == true && !trainingUnit) {
			Input input = App.getInput();
			
			//create scout
			if(input.isKeyDown(Input.KEY_1)) {
				if(App.getActiveScene().removeResource(Scout.COST_TO_TRAIN, "Metal")) {
					trainingUnitType = "scout";
					millisecondTimer = 0;
					endTimerTime = Scout.TIME_TO_TRAIN * 1000;
					trainingUnit = true;
				}
			}
			
			//create builder
			if(input.isKeyDown(Input.KEY_2)) {
				if(App.getActiveScene().removeResource(Builder.COST_TO_TRAIN, "Metal")) {
					trainingUnitType = "builder";
					millisecondTimer = 0;
					endTimerTime = Builder.TIME_TO_TRAIN * 1000;
					trainingUnit = true;
				}
			}
			
			//create engineer
			if(input.isKeyDown(Input.KEY_3)) {
				if(App.getActiveScene().removeResource(Engineer.COST_TO_TRAIN, "Metal")) {
					trainingUnitType = "engineer";
					millisecondTimer = 0;
					endTimerTime = Engineer.TIME_TO_TRAIN * 1000;
					trainingUnit = true;
				}
			}
		}
		
		//keep the command centre training units and increment the timer
		if(trainingUnit) {
			millisecondTimer += delta;
			if(millisecondTimer >= endTimerTime) {
				App.getActiveScene().instantiateGameObject(trainingUnitType, (int)getX(), (int)getY());
				trainingUnit = false;
			}
		}
	}
	
	/**
	 * Takes in a resource and adds the resource to the global counter
	 * @param amount - The amount of resource to receive
	 * @param type - The type of resource to receive
	 */
	public void receiveResource(int amount, String type) {
		App.getActiveScene().addResource(amount, type);
	}
	
	/**
	 * Gets the interaction functions of the Command Centre to be rendered to the GUI
	 * @return A String containing the interaction functions
	 */
	public String getInteractionFunctions() {
		return "1- Create Scout\n2- Create Builder\n3- Create Engineer\n";
	}
}
