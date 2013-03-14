package de.egore911.xdp.model.values;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.iharder.Base64;

public class ImageValue extends Value {

	private static final Logger log = Logger.getLogger(ImageValue.class
			.getName());

	private byte[] data;
	private String mimeType;
	private String src;

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public String getMimeType() {
		return mimeType;
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public void setBase64Data(String base64data) {
		try {
			this.data = Base64.decode(base64data);
		} catch (IOException e) {
			log.log(Level.SEVERE, e.getMessage(), e);
		}
	}

}
