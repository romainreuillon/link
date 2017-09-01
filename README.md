# link

Link is a [scala.js](http://www.scala-js.org) example that demonstrate how to interact with an ethereum blockchain from scala code compiled to JS code and runnig within a browser. Starting from there you can develop you [ethereum](https://www.ethereum.org/) application in pure scala. Nice isn't it?

This application takes the web3 object either from a web3 capable browser (MIST, MetaMask, Parity...) if not it falls back using [uport-connect](https://github.com/uport-project/uport-connect). It let the user query and transact with a contract deployed on the rinkeby ethereum test network. To get some test net ethers, go to the [rinkeby faucet](https://faucet.rinkeby.io/).

It is still a very early stage :). To test it:

```
sbt assemble
cd truffle
npm dev run
```

Now you can browse http://localhost:3000. You need a web3 enabled browser or metamask.

To deploy the smart contract:

```
truffle migrate
```

Enjoy !

The mid term plan is to fully map the [web3.js API](https://github.com/ethereum/wiki/wiki/JavaScript-API).

