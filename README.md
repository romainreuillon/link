# link

Link is a [scala.js](http://www.scala-js.org) example that demonstrate how to interact with an ethereum blockchain from scala code compiled to JS code and runnig within a browser. Starting from there you can develop you [ethereum](https://www.ethereum.org/) application in pure scala. Nice isn't it?

It is still a very early stage :). To test it anyway, you'll need [docker-compose](https://docs.docker.com/compose/) and [SBT](http://www.scala-sbt.org/), and then:

```
sbt assemble
cd docker
docker-compose up
```

Now you can browse http://localhost:7000.

Enjoy !

The mid term plan is to fully map the [web3.js API](https://github.com/ethereum/wiki/wiki/JavaScript-API).

PS: you might want to wait a bit before the geth light client gets fully synchronised, otherwise the balance will be 0.

