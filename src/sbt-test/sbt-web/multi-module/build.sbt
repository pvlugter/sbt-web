lazy val a = (project in file("."))
  .enablePlugins(SbtWeb)
  .dependsOn(b)

lazy val b = (project in file("modules/b"))
  .enablePlugins(SbtWeb)
  .dependsOn(c, d, x)
  .settings(
    WebKeys.exportAssets in TestAssets := true
  )

lazy val c = (project in file("modules/c"))
  .enablePlugins(SbtWeb)
  .dependsOn(e)

lazy val d = (project in file("modules/d"))
  .enablePlugins(SbtWeb)
  .dependsOn(e)

lazy val e = (project in file("modules/e"))
  .enablePlugins(SbtWeb)
  .settings(
    libraryDependencies += "org.webjars" % "jquery" % "2.0.3-1"
  )

lazy val x = (project in file("modules/x"))
