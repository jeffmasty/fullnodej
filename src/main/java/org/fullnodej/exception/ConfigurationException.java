package org.fullnodej.exception;

import java.io.IOException;
import java.net.SocketException;

import lombok.Getter;


public class ConfigurationException extends Exception {
	private static final long serialVersionUID = -5001473600988701366L;

	public static enum Type {
		AUTHENTICATION,
		ALREADY_RUNNING,
		INVALID_LISTENER_PORT,
		LISTENER_PORT_CONFLICT,
		REMOTE_EXCEPTION,
		NULL_NOTIFY,
		DUPLICATE_NOTIFY,
		OTHER;
	}

	@Getter private final Type type;

	public ConfigurationException(Type type) {
		this(type, type.name());
	}

	public ConfigurationException(Type type, String message) {
		super(message);
		this.type = type;
	}

	public ConfigurationException(Type type, Throwable t) {
		super(type.name() + ": "  + t.getMessage(), t);
		this.type = type;
	}

	public ConfigurationException(SocketException e) {
		super(Type.REMOTE_EXCEPTION.name() + " " + e.getMessage());
		type = Type.REMOTE_EXCEPTION;
	}

	public ConfigurationException(IOException e) {
		super(Type.AUTHENTICATION.name() + " " + e.getMessage());
		type = (e instanceof SocketException) ? Type.REMOTE_EXCEPTION : Type.AUTHENTICATION;
	}

	public ConfigurationException(int port, Throwable t) {
		super(Type.LISTENER_PORT_CONFLICT.name() + " Port: " + port + " " + t.getMessage(), t);
		this.type = Type.LISTENER_PORT_CONFLICT;
	}

	public ConfigurationException(Type type, int port) {
		super(type.name() + " Port: " + port);
		this.type = type;
	}
}
