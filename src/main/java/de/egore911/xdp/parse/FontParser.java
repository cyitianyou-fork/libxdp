/*
 * Copyright (c) 2013 Christoph Brill
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package de.egore911.xdp.parse;

import java.util.Iterator;
import java.util.logging.Logger;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import de.egore911.xdp.model.Font;
import de.egore911.xdp.util.Constants;

public class FontParser extends Parser {

	private static final Logger log = Logger.getLogger(FontParser.class
			.getName());

	private Font font;

	public FontParser(XMLEventReader eventReader, StartElement startEvent,
			Parser parent) throws XMLStreamException {
		super(new QName(Constants.XDP_NAMESPACE, "font"), parent);

		font = new Font();

		@SuppressWarnings("unchecked")
		Iterator<Attribute> iter = startEvent.getAttributes();
		while (iter.hasNext()) {
			Attribute attribute = iter.next();
			if ("typeface".equals(attribute.getName().getLocalPart())) {
				font.setFamily(attribute.getValue());
			} else if ("size".equals(attribute.getName().getLocalPart())) {
				font.setSize(attribute.getValue());
			} else if ("weight".equals(attribute.getName().getLocalPart())) {
				font.setWeight(attribute.getValue());
			} else if ("underline".equals(attribute.getName().getLocalPart())) {
				if ("1".equals(attribute.getValue().trim())) {
					font.setUnderline(true);
				} else {
					log.warning("Attribute '"
							+ attribute.getName().getLocalPart()
							+ "' with vakue='" + attribute.getValue()
							+ "' of 'font' not handled yet");
				}
			} else {
				log.warning("Attribute '" + attribute.getName().getLocalPart()
						+ "' of 'font' not handled yet");
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
				if ("fill".equals(event.asStartElement().getName()
						.getLocalPart())) {
					log.info("+FillParser");
					FillParser fillParser = new FillParser(eventReader,
							event.asStartElement(), this);
					font.setFill(fillParser.getFill());
					log.info("-FillParser");
				} else {

					log.severe("<"
							+ event.asStartElement().getName().getLocalPart());
				}
			}

			if (event.isEndElement()) {
				if (isTerminatingEvent(event.asEndElement())) {
					break;
				}
				log.severe("</" + event.asEndElement().getName().getLocalPart());
			}
		}
	}

	public Font getFont() {
		return font;
	}
}
