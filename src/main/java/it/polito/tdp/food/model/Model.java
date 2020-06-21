package it.polito.tdp.food.model;

import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.food.db.FoodDAO;

public class Model {
	private List<Food> cibi;
	private Graph<Food, DefaultWeightedEdge> grafo;
	
	public Model() {
		this.grafo= new SimpleWeightedGraph<Food, DefaultWeightedEdge>(DefaultWeightedEdge.class);	
	}
	
	public List<Food> getFoods(int porzioni) {
		FoodDAO dao= new FoodDAO();
		this.cibi= dao.getFoodByPortions(porzioni);
		Graphs.addAllVertices(this.grafo, this.cibi);
		for (Food f1: this.cibi) {
			for (Food f2: this.cibi) {
				if (!f1.equals(f2) && f1.getFood_code()<f2.getFood_code()) {
					Double peso= dao.calorieCongiunte (f1,f2);
					if (peso!=null) {
						Graphs.addEdge(this.grafo, f1, f2, peso);
					}
					
				}
				
			}
		}
		System.out.println(this.grafo);
		return this.cibi;
	}

}
