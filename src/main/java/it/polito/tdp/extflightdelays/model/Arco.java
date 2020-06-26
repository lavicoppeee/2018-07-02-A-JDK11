package it.polito.tdp.extflightdelays.model;

public class Arco implements Comparable<Arco> {

	Airport A1;
	Airport A2;
	Double peso;
	
	public Arco(Airport a1, Airport a2, Double peso) {
		super();
		A1 = a1;
		A2 = a2;
		this.peso = peso;
	}
	public Airport getA1() {
		return A1;
	}
	public void setA1(Airport a1) {
		A1 = a1;
	}
	public Airport getA2() {
		return A2;
	}
	public void setA2(Airport a2) {
		A2 = a2;
	}
	public Double getPeso() {
		return peso;
	}
	public void setPeso(Double peso) {
		this.peso = peso;
	}
	@Override
	public int compareTo(Arco o) {
		// TODO Auto-generated method stub
		return -(this.peso.compareTo(o.getPeso()));
	}
	@Override
	public String toString() {
		return A2+" "+peso;
	}
	
	
	
}
