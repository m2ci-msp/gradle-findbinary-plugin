package org.m2ci.msp.gradle.tasks.findbinary

import org.gradle.api.Project

// Specialized class for finding binaries on a windows system
class FindBinaryWindows extends FindBinary {

  public FindBinaryWindows(Project project) {

    super(project)

    // activate recursive search
    super.recursive = true

    // add program files paths (look in both Program Files and Program Files (x86))

    // force using the Program Files directory with no suffix, as 
    // "Program  Files (x86)" is returned if using 32 bit JVM
    def programFiles = System.getenv('ProgramFiles').replace(" (x86)", "")

    // explicitly ask for x86
    def programFiles86 = System.getenv('ProgramFiles(x86)')

    if( programFiles != null ) {
      super.pathCandidates.add(programFiles)
    }

    if( programFiles86 != null ) {
      super.pathCandidates.add(programFiles86)
    }

    // remove duplicate entries
    super.pathCandidates.unique()

  }

  @Override
  public void binary(String name) {

    this.binaryName = name + ".exe"

  }

  @Override
  protected void addPathsFromEnvironment() {
    def path = System.getenv()["Path"]
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
