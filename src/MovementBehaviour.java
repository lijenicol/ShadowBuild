/**
 * Interface for any movement behaviours
 */
public interface MovementBehaviour {
	/**
	 * Updates the movement behaviour based on a GameObjects' 
	 * position (x, y) and velocity
	 * @param delta - Time since last frame in milliseconds
	 * @param velocity - Velocity at which to move
	 * @param x - x coordinate
	 * @param y - y coordinate
	 */
	public void update(int delta, float velocity, float x, float y);
	
	/**
	 * Set's the target to which a GameObject should move to
	 * @param x
	 * @param y
	 */
	public void setTarget(float x, float y);
	
	/**
	 * Gets the updated x coordinate from the movement behaviour
	 * @return The updated x coordinate
	 */
	public float getUpdatedX();
	
	/**
	 * Gets the updated y coordinate from the movement behaviour
	 * @return The updated y coordinate
	 */
	public float getUpdatedY();
}
