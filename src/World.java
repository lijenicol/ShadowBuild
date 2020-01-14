import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

/**
 * This class stores information about the world's tilemap and provides a
 * method to be able to render the tilemap
 */
public class World {
	//tilemap information
	private TiledMap tileMap;
	private static final int TILE_SIZE = 64;
	private int tileMapWidth;
	private int tileMapHeight;
	
	/**
	 * Constructs a World based on a TiledMap
	 * @param tileMapLocation - The location of the TiledMap
	 * @throws SlickException
	 */
	public World (String tileMapLocation) throws SlickException {
		tileMap = new TiledMap(tileMapLocation);
		tileMapWidth = tileMap.getWidth();
		tileMapHeight = tileMap.getHeight();
	}
	
	/**
	 * Renders the TiledMap based on the scene's active camera
	 */
	public void render() {
		Camera camera = App.getActiveScene().getCamera();
		tileMap.render(-(int)camera.getX(), -(int)camera.getY());
	}
	
	/**
	 * Returns true if a tile at an (x, y) world position is solid. 
	 * This method is necessary for collision detection
	 * @param x - x coordinate to check 
	 * @param y - y coordinate to check
	 * @return True if there is a solid tile at (x, y), false otherwise
	 */
	public boolean isSolidTile(float x, float y) {
		//calculate the ID of a tile from x,y coordinate
		int tileindexX = (int)x/TILE_SIZE;
		int tileIndexY = (int)y/TILE_SIZE;
		int tileID = tileMap.getTileId(tileindexX, tileIndexY, 0);
		
		//check if the corresponding tile is solid
		if(tileMap.getTileProperty(tileID, "solid", "false").equals("true")) {
			return true;
		}
		return false;
	}
	
	/**
	 * Returns true if a tile at an (x, y) world position is occupied.
	 * @param x - x coordinate to check
	 * @param y - y coordinate to check
	 * @return True if there is an occupied tile at (x, y), false otherwise
	 */
	public boolean isOccupiedTile(float x, float y) {
		//calculate the ID of a tile from x,y coordinate
		int tileindexX = (int)x/TILE_SIZE;
		int tileIndexY = (int)y/TILE_SIZE;
		int tileID = tileMap.getTileId(tileindexX, tileIndexY, 0);
		
		//check if the corresponding tile is solid
		if(tileMap.getTileProperty(tileID, "occupied", "false").equals("true")) {
			return true;
		}
		return false;
	}
	
	/**
	 * Gets the width of the TiledMap in pixels
	 * @return The width of the TiledMap in pixels
	 */
	public int getWidth() {
		return tileMapWidth * TILE_SIZE;
	}
	
	/**
	 * Gets the height of the TiledMap in pixels
	 * @return The height of the TiledMap in pixels
	 */
	public int getHeight() {
		return tileMapHeight * TILE_SIZE;
	}
}
