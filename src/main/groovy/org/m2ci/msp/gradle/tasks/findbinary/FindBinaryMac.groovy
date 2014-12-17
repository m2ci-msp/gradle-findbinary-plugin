package org.m2ci.msp.gradle.tasks.findbinary

import org.gradle.api.Project

// Specialized class for finding binaries on a Mac system
class FindBinaryMac extends FindBinary {

    public FindBinaryMac(Project project) {

      // call constructor of parent class
      super(project)

    }

    @Override
    public void binary(String name) {

      super.binary_name = name

      // generate path to Application bundle that might exist
      // and add it to the beginning of the list
      super.path_candidates.add(0,
        File.separator + "Applications" + File.separator +
        "${name}.app" + File.separator + "Contents" +
        File.separator +
        "MacOS"
        )

    }

}
