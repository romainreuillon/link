
enablePlugins(ScalaJSPlugin)

name := "link"

scalaVersion := "2.12.2"

resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"
resolvers += "releases" at "https://oss.sonatype.org/service/local/staging/deploy/maven2"

libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "0.9.2"
libraryDependencies += "com.lihaoyi" %%% "scalarx" % "0.3.2"
libraryDependencies += "com.lihaoyi" %%% "scalatags" % "0.6.5"
libraryDependencies += "com.lihaoyi" %%% "upickle" % "0.4.4"
jsDependencies += "org.webjars" % "requirejs" % "2.1.22" / "require.js"



