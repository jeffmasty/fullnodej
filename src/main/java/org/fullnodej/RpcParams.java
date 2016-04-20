package org.fullnodej;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

/** rpc password/user (to match bitcoin.conf) and network location of bitcoind's RPC interface.  */
@Getter @EqualsAndHashCode @Slf4j @ToString(exclude="password") @RequiredArgsConstructor
public class RpcParams {

	/**list of keys looked for in a .properties collection */
	@RequiredArgsConstructor @Getter
	public enum PropertyKeys {

		USER("fullnode.rpcuser"),
		PASSWORD("fullnode.rpcpassword"),
		PROTOCOL("fullnode.protocol"),
		ADDRESS("fullnode.ipaddress"),
		PORT("fullnode.port");

		public final String key;
	}

	public static final String DEFAULT_USER = "bitcoinrpc";
	public static final URL DEFAULT_URL = _staticUrl("http://127.0.0.1:8332");
	public static final URL DEFUALT_TESTNET_URL = _staticUrl("http://127.0.0.1:18332");
	private static URL _staticUrl(String s) { try { return new URL(s);} catch (MalformedURLException t) { return null; }}

	/** bitcoind rpc username */
	private final String user;

	/** bitcoind rpc password */
	private final String password;

	/** location of the full node rpc interface */
	private final URL url;

	/**@param props a properties object containing settings with keys listed in the {@link PropertyKeys} enum
	 * @throws MalformedURLException */
	public RpcParams(Properties props) throws MalformedURLException {
		user = props.getProperty(PropertyKeys.USER.getKey(), "bitcoinrpc");
		password = props.getProperty(PropertyKeys.PASSWORD.getKey(), "");
		String ipAddress = props.getProperty(PropertyKeys.ADDRESS.getKey(), "127.0.0.1");
		String protocol = props.getProperty(PropertyKeys.PROTOCOL.getKey(), "http");
		int port = 8332;
		try {
			port = Integer.parseInt(props.getProperty(PropertyKeys.PORT.getKey()));
		} catch (NumberFormatException | NullPointerException e) {
			log.debug(props.getProperty(PropertyKeys.PORT.getKey()) + " " + e.getMessage() );
		}
		url = new URL(protocol + "://" + ipAddress + ":" + port + "/");
	}

	/** very basic params using defaults and supplied bitcoin.conf password if isTestnet then port = 18332 else port = 8332 */
	public RpcParams(String password, boolean isTestnet) {
		this.password = password;
		this.user = DEFAULT_USER;
		this.url = (isTestnet) ? DEFUALT_TESTNET_URL : DEFAULT_URL;
	}

	/** basic params using defaults and supplied RPC password and port
	 * @throws MalformedURLException */
	public RpcParams(String password, int bitcoindPort) throws MalformedURLException {
		this.password = password;
		this.user = DEFAULT_USER;
		this. url = new URL("http://127.0.0.1:" + bitcoindPort);
	}

	/** very basic params mainnet defaults and supplied bitcoin.conf password */
	public RpcParams(String password) {
		this.password = password;
		this.user = DEFAULT_USER;
		this.url = DEFAULT_URL;
	}

	public Map<String, String> basicHeader() {
		Map<String, String> result = new HashMap<>(2);
		result.put("content-type", "text/plain");
		result.put("Authorization", "Basic " +
				new String(Base64.getEncoder().encode((user + ":" + password).getBytes())));
		return result;
	}

}