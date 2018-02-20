package org.unibl.etf.gui.plants.controller;

import java.util.Iterator;
import java.util.Map;
import java.util.Stack;

import org.unibl.etf.dto.Region;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Polyline;

public class SelectTool extends CanvasEditor {

	public SelectTool() {
	}

	public SelectTool(Map<Polygon, Region> regions, Map<Polygon, Polyline> outlines, DrawRegionsController controller,
			Stack<Command> undoCommands, Stack<Command> redoCommands) {
		super(regions, outlines, undoCommands, redoCommands);
		this.controller = controller;
		for (Iterator<Polygon> it = regions.keySet().iterator(); it.hasNext();) {
			Polygon p = it.next();
			p.setOnMouseClicked(new EventHandler<Event>() {

				@Override
				public void handle(Event event) {
					if (selected != null) {
						selected.getStyleClass().clear();
						selected.getStyleClass().add("transparent-brown");
					}
					selected = p;
					selected.getStyleClass().add("transparent-green");
					Region region = regions.get(p);
					SelectTool.this.controller.displayInfo(region);
					SelectTool.this.controller.updateRegionLabel();
				}

			});
		}
		SelectTool.this.controller.updateRegionLabel();
	}
	
	public Region getSelectedRegion() {
		if(selected != null)
			return regions.get(selected);
		return null;
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

	private Polygon selected = null;
	private DrawRegionsController controller;

	@Override
	public void invalidate() {
		if (selected != null) {
			selected.getStyleClass().clear();
			selected.getStyleClass().add("transparent-brown");
			selected = null;
		}
		controller.clear();
	}

	@Override
	public void undo() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void redo() {
		// TODO Auto-generated method stub
		
	}
}
