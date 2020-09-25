addSbtPlugin("org.scalameta" % "sbt-scalafmt" % "2.3.2")
addSbtPlugin("org.scalameta"       % "sbt-mdoc"        % "2.2.6")
addSbtPlugin("com.thesamet" % "sbt-protoc" % "0.99.25")
addSbtPlugin("com.github.tkawachi"  % "sbt-doctest"     % "0.9.6")
addSbtPlugin("com.eed3si9n"         % "sbt-unidoc"      % "0.4.3")
addSbtPlugin("com.typesafe"         % "sbt-mima-plugin" % "0.7.0")
addSbtPlugin("de.heikoseeberger"    % "sbt-header"      % "5.4.0")
addSbtPlugin("com.geirsson" % "sbt-ci-release" % "1.5.3")
libraryDependencies += "com.thesamet.scalapb" %% "compilerplugin" % "0.10.8"
