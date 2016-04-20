package org.fullnodej;

public interface Wallet {

	boolean isLocked();

	boolean unlock(String Passphrase, int seconds);

	void lock();

	boolean isEncrypted();

}