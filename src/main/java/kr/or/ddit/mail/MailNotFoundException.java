package kr.or.ddit.mail;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class MailNotFoundException extends ResponseStatusException {

	public MailNotFoundException(HttpStatus status, String reason, Throwable cause) {
		super(status, reason, cause);
	}

	public MailNotFoundException(HttpStatus status, String reason) {
		super(status, reason);
	}

	public MailNotFoundException(HttpStatus status) {
		super(status);
	}

	public MailNotFoundException(int rawStatusCode, String reason, Throwable cause) {
		super(rawStatusCode, reason, cause);
	}

}
