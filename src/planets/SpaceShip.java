package planets;

import java.util.ArrayList;

/**
 * <h2> Class Description: </h2>
 * <p1> Holds the information about the status of the ship</p1>
 */
public class SpaceShip {
	private ArrayList<Route> routePath = new ArrayList<Route>();
	private int passengers;
	private int maxPassengers;
	private int maxSpeed;
	private Planet currentPlanet;
	
	/** 
	 * <h2> Method Description: </h2>
	 * <p1> Constructs the SpaceShip Object </p1>
	 * @param passengers The initial number of passengers aboard
	 * @param maxPassengers The maximum number of passengers that can be aboard
	 * @param maxSpeed The max speed the Ship can travel at in m/s
	**/
	public SpaceShip(int passengers, int maxPassengers, int maxSpeed) {	
		this.passengers = passengers;
		this.maxPassengers = maxPassengers;
		this.maxSpeed = maxSpeed;
	}

	/** 
	 * <h2> Method Description: </h2>
	 * <p1> Determines the number of passengers that can be collected. 
	 * Adds the collected passengers to the ship and Subtracts them from the Planet. </p1>
	**/
	public void collectPassengers() {
		if(this.currentPlanet.getName().equalsIgnoreCase("EARTH")) {
			int passSpace = this.maxPassengers - this.passengers;
			int planetPop = this.currentPlanet.getPop();
			
			if(planetPop >= passSpace) {
				currentPlanet.setPop(planetPop - passSpace);
				this.passengers = this.passengers + passSpace;
			} else {
				this.passengers = this.passengers + planetPop;
				this.currentPlanet.setPop(0);
			}
		}
	}
	
	/** 
	 * <h2> Method Description: </h2>
	 * <p1> Determines the number of passengers that can be dropped. 
	 * Subtracts the collected passengers to the ship and adds them from the Planet. </p1>
	**/
	public void dropPassengers() {
		if(!this.currentPlanet.getName().equalsIgnoreCase("EARTH")) {
			int planetSpace = this.currentPlanet.getMaxPop() - this.currentPlanet.getPop();
			int planetPop = this.currentPlanet.getPop();
			
			if(this.getPassengers() <= planetSpace) {
				this.currentPlanet.setPop(planetPop + this.getPassengers());
				this.setPassengers(0);
			} else {
				this.currentPlanet.setPop(planetPop + planetSpace); 
				this.passengers = this.passengers - planetSpace;
			}
		}
	}
	
	/** 
	 * <h2> Method Description: </h2>
	 * <p1> Determines the number of passengers that can be collected. 
	 * Doesn't perform any actions on the planet or ship </p1>
	 * @param currentPlanet The planet that's collection capacity is to be determined
	 * @return The number of passengers that can be collected
	**/ 
	public int determineCollectPassenger(Planet currentPlanet) {
		int passSpace = this.maxPassengers - this.passengers;
		int planetPop = this.currentPlanet.getPop();
		
		if(planetPop >= passSpace) {
			return passSpace;
		} else {
			return planetPop;
		}
	}
	
	/** 
	 * <h2> Method Description: </h2>
	 * <p1> Determines the number of passengers that can be dropped. 
	 * Doesn't perform any actions on the planet or ship </p1>
	 * 	 * @param currentPlanet The planet that's drop capacity is to be determined
	 * @return The number of passengers that can be dropped
	**/ 
	public int determineDropPassengers(Planet currentPlanet) {
		int planetSpace = this.currentPlanet.getMaxPop() - this.currentPlanet.getPop();
		
		if(this.getPassengers() <= planetSpace) {
			return this.getPassengers();
		} else {
			return planetSpace;
		}	
	}
	
	public ArrayList<Route> getRoutePath() {
		return routePath;
	}

	public void setRoutePath(ArrayList<Route> routePath) {
		this.routePath = routePath;
	}

	public int getPassengers() {
		return passengers;
	}

	public void setPassengers(int passengers) {
		this.passengers = passengers;
	}

	public int getMaxPassengers() {
		return maxPassengers;
	}

	public void setMaxPassengers(int maxPassengers) {
		this.maxPassengers = maxPassengers;
	}

	public int getMaxSpeed() {
		return maxSpeed;
	}

	public void setMaxSpeed(int maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

	public Planet getCurrentPlanet() {
		return currentPlanet;
	}

	public void setCurrentPlanet(Planet currentPlanet) {
		this.currentPlanet = currentPlanet;
	}
	
}
