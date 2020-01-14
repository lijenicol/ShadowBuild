import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/**
 * The Factory class of the game
 */
public class Factory extends Building {
	private static final String SPRITE = "assets/buildings/factory.png";
	
	/**
	 * The time it takes to build a Factory
	 */
	public static final int TIME_TO_BUILD = 10;
	
	/**
	 * The amount of metal it takes to make a Factory
	 */
	public static final int COST_TO_BUILD = 100;
	
	//training attributes
	private int millisecondTimer;
	private int endTimerTime;
	private boolean trainingUnit;
	
	/**
	 * Constructs a Factory at a given position (x, y)
	 * @param x - x coordinate
	 * @param y - y coordinate
	 * @throws SlickException
	 */
	public Factory (int x, int y) throws SlickException {
		super(x, y, SPRITE);
	}
	
	/**
	 * Updates the Factory
	 * @param delta - Time since last frame in milliseconds
	 */
	public void update(int delta) {
		//only get input from user if active or not training anything
		if(activeStatus() == true && !trainingUnit) {
			Input input = App.getInput();
			//train truck if user presses 1
			if(input.isKeyDown(Input.KEY_1)) {
				if(App.getActiveScene().removeResource(Truck.COST_TO_TRAIN, "Metal")) {
					millisecondTimer = 0;
					endTimerTime = Truck.TIME_TO_TRAIN * 1000;
					trainingUnit = true;
				}
			}
		}
		
		//if training then increment timer 
		if(trainingUnit) {
			millisecondTimer += delta;
			
			//when training time is up then instantiate a truck object
			if(millisecondTimer >= endTimerTime) {
				App.getActiveScene().instantiateGameObject("truck", (int)getX(), (int)getY());
				trainingUnit = false;
			}
		}
	}
	
	/**
	 * Gets the interaction functions of the Factory to be rendered to the GUI
	 * @return A String made up of the interaction functions
	 */
	public String getInteractionFunctions() {
		return "1- Train Truck";
	}
}
