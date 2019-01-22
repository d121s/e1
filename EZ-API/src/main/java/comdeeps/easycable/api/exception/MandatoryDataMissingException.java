package comdeeps.easycable.api.exception;

public class MandatoryDataMissingException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public MandatoryDataMissingException(String details) {
        super("Mandatory Data Missing : " + details);
    }
}