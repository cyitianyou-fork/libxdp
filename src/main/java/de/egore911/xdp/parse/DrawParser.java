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
import de.egore911.xdp.model.Page;
import de.egore911.xdp.util.Constants;

public class DrawParser extends Parser {

	private static final Logger log = Logger.getLogger(DrawParser.class
			.getName());

	private Draw draw;

	public DrawParser(XMLEventReader eventReader, StartElement startEvent,
			Parser parent) throws XMLStreamException {
		super(new QName(Constants.XDP_NAMESPACE, "draw"), parent);

		draw = new Draw();

		Page page = ((PageParser) parent).getPage();
		page.getDraws().add(draw);
		draw.setPage(page);

		@SuppressWarnings("unchecked")
		Iterator<Attribute> iter = startEvent.getAttributes();
		while (iter.hasNext()) {
			Attribute attribute = iter.next();
			if ("name".equals(attribute.getName().getLocalPart())) {
				draw.setName(attribute.getValue());
			} else if ("w".equals(attribute.getName().getLocalPart())) {
				draw.getDimension().setWidth(attribute.getValue());
			} else if ("h".equals(attribute.getName().getLocalPart())) {
				draw.getDimension().setHeight(attribute.getValue());
			} else if ("x".equals(attribute.getName().getLocalPart())) {
				draw.getPosition().setX(attribute.getValue());
			} else if ("y".equals(attribute.getName().getLocalPart())) {
				draw.getPosition().setY(attribute.getValue());
			} else {
				log.warning("Attribute '" + attribute.getName().getLocalPart()
						+ "' of 'value' not handled yet");
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
				if ("value".equals(event.asStartElement().getName()
						.getLocalPart())) {
					log.info("+ValueParser");
					new ValueParser(eventReader, event.asStartElement(), this);
					log.info("-ValueParser");
				} else if ("caption".equals(event.asStartElement().getName()
						.getLocalPart())) {
					log.info("+CaptionParser");
					CaptionParser parser = new CaptionParser(eventReader,
							event.asStartElement(), this);
					log.warning("Where to put the caption?");
					log.info("-CaptionParser");
				} else if ("font".equals(event.asStartElement().getName()
						.getLocalPart())) {
					log.info("+FontParser");
					FontParser parser = new FontParser(eventReader,
							event.asStartElement(), this);
					getDraw().setFont(parser.getFont());
					log.info("-FontParser");
				} else if ("border".equals(event.asStartElement().getName()
						.getLocalPart())) {
					log.info("+BorderParser");
					BorderParser parser = new BorderParser(eventReader,
							event.asStartElement(), this);
					log.warning("Where to put the border?");
					log.info("-BorderParser");
				} else if ("ui".equals(event.asStartElement().getName()
						.getLocalPart())) {
					log.info("+UiParser");
					new UiParser(eventReader, event.asStartElement(), this);
					log.info("-UiParser");
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

			if (event.isCharacters()) {
				if (!event.asCharacters().getData().trim().equals("")) {
					log.severe(event.asCharacters().getData().trim());
				}
			}
		}
	}

	public Draw getDraw() {
		return this.draw;
	}
}
