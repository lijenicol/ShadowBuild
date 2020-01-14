/**
 * Exception thrown when trying to create a GameObject of a type not recognized
 */
@SuppressWarnings("serial")
public class GameObjectTypeNotFoundException extends Exception {
	/**
	 * Constructs the GameObject type not found exception
	 * @param type - The type of GameObject not recognized
	 */
	public GameObjectTypeNotFoundException(String type) {
		super("The game object of type: " + type + " is not recognized");
	}
}
