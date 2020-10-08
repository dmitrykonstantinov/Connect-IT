package breakingumbrella.connectit.error;

import java.util.LinkedList;
import java.util.Queue;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ErrorHolder {

	@Inject
	ErrorHolder() {
		errors = new LinkedList<>();
	}

	private Queue<IError> errors;

	public void addError(IError error) {
		errors.add(error);
	}

	public IError getError() {
		return errors.poll();
	}

	public boolean isErrorExist() {
		return errors.peek() != null;
	}

}
