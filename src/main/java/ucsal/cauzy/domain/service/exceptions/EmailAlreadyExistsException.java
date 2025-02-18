package ucsal.cauzy.domain.service.exceptions;

public class EmailAlreadyExistsException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public EmailAlreadyExistsException() {
        super("The e-mail you are trying to register already exists");
    }
}
