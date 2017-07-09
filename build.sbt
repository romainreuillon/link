
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

// This is an application with a main method
scalaJSUseMainModuleInitializer := true


val assemble = TaskKey[File]("assemble")

assemble := {
  val js = (fullOptJS in Compile).value
  val resourceDir = (resources in Compile).value

  val site = target.value / "site"
  site.mkdirs

  for(
    f <- js.data.getParentFile.listFiles
    if(!f.isDirectory)
    if(f.getName.takeRight(3) == ".js" || f.getName.takeRight(4) == ".map")
  ) IO.copyFile(f, site / f.getName)
 
  for(
    f <- resourceDir
    if(!f.isDirectory)
  ) IO.copyFile(f, site / f.getName)

  site
}

