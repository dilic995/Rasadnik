package org.unibl.etf.gui.plants.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.unibl.etf.dto.Observer;
import org.unibl.etf.dto.Plant;
import org.unibl.etf.dto.PlantContainer;
import org.unibl.etf.gui.view.base.BaseController;

public abstract class PlantBrowserController extends BaseController implements Observer {

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.container.attachObserver(this);
	}
	
	public boolean addPlant(Plant plant) {
		return container.addPlant(plant);
	}

	public Plant removePlant(Plant plant) {
		return container.remove(plant);
	}
	
	protected PlantContainer container = new PlantContainer();
	protected boolean buildAll = false;
}
