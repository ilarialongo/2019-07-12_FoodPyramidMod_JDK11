package it.polito.tdp.food.model;

import java.util.List;

import it.polito.tdp.food.db.FoodDAO;

public class Model {
	private List<Food> cibi;
	public Model() {
		
	}
	
	public List<Food> getFoods(int porzioni) {
		FoodDAO dao= new FoodDAO();
		this.cibi= dao.getFoodByPortions(porzioni);
		return this.cibi;
	}

}
