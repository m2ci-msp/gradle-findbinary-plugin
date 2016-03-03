package org.m2ci.msp.gradle.tasks.findbinary

import org.gradle.api.Project
import static groovy.io.FileVisitResult.*

// Base class for finding binaries
class FindBinary {

  protected List<String> pathCandidates = []
  protected String binaryName = ""
  protected Boolean recursive = false
  protected Project project = null

  public FindBinary(Project project) {

    this.project = project

    // initialize candidates to path environment
    addPathsFromEnvironment()

  }

  // adds a user provided path to the candidates
  // prepend it to the list so that user paths have priority
  public void custom_path(String newPath) {

    this.pathCandidates.add(0, newPath)

  }

  // sets the binary to look for
  public void binary(String name) {

    this.binaryName = name

  }

  // sets recursive flag
  public void recursive(Boolean flag) {
    this.recursive = flag
  }

  // search for binary, return first found version or null on failure
  public String search() {

    def result = flat_search()

    // perform a deep search if binary was not found and if the recursive flag
    // is set to true
    if( result == null && this.recursive == true ) {

      result = deep_search()

    }

    // throw exception if no path was found
    if( result == null ) {
      throw new FileNotFoundException("Could not find binary [$binaryName]")
    }

    return result

  }

  // looks for the binary only in the provided folders, not their subdirectories
  protected String flat_search() {
    def result = this.pathCandidates.findResult{ candidate ->

      // construct binary path for current candidate
      def binaryPath = new File( candidate + File.separator + this.binaryName )

      // verify that the found path points to a file and that the file is readable and executable
      if( isReadableExecutable(binaryPath) ) {
        return binaryPath
      }
      else {
        return null
      }

    } // end closure

    return result

  }

  // performs a recursive search in each provided directory
  protected String deep_search() {

    def result = this.pathCandidates.findResult{ candidate ->

      def path = new File(candidate)

      def found = null

      path.traverse{ currentFile ->
        if( currentFile.name == this.binaryName) {
          if( isReadableExecutable(currentFile) ) {
            found = currentFile
            return TERMINATE
          }
        }
      }

      return found

    } // end closure

    return result

  }

  protected boolean isReadableExecutable(File path) {
    return ( path.isFile() && path.canRead() && path.canExecute() )
  }

  protected void addPathsFromEnvironment() {
    def path = System.getenv()["PATH"]

    if (path != null) {

      // ensure that the paths actually exist
      path.tokenize(File.pathSeparator).each{ currentPath ->

        if( (new File(currentPath) ).exists() ) {
          pathCandidates.add(currentPath)
        }

      }

    }

  }

}
