package de.egore911.xdp.util;

import java.io.File;
import java.io.IOException;

import de.egore911.xdp.model.Form;
import de.egore911.xdp.parse.XdpParse;
import de.egore911.xdp.render.HtmlRenderer;

public class HtmlRendererTest {
	public static void main(String[] args) throws IOException {
		HtmlRenderer htmlRenderer = new HtmlRenderer();
		XdpParse parser = new XdpParse();
		Form form = parser.parse(args[0]);
		File dir = new File(System.getProperty("java.io.tmpdir"));
		int pageNumber = 1;
		htmlRenderer.render(form, dir, pageNumber);
	}
}
