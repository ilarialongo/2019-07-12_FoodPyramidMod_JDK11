package it.polito.tdp.food.model;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.food.model.Evento.EventType;
import it.polito.tdp.food.model.Food.StatoPreparazione;

public class Simulatore {
	//modello del mondo
	//le stazioni mi dicono se sono occupate o libere, quale cibo stanno preparando
	
	private List<Stazione> stazioni; 
	private List<Food> cibi;
	private Graph<Food, DefaultWeightedEdge> grafo;
	private Model model;
	
	//parametri di simulazione
	
	private int K=5; //numero stazioni disponibili (metto 5 a caso)
	
	
	//risultati calcolati
	private Double tempoPreparazione;
	private int cibiPreparati;
	
	//coda degli eventi
	private PriorityQueue<Evento> queue;
	
	public Simulatore (Graph<Food, DefaultWeightedEdge> grafo, Model model) {
		this.grafo=grafo;
		this.model=model;
	}
		
	public void init(Food partenza) {
		//Ogni volta che avvio una simulazione ho un cibo di partenza differente
		this.cibi= new ArrayList<>(this.grafo.vertexSet()); //vertici del grafo
		for (Food cibo:this.cibi) {
			cibo.setPreparazione(StatoPreparazione.DA_FARE);
		}
		this.stazioni= new ArrayList<Stazione>();
		this.cibiPreparati=0;
		for (int i=0; i<K; i++) {
			this.stazioni.add(new Stazione(true, null));
		}
		this.tempoPreparazione=0.0;
		this.queue= new PriorityQueue<>();
		List<FoodCalories> vicini= this.model.elencoCibiConnessi(partenza);
		
		for (int i=0; i<K && i<vicini.size(); i++) {
			this.stazioni.get(i).setLibera(false);
			this.stazioni.get(i).setFood(vicini.get(i).getFood());
			Evento e= new Evento(EventType.FINE_PREPARAZIONE, vicini.get(i).getCalories(), this.stazioni.get(i),vicini.get(i).getFood());
			queue.add(e);
		}
	}
		

	
	public void run() {
		while(!queue.isEmpty()) {
			Evento e=queue.poll();
			porcessEvent(e);
		}
		
	}
	

	private void porcessEvent(Evento e) {
		switch(e.getType()) {
		case INIZIO_PREPARAZIONE:
			List<FoodCalories> vicini= this.model.elencoCibiConnessi(e.getFood());
			FoodCalories prossimo=null;
			for (FoodCalories vicino:vicini) {
				if (vicino.getFood().getPreparazione()==StatoPreparazione.DA_FARE) {
					prossimo= vicino;
					break;
				}
			}
 			if (prossimo!=null) {
 				prossimo.getFood().setPreparazione(StatoPreparazione.IN_CORSO);
 				e.getStazione().setLibera(false);
 				e.getStazione().setFood(prossimo.getFood()); //cibo che sta preparando
 				Evento e2= new Evento(EventType.FINE_PREPARAZIONE, e.getTime()+prossimo.getCalories(), e.getStazione(), prossimo.getFood());	
 				this.queue.add(e2);
 			}
			
			break;
		case FINE_PREPARAZIONE: 
			this.cibiPreparati++;
			this.tempoPreparazione=e.getTime();
			e.getStazione().setLibera(true);
			e.getFood().setPreparazione(StatoPreparazione.PREPARATO);
			Evento e2=new Evento(EventType.INIZIO_PREPARAZIONE, e.getTime(), e.getStazione(), e.getFood()); //ultimo cibo che ho preparato
			this.queue.add(e2);
			break;
			
		}
		
	}
	
	

	public int getCibiPreparati() {
		return cibiPreparati;
	}

	public void setCibiPreparati(int cibiPreparati) {
		this.cibiPreparati = cibiPreparati;
	}

	public Double getTempoPreparazione() {
		return tempoPreparazione;
	}

	public int getK() {
		return K;
	}

	public void setK(int k) {
		K = k;
	}
}
