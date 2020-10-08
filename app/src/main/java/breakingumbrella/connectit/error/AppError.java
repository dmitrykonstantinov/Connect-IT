package breakingumbrella.connectit.error;

public class AppError extends Exception implements IError {

	private String errorTitle;
	private String errorMessage;
	private String causeBy;


	public AppError(Exception ex) {
		errorTitle = "Application error";
		errorMessage = ex.getMessage();
		causeBy = ex.getCause() == null ? "Caused by - unspecified" : ex.getCause().getMessage();
		this.setStackTrace(ex.getStackTrace());
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
