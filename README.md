# link

Link is a [scala.js](http://www.scala-js.org) example that demonstrate how to interact with an ethereum blockchain from scala code compiled to JS code and running within a browser. Starting from there you can develop you [ethereum](https://www.ethereum.org/) application in pure scala. Nice isn't it?

This application takes the web3 object either from a web3 capable browser (MIST, MetaMask, Parity...) if not it falls back using [uport-connect](https://github.com/uport-project/uport-connect). It lets the user query and transact with a contract deployed on the rinkeby ethereum test network. To get some test net ethers, go to the [rinkeby faucet](https://faucet.rinkeby.io/).

It is still a very early stage :). To test it:

```
sbt assemble # whenever you update the scala code
cd truffle
npm install # only the first time
npm run dev
```

Now you can browse http://localhost:3000. You need a web3 enabled browser or metamask.


### Deploy the smart contract on a local node:

```
npm install -g ethereumjs-testrpc
testrpc
cd truffle
truffle migrate --reset --network development
```

### Deploy the smart contract on Rinkeby test net:

Start a `geth` node on *Rinkeby*

```bash
cd docker
docker-compose up --build
docker exec -it docker_geth_1 geth attach ipc://root/.ethereum/rinkeby/geth.ipc
```

You're now in a JavaScript console, you need to:
 - retrieve your (newly created) account address
 - get toy Ether from the *Rinkeby faucet*
 - unlock your account to enable truffle to use it to deploy the smart contract

```javascript
var address = personal.listAccounts[0]
address // copy paste this for rinkeby faucet
```

Visit [Rinkeby faucet](https://faucet.rinkeby.io/) and follow their instructions to make your address (previously copied) known to their service.
Go back to your `geth` JavaScript console when done.

```javascript
eth.getBalance(address) // check you received the funds
personal.unlockAccount(address)
```

Finally deploy the contract from a regular shell console:

```bash
cd ../truffle
truffle migrate --reset --network rinkeby
```

### Troubleshooting

- Cannot deploy smart contract: `rm -rf truffle/build`

Enjoy !

The mid term plan is to fully map the [web3.js API](https://github.com/ethereum/wiki/wiki/JavaScript-API).
