package org.m2ci.msp.gradle.tasks.findbinary

// Base class for finding binaries
class FindBinary {
  protected List<String> path_candidates = []
  protected String binary_name = ""

  public FindBinary() {

      // initialize candidates to path environment
      path_candidates = System.getenv()["PATH"].tokenize(File.pathSeparator)

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

  // search for binary, return first found version or null on failure
  public String search() {

    def result = this.path_candidates.findResult{ candidate ->

      // construct binary path for current candidate
      def binary_path = new File( candidate + File.separator + this.binary_name )

        if( binary_path.exists() == true ) {
          return binary_path
        }
        else {
          return null
        }

    } // end closure

    return result

  }

}
