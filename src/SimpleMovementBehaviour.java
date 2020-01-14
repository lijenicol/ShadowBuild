/**
 * Defines a simple movement behaviour that GameObjects can use
 */
public class SimpleMovementBehaviour implements MovementBehaviour {
	private static final double TARGET_MIN_DISTANCE = 0.25;
	private boolean isMoving;
	private float targetX;
	private float targetY;
	private float x;
	private float y;
	
	/**
	 * Constructs the movement behaviour based on a position (x, y)
	 * @param x - x coordinate
	 * @param y - y coordinate
	 */
	public SimpleMovementBehaviour(float x, float y) {
		this.x = x;
		this.y = y;
		this.isMoving = false;
	}
	
	/**
	 * Moves and updates position in a straight line towards a specified target
	 */
	@Override
	public void update(int delta, float velocity, float x, float y) {
		if(isMoving) {	
			//move position with respect to velocity
			double moveAngle = Math.atan2(targetY - y, targetX - x);
			float velX = velocity * (float)Math.cos(moveAngle);
			float velY = velocity * (float)Math.sin(moveAngle);
			x += velX * delta;
			y += velY * delta;
			
			//do not let unit pass through solid tile
			if(App.getActiveScene().getWorld().isSolidTile(x, y)) {
				isMoving = false;
				return;
			}
				
			//commit positional changes to global
			this.x = x;
			this.y = y;
				
			//stop unit once they are within MIN_DISTANCE of target
			double distance = Math.sqrt(Math.pow(targetX-x, 2) + Math.pow(targetY-y, 2));
			if(distance < TARGET_MIN_DISTANCE) {
				isMoving = false;
			}
		}
	}

	@Override
	public void setTarget(float x, float y) {
		targetX = x;
		targetY = y;
		if(targetX != this.x || targetY != this.y) {
			isMoving = true;
		}
	}

	@Override
	public float getUpdatedX() {
		return x;
	}

	@Override
	public float getUpdatedY() {
		return y;
	}
}
