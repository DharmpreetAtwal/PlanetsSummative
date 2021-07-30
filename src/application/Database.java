package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import planets.Planet;

public class Database {
	static Connection con;
	static String driver = "com.mysql.cj.jdbc.Driver";
	static String url = "jdbc:mysql://localhost:3306/planets?&serverTimezone=UTC";
	static String user = "root";
	static String password = "Da20021205";

	public static String establishConnection(){
		String result = "error";
		try {
			con = DriverManager.getConnection(url, user, password);
			Class.forName(driver);
			result = url;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch(SQLException e1) {
			Main.dbTextArea.appendText("\n" + "**CONNECTION FAILED**" + "\n" + "CHECK TO MAKE SURE DATABASE IS CONFIGURED" + "\n" );
		} 
		return result;
	}
	
	public static String print() {
		String result = Database.query("SELECT * FROM planet_info;");
		
		return result;
	}
	
	public static String insert(String name, int angle, int pop, int maxPop, double orbitRad) {
		String query = "INSERT INTO planet_info(name, angle, population, max_pop, orbit_radius_AU) VALUES("+ "\"" + name + "\"" + ","
				+ angle + "," + pop + "," + maxPop + "," + orbitRad + ");";
		Planet.insertList(name, angle, pop, maxPop, orbitRad);
		
		try {
			PreparedStatement prepST = con.prepareStatement(query);
			prepST.executeUpdate();
			return prepST.toString();
		} catch (SQLException e) {
			e.printStackTrace();
			return "ERROR INSERTING" + "\n";
		} catch(NullPointerException e1) {
			return "ERROR INSERTING" + "\n";
		}
	}
	
	public static String delete(String name, int angle) {
		String query = "DELETE FROM planet_info WHERE name =" + "\""+ name + "\"" +  " AND angle=" + angle + ";";
		Planet.deleteList(name, angle);
		
		try {
			PreparedStatement prepST = con.prepareStatement(query);
			prepST.executeUpdate();
			return prepST.toString();
		} catch (SQLException e) {
			e.printStackTrace();
			return "ERROR DELETING" + "\n";
		} catch(NullPointerException e1) {
			return "ERROR DELETING" + "\n";
		}
	}
	
	public static String update(String name, int angle, int pop, int maxPop, double orbitRad) {
		String query = "UPDATE planet_info SET angle =" + angle + ", population=" + pop + ", max_pop=" + maxPop + 
				", orbit_radius_AU=" + orbitRad + " WHERE name =" + "\""+ name + "\"" + ";";
		Planet.updateList(name, angle, pop, maxPop, orbitRad);
		
		try {
			PreparedStatement prepST = con.prepareStatement(query);
			prepST.executeUpdate();
			return prepST.toString();
		} catch (SQLException e) {
			return "ERROR UPDATING" + "\n";
		} catch(NullPointerException e1) {
			return "ERROR UPDATING" + "\n";
		}
	}
	
	public static String query(String query) {
		try {
			PreparedStatement prepST = con.prepareStatement(query);
			ResultSet rs = prepST.executeQuery();
			return resultSetToString(rs);
		} catch (SQLException e) {
			return "ERROR QUERYING" + "\n";
		} catch(NullPointerException e) {
			return "ERROR QUERYING" + "\n";
		}

	}
	
	private static String resultSetToString(ResultSet rs) throws SQLException {
		String result ="";
		int col=0;
		if(rs != null) {
			Planet.planetList.clear();
			ResultSetMetaData rsMetaData = rs.getMetaData();
			col = rsMetaData.getColumnCount();
			
			if(col > 1) {
				while(rs.next()) {
					for(int i=1; i<=col; i++) {
						if(i==1) {
							result = result + rs.getString(i) + " | ";
						}
						
						if(i==2) {
							result = result + rs.getInt(i) + "° | ";
						}
						
						if(i==3) {
							result = result + rs.getString(i) + " | ";
						}
						
						if(i==4) {
							result = result + rs.getInt(i) + " | ";
						}
						
						if(i==5) {
							result = result + rs.getDouble(i) + " AU | ";
						}
					}
					Planet planet = new Planet(rs.getString(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getDouble(5));
					result = result + "\n-----------------------------------------------\n";
				}
			}
			rs.close();
		}
		return result;
	}
	
}
