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
	
	private List<Airport> airports;
	private List<Arco> archi;
	
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

  }


