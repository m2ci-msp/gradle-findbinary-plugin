package org.m2ci.msp.gradle.tasks.findbinary

import org.gradle.api.Project

// Specialized class for finding binaries on a windows system
class FindBinaryWindows extends FindBinary {

    public FindBinaryWindows(Project project) {

      // call constructor of parent class
      super(project)

      // activate recursive search
      super.recursive = true

      // add program files paths
      super.path_candidates.add(System.getenv('ProgramFiles'))
      super.path_candidates.add(System.getenv('ProgramFiles(x86)'))

      // remove duplicate entries
      super.path_candidates.unique()

    }

}
