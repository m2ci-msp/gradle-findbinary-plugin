package org.m2ci.msp.gradle.tasks.findbinary

import org.gradle.api.Project

// Specialized class for finding binaries on a linux system
class FindBinaryLinux extends FindBinary {

    public FindBinaryLinux(Project project) {

      // call constructor of parent class
      super(project)

      // add OS default locations
      super.path_candidates.add("/bin")
      super.path_candidates.add("/usr/bin")
      super.path_candidates.add("/usr/local/bin")
      super.path_candidates.add("/opt/bin")

      // remove duplicate entries
      super.path_candidates.unique()

    }

}
