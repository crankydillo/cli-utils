package org.beeherd.cli.utils

import org.specs._
import org.specs.runner.JUnit4
import org.specs.runner.SpecsFileRunner

object SpecsRunner extends SpecsFileRunner("**/*Spec.scala", ".*")
