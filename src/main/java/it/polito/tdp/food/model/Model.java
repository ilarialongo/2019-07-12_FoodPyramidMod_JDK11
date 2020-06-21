package it.polito.tdp.food.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.food.db.FoodDAO;

public class Model {
	private Graph <Food, DefaultWeightedEdge> grafo;
	private Map<Integer, Food> idMap;
	private FoodDAO dao;
	private List<Adiacenza> adiacenze;
	public Model() {
		this.dao=new FoodDAO();
		this.idMap= new HashMap<Integer, Food>();
	}
	public List<String> getFood() {
		List<String> cibi= new ArrayList<>();
		for (Food f: idMap.values()) {
		cibi.add(f.getDisplay_name());	
		}
		return cibi;
	}
	
	public void creaGrafo(int porzioni) {
		this.dao.getCibiSelezionati(porzioni, this.idMap);
		this.grafo= new SimpleWeightedGraph<Food,DefaultWeightedEdge>(DefaultWeightedEdge.class);
		this.adiacenze= this.dao.getAdiacenze();
		for (Food f: idMap.values()) {
			this.grafo.addVertex(f);
		}
		for (Adiacenza a: adiacenze) {
			if(this.grafo.containsVertex(a.getF1()) && this.grafo.containsVertex(a.getF2())) {
				if (this.grafo.getEdge(a.getF1(), a.getF2())==null) {
					Graphs.addEdgeWithVertices(this.grafo, a.getF1(), a.getF2(), a.getPeso());
				}
			}
			
		}
		System.out.println("Grafo creato!");
		System.out.println("#VERTICI: "+ this.grafo.vertexSet().size());
		System.out.println("#ARCHI: "+ this.grafo.edgeSet().size());
	}
	
	public int vertici() {
		return this.grafo.vertexSet().size();
	}
	
	public int archi() {
		return this.grafo.edgeSet().size();
	}

}