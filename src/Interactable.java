/**
 * Interface for Interactable objects in the game
 *
 */
public interface Interactable {
	/**
	 * Gets the interaction functions of an Interactable
	 * @return A String made up of the interaction functions 
	 * to be rendered to the GUI
	 */
	public String getInteractionFunctions();
	
	/**
	 * Renders an Interactable
	 */
	public void render();
}
