package org.m2ci.msp.gradle.tasks.findbinary

import org.gradle.api.Project

// Base class for finding binaries
class FindBinary {

  protected List<String> path_candidates = []
  protected String binary_name = ""
  protected Boolean recursive = false
  protected Project project = null

  public FindBinary(Project project) {

      this.project = project

      // initialize candidates to path environment
      def path = System.getenv()["PATH"]

      if (path != null) {
        path_candidates = path.tokenize(File.pathSeparator)
      }

  }

  // adds a user provided path to the candidates
  // prepend it to the list so that user paths have priority
  public void custom_path(String new_path) {

    this.path_candidates.add(0, new_path)

  }

  // sets the binary to look for
  public void binary(String name) {

    this.binary_name = name

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

    return result

  }

  // looks for the binary only in the provided folders, not their subdirectories
  protected String flat_search() {
    def result = this.path_candidates.findResult{ candidate ->

      // construct binary path for current candidate
      def binary_path = new File( candidate + File.separator + this.binary_name )

        // verify that the found path points to a file and that the file is executable
        if( binary_path.isFile() == true && binary_path.canExecute() ) {
          return binary_path
        }
        else {
          return null
        }

    } // end closure

    return result

  }

  // performs a recursive search in each provided directory
  protected String deep_search() {

    def result = this.path_candidates.findResult{ candidate ->

      def path = new File(candidate)

      // create file tree of directories containing the files with
      // the desired name
      def files = this.project.fileTree(path) {
        include "**/$binary_name"
      }

      // find first path that points to a file which is executable
      return files.findResult{ found ->
        if( found.isFile() == true && found.canExecute() == true ) {
          return found
        }
        else {
          return null
        }
      }

    } // end closure

    return result

  }

}
