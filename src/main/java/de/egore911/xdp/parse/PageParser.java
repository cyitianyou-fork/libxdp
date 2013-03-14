package de.egore911.xdp.parse;

import java.util.Iterator;
import java.util.logging.Logger;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;

import de.egore911.xdp.model.Form;
import de.egore911.xdp.model.Page;
import de.egore911.xdp.util.Constants;

public class PageParser extends Parser {

	private static final Logger log = Logger.getLogger(PageParser.class
			.getName());

	private final Page page;

	public PageParser(XMLEventReader eventReader, StartElement event,
			Parser parent) {
		super(new QName(Constants.XDP_NAMESPACE, "subform"), parent);
		page = new Page();

		Form form = ((FormParser) parent).getForm();
		form.getPages().add(page);
		page.setForm(form);

		@SuppressWarnings("unchecked")
		Iterator<Attribute> iter = event.getAttributes();
		while (iter.hasNext()) {
			Attribute attribute = iter.next();
			if ("name".equals(attribute.getName().getLocalPart())) {
				page.setName(attribute.getValue());
			} else if ("w".equals(attribute.getName().getLocalPart())) {
				page.getDimension().setWidth(attribute.getValue());
			} else if ("h".equals(attribute.getName().getLocalPart())) {
				page.getDimension().setHeight(attribute.getValue());
			} else if ("x".equals(attribute.getName().getLocalPart())) {
				page.getPosition().setX(attribute.getValue());
			} else if ("y".equals(attribute.getName().getLocalPart())) {
				page.getPosition().setY(attribute.getValue());
			} else {
				log.warning("Attribute '" + attribute.getName().getLocalPart()
						+ "' of 'subform' not handled yet");
			}
		}
	}

	public Page getPage() {
		return page;
	}
}
