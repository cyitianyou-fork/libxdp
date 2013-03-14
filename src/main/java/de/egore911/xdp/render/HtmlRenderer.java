package de.egore911.xdp.render;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Logger;

import de.egore911.xdp.model.Border;
import de.egore911.xdp.model.Draw;
import de.egore911.xdp.model.Edge;
import de.egore911.xdp.model.Field;
import de.egore911.xdp.model.Fill;
import de.egore911.xdp.model.Font;
import de.egore911.xdp.model.Form;
import de.egore911.xdp.model.Margin;
import de.egore911.xdp.model.Page;
import de.egore911.xdp.model.ui.ButtonElement;
import de.egore911.xdp.model.ui.CheckboxElement;
import de.egore911.xdp.model.ui.TextElement;
import de.egore911.xdp.model.util.Dimension;
import de.egore911.xdp.model.util.Position;
import de.egore911.xdp.model.values.HtmlValue;
import de.egore911.xdp.model.values.ImageValue;
import de.egore911.xdp.model.values.LineValue;
import de.egore911.xdp.model.values.RectangleValue;
import de.egore911.xdp.model.values.TextValue;

public class HtmlRenderer {

	private static final Logger log = Logger.getLogger(HtmlRenderer.class
			.getName());

	public void render(Form form, File dir, int pageNumber) throws IOException {

		FileOutputStream fos = new FileOutputStream(new File(dir,
				form.getName() + ".html"));

		int imageCounter = 1;

		try {

			writeln(fos, "<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			writeln(fos, "<!DOCTYPE html>");
			writeln(fos, "<html>");
			writeln(fos, " <head>");
			writeln(fos,
					"  <meta content=\"text/html; charset=UTF-8\" http-equiv=\"Content-Type\" />");
			writeln(fos, "  <title>" + form.getName() + "</title>");
			writeln(fos,
					"  <style type=\"text/css\">input { padding: 0; } p { margin: 0; }</style>");
			writeln(fos, " </head>");
			writeln(fos, " <body style=\"font-size: 10pt;\">");

			Page page = form.getPages().get(pageNumber - 1);

			writeln(fos,
					"  <div style=\"border: 1px solid #919191; -moz-box-shadow: 3px 3px 10px gray; -webkit-box-shadow: 3px 3px 10px gray; box-shadow: 3px 3px 10px gray;"
							+ addDimension(page.getDimension())
							+ addPosition(page.getPosition()) + "\">");

			for (Draw d : page.getDraws()) {

				if (d.getValue() instanceof RectangleValue) {
					renderRectangleValue(fos, d);
				} else if (d.getValue() instanceof LineValue) {
					renderLineValue(fos, d);
				} else if (d.getValue() instanceof ImageValue) {
					imageCounter = renderImageValue(form, dir, fos,
							imageCounter, d);
				} else if (d.getValue() instanceof HtmlValue) {
					renderHtmlOrTextValue(fos, d);
				} else if (d.getValue() instanceof TextValue) {
					renderHtmlOrTextValue(fos, d);
				} else {
					throw new UnsupportedOperationException("Can't handle "
							+ d.getValue().getClass().getSimpleName()
							+ " as value for draw");
				}

			}

			for (Field f : page.getFields()) {
				if (f.getUi() instanceof TextElement) {
					renderTextElement(fos, f);
				} else if (f.getUi() instanceof ButtonElement) {
					renderButtonElement(fos, f);
				} else if (f.getUi() instanceof CheckboxElement) {
					renderCheckboxElement(fos, f);
				} else {
					if (f.getValue() instanceof RectangleValue) {
						renderRectangleValue(fos, f);
					} else if (f.getValue() instanceof LineValue) {
						renderLineValue(fos, f);
					} else if (f.getValue() instanceof ImageValue) {
						imageCounter = renderImageValue(form, dir, fos,
								imageCounter, f);
					} else if (f.getValue() instanceof HtmlValue) {
						renderHtmlOrTextValue(fos, f);
					} else if (f.getValue() instanceof TextValue) {
						renderHtmlOrTextValue(fos, f);
					} else {
						throw new UnsupportedOperationException("Can't handle "
								+ f.getUi().getClass().getSimpleName()
								+ " as value for field " + f.getName());
					}
				}

			}

			writeln(fos, "  </div>");

			writeln(fos, " </body>");
			writeln(fos, "</html>");

		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					// Don't care
				}
			}
		}
	}

	private void renderHtmlOrTextValue(FileOutputStream fos, Draw d)
			throws IOException {
		write(fos, "  <div id=\"");
		write(fos, d.getName());
		write(fos, "\" style=\"");
		write(fos, addDimension(d.getDimension()));
		write(fos, addPosition(d.getPosition()));
		write(fos, addFont(d.getFont()));
		write(fos, addMargin(d.getMargin()));
		write(fos, "\">");
		if (d.getValue() instanceof TextValue) {
			if (((TextValue) d.getValue()).getText() != null) {
				write(fos, nl2br(((TextValue) d.getValue()).getText()));
			}
		} else if (d.getValue() instanceof HtmlValue) {
			if (((HtmlValue) d.getValue()).getHtmlContent() != null) {
				write(fos, ((HtmlValue) d.getValue()).getHtmlContent());
			}
		} else {
			throw new UnsupportedOperationException("Can't handle "
					+ d.getValue().getClass().getSimpleName()
					+ " as value for draw");
		}
		writeln(fos, "</div>");
	}

	private int renderImageValue(Form form, File dir, FileOutputStream fos,
			int imageCounter, Draw d) throws FileNotFoundException, IOException {
		ImageValue image = (ImageValue) d.getValue();

		write(fos, "<img id=\"");
		write(fos, d.getName());
		write(fos, "\" src=\"");

		if (image.getData() != null && image.getData().length > 0) {
			String fileName = form.getName() + (imageCounter++);
			if (((ImageValue) d.getValue()).getMimeType().equals("image/png")) {
				fileName += ".png";
			} else {
				log.severe("MimeType "
						+ ((ImageValue) d.getValue()).getMimeType()
						+ " of image unknown");
			}
			FileOutputStream imageFos = new FileOutputStream(new File(dir,
					fileName));
			imageFos.write(((ImageValue) d.getValue()).getData());
			imageFos.close();

			write(fos, fileName);
		} else {
			write(fos, image.getSrc());
		}

		write(fos, "\" style=\"");
		write(fos, addDimension(d.getDimension()));
		write(fos, addPosition(d.getPosition()));
		write(fos, addMargin(d.getMargin()));
		write(fos, "\"/>");
		log.severe("ImageValue not handled yet");
		return imageCounter;
	}

	private void renderLineValue(FileOutputStream fos, Draw d)
			throws IOException {
		write(fos, "  <div id=\"");
		write(fos, d.getName());
		write(fos, "\" style=\"");

		Border border = ((LineValue) d.getValue()).getBorder();
		if (border != null && border.getEdge() != null
				&& Math.abs(border.getEdge().getWidth()) > 0.001) {
			if (d.getDimension().getHeight() > 0.001
					&& d.getDimension().getWidth() > 0.001) {
				throw new RuntimeException("Line has witdh and height");
			} else if (d.getDimension().getHeight() > 0.001) {
				write(fos, "border-right: ");
			} else if (d.getDimension().getWidth() > 0.001) {
				write(fos, "border-top: ");
			} else {
				throw new RuntimeException("Line has no witdh and no height");
			}
			write(fos, Double.toString(border.getEdge().getWidthPx()));
			write(fos, "px solid ");
			write(fos, colorToRgb(border.getEdge().getColor()));
			write(fos, ";");
		} else {
			log.severe("Can't hanlde border of " + d);
		}

		write(fos, addDimension(d.getDimension()));
		write(fos, addPosition(d.getPosition()));
		write(fos, addFont(d.getFont()));
		write(fos, addMargin(d.getMargin()));
		write(fos, "\">");
		writeln(fos, "</div>");
	}

	private void renderRectangleValue(FileOutputStream fos, Draw d)
			throws IOException {
		write(fos, "  <div id=\"");
		write(fos, d.getName());
		write(fos, "\" style=\"");
		write(fos, addDimension(d.getDimension()));
		write(fos, addPosition(d.getPosition()));
		write(fos, addFont(d.getFont()));
		write(fos, addMargin(d.getMargin()));
		write(fos, addFill(((RectangleValue) d.getValue()).getFill()));
		write(fos, addBorder(((RectangleValue) d.getValue()).getBorder()));
		write(fos, "\">");
		writeln(fos, "</div>");
	}

	private void renderButtonElement(FileOutputStream fos, Field f)
			throws IOException {
		write(fos, "  <input id=\"");
		write(fos, f.getName());
		write(fos, "\" type=\"submit\" name=\"");
		write(fos, f.getName());
		write(fos, "\" style=\"");
		write(fos, addDimension(f.getDimension()));
		write(fos, addPosition(f.getPosition()));
		write(fos, addFont(f.getCaption().getFont() == null ? f.getFont() : f
				.getCaption().getFont()));
		write(fos, addMargin(f.getMargin()));
		write(fos, addBorder(f.getBorder()));
		write(fos, addVisible(f.isVisible()) + "\"");
		if (f.getCaption().getValue() instanceof TextValue
				&& ((TextValue) f.getCaption().getValue()).getText() != null) {
			write(fos, " value=\"");
			write(fos, ((TextValue) f.getCaption().getValue()).getText());
			write(fos, "\"");
		}
		if (!f.isEditable()) {
			write(fos, " disabled=\"disabled\"");
		}
		writeln(fos, " />");
	}

	private void renderCheckboxElement(FileOutputStream fos, Field f)
			throws IOException {
		write(fos, "  <input id=\"");
		write(fos, f.getName());
		write(fos, "\" type=\"checkbox\" name=\"");
		write(fos, f.getName());
		write(fos, "\" style=\"");
		write(fos, addDimension(f.getDimension()));
		write(fos, addPosition(f.getPosition()));
		write(fos, addFont(f.getCaption().getFont() == null ? f.getFont() : f
				.getCaption().getFont()));
		write(fos, addMargin(f.getMargin()));
		write(fos, addBorder(f.getBorder()));
		write(fos, addVisible(f.isVisible()) + "\"");
		if (f.getCaption().getValue() instanceof TextValue
				&& ((TextValue) f.getCaption().getValue()).getText() != null) {
			write(fos, " value=\"");
			write(fos, ((TextValue) f.getCaption().getValue()).getText());
			write(fos, "\"");
		}
		if (!f.isEditable()) {
			write(fos, " disabled=\"disabled\"");
		}
		writeln(fos, " />");
	}

	private void renderTextElement(FileOutputStream fos, Field f)
			throws IOException {

		if (f.getCaption() != null) {
			write(fos, "  <div style=\"");
			write(fos, addDimension(f.getDimension()));
			write(fos, addPosition(f.getPosition()));
			writeln(fos, "\">");
			if (f.getCaption().getValue() != null) {
				write(fos, "  <label for=\"");
				write(fos, f.getName());
				write(fos, "\" style=\"");
				write(fos,
						addFont(f.getCaption().getFont() == null ? f.getFont()
								: f.getCaption().getFont()));
				write(fos, "\">");
				if (f.getCaption().getValue() instanceof TextValue) {
					if (((TextValue) f.getCaption().getValue()).getText() != null) {
						write(fos,
								((TextValue) f.getCaption().getValue())
										.getText());
					}
				} else {
					write(fos,
							((HtmlValue) f.getCaption().getValue())
									.getHtmlContent());
				}
				writeln(fos, "</label>");
			}
		}

		if (((TextElement) f.getUi()).isMultiline()) {

			write(fos, "  <textarea id=\"");
			write(fos, f.getName());
			write(fos, "\" name=\"");
			write(fos, f.getName());
			write(fos, "\" style=\"");
			if (f.getCaption() == null) {
				write(fos, addDimension(f.getDimension()));
				write(fos, addPosition(f.getPosition()));
			} else {
				double offsetLeft = f.getCaption().getReservePx();
				if (Math.abs(offsetLeft) > 0.001) {
					write(fos, "position:absolute; left: " + offsetLeft + "px;");
				}
				write(fos, "top:0;");
				double height = f.getDimension().getHeight()
						- f.getMargin().getTop() - f.getMargin().getBottom();
				write(fos, "height:" + height + "mm;");
				double width = f.getDimension().getWidth()
						- f.getMargin().getLeft() - f.getMargin().getRight()
						- offsetLeft;
				write(fos, "width:" + width + "mm;");
			}

			write(fos, addFont(f.getFont()));
			write(fos, addMargin(f.getMargin()));
			write(fos, addBorder(f.getBorder()));
			write(fos, addVisible(f.isVisible()));
			write(fos, "background-color: transparent;"); // XXX guess
			write(fos, "\"");
			if (((TextValue) f.getValue()).getMaxLength() > 0) {
				write(fos,
						" maxlength=\""
								+ ((TextValue) f.getValue()).getMaxLength()
								+ "\"");
			}
			if (!f.isEditable()) {
				write(fos, " disabled=\"disabled\"");
			}
			if (f.isReadOnly()) {
				write(fos, " readonly=\"readonly\"");
			}
			write(fos, ">");
			if (((TextValue) f.getValue()).getText() != null) {
				write(fos, ((TextValue) f.getValue()).getText());
			}
			writeln(fos, "</textarea>");
		} else {
			write(fos, "  <input id=\"");
			write(fos, f.getName());
			write(fos, "\" type=\"text\" name=\"");
			write(fos, f.getName());
			write(fos, "\" style=\"");
			if (f.getCaption() == null) {
				write(fos, addDimension(f.getDimension()));
				write(fos, addPosition(f.getPosition()));
			} else {
				double offsetLeft = f.getCaption().getReserve();
				if (Math.abs(offsetLeft) > 0.001) {
					write(fos, "position:absolute; left: "
							+ f.getCaption().getReservePx() + "px;");
				}
				write(fos, "top:0;");
				double height = f.getDimension().getHeight()
						- f.getMargin().getTop() - f.getMargin().getBottom();
				write(fos, "height:" + height + "mm;");
				double width = f.getDimension().getWidth()
						- f.getMargin().getLeft() - f.getMargin().getRight()
						- offsetLeft;
				write(fos, "width:" + width + "mm;");
			}
			write(fos, addFont(f.getFont()));
			write(fos, addMargin(f.getMargin()));
			write(fos, addBorder(f.getBorder()));
			write(fos, addVisible(f.isVisible()));
			write(fos, "background-color: transparent;"); // XXX guess
			write(fos, "\"");
			if (f.getValue() instanceof TextValue
					&& ((TextValue) f.getValue()).getText() != null) {
				write(fos, " value=\"");
				write(fos, ((TextValue) f.getValue()).getText());
				write(fos, "\"");
			}
			if (!f.isEditable()) {
				write(fos, " disabled=\"disabled\"");
			}
			writeln(fos, " />");
		}

		if (f.getCaption() != null) {
			writeln(fos, "  </div>");
		}
	}

	private String nl2br(String text) {
		if (text == null)
			return null;

		return text.replace("\r\n", "<br/>").replace("\r", "<br/>")
				.replace("\n", "<br/>");
	}

	private String addMargin(Margin margin) {
		if (margin == null) {
			return "";
		}
		StringBuilder style = new StringBuilder();

		if (margin.getTop() == margin.getLeft()
				&& margin.getTop() == margin.getBottom()
				&& margin.getTop() == margin.getRight()) {
			style.append("margin:");
			style.append(margin.getTop());
			style.append("mm;");
		} else {

			if (Math.abs(margin.getTop()) > 0.001) {
				style.append("margin-top:");
				style.append(margin.getTop());
				style.append("mm;");
			}
			if (Math.abs(margin.getLeft()) > 0.001) {
				style.append("margin-left:");
				style.append(margin.getLeft());
				style.append("mm;");
			}
			if (Math.abs(margin.getBottom()) > 0.001) {
				style.append("margin-bottom:");
				style.append(margin.getBottom());
				style.append("mm;");
			}
			if (Math.abs(margin.getRight()) > 0.001) {
				style.append("margin-right:");
				style.append(margin.getRight());
				style.append("mm;");
			}
		}
		return style.toString();
	}

	private String addFill(Fill fill) {
		if (fill == null || !fill.isVisible()) {
			return "";
		}
		StringBuilder style = new StringBuilder();
		if (fill.getColor() != null) {
			style.append("background-color:");
			style.append(colorToRgb(fill.getColor()));
			style.append(";");
		}
		return style.toString();
	}

	private String addBorder(Border border) {
		if (border == null || !border.isVisible() || border.getEdge() == null) {
			return "";
		}
		StringBuilder style = new StringBuilder();
		if (border.getEdge().getWidth() > -0.999) {
			if (Math.abs(border.getEdge().getWidth()) < 0.001) {
				style.append("border: none;");
			} else {

				if (border.getEdge().getType() == null) {
					style.append("border:");
					style.append(border.getEdge().getWidthPx());
					style.append("px solid ");
					style.append(colorToRgb(border.getEdge().getColor()));
					style.append(";");
				} else {
					if (border.getEdge().getType() == Edge.Type.lowered) {
						style.append("border:");
						style.append(border.getEdge().getWidthPx());
						style.append("px inset;");
					} else if (border.getEdge().getType() == Edge.Type.raised) {
						style.append("border:");
						style.append(border.getEdge().getWidthPx());
						style.append("px outset;");
					} else {
						log.warning("Unhandled border typ "
								+ border.getEdge().getType());
					}
				}
			}
		}
		return style.toString();
	}

	private String addVisible(boolean visible) {
		if (!visible) {
			return "display:none;";
		}
		return "";
	}

	private static final String colorToRgb(Color color) {

		if (color == null) {
			// Workaround: Default to black
			color = Color.black;
		}

		StringBuilder style = new StringBuilder();
		style.append("rgb(");
		style.append(color.getRed());
		style.append(',');
		style.append(color.getGreen());
		style.append(',');
		style.append(color.getBlue());
		style.append(")");
		return style.toString();
	}

	private String addFont(Font font) {
		if (font == null) {
			return "";
		}
		StringBuilder style = new StringBuilder();
		if ("Sans".equals(font.getFamily())) {
			style.append("font-family:sans;");
		} else if ("Arial".equals(font.getFamily())) {
			style.append("font-family:Arial,sans;");
		} else if ("Verdana".equals(font.getFamily())) {
			style.append("font-family:Verdana,Arial,sans;");
		} else if ("Wingdings".equals(font.getFamily())) {
			style.append("font-family:Wingdings;");
		} else {
			throw new RuntimeException("Unknown font " + font.getFamily());
		}

		if (Math.abs(font.getSize()) > 0.001) {
			style.append("font-size:");
			style.append(font.getSizePx());
			style.append("px;");
		}

		if (font.getWeight() != null) {
			style.append("font-weight:");
			style.append(font.getWeight());
			style.append(";");
		}

		if (font.isUnderline()) {
			style.append("text-decoration:underline;");
		}

		if (font.getFill() != null && font.getFill().getColor() != null) {
			style.append("font-color:");
			style.append(colorToRgb(font.getFill().getColor()));
			style.append(";");
		}

		return style.toString();
	}

	private String addDimension(Dimension dimension) {
		StringBuilder style = new StringBuilder();
		if (Math.abs(dimension.getHeight()) > 0.001) {
			style.append("height:");
			double height = dimension.getHeightPx();
			style.append(height);
			style.append("px;");
		}
		if (Math.abs(dimension.getWidth()) > 0.001) {
			style.append("width:");
			double width = dimension.getWidthPx();
			style.append(width);
			style.append("px;");
		}
		return style.toString();
	}

	private String addPosition(Position position) {
		StringBuilder style = new StringBuilder();
		if (Math.abs(position.getX()) > 0.001) {
			style.append("left:");
			double x = position.getXPx();
			style.append(x);
			style.append("px;");
		}

		style.append("top:");
		double y = position.getYPx();
		style.append(y);
		style.append("px;");

		if (style.length() > 0) {
			style.append("position:absolute;");
		}
		return style.toString();
	}

	private static void write(FileOutputStream fos, String text)
			throws IOException {
		fos.write(text.getBytes());
	}

	private static void writeln(FileOutputStream fos, String text)
			throws IOException {
		write(fos, text);
		fos.write('\n');
	}

}
