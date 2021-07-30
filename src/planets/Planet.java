package planets;

import application.Database;
import application.Main;
import java.util.ArrayList;
import java.util.Iterator;

import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * <h2> Class Description: </h2>
 * <p1> Holds the information about each Planet</p1>
 */
public class Planet {
	public static ArrayList<Planet> planetList = new ArrayList<Planet>();
	private String name;
	private int angle;
	private int pop;
	private int maxPop;
	private double orbitRad;
	private int x;
	private int y;
	
	private Circle image;
	private Circle orbit ;
	private TextArea planetInfo;

	/** 
	 * <h2> Method Description: </h2>
	 * <p1> Constructor for creating a Planet </p1>
	 * @param name The identifying name for the planet
	 * @param angle The heliocentric angle of the planet
	 * @param pop The current population of the planet
	 * @param maxPop The maximum population of the planet
	 * @param orbitRad The radius of the Planet's orbit in AU
	**/
	public Planet(String name, int angle, int pop, int maxPop, double orbitRad) {
		this.name = name;
		this.angle = angle;
		this.pop = pop;
		this.maxPop = maxPop;
		this.orbitRad = orbitRad;
				
		this.x = (int) ((100 * this.orbitRad * Math.cos(Math.toRadians(this.angle))) + Main.sun.getCenterX());
		this.y = (int) ((100 * this.orbitRad * Math.sin(Math.toRadians(this.angle))) + Main.sun.getCenterY());
		
		this.image = new Circle(this.x, this.y, 10);
		this.image.setFill(Color.color(Math.random(), Math.random(), Math.random(), 1));
		initImageActions();
		
		this.orbit = new Circle(Main.sun.getCenterX(), Main.sun.getCenterY(), 100 * this.orbitRad);
		this.orbit.setFill(Color.TRANSPARENT);
		this.orbit.setStroke(Color.BLACK);
		this.orbit.setStrokeWidth(1);
		
		planetInfo = new TextArea();
		this.planetInfo.setVisible(false);
		this.planetInfo.setPrefSize(170, 100);
		this.planetInfo.setWrapText(false);
		
		planetList.add(this);
	}
	
	/** 
	 * <h2> Method Description: </h2>
	 * <p1> Initializes the actions for the image </p1>
	**/
	private void initImageActions() {
		this.image.setOnMouseDragged(e->{
			if(Main.isSimulating == false) {
				double x2 = e.getSceneX();
				double y2 = e.getSceneY();
	
				double xAngle = x2 - Main.sun.getCenterX();
				double yAngle = y2 - Main.sun.getCenterY();
				
				if(xAngle < 0 && yAngle < 0) {
					double tempAngleDeg = Math.toDegrees(Math.atan(yAngle / xAngle)) - 90;
					this.angle = (int)(tempAngleDeg - 90);
				} else if(xAngle < 0 && yAngle > 0) {
					double tempAngleDeg = Math.toDegrees(Math.atan(yAngle / xAngle)) + 90;
					this.angle = (int)(tempAngleDeg + 90);
				} else {
					this.angle = (int) Math.toDegrees(Math.atan(yAngle / xAngle));
				}
	
				this.x = (int) ((100 * this.orbitRad * Math.cos(Math.toRadians(this.angle))) + Main.sun.getCenterX());
				this.y = (int) ((100 * this.orbitRad * Math.sin(Math.toRadians(this.angle))) + Main.sun.getCenterY());
				this.image.setCenterX(this.x);
				this.image.setCenterY(this.y);
				
				
				this.planetInfo.setText("Planet Name: " + this.name + "\n" +
										"Angle: " + this.angle + "°" + "\n" +
										"Population: " + this.pop + "\n" +
										"Max Population: " + this.maxPop + "\n" +
										"Orbit Radius: " + this.orbitRad + "AU");
				this.planetInfo.setLayoutX(this.x + 10);
				this.planetInfo.setLayoutY(this.y + 10);
				this.planetInfo.setEditable(false);
			}
		});
		
		this.image.setOnMouseClicked(e->{
			if(this.planetInfo.isVisible()) {
				this.planetInfo.setVisible(false);
			} else {
				this.planetInfo.setVisible(true);
				
				this.planetInfo.setText("Planet Name: " + this.name + "\n" +
										"Angle: " + this.angle + "°" + "\n" +
										"Population: " + this.pop + "\n" +
										"Max Population: " + this.maxPop + "\n" +
										"Orbit Radius: " + this.orbitRad + "AU");
		
				this.planetInfo.setLayoutX(this.x + 10);
				this.planetInfo.setLayoutY(this.y + 10);
			}
		});
		
		this.image.setOnMouseReleased(e->{
			if(!Main.isSimulating) {
				Database.update(this.name, this.angle, this.pop, this.maxPop, this.orbitRad);
				Main.dbTextArea.setText(Database.print());
			}
		});
	}
	
	/** 
	 * <h2> Method Description: </h2>
	 * <p1> Prints information about all the created planets to the console </p1>
	**/
	public static void printList() {
		for(Planet planet : planetList) {
			System.out.println(planet.getName() + ", " + planet.getPop());
		}
	}
	
	/** 
	 * <h2> Method Description: </h2>
	 * <p1> Inserts a new Planet into the static ArrayList  </p1>
	 * @param name The identifying name for the planet
	 * @param angle The heliocentric angle of the planet
	 * @param pop The current population of the planet
	 * @param maxPop The maximum population of the planet
	 * @param orbitRad The radius of the Planet's orbit in AU
	**/
	public static void insertList(String name, int angle, int pop, int maxPop, double orbitRad) {
		Planet planet = new Planet(name, angle, pop, maxPop, orbitRad);
	}
	
	/** 
	 * <h2> Method Description: </h2>
	 * <p1> Deletes a Planet by it's identifying name and angle </p1>
	 * @param name The identifying name for the planet
	 * @param angle The heliocentric angle of the planet
	**/
	public static void deleteList(String name, int angle) {
		Iterator<Planet> iterator = planetList.iterator();
		
		while(iterator.hasNext()) {
			Planet planet = (Planet)iterator.next();
			if(planet.getName().equals(name) && planet.getAngle() == angle) {
				planet.getImage().setVisible(false);
				planet.getOrbit().setVisible(false);
				iterator.remove();
			}
		}

	}
	
	/** 
	 * <h2> Method Description: </h2>
	 * <p1> Updates a planet by it's identifying name </p1>
	 * @param name The identifying name for the planet
	 * @param angle The heliocentric angle of the planet
	 * @param pop The current population of the planet
	 * @param maxPop The maximum population of the planet
	 * @param orbitRad The radius of the Planet's orbit in AU
	**/
	public static void updateList(String name, int angle, int pop, int maxPop, double orbitRad) {
		for(Planet planet : planetList) {
			if(planet.getName().equals(name)) {
				planet.setAngle(angle);
				planet.setPop(pop);
				planet.setMaxPop(maxPop);
				planet.setOrbitRad(orbitRad);
				
				planet.getImage().setCenterX((int) (100 * planet.getOrbitRad() * Math.cos(Math.toRadians(planet.getAngle())) + Main.sun.getCenterX()));
				planet.getImage().setCenterY((int) (100 * planet.getOrbitRad() * Math.sin(Math.toRadians(planet.getAngle())) + Main.sun.getCenterY()));

				planet.getOrbit().setRadius(100 * orbitRad);
				
				planet.setX((int) planet.getImage().getCenterX());
				planet.setY((int) planet.getImage().getCenterY());
			}
		}
	}
	
	/** 
	 * <h2> Method Description: </h2>
	 * <p1> Searches for a planet by name </p1>
	 * @param name The identifying name for the planet
	 * @return The Planet with the identifying name
	**/
	public static Planet searchPlanet(String name) {
		for(Planet planet : Planet.planetList) {
			if(planet.getName().equalsIgnoreCase(name)) {
				return planet;
			}
		}
		Planet errorPlanet = new Planet("ERROR", 0, 0, 0, 0);
		
		return errorPlanet;
	}
	
	/** 
	 * <h2> Method Description: </h2>
	 * <p1> A method used to initialize the ComboBox </p1>
	 * @return An array of Planet names
	**/
	public static String[] getPlanetStringList() {
		String[] names = new String[planetList.size()];
		int index=0;

		for(Planet planet : planetList) {
			names[index] = planet.getName();
			index++;
		}
		
		return names;
	}
	
	/** 
	 * <h2> Method Description: </h2>
	 * <p1> A method used to determine the other planets the user can travel to </p1>
	 * @param planetName The Planet to be left out of the array
	 * @return An array of Planet, not including the Planet in the parameters
	**/
	public static Planet[] getPlanetList(String planetName) {
		Planet[] list = {new Planet("ERROR", 0 , 0, 0, 0)};
		
		if(planetList.size()>=1) {
			list = new Planet[planetList.size()-1];
		}
		
		int index=0;
		for(Planet planet : planetList) {
			if(!planet.getName().equalsIgnoreCase(planetName)) {
				list[index] = planet;
				index++;
			}
		}

		return list;
	}
	
	/** 
	 * <h2> Method Description: </h2>
	 * <p1> A method used to determine the other planets the user can travel to </p1>
	 * @param planetName The Planet name to be left out of the array
	 * @return An array of Planet names , not including the Planet name in the parameters
	**/
	public static String[] getPlanetStringList(String planetName) {
		String[] names = {"ERROR"};
		
		if(planetList.size()>=1) {
			names = new String[planetList.size()-1];
		}
		int index=0;

		for(Planet planet : planetList) {
			if(!planet.getName().equalsIgnoreCase(planetName)) {
				names[index] = planet.getName();
				index++;
			}
		}
		
		return names;
	}

	public static ArrayList<Planet> getPlanetList() {
		return planetList;
	}

	public static void setPlanetList(ArrayList<Planet> planetList) {
		Planet.planetList = planetList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAngle() {
		return angle;
	}

	public void setAngle(int angle) {
		this.angle = angle;
	}

	public int getPop() {
		return pop;
	}

	public void setPop(int pop) {
		this.pop = pop;
	}

	public int getMaxPop() {
		return maxPop;
	}

	public void setMaxPop(int maxPop) {
		this.maxPop = maxPop;
	}

	public double getOrbitRad() {
		return orbitRad;
	}
	
	public void setOrbitRad(double orbitRad) {
		this.orbitRad = orbitRad;
	}

	public Circle getImage() {
		return image;
	}

	public void setImage(Circle image) {
		this.image = image;
	}

	public Circle getOrbit() {
		return orbit;
	}

	public void setOrbit(Circle orbit) {
		this.orbit = orbit;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public TextArea getPlanetInfo() {
		return planetInfo;
	}

	public void setPlanetInfo(TextArea planetInfo) {
		this.planetInfo = planetInfo;
	}
	
	
}
