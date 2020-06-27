package it.polito.tdp.extflightdelays.model;

import java.util.*;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.extflightdelays.db.ExtFlightDelaysDAO;

public class Model {
	
	private ExtFlightDelaysDAO dao;
	
	private Graph<Airport,DefaultWeightedEdge> graph;
	private Map<Integer,Airport> idMap;
	
	private List<Arco> archi;
	
	private Double bestPeso;
	private List<Airport> bestCammino;
	
	public Model() {
		dao=new ExtFlightDelaysDAO();
		idMap = new HashMap<>();
	}

	public void creaGrafo(int x) {
		this.graph=new SimpleWeightedGraph<Airport,DefaultWeightedEdge>(DefaultWeightedEdge.class);
		this.archi=new ArrayList<>();
		
		dao.getVertici(x,idMap);
		Graphs.addAllVertices(graph, idMap.values());
		
		
		for (Arco a : dao.getArchi(x,idMap)) {
			if (this.graph.vertexSet().contains(a.getA1()) && this.graph.vertexSet().contains(a.getA2())) {
				Graphs.addEdgeWithVertices(graph, a.getA1(), a.getA2(), a.getPeso());
				archi.add(new Arco(a.getA1(),a.getA2(),a.getPeso()));
			}
		}
		
	}
	
	public Collection<Airport> vertici(){
		return idMap.values();
	}
	public int nVertici() {
			return this.graph.vertexSet().size();
		}

		public int nArchi() {
			return this.graph.edgeSet().size();
		}
	
	public List<Arco> getConnessi(Airport a){
			
			List<Arco> archiConnessi=new ArrayList<>();
			
			List<Airport> vicini=Graphs.neighborListOf(this.graph, a);
			
			for(Airport v: vicini) {
				double peso=this.graph.getEdgeWeight(this.graph.getEdge(a, v));
					Arco anew=new Arco(a, v,peso);
					archiConnessi.add(anew);
				
				
			}
			
			
			Collections.sort(archiConnessi);
			return archiConnessi;
			
		}
	
	public List<Airport> cammino (int xMax,Airport partenza) {
		this.bestPeso=0.0;
		this.bestCammino=null;
		
		List<Airport> parziale=new ArrayList<Airport>();
		parziale.add(partenza);
		
		ricorsione(parziale,xMax);
		
		return bestCammino;
	}

	private void ricorsione(List<Airport> parziale, int xMax) {
		
		//caso terminale 
		   //se il peso degli archi è pari al peso delle miglia massime
			double peso=this.calcolaPeso(parziale);
			if(peso>xMax) {
				this.bestPeso=peso;
				this.bestCammino=new ArrayList<>(parziale);
				return;
			}
			
		
		List<Airport> vicini=Graphs.neighborListOf(graph, parziale.get(0));
		for(Airport v:vicini) {
			if(!parziale.contains(v)) {
				parziale.add(v);
				ricorsione(parziale,xMax);
				parziale.remove(parziale.size()-1);
			}
		}
		
	
	}
	
	
	public Double getBestPeso() {
		return bestPeso;
	}

	private double calcolaPeso(List<Airport> parziale) {
		double peso=0.0;
		
		for(int i=1; i<parziale.size();i++) {
			if(this.graph.getEdge(parziale.get(i), parziale.get(i-1)) != null) {
				
			Double pNew=this.graph.getEdgeWeight(this.graph.getEdge(parziale.get(i-1),parziale.get(i)));
			peso+=pNew;
		}
	}
		
		return peso;
	}

  }


