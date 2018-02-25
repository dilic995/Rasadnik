package org.unibl.etf.gui.plants.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.unibl.etf.dto.Plan;
import org.unibl.etf.dto.Region;
import org.unibl.etf.dto.Task;
import org.unibl.etf.dto.TaskTableItem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Polygon;

public class EmptyTool extends CanvasEditor {

	private TableView<TaskTableItem> tblTasks;
	private Plan plan;

	public EmptyTool(Map<Polygon, Region> regionsMap, TableView<TaskTableItem> tblTasks, Plan plan) {
		super(regionsMap, null, null, null);
		this.tblTasks = tblTasks;
		this.plan = plan;
		
		Set<Region> setRegiona = plan.getTasks().keySet();
		for (Region r : setRegiona) {
			for (Polygon p : regionsMap.keySet()) {
				if (r.equals(regionsMap.get(p))) {
					p.getStyleClass().clear();
					p.getStyleClass().add(plan.getTasks().get(r).getState().getStyle());
					break;
				}
			}
		}

		for (Polygon p : regionsMap.keySet()) {
			if (plan.getTasks().keySet().contains(regionsMap.get(p))) {
				p.setOnMouseClicked(new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent event) {
						Region reg = regionsMap.get(p);
						List<Task> listaTaskova = new ArrayList<Task>();
						listaTaskova.addAll(plan.getTasks().get(reg).getPlannedTasks());
						ObservableList<TaskTableItem> items = FXCollections
								.observableArrayList(TaskTableItem.convert(listaTaskova));
						 tblTasks.setItems(items);
						 DrawRegionsController.setListaTaskova(items);
					}
				});
			} else {
				p.setDisable(true);
				p.getStyleClass().clear();
				p.getStyleClass().add("transparent-darkbrown");
			}
		}
	}

	@Override
	public void click(MouseEvent event) {
	}

	@Override
	public void startDrawing(MouseEvent event) {
	}

	@Override
	public void finishDrawing(MouseEvent event) {
	}

	@Override
	public void undo() {
	}

	@Override
	public void redo() {
	}

	@Override
	public void invalidate() {
	}

}
