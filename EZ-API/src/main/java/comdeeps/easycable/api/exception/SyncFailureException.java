package comdeeps.easycable.api.exception;

public class SyncFailureException extends RuntimeException {
	
	private static final long serialVersionUID = 8838537468574045276L;

	public SyncFailureException(String msg) {
		super(msg);
	}

}
