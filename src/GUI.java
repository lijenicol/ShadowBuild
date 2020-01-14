import java.util.HashMap;

import org.newdawn.slick.Graphics;

/**
 * The GUI class of the game. Handles text overlays.
 *
 */
public class GUI {
	private static final int RESOURCE_COUNT_X = 32;
	private static final int RESOURCE_COUNT_Y = 32;
	private static final int INTERACTION_FUNCTION_POSITION_X = 32;
	private static final int INTERACTION_FUNCTION_POSITION_Y = 100;
	
	/**
	 * Renders the GUI to the screen. The GUI consists of text for the game's resources
	 * and text for the active GameObject's interaction functions
	 * @param g - The Slick graphics object, used for drawing.
	 */
	public void render(Graphics g) {
		//Construct the resource count string to be rendered
		String resourceString = "";
		HashMap<String, Integer> resourceCounts = App.getActiveScene().getResourceCounts();
		for(String type : resourceCounts.keySet()) {
			resourceString += type + ": " + resourceCounts.get(type) + "\n";
		}
		
		//Render the resource count string
		g.drawString(resourceString, 
				RESOURCE_COUNT_X, RESOURCE_COUNT_Y);
		
		//get the interaction functions of the active GameObject
		GameObject activeObject = App.getActiveScene().getActiveGameObject();
		if(activeObject != null) {
			Interactable activeObjectInteractable = (Interactable) activeObject;
			String interactionFunctions = activeObjectInteractable.getInteractionFunctions();
			
			//only render the text if the GameObject has specified any interaction functions
			if(interactionFunctions != null) {
				//render the interaction functions
				g.drawString(activeObjectInteractable.getInteractionFunctions(), 
						INTERACTION_FUNCTION_POSITION_X, INTERACTION_FUNCTION_POSITION_Y);
			}
		}
	}
}
