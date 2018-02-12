package org.unibl.etf.gui.plants.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.unibl.etf.dao.interfaces.DAOException;
import org.unibl.etf.dao.interfaces.DAOFactory;
import org.unibl.etf.dto.Observer;
import org.unibl.etf.dto.PlantContainer;
import org.unibl.etf.gui.view.base.BaseController;

import com.sun.corba.se.spi.orbutil.fsm.Action;

import javafx.beans.value.ObservableStringValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.Button;

import javafx.scene.control.Label;

import javafx.scene.control.TextArea;

public class TestController extends BaseController implements Observer{
	@FXML
	private Label lblTitle;
	@FXML
	private Button btnPrev;
	@FXML
	private Button btnNext;
	@FXML
	private TextArea taDesc;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		container = new PlantContainer(DAOFactory.getInstance().getPlantDAO().selectAll());
		container.attachObserver(this);
		lblTitle.setText(container.current().getScientificName());
		taDesc.setText(container.current().getDescription());
	}
	@FXML
	public void showNext(ActionEvent event) {
		container.next();
	}
	@FXML
	public void showPrev(ActionEvent event) {
		container.previous();
	}
	
	private PlantContainer container;
	@Override
	public void update() {
		lblTitle.setText(container.current().getPlantId() + ". " + container.current().getScientificName());
		taDesc.setText(container.current().getDescription());
	}
}
