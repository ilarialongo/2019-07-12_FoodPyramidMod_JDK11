package it.polito.tdp.food.model;

import java.util.ArrayList;
import java.util.Collections;
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
			
	}
	
	public List<Food> getFoods(int porzioni) {
		FoodDAO dao= new FoodDAO();
		this.cibi= dao.getFoodByPortions(porzioni);
		this.grafo= new SimpleWeightedGraph<Food, DefaultWeightedEdge>(DefaultWeightedEdge.class);
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
	
	public List<FoodCalories> elencoCibiConnessi(Food f) {
		List <FoodCalories> result= new ArrayList<>();
		List<Food> vicini= Graphs.neighborListOf(this.grafo, f);
		for (Food v: vicini) {
			double calorie= this.grafo.getEdgeWeight(this.grafo.getEdge(f, v));
			result.add(new FoodCalories (v, calorie));	
		}
		Collections.sort(result);
		return result;	
	}
	
	public int vertici() {
		return this.grafo.vertexSet().size();
	}
	
	public int archi() {
		return this.grafo.edgeSet().size();
	}
	
	public String simula(Food cibo, int k) {
		Simulatore sim=new Simulatore(this.grafo, this);
		sim.setK(k);
		sim.init(cibo);
		sim.run();
		String messaggio= String.format("Preparati %d cibi in %f minuti \n", sim.getCibiPreparati(), sim.getTempoPreparazione());
		return messaggio;
	}

}
