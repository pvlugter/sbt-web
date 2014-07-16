lazy val a = (project in file("."))
  .enablePlugins(SbtWeb)
  .dependsOn(b)

lazy val b = (project in file("modules/b"))
  .enablePlugins(SbtWeb)
  .dependsOn(c, d)
  .settings(
    WebKeys.exportAssets in TestAssets := true
  )

lazy val c = (project in file("modules/c"))
  .enablePlugins(SbtWeb)
  .settings(
    libraryDependencies += jquery
  )

lazy val d = (project in file("modules/d"))
  .enablePlugins(SbtWeb)
  .settings(
    libraryDependencies += jquery
  )

lazy val jquery = "org.webjars" % "jquery" % "2.0.3-1"
