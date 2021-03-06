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

import de.egore911.xdp.model.Draw;
import de.egore911.xdp.model.Field;
import de.egore911.xdp.model.Margin;
import de.egore911.xdp.util.Constants;

public class MarginParser extends Parser {

	private static final Logger log = Logger.getLogger(MarginParser.class
			.getName());

	private Margin margin;

	public MarginParser(XMLEventReader eventReader, StartElement startEvent,
			Parser parent) throws XMLStreamException {
		super(new QName(Constants.XDP_NAMESPACE, "margin"), parent);

		margin = new Margin();

		if (parent instanceof FieldParser) {
			Field field = ((FieldParser) parent).getField();
			field.setMargin(margin);
		} else {
			Draw draw = ((DrawParser) parent).getDraw();
			draw.setMargin(margin);
		}

		@SuppressWarnings("unchecked")
		Iterator<Attribute> iter = startEvent.getAttributes();
		while (iter.hasNext()) {
			Attribute attribute = iter.next();
			if ("topInset".equals(attribute.getName().getLocalPart())) {
				margin.setTop(attribute.getValue());
			} else if ("rightInset".equals(attribute.getName().getLocalPart())) {
				margin.setRight(attribute.getValue());
			} else if ("leftInset".equals(attribute.getName().getLocalPart())) {
				margin.setLeft(attribute.getValue());
			} else if ("bottomInset".equals(attribute.getName().getLocalPart())) {
				margin.setBottom(attribute.getValue());
			} else {
				log.warning("Attribute '" + attribute.getName().getLocalPart()
						+ "' of 'margin' not handled yet");
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
				log.severe("<"
						+ event.asStartElement().getName().getLocalPart());
			}

			if (event.isEndElement()) {
				if (isTerminatingEvent(event.asEndElement())) {
					break;
				} else {
					log.severe("</"
							+ event.asEndElement().getName().getLocalPart());
				}
			}
		}
	}
}
