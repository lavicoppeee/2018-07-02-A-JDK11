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
		
	}

	public void creaGrafo(int x) {
		this.graph=new SimpleWeightedGraph<Airport,DefaultWeightedEdge>(DefaultWeightedEdge.class);
		this.archi=new ArrayList<>();
		idMap = new HashMap<>();
		
		dao.getVertici(idMap);
		//Graphs.addAllVertices(graph, idMap.values());
		
		for (Arco a : dao.getArchi(x,idMap)) {
			if (!this.graph.containsEdge(a.getA1(), a.getA2())){
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
		this.bestCammino=new ArrayList<>();
		this.bestCammino.add(partenza);

		List<Airport> parziale=new ArrayList<Airport>();
		parziale.add(partenza);
		
		ricorsione(parziale,0, xMax);
		
		return bestCammino;
	}

	
	
	
	
	private void ricorsione(List<Airport> parziale, double l, double xMax) {
		// TODO Auto-generated method stub
        Airport last = parziale.get(parziale.size()-1);
		
      //caso terminale 
        
		    if(parziale.size() > this.bestCammino.size() || l==xMax || (parziale.size()== this.bestCammino.size() && l> this.bestPeso)) {
			this.bestCammino = new ArrayList<>(parziale);
			this.bestPeso = l;
		}
	
		//ricorsione, aggiorno il peso dell'a
	List<Airport> vicini=Graphs.neighborListOf(this.graph, last);
		
		for(Airport v : vicini) {
			Double peso = this.graph.getEdgeWeight(this.graph.getEdge(last, v));
			if(!parziale.contains(v) && (xMax-l) >= peso) {
				parziale.add(v);
				l += peso;
				this.ricorsione(parziale, l, xMax);
				
				parziale.remove(parziale.size()-1);
				l -= peso;
			}
		}
   
	}

		
	public Double getBestPeso() {
		return bestPeso;
	}


  }


