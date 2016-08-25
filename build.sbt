
enablePlugins(ScalaJSPlugin)

name := "link"

scalaVersion := "2.11.8"

resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"
resolvers += "releases" at "https://oss.sonatype.org/service/local/staging/deploy/maven2"

libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "0.9.0"
libraryDependencies += "com.lihaoyi" %%% "scalarx" % "0.3.1"
libraryDependencies += "com.lihaoyi" %%% "scalatags" % "0.6.0"
libraryDependencies += "com.lihaoyi" %%% "upickle" % "0.4.1"
jsDependencies += "org.webjars" % "requirejs" % "2.1.22" / "require.js"