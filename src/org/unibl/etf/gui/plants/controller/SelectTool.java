package org.unibl.etf.gui.plants.controller;

import java.util.Iterator;
import java.util.Map;

import org.unibl.etf.dto.Region;
import org.unibl.etf.gui.util.DisplayUtil;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Polyline;

public class SelectTool extends CanvasEditor {

	public SelectTool() {
	}

	public SelectTool(Map<Polygon, Region> regions, Map<Polygon, Polyline> outlines, DrawRegionsController controller) {
		super(regions, outlines);
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
				}

			});
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
}
