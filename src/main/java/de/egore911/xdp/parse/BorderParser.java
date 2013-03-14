package de.egore911.xdp.parse;

import java.util.Iterator;
import java.util.logging.Logger;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import de.egore911.xdp.model.Border;
import de.egore911.xdp.util.Constants;

public class BorderParser extends Parser {

	private static final Logger log = Logger.getLogger(BorderParser.class
			.getName());

	private Border border;

	public BorderParser(XMLEventReader eventReader, StartElement startEvent,
			Parser parent) throws XMLStreamException {
		super(new QName(Constants.XDP_NAMESPACE, "border"), parent);

		border = new Border();

		@SuppressWarnings("unchecked")
		Iterator<Attribute> iter = startEvent.getAttributes();
		while (iter.hasNext()) {
			Attribute attribute = iter.next();
			if ("hidden".equals(attribute.getValue())) {
				border.setVisible(false);
			} else if ("hand".equals(attribute.getName().getLocalPart())) {
				// TODO disabled because of log spamming
			} else {
				log.warning("Attribute '" + attribute.getName().getLocalPart()
						+ "' of '" + startEvent.getName().getLocalPart()
						+ "' not handled yet");
			}
		}

		if (startEvent.isEndElement()) {
			if (isTerminatingEvent(startEvent.asEndElement())) {
				return;
			}
		}

		while (eventReader.hasNext()) {
			XMLEvent event = eventReader.nextEvent();

			if (event.isStartElement()) {
				if ("edge".equals(event.asStartElement().getName()
						.getLocalPart())) {
					log.info("+EdgeParser");
					EdgeParser edgeParser = new EdgeParser(eventReader,
							event.asStartElement(), this);
					border.setEdge(edgeParser.getEdge());
					log.info("-EdgeParser");
				} else if ("fill".equals(event.asStartElement().getName()
						.getLocalPart())) {
					log.info("+FillParser");
					FillParser fillParser = new FillParser(eventReader,
							event.asStartElement(), this);
					border.setFill(fillParser.getFill());
					log.info("-FillParser");
				} else {
					log.severe("<"
							+ event.asStartElement().getName().getLocalPart());
				}
			}

			if (event.isEndElement()) {
				if ("edge"
						.equals(event.asEndElement().getName().getLocalPart())) {
				} else if (isTerminatingEvent(event.asEndElement())) {
					break;
				} else {
					log.severe("</"
							+ event.asEndElement().getName().getLocalPart());
				}
			}
		}
	}

	public Border getBorder() {
		return border;
	}

}
