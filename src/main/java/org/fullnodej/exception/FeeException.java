package org.fullnodej.exception;

import lombok.Getter;

/** The fee was outside the tolerance bounds or zero. This may be a recoverable exception.*/
public class FeeException extends RuntimeException {
	private static final long serialVersionUID = 2261317392820101615L;

	public static enum Type {ZERO_FEE, HIGH_FEE, REMOTE_EXCEPTION, OTHER, NOT_ENOUGH_DATA}

	@Getter private final Type type;

	public FeeException(Type type) {
		this(type, type.name());
	}

	public FeeException(Type type, String message) {
		super(message);
		this.type = type;
	}

}
