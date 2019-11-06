package edu.csuci.appaca.data.gameres;

public class GameResourcesNotLoadedException extends RuntimeException {

    public GameResourcesNotLoadedException() {
        super();
    }

    public GameResourcesNotLoadedException(String message) {
        super(message);
    }

    public GameResourcesNotLoadedException(String message, Throwable cause) {
        super(message, cause);
    }

    public GameResourcesNotLoadedException(Throwable cause) {
        super(cause);
    }

}
