package it.polito.tdp.food.model;

public class Evento implements Comparable<Evento>{
	public enum EventType {
		INIZIO_PREPARAZIONE, //viene assegnato un cibo ad una stazione
		FINE_PREPARAZIONE, //la stazione ha completato un cibo
	}
	private EventType type;
	private Double time; // tempo in minuti 
	private Stazione stazione; //stazione dove o inizia o finisce la preparazione
	private Food food;
	
	public EventType getType() {
		return type;
	}
	public Evento(EventType type, Double time, Stazione stazione, Food food) {
		super();
		this.type = type;
		this.time = time;
		this.stazione = stazione;
		this.food = food;
	}
	public Double getTime() {
		return time;
	}
	public Stazione getStazione() {
		return stazione;
	}
	public Food getFood() {
		return food;
	}
	@Override
	public int compareTo(Evento o) {
		return this.time.compareTo(o.time);
	}
	
}
	