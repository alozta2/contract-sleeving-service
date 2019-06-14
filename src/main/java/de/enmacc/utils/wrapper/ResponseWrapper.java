package de.enmacc.utils.wrapper;

import java.util.List;

/**
 * Generic wrapper including a generic server response.
 * This offers more flexible responses to user.
 * 
 * @param <T>
 * */
public class ResponseWrapper<T> {

	private List<T> data;
	private String response;
	
	public ResponseWrapper(List<T> data, String response) {
		this.data = data;
		this.response = response;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}
}
