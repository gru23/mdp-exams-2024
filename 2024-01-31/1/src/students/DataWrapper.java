package students;

import java.io.Serializable;

public class DataWrapper implements Serializable {
	private static final long serialVersionUID = -5880820162454009761L;
	
	private Object data;
	/**
	 * represents type of socket request/response (e.g. GET, CREATE, ...) 
	 */
	private String type;
	
	public DataWrapper(String type, Object data) {
		this.data = data;
		this.type = type;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
