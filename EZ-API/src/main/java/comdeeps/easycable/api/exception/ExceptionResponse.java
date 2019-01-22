package comdeeps.easycable.api.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;

public class ExceptionResponse {

	private String timeStamp;
	private int status;
	private String error;
	private String message;
	private String path;

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Override
	public String toString() {
		return "ExceptionResponse [timeStamp=" + timeStamp + ", status=" + status + ", error=" + error + ", message="
				+ message + ", path=" + path + "]";
	}

	public ExceptionResponse(String timeStamp, int status, String error, String message, String path) {
		super();
		this.timeStamp = timeStamp;
		this.status = status;
		this.error = error;
		this.message = message;
		this.path = path;
	}
	
	public ExceptionResponse(HttpStatus httpStatus,String message,String path) {
		this.timeStamp=""+new Date();
		this.status=httpStatus.value();
		this.error=httpStatus.name();
		this.message=message;
		this.path=path;
	}

	public ExceptionResponse() {
		
	}	

}
