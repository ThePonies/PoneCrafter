package org.theponies.ponecrafter.exceptions;

public class SavePackageException extends Exception {
    public SavePackageException (String message) {
        super(message);
    }

    public SavePackageException (String message, Throwable cause) {
        super(message, cause);
    }
}
