package comdeeps.easycable.api.exception;

public class UIPermissonError extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public UIPermissonError(String details) {
        super("Permission Error : " + details);
    }
}