package org.m2ci.msp.gradle.tasks.findpath

import java.io.IOException;


class FindBinaryLinux {
    private List<String> path_candidates

    public FindBinaryLinux() {
      this.binary = null

      // initialize candidates to OS default locations
      this.path_candidates.add("/usr/bin")
      this.path_candidates.add("/usr/local/bin")
      this.path_candidates.add("/opt/bin")

      // add possible local user binaries
      this.path_candidates.add(System.getProperty("user.home") + "/usr/bin")

    }

    // adds a user provided path to the candidates
    // TODO: maybe prepend it to the list so that user paths have priority
    public void path(String new_path) {
      this.path_candidates.add(new_path)
    }

    // sets the binary to look for
    public void binary(String binary) {
      this.binary = binary
    }

    public String search() {
      def result = this.path_candidates.findResult{ candidate ->
        def binary_path = new File( candidate + this.binary )

        if( binary_path.exists() == true ) {
          return binary_path
        }
        else {
          return null
        }
      }

      return result

    }

}
