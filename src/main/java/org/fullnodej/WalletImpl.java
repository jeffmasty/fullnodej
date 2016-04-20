package org.fullnodej;

public class WalletImpl implements Wallet {

	private final Rpc rpc;
	public WalletImpl(Rpc rpc) {
		this.rpc = rpc;
	}

	/* (non-Javadoc) @see org.fullnodej.IWallet#isLocked() */
	@Override
	public boolean isLocked() {
		if (isEncrypted()) {
			return 0l == rpc.getwalletinfo().getUnlocked_until();
		}
		return false;
	}

	/* (non-Javadoc) @see org.fullnodej.IWallet#unlock(java.lang.String, int) */
	@Override
	public boolean unlock(String Passphrase, int seconds) {
		if (!isEncrypted()) return true;
		if (!isLocked()) return true;

		try {
			rpc.walletpassphrase(Passphrase, seconds);
			return true;
		} catch (Throwable e) {
			return false;
		}
	}

	/* (non-Javadoc) @see org.fullnodej.IWallet#lock() */
	@Override
	public void lock() {
		if (!isEncrypted()) return;
		if (isLocked()) return;
		rpc.walletlock();
	}

	/* (non-Javadoc) @see org.fullnodej.IWallet#isEncrypted() */
	@Override
	public boolean isEncrypted() {
		return rpc.getwalletinfo().getUnlocked_until() != null;
	}

}
