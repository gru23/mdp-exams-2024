package vehicles;

import java.io.Serializable;

public class DataWrapper implements Serializable {
	private static final long serialVersionUID = -6437179101860634308L;
	
	private String type;
	private Object data;
	
	public DataWrapper(String type, Object data) {
		this.type = type;
		this.data = data;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
