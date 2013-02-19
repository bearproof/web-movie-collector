package ro.isdc.utils;

public class BasicAjaxResponse {

	private boolean isError;
	private String message;

	public BasicAjaxResponse() {

	}

	public BasicAjaxResponse(boolean isError, String message) {
		this.isError = isError;
		this.message = message;
	}

	public boolean isError() {
		return isError;
	}

	public void setError(boolean isError) {
		this.isError = isError;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
