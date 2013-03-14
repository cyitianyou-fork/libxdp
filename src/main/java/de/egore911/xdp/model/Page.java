package de.egore911.xdp.model;

import java.util.ArrayList;
import java.util.List;

import de.egore911.xdp.model.util.Dimension;
import de.egore911.xdp.model.util.Position;

public class Page {

	private String name;
	private Form form;
	private final Dimension dimension = new Dimension();
	private final Position position = new Position();
	private List<Field> fields = new ArrayList<Field>();
	private List<Draw> draws = new ArrayList<Draw>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Form getForm() {
		return form;
	}

	public void setForm(Form form) {
		this.form = form;
	}

	public Dimension getDimension() {
		return dimension;
	}

	public Position getPosition() {
		return position;
	}

	public List<Field> getFields() {
		return fields;
	}

	public void setFields(List<Field> fields) {
		this.fields = fields;
	}

	public List<Draw> getDraws() {
		return draws;
	}

	public void setDraws(List<Draw> draws) {
		this.draws = draws;
	}

}
