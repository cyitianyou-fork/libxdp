package de.egore911.xdp.parse;

import java.util.Iterator;
import java.util.logging.Logger;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;

import de.egore911.xdp.model.Form;
import de.egore911.xdp.util.Constants;

public class FormParser extends Parser {

	private static final Logger log = Logger.getLogger(FormParser.class
			.getName());

	private final Form form;

	public FormParser(XMLEventReader eventReader, StartElement event,
			Parser parent) {
		super(new QName(Constants.XDP_NAMESPACE, "subform"), parent);
		form = new Form();

		@SuppressWarnings("unchecked")
		Iterator<Attribute> iter = event.getAttributes();
		while (iter.hasNext()) {
			Attribute attribute = iter.next();
			if ("name".equals(attribute.getName().getLocalPart())) {
				form.setName(attribute.getValue());
			} else {
				log.warning("Attribute '" + attribute.getName().getLocalPart()
						+ "' of 'subform' not handled yet");
			}
		}

	}

	public Form getForm() {
		return form;
	}

}
