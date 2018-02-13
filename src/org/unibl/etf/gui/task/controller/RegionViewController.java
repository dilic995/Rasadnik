package org.unibl.etf.gui.task.controller;


import java.awt.event.ItemListener;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.stream.Collectors;

import org.controlsfx.control.CheckListView;
import org.unibl.etf.dao.interfaces.DAOFactory;
import org.unibl.etf.dto.Basis;
import org.unibl.etf.dto.Estate;
import org.unibl.etf.dto.MaintenancePlan;
import org.unibl.etf.dto.MaintenancePlanItem;
import org.unibl.etf.dto.Observer;
import org.unibl.etf.dto.Plant;
import org.unibl.etf.dto.PlantMaintanceActivity;
import org.unibl.etf.dto.Region;
import org.unibl.etf.dto.Task;
import org.unibl.etf.dto.TaskListItem;
import org.unibl.etf.gui.view.base.BaseController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class RegionViewController extends BaseController implements Initializable, Observer{
	
	@FXML
	private CheckListView<TaskListItem> clvTasks;
	
	@FXML
    private Label region1;
	private RegionPane testRegion1;

    @FXML
    private Label region2;
    private RegionPane testRegion2;

    @FXML
    private Label region3;
    private RegionPane testRegion3;
    
    MaintenancePlan plan = new MaintenancePlan();
    List<Region> regioni = DAOFactory.getInstance().getRegionDAO().selectAll();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initializeTaskList();
		testRegion1 = new RegionPane(region1);
		testRegion2 = new RegionPane(region2);
		testRegion3 = new RegionPane(region3);
		testRegion1.attachObserver(this);
		testRegion2.attachObserver(this);
		testRegion3.attachObserver(this);
		
		List<Plant> plants = DAOFactory.getInstance().getPlantDAO().selectAll();
		List<Basis> bases = DAOFactory.getInstance().getBasisDAO().getByActive(true);
		List<Region> regions = new ArrayList<Region>();
		for(Basis b : bases) {
			Region region = new Region(null, 0, b, b.getBasisId());
			regions.add(region);
		}
		Estate estate = new Estate(regions);
		estate.addPlants(estate.getRegions().get(0), 10);
		estate.addPlants(estate.getRegions().get(1), 100);
		estate.addPlants(estate.getRegions().get(2), 50);
		
		List<PlantMaintanceActivity> aktivnosti = DAOFactory.getInstance().getPlantMaintanceActivityDAO().selectAll();
		
		plan.setActive(true);
		plan.setDateFrom(LocalDate.now());
		plan.setDateTo(LocalDate.of(2018, 2, 15));
		HashMap<Region, MaintenancePlanItem> map = new HashMap<Region, MaintenancePlanItem>();
		for(Region r : regioni) {
			List<Task> tasks = DAOFactory.getInstance().getTaskDAO().getByRegionId(r.getRegionId());
			HashSet<Task> set =  new HashSet<>(tasks);
			map.put(r, new MaintenancePlanItem(r,set));
		}
		plan.setPlannedTasks(map);
		
		testRegion1.setItem(map.get(regioni.get(0)));
		testRegion2.setItem(map.get(regioni.get(1)));
		testRegion3.setItem(map.get(regioni.get(2)));
		
	}
	
	@FXML
    void region1Clicked(MouseEvent event) {
		populateTaskList(testRegion1);
		currentSelectedRegion = testRegion1;
    }
	
	@FXML
    void region2Clicked(MouseEvent event) {
		populateTaskList(testRegion2);
		currentSelectedRegion = testRegion2;
    }
	
	@FXML
    void region3Clicked(MouseEvent event) {
		populateTaskList(testRegion3);
		currentSelectedRegion = testRegion3;
    }
	
	@FXML
    void promijeni(ActionEvent event) {
		plan.setDone(regioni.get(0), DAOFactory.getInstance().getTaskDAO().getByPrimaryKey(1), true);
		testRegion1.update();
    }
	
	public void saveTasksState() {
		System.out.println(clvTasks.getItems());
	}
	
	public void cancelTasksState() {
		System.out.println(taskList);
	}
	
	public void initializeTaskList() {
		taskList = FXCollections.observableArrayList();
		clvTasks.setItems(taskList);
	}
	
	public void populateTaskList(RegionPane region) {
		List<Task> list = new ArrayList<>(region.getTasks());
		taskList = FXCollections.observableArrayList(TaskListItem.convert(list));
		clvTasks.setItems(taskList);
		taskList.stream().forEach(x -> {
			if(x.getDone())
				clvTasks.checkModelProperty().get().check(x);
		});
		clvTasks.refresh();
	}
	
	public List<TaskListItem> pullDataTasks(int regionID) {
		List<TaskListItem> retVal = new ArrayList<>();
		List<Task> tmpList = DAOFactory.getInstance().getTaskDAO().getByRegionId(regionID);
		tmpList = tmpList.stream().filter(x -> !x.getIsDeleted().booleanValue()).collect(Collectors.toList());
		retVal = TaskListItem.convert(tmpList);
		return retVal;
	}
	
	ObservableList<TaskListItem> taskList;
	RegionPane currentSelectedRegion = testRegion1;

	@Override
	public void update() {
		populateTaskList(currentSelectedRegion);
	}

}
