package planets;

import java.util.ArrayList;

import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/**
 * <h2> Class Description: </h2>
 * <p1> Holds the information about the a route between two planets</p1>
 */
public class Route {
	private Planet startPlanet;
	private Planet endPlanet;
	private double distance;
	private double time;
	private int routeNum;
	
	private Line routeLine;
	private TextArea routeInfo;
	
	public static ArrayList<Route>routeList = new ArrayList<Route>();
	
	/** 
	 * <h2> Method Description: </h2>
	 * <p1> A null Route Constructor </p1>
	**/
	public Route() {
		this.distance = 0;
		this.time = 0;
		Planet nullPlanet = new Planet("NULL", 0, 0, 0, 0);
		this.endPlanet = nullPlanet;
	}
	
	/** 
	 * <h2> Method Description: </h2>
	 * <p1> A constructor for a route between two Planets </p1>
	 * @param routeNum The route number in the ordered list
	 * @param startPlanet The Planet the route begins at
	 * @param endPlanet The Planet the route ends at
	 * @param shipSpeed The speed of the ship traveling the route
	**/
	public Route(int routeNum, Planet startPlanet, Planet endPlanet, int shipSpeed) {
		this.routeNum = routeNum;
		this.startPlanet = startPlanet;
		this.endPlanet = endPlanet;
		
		double x1 = startPlanet.getX();
		double y1 = startPlanet.getY();
		
		double x2 = endPlanet.getX();
		double y2 = endPlanet.getY();
		
		double x = x2 - x1;
		double y = y2 - y1;
		this.distance = Math.sqrt((x*x) + (y*y));
		
		this.routeLine = new Line(x1, y1, x2, y2);
		this.routeLine.setStrokeWidth(3);
		this.routeLine.setFill(Color.YELLOW);
		
		this.time = this.distance / shipSpeed;	
		
		this.routeInfo = new TextArea();
		this.routeInfo.setPrefSize(210, 115);
		this.routeInfo.setText("Route #: " + this.routeNum + "\n" +
								"Starting Planet: " + this.startPlanet.getName() + "\n" +
							   "End Planet: " + this.endPlanet.getName() + "\n" +
							   "Distance: " + this.distance + "\n" +
							   "Time: " + this.time);
		this.routeInfo.setVisible(false);
		this.routeInfo.setLayoutX(this.getRouteLine().getEndX() + 10);
		this.routeInfo.setLayoutY(this.getRouteLine().getEndY() + 10);
		this.routeInfo.setEditable(false);
		initRouteInfoActions();
		
		routeList.add(this);
	}

	/** 
	 * <h2> Method Description: </h2>
	 * <p1> Initializes the actions for the route image </p1>
	**/
	private void initRouteInfoActions() {
		this.routeLine.setOnMouseClicked(e->{
			if(this.routeInfo.isVisible()) {
				this.routeInfo.setVisible(false);
			} else {
				this.routeInfo.setVisible(true);
			}
		});
	}
	public Planet getStartPlanet() {
		return startPlanet;
	}

	public void setStartPlanet(Planet startPlanet) {
		this.startPlanet = startPlanet;
	}

	public Planet getEndPlanet() {
		return endPlanet;
	}

	public void setEndPlanet(Planet endPlanet) {
		this.endPlanet = endPlanet;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public double getTime() {
		return time;
	}

	public void setTime(double time) {
		this.time = time;
	}

	public Line getRouteLine() {
		return routeLine;
	}

	public void setRouteLine(Line routeLine) {
		this.routeLine = routeLine;
	}

	public int getRouteNum() {
		return routeNum;
	}

	public void setRouteNum(int routeNum) {
		this.routeNum = routeNum;
	}

	public TextArea getRouteInfo() {
		return routeInfo;
	}

	public void setRouteInfo(TextArea routeInfo) {
		this.routeInfo = routeInfo;
	}
		
}
