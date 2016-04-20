package org.fullnodej;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.fullnodej.data.VerboseTx;
import org.fullnodej.exception.FeeException;
import org.fullnodej.exception.FeeException.Type;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class Util {

	private final Rpc rpc;
	public Util(Rpc rpc) {
		this.rpc = rpc;
	}

	public static interface Constants {

		public static final String TESTNET = "test";
		String MAINNET = "main";
		/** the special wallet account that has no label */
		String DEFAULT_ACCOUNT = "";
		/**param used to retrieve a {@link VerboseTx} rather than raw hexstring during {@link #getrawtransaction(String, Integer)} */
		int VERBOSE = 1;

		BigDecimal LARGE_FEE = new BigDecimal(0.001);

		/** base fee estimates on getting a transaction mined into a block within 3 blocks */
		int DEFAULT_BLOCK_FEE_WINDOW = 3;
		int PORT = 7931;

	}

	public boolean isTestnet() {
		return rpc.getblockchaininfo().getChain().equals(Constants.TESTNET);
	}

	public boolean isMainnet() {
		return rpc.getblockchaininfo().getChain().equals(Constants.MAINNET);
	}

	/**Direct the rpc to use a fee/kb for new transactions that it determines (as of right now) to be
	 * appropriate to get a transaction mined into a block by the time numOfBlocks have been mined.
	 * @param numOfBlocks calculate fee based on getting a transaction mined into a block within numOfBlock blocks
	 * @param feeLimit exception thrown if estimated fee is above the limit, pass <code>null</code> for no limit (accepting whatever the rpc returns)
	 * @throws FeeException estimate fee is 0, or estimate > feeLimit, rpc error
	 * @return the fee set as per {@link Rpc#settxfee(BigDecimal)}*/
	public BigDecimal useEstimatedFees(int numOfBlocks, BigDecimal feeLimit) throws FeeException {
		BigDecimal estimate = rpc.estimatefee(numOfBlocks);
		if (estimate.compareTo(BigDecimal.ZERO) < 0) throw new FeeException(Type.NOT_ENOUGH_DATA);
		if (estimate.compareTo(BigDecimal.ZERO) <= 0) log.warn("Using a Zero fee returned from Bitcoind.");
		if (feeLimit != null && estimate.compareTo(feeLimit) >= 0) throw new FeeException(Type.HIGH_FEE, "high fee: " + estimate + " limit: " + feeLimit);
		if (estimate.compareTo(BigDecimal.ZERO) <= 0)
			log.error("Skipping settxfee(" + estimate + ") to bitcoind");
		else if (false == rpc.settxfee(estimate)) //throw new FeeException(Type.REMOTE_EXCEPTION,
				log.warn("settxfee(" + estimate + ") returned false");
		return estimate;
	}

	/**Direct the rpc to use a fee/kb for new transactions that it determines (as of right now) to be
	 * appropriate to get a transaction mined into a block by the time <b>Three<b> blocks have been mined.
	 * @return the fee set as per {@link Rpc#settxfee(BigDecimal)}
	 * @throws FeeException if the estimated fee is 0 or > {@link Constants#LARGE_FEE}*/
	public BigDecimal useEstimatedFees() throws FeeException {
		return useEstimatedFees(3, Constants.LARGE_FEE);
	}

	public boolean isWalletAddress(String address) {
		String account = rpc.getaccount(address);
		if (account == null) return false;
		if (account.equals(Constants.DEFAULT_ACCOUNT))
			return rpc.getaddressesbyaccount(Constants.DEFAULT_ACCOUNT).contains(address);
		return true;
	}

	/**@return all addresses in control by the wallet*/
	public List<String> allAddresses() {
		List<String> result = new ArrayList<>();
		for (String account : rpc.listaccounts().keySet())
			result.addAll(rpc.getaddressesbyaccount(account));
		return result;
	}

}
