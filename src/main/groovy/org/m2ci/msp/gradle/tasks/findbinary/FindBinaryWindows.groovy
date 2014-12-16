package org.m2ci.msp.gradle.tasks.findbinary

import org.gradle.api.Project

// Specialized class for finding binaries on a windows system
class FindBinaryWindows extends FindBinary {

    public FindBinaryWindows(Project project) {

      this.project = project

      // initialize candidates to path environment
      path_candidates = System.getenv()["Path"].tokenize(File.pathSeparator)

      // activate recursive search
      super.recursive = true

      // add program files paths
      super.path_candidates.add(System.getenv('ProgramFiles'))
      super.path_candidates.add(System.getenv('ProgramFiles(x86)'))

      // remove duplicate entries
      super.path_candidates.unique()

    }

}
