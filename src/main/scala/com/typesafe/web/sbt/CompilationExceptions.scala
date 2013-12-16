package com.typesafe.web.sbt

import sbt._
import xsbti.{Maybe, Position, Severity, Problem}

/**
 * Capture a general problem with the compilation of a source file. General problems
 * have no associated line number and are always regarded as errors.
 * @param message The message to report.
 * @param source The source file containing the general error.
 */
class GeneralProblem(val message: String, source: File) extends Problem {
  def category(): String = ""

  def severity(): Severity = Severity.Error

  def position(): Position = new Position {
    def line(): Maybe[Integer] = Maybe.nothing()

    def lineContent(): String = ""

    def offset(): Maybe[Integer] = Maybe.nothing()

    def pointer(): Maybe[Integer] = Maybe.nothing()

    def pointerSpace(): Maybe[String] = Maybe.nothing()

    def sourcePath(): Maybe[String] = Maybe.just(source.getCanonicalPath)

    def sourceFile(): Maybe[File] = Maybe.just(source)
  }
}

/**
 * Capture a line/column position along with the line's content for a given source file.
 * @param lineNumber The line number - starts at 1.
 * @param lineContent The content of the line itself.
 * @param characterOffset The offset character position - starts at 0.
 * @param source The associated source file.
 */
class LinePosition(
                    lineNumber: Int,
                    override val lineContent: String,
                    characterOffset: Int,
                    source: File
                    ) extends Position {
  def line(): Maybe[Integer] = Maybe.just(lineNumber)

  def offset(): Maybe[Integer] = Maybe.just(characterOffset)

  def pointer(): Maybe[Integer] = offset()

  def pointerSpace(): Maybe[String] = Maybe.just(
    lineContent.take(pointer().get).map {
      case '\t' => '\t'
      case x => ' '
    })

  def sourcePath(): Maybe[String] = Maybe.just(source.getPath)

  def sourceFile(): Maybe[File] = Maybe.just(source)
}

/**
 * Capture a problem associated with a line number and character offset.
 */
class LineBasedProblem(
                        override val message: String,
                        override val severity: Severity,
                        lineNumber: Int,
                        characterOffset: Int,
                        lineContent: String,
                        source: File
                        ) extends Problem {

  def category(): String = ""

  override def position: Position = new LinePosition(lineNumber, lineContent, characterOffset, source)
}