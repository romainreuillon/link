
enablePlugins(ScalaJSPlugin)
//enablePlugins(ScalaJSBundlerPlugin)

name := "link"

scalaVersion := "2.12.3"

resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"
resolvers += "releases" at "https://oss.sonatype.org/service/local/staging/deploy/maven2"

libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "0.9.2"
libraryDependencies += "com.lihaoyi" %%% "scalarx" % "0.3.2"
libraryDependencies += "com.lihaoyi" %%% "scalatags" % "0.6.5"
libraryDependencies += "com.lihaoyi" %%% "upickle" % "0.4.4"
libraryDependencies += "fr.iscpif" %%% "scaladget" % "0.9.4"

//npmDevDependencies in Compile += "web3" -> "0.19.1"
//webpackConfigFile in fastOptJS := Some(baseDirectory.value  / "webpack.config.js")

//jsDependencies += "org.webjars" % "requirejs" % "2.1.22" / "require.js"
//jsDependencies += "org.webjars.npm" % "bignumber.js" % "2.4.0" / "bignumber.js"
// This is an application with a main method
scalaJSUseMainModuleInitializer := true


val assemble = TaskKey[File]("assemble")

assemble := {
  val js = (fastOptJS in Compile).value
  //val wp = (webpack in fastOptJS in Compile).value
  val resourceDir = (resourceDirectory in Compile).value

  val site = target.value / "site"
  site.mkdirs

  for(
    f <- js.data.getParentFile.listFiles
    if(!f.isDirectory)
    if(f.getName.takeRight(3) == ".js" || f.getName.takeRight(4) == ".map")
  ) IO.copyFile(f, site / f.getName)


//  for {
//    f <- wp
//  } {
//    if(!f.isDirectory) IO.copyFile(f, site / f.getName)
//    else IO.copyDirectory(f, site / f.getName)
//  }

//  IO.copyFile(wp, site / wp.getName )

  for {
    f <- resourceDir.listFiles()
  } {
    if(!f.isDirectory) IO.copyFile(f, site / f.getName)
    else IO.copyDirectory(f, site / f.getName)
  }

  site
}

