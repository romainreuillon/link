# link

Link is a [scala.js](http://www.scala-js.org) example that demonstrate how to interact with an ethereum blockchain from scala code compiled to JS code and runnig within a browser. Starting from there you can develop you ethereum application in pure scala. Nice isn't it?

It is a very early stage :). To test it anyway, you'll need [docker-compose](https://docs.docker.com/compose/) and [SBT](http://www.scala-sbt.org/), and then:

```
sbt assemble
cd docker
docker-compose up
```

Now you can connect you browser to http://localhost:7000.

Enjoy !

