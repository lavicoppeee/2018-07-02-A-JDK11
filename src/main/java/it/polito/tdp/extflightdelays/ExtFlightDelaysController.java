/**
 * Sample Skeleton for 'ExtFlightDelays.fxml' Controller Class
 */

package it.polito.tdp.extflightdelays;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.extflightdelays.model.Airport;
import it.polito.tdp.extflightdelays.model.Arco;
import it.polito.tdp.extflightdelays.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ExtFlightDelaysController {

	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML // fx:id="distanzaMinima"
    private TextField distanzaMinima; // Value injected by FXMLLoader

    @FXML // fx:id="btnAnalizza"
    private Button btnAnalizza; // Value injected by FXMLLoader

    @FXML // fx:id="cmbBoxAeroportoPartenza"
    private ComboBox<Airport> cmbBoxAeroportoPartenza; // Value injected by FXMLLoader

    @FXML // fx:id="btnAeroportiConnessi"
    private Button btnAeroportiConnessi; // Value injected by FXMLLoader

    @FXML // fx:id="numeroVoliTxtInput"
    private TextField numeroVoliTxtInput; // Value injected by FXMLLoader

    @FXML // fx:id="btnCercaItinerario"
    private Button btnCercaItinerario; // Value injected by FXMLLoader

    @FXML
    void doAnalizzaAeroporti(ActionEvent event) {
    	this.txtResult.clear();
    	
        String xS=this.distanzaMinima.getText();
        
        Integer x;
    	
    	try {
    		x=Integer.parseInt(xS);
    	}catch(NumberFormatException e) {
    		
    		txtResult.setText("Devi inserire solo numeri");
    		return ;
    	}
    	this.model.creaGrafo(x);
    	
    	txtResult.appendText("Grafo Creato!\n");
     	txtResult.appendText("# Vertici: " + model.nVertici()+ "\n");
     	txtResult.appendText("# Archi: " + model.nArchi() + "\n");
     	
     	this.cmbBoxAeroportoPartenza.getItems().addAll(model.vertici());
    }

    @FXML
    void doCalcolaAeroportiConnessi(ActionEvent event) {
    	this.txtResult.clear();
        
    	Airport a=this.cmbBoxAeroportoPartenza.getValue();
    	List<Arco> aC=model.getConnessi(a);
    	txtResult.appendText("Gli aeroporti connessi ad "+a+" sono: \n");
    	
    	for(Arco aa:aC) {
    		txtResult.appendText(aa.getA2()+" "+aa.getPeso()+"\n");
    	}
    	
    }

    @FXML
    void doCercaItinerario(ActionEvent event) {
    	this.txtResult.clear();
    	Airport a=this.cmbBoxAeroportoPartenza.getValue();
    	
    	String xM=this.numeroVoliTxtInput.getText();
    	
        Integer xMax;
    	
    	try {
    		xMax=Integer.parseInt(xM);
    	}catch(NumberFormatException e) {
    		
    		txtResult.setText("Devi inserire solo numeri");
    		return ;
    	}
    	
    	List<Airport> aList=this.model.cammino(xMax, a);
    	
    	txtResult.appendText("Con un numero max di miglia di "+xMax+" da" + a +" si genera un cammino con peso "+model.getBestPeso()+" e possiamo raggiungere: \n");
    	
    	for(Airport aL:aList) {
    		txtResult.appendText(aL.toString()+"\n");
    	}

    	
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert distanzaMinima != null : "fx:id=\"distanzaMinima\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert btnAnalizza != null : "fx:id=\"btnAnalizza\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert cmbBoxAeroportoPartenza != null : "fx:id=\"cmbBoxAeroportoPartenza\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert btnAeroportiConnessi != null : "fx:id=\"btnAeroportiConnessi\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert numeroVoliTxtInput != null : "fx:id=\"numeroVoliTxtInput\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert btnCercaItinerario != null : "fx:id=\"btnCercaItinerario\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";

    }
    
    public void setModel(Model model) {
		this.model = model;
	}
}

