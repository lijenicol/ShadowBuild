import org.newdawn.slick.SlickException;

/**
 * Abstract Resource class for the game
 */
public abstract class Resource extends GameObject implements Interactable {
	private int resourceAmount;
	private String resourceName;
	
	/**
	 * Constructs a resource desposit at a given location (x, y)
	 * @param x - x coordinate
	 * @param y - y coordinate
	 * @param resourceAmount - Amount of resource initially in deposit
	 * @param resourceName - The name of the type of resource deposit to be created
	 * @param imgLocation - The location of the resource deposits' sprite
	 * @throws SlickException
	 */
	public Resource(int x, int y, int resourceAmount, String resourceName, String imgLocation) throws SlickException {
		super(x, y, imgLocation);
		this.resourceAmount = resourceAmount;
		this.resourceName = resourceName;
	}
	
	/**
	 * Returns the amount of resource left in resource deposit
	 */
	public String getInteractionFunctions() {
		return resourceName + ": " + resourceAmount;
	}
	
	/**
	 * Tries to mine the resource by the specified amount
	 * and returns the max amount of resource that can be mined 
	 * by that amount
	 * @param amount
	 * @return
	 */
	public int mineResource(int amount) {
		//if there is not enough resource then return what is left
		if(resourceAmount < amount) {
			//destroy the deposit as there is no resource left
			destroy();
			return resourceAmount;
		}
		
		//if there is no resource left then destroy the deposit
		resourceAmount -= amount;
		if(resourceAmount == 0) {
			destroy();
		}
		
		//return the amount mined
		return amount;
	}
	
	/**
	 * Gets the name of the type of resource deposit
	 * @return The name of the resource deposit
	 */
	public String getResourceName() {
		return resourceName;
	}
	
	/**
	 * Updates the resource deposit
	 */
	public void update(int delta) {}
	
	/**
	 * Renders the resource deposit using the GameObject render method
	 */
	public void render() {super.render();}
}
