import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/* This class stores information about a scene in the game.
 * stores all GameObjects, Camera and World information in that scene. 
 * Each scene can only have one world */

public class Scene {
	private World world;
	private Camera camera;
	private GUI gui;
	
	//all GameObject lists required
	private HashMap<String, ArrayList<Object>> gameObjects;
	private HashMap<String, ArrayList<Object>> gameObjectQueue;
	private ArrayList<Object> gameObjectDeleteQueue;
	private boolean isIteratingGameObjects;
	
	//keep track of active GameObject
	private GameObject activeGameObject;
	
	//Map used for storing multiple resource types
	private HashMap<String, Integer> resourceCounts;
	
	/**
	 * Creates a new scene with a World, GameObjects and Camera
	 * @param world - The world the scene is set in
	 * @param camera - The Camera to render the scene from
	 * @param layoutLocation - The csv specifying all the GameObjects to be created
	 * @throws SlickException
	 */
	public Scene (World world, Camera camera, String layoutLocation) throws SlickException {
		this.world = world;
		this.camera = camera;
		this.gui = new GUI();
		
		initGameObjects(layoutLocation);
		activeGameObject = null;
	}
	
	//Initializes the GameObjects
	private void initGameObjects(String layoutLocation) throws SlickException {
		//instantiate all the scene's lists
		gameObjects = new HashMap<String, ArrayList<Object>>();
		gameObjectQueue = new HashMap<String, ArrayList<Object>>();
		gameObjectDeleteQueue = new ArrayList<Object>();
		resourceCounts = new HashMap<String, Integer>();
		
		//read csv containing all the GameObjects to be created
		try (BufferedReader br = new BufferedReader(new FileReader(layoutLocation))){
			String line;
			while((line = br.readLine()) != null) {
				String lineValues[] = line.split(",");
				//instantiate the new gameobjects based on the values from the csv
				instantiateGameObject(lineValues[0], Integer.parseInt(lineValues[1]), Integer.parseInt(lineValues[2]));
				
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Instantiates a GameObject from a type and position (x, y)
	 * @param type - The type of GameObject to create
	 * @param x - x coordinate
	 * @param y - y coordinate
	 */
	public void instantiateGameObject(String type, int x, int y) {
		try {
			//create the object based on the type
			GameObject gameObject;
			switch (type) {
				case "engineer":
					Engineer engineer = new Engineer(x, y);
					gameObject = engineer;
					break;
				case "scout":
					Scout scout = new Scout(x, y);
					gameObject = scout;
					break;
				case "builder":
					Builder builder = new Builder(x, y);
					gameObject = builder;
					break;
				case "truck":
					Truck truck = new Truck(x, y);
					gameObject = truck;
					break;
				case "metal_mine":
					Metal metal = new Metal(x, y);
					gameObject = metal;
					break;
				case "unobtainium_mine":
					Unobtanium unobtanium = new Unobtanium(x, y);
					gameObject = unobtanium;
					break;
				case "factory":
					Factory factory = new Factory(x, y);
					gameObject = factory;
					break;
				case "command_centre":
					CommandCentre commandCentre = new CommandCentre(x, y);
					gameObject = commandCentre;
					break;
				case "pylon":
					Pylon pylon = new Pylon(x, y);
					gameObject = pylon;
					break;
				default:
					//if type not found then throw exception
					throw new GameObjectTypeNotFoundException(type);
			}
			
			//get the instances of the object and put the object in each according key
			ArrayList<String> instances = checkInstances(gameObject);
			for(String instanceName : instances) {
				//place GameObject in queue if the game is iterating through the objects
				if(isIteratingGameObjects) {
					addObjectToMapList(instanceName, gameObject, gameObjectQueue);
					continue;
				}
				addObjectToMapList(instanceName, gameObject, gameObjects);
			}
			
			//place GameObject in queue if the game is iterating through the objects
			if(isIteratingGameObjects) {
				addObjectToMapList(type, gameObject, gameObjectQueue);
				addObjectToMapList("game_objects", gameObject, gameObjectQueue);
			}
			else {
				addObjectToMapList(type, gameObject, gameObjects);
				addObjectToMapList("game_objects", gameObject, gameObjects);
			}
			
		} catch(GameObjectTypeNotFoundException e) {
			e.printStackTrace();
		} catch(SlickException e) {
			e.printStackTrace();
		}
	}
	
	//returns an arraylist of whatever the object (mainly gameobject) is an instance of
	//so that we can create filters on gameobjects
	//explicitly state what instances to check here so that a filter is not created for 
	//every type of gameobject
	/**
	 * Returns an ArrayList of whatever the object (GameObject) is an instance of
	 * so that we can create filters on GameObjects
	 * @param <T>
	 * @param object - The Object to check instances of
	 * @return - ArrayList of a GameObjects instances
	 */
	public <T> ArrayList<String> checkInstances(T object) {
		ArrayList<String> instances = new ArrayList<String>();
		
		if(object instanceof Interactable) {
			instances.add("interactables");
		}
		
		if(object instanceof Building) {
			instances.add("buildings");
		}
		
		if(object instanceof Unit) {
			instances.add("units");
		}
		
		if(object instanceof Resource) {
			instances.add("resources");
			
			//add the resource type to the resource count list if the resource
			//type does not already exist
			String resourceType = ((Resource) object).getResourceName();
			resourceCounts.put(resourceType, 0);
		}
		
		return instances;
	}
		
	//method that creates a list in the map if the list does not exist at a key
	//adds GameObject to the list
	/**
	 * Method that adds an object to a HashMap based on its type.
	 * @param <T>
	 * @param key - The key of the HashMap's list to add the object
	 * @param object - the object to add to the list
	 * @param listMap - the HashMap to be added to
	 */
	public <T> void addObjectToMapList(String key, T object, HashMap<String, ArrayList<Object>> listMap) {
		//if type list does not already exist then create it
		if(listMap.get(key) == null) {
			listMap.put(key, new ArrayList<Object>()); 
		}
		
		//add object to the the type list
		ArrayList<Object> typeArray = (ArrayList<Object>) listMap.get(key); 
		typeArray.add(object);
	}
	
	/**
	 * Gets a GameObject list from the GameObject HashMap 
	 * @param type - Type of GameObject to retrieve from the HashMap
	 * @return A list of GameObjects
	 */
	public ArrayList<Object> getGameObjectList(String type){
		return gameObjects.get(type);
	}

	/**
	 * Removes object from the GameObjects HashMap
	 * @param gameObject
	 */
	public void destroyGameObject(GameObject gameObject) {
		//remove all references to that object in the list
		gameObjectDeleteQueue.add(gameObject);
	}
	
	/**
	 * Updates Camera and all GameObjects within the scene
	 * @param delta - Time since last frame in milliseconds
	 */
	public void update(int delta) {
		//when a user clicks iterate through gameobjects and check distance to mouse
		Input input = App.getInput();
		if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
			//get the world coordinates of the mouse
			Camera camera = App.getActiveScene().getCamera();
			float mouseWorldX = input.getMouseX() + camera.getX();
			float mouseWorldY = input.getMouseY() + camera.getY();
			
			//iterate through the interactables so that we do not check all gameobjects
			boolean foundGameObject = false;
			for(Object object : gameObjects.get("interactables")) {
				GameObject gameObject = (GameObject) object;
				double distance = Math.sqrt(Math.pow((gameObject.getX() - mouseWorldX), 2) + Math.pow(gameObject.getY() - mouseWorldY,2));
				if(distance <= App.SPRITE_SIZE/2) {
					//select the gameobject
					setActiveGameObject(gameObject);
					camera.setFollowMode(true);
					foundGameObject = true;
				}
			}
			if(!foundGameObject) {
				setActiveGameObject(null);
			}
		}
		
		//The camera should only follow the player that is active
		camera.update(delta, activeGameObject);
		
		//update all GameObjects 
		isIteratingGameObjects = true;
		ArrayList<Object> updateList = gameObjects.get("game_objects");
		for(Object object : updateList) {
			GameObject gameObject = (GameObject) object;
			gameObject.update(delta);
		}
		isIteratingGameObjects = false;
		
		//add all the new gameobjects post iteration to avoid adding while iterating
		//remove the objects from the queue when added to main hashmap
		for(String key : gameObjectQueue.keySet()) {
			for(Object object : gameObjectQueue.get(key)) {
				addObjectToMapList(key, object, gameObjects);
			}
			gameObjectQueue.get(key).clear();
		}
		
		//delete all the objects in the delete queue post iteration
		for(Object o : gameObjectDeleteQueue) {
			for(String key : gameObjects.keySet()) {
				gameObjects.get(key).remove(o);
			}
		}
		gameObjectDeleteQueue.clear();
	}
	
	/**
	 * Sets the active GameObject of the scene
	 * @param gameObject - The new active GameObject
	 */
	public void setActiveGameObject(GameObject gameObject) {
		//Deactivate the prev GameObject
		if(activeGameObject != null) {
			activeGameObject.setActive(false);
		}
		
		//Active the new GameObject
		if(gameObject != null) {
			gameObject.setActive(true);
		}
		
		//Sets the active GameObject
		activeGameObject = gameObject;
	}
	
	/**
	 * Gets the active GameObject of the scene
	 * @return The active GameObject
	 */
	public GameObject getActiveGameObject() {
		return activeGameObject;
	}
	
	/**
	 * Adds resource to the resource counter
	 * @param amount - The amount to be added
	 * @param type - The type of resource to add
	 */
	public void addResource(int amount, String type) {
		int prevAmount = resourceCounts.get(type);
		resourceCounts.put(type, prevAmount + amount);
	}
	
	//returns true if resource can be removed,
	//false otherwise
	/**
	 * Removes resource from the resource counter
	 * @param amount
	 * @param type
	 * @return True if resource can be removed, false otherwise
	 */
	boolean removeResource(int amount, String type) {
		int prevAmount = resourceCounts.get(type);
		
		//if there is not enough resource then return false
		if(prevAmount < amount) {
			return false;
		}
		
		//else subtract the amount from the resource counts
		resourceCounts.put(type, prevAmount - amount);
		return true;
	}
	
	/**
	 * Gets the resource counts HashMap
	 * @return The resource counts HashMap
	 */
	public HashMap<String, Integer> getResourceCounts(){
		return resourceCounts;
	}
	
	/**
	 * Renders everything within the scene
	 * @param g - The Slick graphics object, used for drawing
	 */
	public void render(Graphics g) {
		//render the world first
		world.render();
		
		//render buildings
		ArrayList<Object> buildingList = gameObjects.get("buildings");
		for(Object object : buildingList) {
			GameObject gameObject = (GameObject) object;
			gameObject.render();
		}
		
		//render resources
		ArrayList<Object> resourceList = gameObjects.get("resources");
		for(Object object : resourceList) {
			GameObject gameObject = (GameObject) object;
			gameObject.render();
		}
		
		//render units
		ArrayList<Object> unitList = gameObjects.get("units");
		for(Object object : unitList) {
			GameObject gameObject = (GameObject) object;
			gameObject.render();
		}
		
		//render the GUI after everything
		gui.render(g);
	}
	
	/**
	 * Gets the scene's camera
	 * @return The scene's camera
	 */
	public Camera getCamera() {
		return camera;
	}
	
	/**
	 * Gets the scene's world
	 * @return The scene's world
	 */
	public World getWorld() {
		return world;
	}
}