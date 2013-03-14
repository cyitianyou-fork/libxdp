package de.egore911.xdp.parse;

import java.awt.Color;
import java.util.Iterator;
import java.util.logging.Logger;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import de.egore911.xdp.model.Fill;
import de.egore911.xdp.util.Constants;

public class FillParser extends Parser {

	private static final Logger log = Logger.getLogger(FillParser.class
			.getName());

	private Fill fill;

	public FillParser(XMLEventReader eventReader, StartElement startEvent,
			Parser parent) throws XMLStreamException {
		super(new QName(Constants.XDP_NAMESPACE, "fill"), parent);

		fill = new Fill();

		// Workaround: Ein leeres Fill scheint nur weiß zu füllen
		fill.setColor(Color.white);

		@SuppressWarnings("unchecked")
		Iterator<Attribute> iter = startEvent.getAttributes();
		while (iter.hasNext()) {
			Attribute attribute = iter.next();

			if ("presence".equals(attribute.getName().getLocalPart())) {
				if ("hidden".equals(attribute.getValue())) {
					fill.setVisible(false);
				} else {
					log.warning("Attribute '"
							+ attribute.getName().getLocalPart()
							+ "' with value '" + attribute.getValue()
							+ "' of '" + startEvent.getName().getLocalPart()
							+ "' not handled yet");
				}
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
				if ("color".equals(event.asStartElement().getName()
						.getLocalPart())) {

					Color color = null;

					@SuppressWarnings("unchecked")
					Iterator<Attribute> iterator = event.asStartElement()
							.getAttributes();
					while (iterator.hasNext()) {
						Attribute attribute = iterator.next();
						if ("value".equals(attribute.getName().getLocalPart())) {

							String value = attribute.getValue();
							if (value.contains(",")) {
								String[] colorvalues = value.split(",");

								if (colorvalues.length == 3) {
									if (colorvalues[0].contains(".")) {
										color = new Color(
												Float.parseFloat(colorvalues[0]
														.trim()),
												Float.parseFloat(colorvalues[1]
														.trim()),
												Float.parseFloat(colorvalues[2]
														.trim()));
									} else {
										color = new Color(
												Integer.parseInt(colorvalues[0]
														.trim()),
												Integer.parseInt(colorvalues[1]
														.trim()),
												Integer.parseInt(colorvalues[2]
														.trim()));
									}
								} else if (colorvalues.length == 4) {
									if (colorvalues[0].contains(".")) {
										color = new Color(
												Float.parseFloat(colorvalues[0]
														.trim()),
												Float.parseFloat(colorvalues[1]
														.trim()),
												Float.parseFloat(colorvalues[2]
														.trim()),
												Float.parseFloat(colorvalues[3]
														.trim()));
									} else {
										color = new Color(
												Integer.parseInt(colorvalues[0]
														.trim()),
												Integer.parseInt(colorvalues[1]
														.trim()),
												Integer.parseInt(colorvalues[2]
														.trim()),
												Integer.parseInt(colorvalues[3]
														.trim()));
									}
								} else {
									log.severe("Color value " + value
											+ " not understood");
								}
							} else {
								log.severe("Color value " + value
										+ " not understood");
							}

						} else {
							log.warning("Attribute '"
									+ attribute.getName().getLocalPart()
									+ "' of '"
									+ event.asStartElement().getName()
											.getLocalPart()
									+ "' not handled yet");
						}
					}

					fill.setColor(color);

				} else {
					log.severe("<"
							+ event.asStartElement().getName().getLocalPart());
				}
			}

			if (event.isEndElement()) {
				if ("color".equals(event.asEndElement().getName()
						.getLocalPart())) {
				} else if (isTerminatingEvent(event.asEndElement())) {
					break;
				} else {
					log.severe("</"
							+ event.asEndElement().getName().getLocalPart());
				}
			}

			if (event.isCharacters()) {
				if (!event.asCharacters().getData().trim().equals("")) {
					log.severe(event.asCharacters().getData().trim());
				}
			}
		}

	}

	public Fill getFill() {
		return fill;
	}
}
