package breakingumbrella.connectit.error;


/**
 * Created by dem3n on 28.05.2017.
 */

public class ApiError extends Exception implements IError {

	private String errorTitle;
	private String errorMessage;
	private String causeBy;

	public ApiError(String errorTitle, String errorMessage, String causeBy) {
		this.errorTitle = errorTitle;
		this.errorMessage = errorMessage;
		this.causeBy = causeBy;
	}

	public ApiError(okhttp3.Response response) {
		errorTitle = (response.headers().toString());
		errorMessage += "Network response: " + (response.networkResponse() == null ? ""  : response.networkResponse().toString());
		errorMessage += "\n";
		errorMessage += "Message: " + response.message();
		errorMessage += "\n";
		errorMessage += "Response code: " + response.code();
		errorMessage += "\n";
		errorMessage += "Request: " + response.request().toString();
	}

	public ApiError(Throwable t) {
		this.setStackTrace(t.getStackTrace());
		errorTitle = ("Unspecified api error");
		errorMessage += "Message: " + t.getMessage();
		errorMessage += "\n";
		causeBy = t.getCause() == null ? "Unspecified" : t.getCause().getMessage();
		this.setStackTrace(t.getStackTrace());
	}

	@Override
	public String getErrorTitle() {
		return errorTitle;
	}

	@Override
	public String getErrorMessage() {
		return errorMessage;
	}

	@Override
	public String getCauseBy() {
		return causeBy;
	}

}
