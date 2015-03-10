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
/**
 * 
 */
package de.egore911.xdp.parse;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.logging.Logger;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

import de.egore911.xdp.model.Form;
import de.egore911.xdp.util.Constants;

/**
 * @author cbrill
 */
public class XdpParse {

	private static final Logger log = Logger
			.getLogger(XdpParse.class.getName());

	public Form parse(String filename) {
		try {
			// zuerst eine neue XMLInputFactory erstellen
			XMLInputFactory inputFactory = XMLInputFactory.newInstance();
			// einen neuen eventReader einrichten
			InputStream in = new FileInputStream(filename);
			XMLEventReader eventReader = inputFactory.createXMLEventReader(in);

			Parser currentParser = null;
			Parser toplevelParser = null;

			// das XML-Dokument lesen
			while (eventReader.hasNext()) {
				XMLEvent event = eventReader.nextEvent();

				if (event.isStartElement()) {
					if (event.asStartElement().getName().getNamespaceURI()
							.equals(Constants.META_NAMESPACE)
							|| event.asStartElement().getName()
									.getNamespaceURI()
									.equals(Constants.RDF_NAMESPACE)
							|| event.asStartElement().getName()
									.getNamespaceURI()
									.equals(Constants.DESC_NAMESPACE)) {
						log.finer("Metadata skipped for now");
					} else if ("subform".equals(event.asStartElement()
							.getName().getLocalPart())) {
						if (currentParser == null) {
							currentParser = new FormParser(eventReader,
									event.asStartElement(), currentParser);
							log.info("+FormParser");
						} else {
							currentParser = new PageParser(eventReader,
									event.asStartElement(), currentParser);
							log.info("+PageParser");
						}
					} else if ("field".equals(event.asStartElement().getName()
							.getLocalPart())) {
						log.info("+FieldParser");
						new FieldParser(eventReader, event.asStartElement(),
								currentParser);
						log.info("-FieldParser");
					} else if ("draw".equals(event.asStartElement().getName()
							.getLocalPart())) {
						log.info("+DrawParser");
						new DrawParser(eventReader, event.asStartElement(),
								currentParser);
						log.info("-DrawParser");
					} else {
						log.severe("Unknown element: <"
								+ event.asStartElement().getName()
										.getLocalPart());
					}
				}

				if (event.isEndElement()) {
					if (event.asEndElement().getName().getNamespaceURI()
							.equals(Constants.META_NAMESPACE)
							|| event.asEndElement().getName().getNamespaceURI()
									.equals(Constants.RDF_NAMESPACE)
							|| event.asEndElement().getName().getNamespaceURI()
									.equals(Constants.DESC_NAMESPACE)) {
						log.finer("Metadata skipped for now");
					} else if (currentParser != null
							&& currentParser.isTerminatingEvent(event
									.asEndElement())) {
						log.info("-" + currentParser.getClass().getSimpleName());
						if (currentParser.isTopParser()) {
							toplevelParser = currentParser;
						}
						currentParser = currentParser.getParent();
					} else {
						log.severe("</"
								+ event.asEndElement().getName().getLocalPart());
					}
				}
			}

			if (toplevelParser instanceof FormParser) {
				return ((FormParser) toplevelParser).getForm();
			}

			throw new RuntimeException("No form found");
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		} catch (XMLStreamException e) {
			throw new RuntimeException(e);
		}
	}

}
