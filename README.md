# Bitcoind Json Rpc Client for Java

A Java library for the Json Rpc interface of a Bitcoin Fullnode. 

Fire up a [bitcoin fullnode](https://github.com/bitcoinclassic/bitcoinclassic/releases/tag/v0.12.0cl1) 
in command-line -server configuration, configure your ports and passwords in Fullnode, and you are 
ready to pull data from the blockchain, maybe even send some data.  Advisable to develop on the 
testnet network until "the time comes".
  
Running bitcoind with default settings, retrieve the current height of the last mined block:

    Fullnode.createRpc(new RpcParams("your_rpc_password")).getblockcount();

An attempt has been made to be feature-to-feature compatible with most commands listed in the 
[Bitcoin RPC Reference](https://bitcoin.org/en/developer-reference#rpcs).      
This library has been tested against Bitcoind 0.11.2 (Classic and Core) but does not yet support 0.12.0 (soon).  

## Listeners

The library can receive messages from the bitcoin node when the node detects transactions that have an affect
on the wallet, when new blocks are mined, or when alerts are broadcast.  Both bitcoind and fullnodej need to be configured properly:
 
#####1. Configure Bitcoind  

Select a port for Fullnodej to use, let's say :7931. Bitcoind needs command-line options to match:

    /usr/bin/bitcoind -server -testnet
    	-blocknotify="echo '%s' | nc 127.0.0.1 7931" \ 
    	-walletnotify="echo '%s' | nc 127.0.0.1 7931" \
		-alertnotify="echo '%s' | nc 127.0.0.1 7931"
    
Note: You may also want to add additional command-line options here: -txindex for a more complete database,
  -rescan to ensure an up-to-date wallet and explicitly define your -rpcport=18332.

#####2. Configure Fullnode 

Next, use this library to open and listen on that port, create an instance of Notify, 
and pass it to the Notifications system: 

	Notifications listener = fullnode.getNotifications();
    lisener.setListenerPort(7931);
    MyBlockListener blocks = new Notify() { @Override... };
    listener.attach(blocks);

You should be picking up new blocks and/or wallet transactions now.  

Be sure to Notifications.detach() your Notify instances and/or call Notifications.shutdown() to free the port.

You may connect to multiple Bitcoin nodes and notification interfaces. 
More configurations can be seen in the accompanying [test artifacts](https://github.com/jeffmasty/fullnodej-test) 
which include [one scenario](https://github.com/jeffmasty/fullnodej-test/blob/master/src/test/java/org/fullnodej/test/pingpong/PingPong.java) 
with 2 nodes sending funds between them while their confirmation listeners notify a referee. 
 
## Build   

	mvn clean install


NOTE: Building this project requires the java-agent lombok on the classpath.  
More info at: [Project Lombok](https://projectlombok.org/download.html) [(github)](https://github.com/rzwitserloot/lombok)    

A De-Lombok'ed version of this library is available:  

[https://github.com/jeffmasty/fullnode-dj](https://github.com/jeffmasty/fullnode-dj)  

## Usage 

Not currently in Maven Central, the dependency will be:

    <dependency>
      <groupId>org.fullnodej</groupId>
      <artifactId>fullnodej</artifactId>
      <version>0.12.1</version>
    </dependency>

## Testing 

JUnit tests were developed alongside this project, available:

[https://github.com/jeffmasty/fullnodej-test](https://github.com/jeffmasty/fullnodej-test)  

The Rpc interface in this library may be useful but you will find the associated tests and framework even more exciting.


## Acknowledgements
 
This project was inspired by Johann Barbie and the 
[BitcoindClient4J](https://github.com/johannbarbie/BitcoindClient4J) project.

## Donations

Bitcoin donations can be sent to:

	1Es3KYL6tyB3Swpq9r81uYb8X6D3TcHkjw
	
Employment opportunities also requested, thanks!

## License

MIT License  
  