package application;
	
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import planets.Planet;
import planets.Route;
import planets.SpaceShip;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Main extends Application {
	TabPane tabPane;
	
	Tab dbTab;
	GridPane dbGridPane;
	TextField dbPlanetNameField;
	TextField dbOrbitRadiusField;
	TextField dbAngleField;
	TextField dbPopField;
	TextField dbMaxPopField;
	TextField dbUserField;
	TextField dbPassField;
	Label dbPlanetNameLbl;
	Label dbOrbitRadiusLbl;
	Label dbAngleLbl;
	Label dbPopLbl;
	Label dbMaxPopLbl;
	Label dbUserLbl;
	Label dbPassLbl;
	Button dbQueryBtn;
	Button dbEstablishConnectionBtn;
	
	ComboBox<String> dbCB;
	public static TextArea dbTextArea;
	ScrollPane dbScrollPane;
	
	Tab viewPlanetTab;
	Pane viewPlanetPane;
	public static Circle sun;
	GridPane viewPlanetGridPane;
	
	Tab routeTab;
	GridPane routeGridPane;
	Label routeSelectPlanetLbl;
	ComboBox<String> routeSelectPlanetCB;
	Label routeNumLbl;
	Label routeStartPlanetLbl;
	Label routeNextPlanetLbl;
	Label routeDistanceLbl;
	Label routeTimeLbl;
	Label routePassLbl;
	Label routePopLbl;
	Label routeNextPlanetPop;
	Label routeNextPlanetMaxPop;
	Button routeTravelBtn;
	Button routeBeginBtn;
	Button routeResetBtn;
	Button routeDetermineBestRoute;
	ScrollPane routeScrollPane;
	TextArea routeTextArea;
	public static boolean isSimulating = false;
	static SpaceShip ship = new SpaceShip(0, 1000, 1000);
	static String startPlanetStr = "EARTH";
	static String nextPlanetStr = "";
	static Route chosenRoute;
	
	public void start(Stage primaryStage) {
		try {
			sun = new Circle(20);
			sun.setCenterX(330);
			sun.setCenterY(300);
			sun.setFill(Color.YELLOW);
						
			initDB();
			initDBAction();
			initViewPlanet();
			initVeiwPlanetAction();
			initRoute();
			initRouteAction();
			initTabs();

			Scene scene = new Scene(tabPane);
			primaryStage.setScene(scene);
			primaryStage.show();

		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void initTabs() {
		tabPane = new TabPane();
		
		dbTab = new Tab("Database", dbGridPane);
		dbTab.setClosable(false);
		
		viewPlanetTab = new Tab("View Planet", viewPlanetPane);
		viewPlanetTab.setClosable(false);
		
		routeTab = new Tab("Route Builder", routeGridPane);
		routeTab.setClosable(false);
		
		tabPane.getTabs().addAll(dbTab, viewPlanetTab, routeTab);
	}
	
	public void initDB() {
		
		dbGridPane = new GridPane();
		dbGridPane.getRowConstraints().add(new RowConstraints(50));
		dbGridPane.getRowConstraints().add(new RowConstraints(50));
		dbGridPane.getRowConstraints().add(new RowConstraints(50));
		dbGridPane.getRowConstraints().add(new RowConstraints(50));
		dbGridPane.getRowConstraints().add(new RowConstraints(50));
		dbGridPane.getRowConstraints().add(new RowConstraints(50));
		dbGridPane.getRowConstraints().add(new RowConstraints(50));
		dbGridPane.getRowConstraints().add(new RowConstraints(50));
		dbGridPane.getRowConstraints().add(new RowConstraints(50));
		dbGridPane.getRowConstraints().add(new RowConstraints(300));

		dbGridPane.getColumnConstraints().add(new ColumnConstraints(150));
		dbGridPane.getColumnConstraints().add(new ColumnConstraints(125));
		dbGridPane.getColumnConstraints().add(new ColumnConstraints(400));

		dbPlanetNameLbl = new Label("Planet Name: ");
		dbPlanetNameLbl.setTextFill(Color.BLUE);
		GridPane.setConstraints(dbPlanetNameLbl, 0, 0);

		dbPlanetNameField = new TextField();
		dbPlanetNameField.setPrefSize(100, 15);
		GridPane.setConstraints(dbPlanetNameField, 1, 0);

		dbAngleLbl = new Label("Angle (DEGREES): ");
		dbAngleLbl.setTextFill(Color.BLUE);
		GridPane.setConstraints(dbAngleLbl, 0, 1);

		dbAngleField = new TextField();
		dbAngleField.setPrefSize(100, 15);
		GridPane.setConstraints(dbAngleField, 1, 1);

		dbPopLbl = new Label("Current Population: ");
		dbPopLbl.setTextFill(Color.BLUE);
		GridPane.setConstraints(dbPopLbl, 0, 2);

		dbPopField = new TextField();
		dbPopField.setPrefSize(100, 15);
		GridPane.setConstraints(dbPopField, 1, 2);

		dbMaxPopLbl = new Label("Max Population");
		dbMaxPopLbl.setTextFill(Color.BLUE);
		GridPane.setConstraints(dbMaxPopLbl, 0, 3);

		dbMaxPopField = new TextField();
		dbMaxPopField.setPrefSize(100, 15);
		GridPane.setConstraints(dbMaxPopField, 1, 3);
		
		dbOrbitRadiusLbl = new Label("Orbit Radius (AU):");
		dbOrbitRadiusLbl.setTextFill(Color.BLUE);
		GridPane.setConstraints(dbOrbitRadiusLbl, 0, 4);
		
		dbOrbitRadiusField = new TextField();
		dbOrbitRadiusField.setPrefSize(100, 15);
		GridPane.setConstraints(dbOrbitRadiusField, 1, 4);
		
		String[] stringOptions = {"INSERT", "UPDATE", "DELETE"};	
		ObservableList<String> options = FXCollections.observableArrayList(stringOptions);
		dbCB = new ComboBox<String>(options);
		GridPane.setConstraints(dbCB, 0, 5);

		dbQueryBtn = new Button("Query");
		dbQueryBtn.setPrefSize(75, 15);
		GridPane.setConstraints(dbQueryBtn, 1, 5);

		dbTextArea = new TextArea();
		dbTextArea.setEditable(false);
		dbTextArea.setPrefSize(400, 300);
		
		dbUserLbl = new Label();
		dbUserLbl.setText("Datbase User:");
		GridPane.setConstraints(dbUserLbl, 0, 6);

		dbUserField = new TextField();
		dbUserField.setPrefSize(100, 15);
		GridPane.setConstraints(dbUserField, 1, 6);
		
		dbPassLbl = new Label();
		dbPassLbl.setText("Database Pass:");
		GridPane.setConstraints(dbPassLbl, 0, 7);
		
		dbPassField = new TextField();
		dbPassField.setPrefSize(100, 15);
		GridPane.setConstraints(dbPassField, 1, 7);

		dbEstablishConnectionBtn = new Button();
		dbEstablishConnectionBtn.setText("Establish Connection");
		dbEstablishConnectionBtn.setPrefSize(150, 15);
		GridPane.setConstraints(dbEstablishConnectionBtn, 0, 8);

		dbScrollPane = new ScrollPane();
		dbScrollPane.setContent(dbTextArea);
		GridPane.setConstraints(dbScrollPane, 2, 9);

		dbGridPane.getChildren().addAll(dbPlanetNameLbl, dbPlanetNameField, dbOrbitRadiusLbl, 
				dbOrbitRadiusField, dbAngleLbl, dbAngleField, dbPopLbl, dbPopField, dbMaxPopLbl, 
				dbMaxPopField, dbCB, dbQueryBtn, dbUserLbl, dbUserField, dbPassLbl, dbPassField, dbEstablishConnectionBtn, dbScrollPane);
	}
	
	public void initDBAction() {
		dbQueryBtn.setOnAction(e->{
			if(dbCB.getValue() == null) {
				dbTextArea.appendText("Please Select an Action from the combo box" + "\n");
			} else if(isSimulating) { 
				dbTextArea.appendText("Simulating is running, cannot edit database" + "\n");
			} else if(dbCB.getValue().equals("INSERT")) {
				String name = dbPlanetNameField.getText().toUpperCase();
				
				try {
					int angle = Integer.parseInt(dbAngleField.getText());
					int pop = Integer.parseInt(dbPopField.getText());
					int maxPop = Integer.parseInt(dbMaxPopField.getText());
					double orbitRad = Double.parseDouble(dbOrbitRadiusField.getText());
					
					Database.insert(name, angle, pop, maxPop, orbitRad);
					dbTextArea.setText(Database.print());
				} catch(NumberFormatException e1) {
					dbTextArea.appendText("Please enter valid integer values" + "\n");
				}
			} else if(dbCB.getValue().equals("DELETE")) {
				String name = dbPlanetNameField.getText().toUpperCase();

				try {
					int angle = Integer.parseInt(dbAngleField.getText());

					Database.delete(name, angle);
					dbTextArea.setText(Database.print());
				} catch(NumberFormatException e1) {
					dbTextArea.appendText("Please enter valid integer values");
				}
			} else if(dbCB.getValue().equals("UPDATE")) {
				String name = dbPlanetNameField.getText().toUpperCase();
				
				try {
					int angle = Integer.parseInt(dbAngleField.getText());
					int pop = Integer.parseInt(dbPopField.getText());
					int maxPop = Integer.parseInt(dbMaxPopField.getText());
					double orbitRad = Double.parseDouble(dbOrbitRadiusField.getText());
					
					Database.update(name, angle, pop, maxPop, orbitRad);
					dbTextArea.setText(Database.print());
				} catch(NumberFormatException e1) {
					dbTextArea.appendText("Please enter valid integer values");
				}
			} else {
				dbTextArea.setText("Please choose an action from the ComboBox");
			}
		});
		
		dbEstablishConnectionBtn.setOnAction(e->{
			Database.password = dbPassField.getText();
			Database.user = dbUserField.getText();
			
			String result = Database.establishConnection();
			
			dbTextArea.appendText("Connection Succesful: " + result + "\n"); 
			dbTextArea.appendText(Database.print());
		});
	}

	public void initViewPlanet() {
		viewPlanetPane = new Pane();
		
		viewPlanetPane.getChildren().add(sun);
	}
	
	public void initVeiwPlanetAction() {
		sun.setOnMouseClicked(e->{
			viewPlanetPane.getChildren().clear();
			viewPlanetPane.getChildren().add(sun);

			for(Planet planet : Planet.getPlanetList()) {
				viewPlanetPane.getChildren().add(planet.getOrbit());
			}
			
			for(Planet planet : Planet.getPlanetList()) {
				viewPlanetPane.getChildren().add(planet.getImage());
				viewPlanetPane.getChildren().add(planet.getPlanetInfo());
			}
		});
	}
	
	public void initRoute() {
		routeGridPane = new GridPane();
		routeGridPane.getRowConstraints().add(new RowConstraints(50));
		routeGridPane.getRowConstraints().add(new RowConstraints(25));
		routeGridPane.getRowConstraints().add(new RowConstraints(25));
		routeGridPane.getRowConstraints().add(new RowConstraints(25));
		routeGridPane.getRowConstraints().add(new RowConstraints(25));
		routeGridPane.getRowConstraints().add(new RowConstraints(25));
		routeGridPane.getRowConstraints().add(new RowConstraints(25));
		routeGridPane.getRowConstraints().add(new RowConstraints(25));
		routeGridPane.getRowConstraints().add(new RowConstraints(25));
		routeGridPane.getRowConstraints().add(new RowConstraints(25));
		routeGridPane.getRowConstraints().add(new RowConstraints(50));
		routeGridPane.getRowConstraints().add(new RowConstraints(50));
		routeGridPane.getRowConstraints().add(new RowConstraints(300));

		routeGridPane.getColumnConstraints().add(new ColumnConstraints(250));
		routeGridPane.getColumnConstraints().add(new ColumnConstraints(150));
		routeGridPane.getColumnConstraints().add(new ColumnConstraints(250));

		routeSelectPlanetLbl = new Label();
		routeSelectPlanetLbl.setText("Select Next Planet: ");
		GridPane.setConstraints(routeSelectPlanetLbl, 0, 0);
		
		ObservableList<String> options = FXCollections.observableArrayList(Planet.getPlanetStringList("EARTH"));
		
		routeSelectPlanetCB = new ComboBox<String>(options);
		GridPane.setConstraints(routeSelectPlanetCB, 1, 0);

		routeNumLbl = new Label("Route #: 1");
		routeNumLbl.setTextFill(Color.BLUE);
		GridPane.setConstraints(routeNumLbl, 0, 1);
		
		routeStartPlanetLbl = new Label("Starting Planet: ");
		routeStartPlanetLbl.setTextFill(Color.BLUE);
		GridPane.setConstraints(routeStartPlanetLbl, 0, 2);
		
		routeNextPlanetLbl = new Label("Next Planet: ");
		routeNextPlanetLbl.setTextFill(Color.BLUE);
		GridPane.setConstraints(routeNextPlanetLbl, 0, 3);

		routeNextPlanetPop = new Label("Next Planet Pop: ");
		routeNextPlanetPop.setTextFill(Color.BLUE);
		GridPane.setConstraints(routeNextPlanetPop, 0, 4);

		routeNextPlanetMaxPop = new Label("Next Planet MaxPop:");
		routeNextPlanetMaxPop.setTextFill(Color.BLUE);
		GridPane.setConstraints(routeNextPlanetMaxPop, 0, 5);
		
		routeDistanceLbl = new Label("Distance: ");
		routeDistanceLbl.setTextFill(Color.BLUE);
		GridPane.setConstraints(routeDistanceLbl, 0, 6);
		
		routeTimeLbl = new Label("Time: ");
		routeTimeLbl.setTextFill(Color.BLUE);
		GridPane.setConstraints(routeTimeLbl, 0, 7);
		
		routePassLbl = new Label("Passengers Left: ");
		routePassLbl.setTextFill(Color.BLUE);
		GridPane.setConstraints(routePassLbl, 0, 8);
		
		routePopLbl = new Label("Left On Doom Planet: ");
		routePopLbl.setTextFill(Color.BLUE);
		GridPane.setConstraints(routePopLbl, 0, 9);
		
		routeBeginBtn = new Button("Begin");
		GridPane.setConstraints(routeBeginBtn, 0, 10);

		routeTravelBtn = new Button("Travel");
		routeTravelBtn.setVisible(false);
		GridPane.setConstraints(routeTravelBtn, 0, 10);
		
		routeResetBtn = new Button("Reset");
		routeResetBtn.setVisible(false);
		GridPane.setConstraints(routeResetBtn, 1, 10);

		routeDetermineBestRoute = new Button("Determine Best Route");
		GridPane.setConstraints(routeDetermineBestRoute, 0, 11);

		routeTextArea = new TextArea();
		routeTextArea.setEditable(false);
		routeTextArea.setPrefSize(250, 300);
		
		routeScrollPane = new ScrollPane();
		routeScrollPane.setContent(routeTextArea);
		GridPane.setConstraints(routeScrollPane, 2, 12);

		routeGridPane.getChildren().addAll(routeSelectPlanetLbl, routeSelectPlanetCB, 
				routeNumLbl, routeStartPlanetLbl, routeNextPlanetLbl, routeNextPlanetPop, 
				routeNextPlanetMaxPop, routeDistanceLbl, routeTimeLbl, routePassLbl, 
				routePopLbl, routeBeginBtn, routeTravelBtn, routeResetBtn, routeDetermineBestRoute, routeScrollPane);
		
	}
	
	public void initRouteAction() {
		ChangeListener<String> cbListener = (observable, oldValue, newValue) -> {
			if(newValue != null && isSimulating) {
				Main.nextPlanetStr = newValue;
				Planet startPlanet = Planet.searchPlanet(startPlanetStr);
				Planet nextPlanet = Planet.searchPlanet(nextPlanetStr);
	
				ship.setCurrentPlanet(nextPlanet);
	
				if(!nextPlanet.getName().equalsIgnoreCase("EARTH")) {
					int passDropped = ship.determineDropPassengers(nextPlanet);
					chosenRoute = new Route(ship.getRoutePath().size() + 1, startPlanet, nextPlanet, ship.getMaxSpeed());
					
					routeNumLbl.setText("Route #: " + (ship.getRoutePath().size() + 1));
					routeStartPlanetLbl.setText("Start Planet: " + startPlanet.getName());
					routeNextPlanetLbl.setText("Next Planet: " + nextPlanet.getName());
					routeNextPlanetPop.setText("Next Planet Pop: " + (nextPlanet.getPop()) + " + " + passDropped);
					routeNextPlanetMaxPop.setText("Next Planet MaxPop: " + nextPlanet.getMaxPop());
					routeDistanceLbl.setText("Distance: " + chosenRoute.getDistance());
					routeTimeLbl.setText("Time: " + chosenRoute.getTime());	
					routePassLbl.setText("Passengers Left On Ship: " + ship.getPassengers() + " - " + passDropped);
					routePopLbl.setText("Left On Doom Planet: " + Planet.searchPlanet("EARTH").getPop());
					
				} else {
					int passCollect = ship.determineCollectPassenger(nextPlanet);
					chosenRoute = new Route((ship.getRoutePath().size() + 1), startPlanet, nextPlanet, ship.getMaxSpeed());
					
					routeNumLbl.setText("Route #: " + (ship.getRoutePath().size() + 1));
					routeStartPlanetLbl.setText("Start Planet: " + startPlanet.getName());
					routeNextPlanetLbl.setText("Next Planet: " + nextPlanet.getName());
					routeNextPlanetPop.setText("Next Planet Pop: " + (nextPlanet.getPop()) + " - " + passCollect);
					routeNextPlanetMaxPop.setText("Next Planet MaxPop: " + nextPlanet.getMaxPop());
					routeDistanceLbl.setText("Distance: " + chosenRoute.getDistance());
					routeTimeLbl.setText("Time: " + chosenRoute.getTime());	
					routePassLbl.setText("Passengers Left On Ship: " + ship.getPassengers() + " + " + passCollect);
					routePopLbl.setText("Left On Doom Planet: " + (Planet.searchPlanet("EARTH").getPop()  + " - " + passCollect));
				}
			}
		};
		routeSelectPlanetCB.valueProperty().addListener(cbListener);
		
		routeBeginBtn.setOnAction(e->{
			routeBeginBtn.setVisible(false);
			routeTravelBtn.setVisible(true);
			routeResetBtn.setVisible(true);
			isSimulating = true;
			
			ship.setCurrentPlanet(Planet.searchPlanet("EARTH"));
			routePopLbl.setText("Left On Doom Planet: " + (Planet.searchPlanet("EARTH").getPop()  + " - 1000"));
			ship.collectPassengers();
		});
		
		routeTravelBtn.setOnAction(e->{	
			
			if(chosenRoute != null) {
				Planet nextPlanet = Planet.searchPlanet(nextPlanetStr);
				
				if(nextPlanet.getName().equalsIgnoreCase("EARTH")) {
					ship.collectPassengers();
				} else {
					ship.dropPassengers();
				}

				nextPlanet.getPlanetInfo().setText("Planet Name: " + nextPlanet.getName() + "\n" +
						"Angle: " + nextPlanet.getAngle() + "°" + "\n" +
						"Population: " + nextPlanet.getPop() + "\n" +
						"Max Population: " + nextPlanet.getMaxPop() + "\n" +
						"Orbit Radius: " + nextPlanet.getOrbitRad() + "AU");

				Planet.updateList(nextPlanet.getName(), nextPlanet.getAngle(), nextPlanet.getPop(), 
						nextPlanet.getMaxPop(), nextPlanet.getOrbitRad());

				ship.getRoutePath().add(chosenRoute);
				viewPlanetPane.getChildren().add(chosenRoute.getRouteLine());
				viewPlanetPane.getChildren().add(chosenRoute.getRouteInfo());
	
				routeTextArea.appendText(routeNumLbl.getText() + "\n" +
										routeStartPlanetLbl.getText() + "\n" +
										routeNextPlanetLbl.getText() + "\n" +
										routeNextPlanetPop.getText() + "\n" +
										routeNextPlanetMaxPop.getText() + "\n" +
										routeDistanceLbl.getText() + "\n" +
										routeTimeLbl.getText()+ "\n" +
										routePassLbl.getText() + "\n" +
										routePopLbl.getText() + "\n");
				
				startPlanetStr = routeSelectPlanetCB.getValue();
	
				ObservableList<String> items = FXCollections.observableArrayList(Planet.getPlanetStringList(nextPlanet.getName()));
				routeSelectPlanetCB.setItems(items);
			}
		});
		
		routeResetBtn.setOnAction(e->{
			for(Route route : ship.getRoutePath()) {
				viewPlanetPane.getChildren().remove(route.getRouteLine());
				viewPlanetPane.getChildren().remove(route.getRouteInfo());
			}	
			ship.getRoutePath().clear();
			
			for(Planet planet : Planet.planetList) {
				viewPlanetPane.getChildren().remove(planet.getImage());
				viewPlanetPane.getChildren().remove(planet.getPlanetInfo());
			}
			
			Database.query("SELECT * FROM planet_info;");
			for(Planet planet : Planet.planetList) {
				viewPlanetPane.getChildren().add(planet.getImage());
				viewPlanetPane.getChildren().add(planet.getPlanetInfo());
			}
			
			routeBeginBtn.setVisible(true);
			routeTravelBtn.setVisible(false);
			routeResetBtn.setVisible(false);
			isSimulating = false;
			
			ship.setCurrentPlanet(Planet.searchPlanet("EARTH"));
			Planet.searchPlanet("EARTH").setPop(5000);
			Planet.searchPlanet("EARTH").setMaxPop(5000);
			
			ObservableList<String> items = FXCollections.observableArrayList(Planet.getPlanetStringList("EARTH"));
			routeSelectPlanetCB.setItems(items);
			
			startPlanetStr = "EARTH";
			nextPlanetStr = "";
		});
		
		routeDetermineBestRoute.setOnAction(e->{

			
		});
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
