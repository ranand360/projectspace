/**
 * @author Anand Raju
 */
package com.openbid.projectspace.rest.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

/**
 * @author Anand Raju
 *
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class IllegalArgumentException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public IllegalArgumentException() {
        super();
    }
    public IllegalArgumentException(String message, Throwable cause) {
        super(message, cause);
    }
    public IllegalArgumentException(String message) {
        super(message);
    }
    public IllegalArgumentException(Throwable cause) {
        super(cause);
    }
}
