package it.polito.tdp.food.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.tdp.food.model.Adiacenza;
import it.polito.tdp.food.model.Condiment;
import it.polito.tdp.food.model.Food;
import it.polito.tdp.food.model.Portion;

public class FoodDAO {
	public List<Food> listAllFoods(){
		String sql = "SELECT * FROM food" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Food> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Food(res.getInt("food_code"),
							res.getString("display_name")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}

	}
	
	public List<Condiment> listAllCondiments(){
		String sql = "SELECT * FROM condiment" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Condiment> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Condiment(res.getInt("condiment_code"),
							res.getString("display_name"),
							res.getDouble("condiment_calories"), 
							res.getDouble("condiment_saturated_fats")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<Portion> listAllPortions(){
		String sql = "SELECT * FROM portion" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Portion> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Portion(res.getInt("portion_id"),
							res.getDouble("portion_amount"),
							res.getString("portion_display_name"), 
							res.getDouble("calories"),
							res.getDouble("saturated_fats"),
							res.getInt("food_code")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}

	}
	
	
	public void getCibiSelezionati(int porzioni, Map<Integer, Food>idMap) {
		String sql="SELECT f.food_code, f.display_name " + 
				"FROM food as f, portion as p " + 
				"WHERE f.food_code=p.food_code " + 
				"GROUP BY f.food_code, f.display_name " + 
				"HAVING COUNT(DISTINCT p.portion_id)<=?";
		Connection conn = DBConnect.getConnection() ;
		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1,porzioni);
			ResultSet res = st.executeQuery();
			while(res.next()) {
				if (!idMap.containsKey(res.getInt("food_code"))) {
					Food cibo= new Food (res.getInt("food_code"), res.getString("display_name"));
					idMap.put(res.getInt("food_code"), cibo);
				}
			}
			conn.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<Adiacenza> getAdiacenze() {
		String sql="SELECT f1.display_name as food1, f1.food_code as code1 , f2.display_name as food2, f2.food_code as code2 , AVG(c.condiment_calories) as peso " + 
				"FROM food as f1, food as f2, food_condiment as f3, food_condiment as f4, condiment as c " + 
				"WHERE f1.food_code=f3.food_code AND f2.food_code=f4.food_code AND f3.condiment_code=f4.condiment_code " + 
				"AND c.condiment_code=f3.condiment_code AND f1.food_code>f2.food_code " + 
				"GROUP BY f1.display_name, f1.food_code, f2.display_name, f2.food_code";
		Connection conn = DBConnect.getConnection() ;
		List<Adiacenza> risultato= new ArrayList<>();
		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while(res.next()) {
				Food f1= new Food(res.getInt("code1"), res.getString("food1"));
				Food f2= new Food(res.getInt("code2"), res.getString("food2"));
				Adiacenza a= new Adiacenza (f1,f2,res.getInt("peso"));
				risultato.add(a);
				}
			conn.close();
			return risultato;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}
}
