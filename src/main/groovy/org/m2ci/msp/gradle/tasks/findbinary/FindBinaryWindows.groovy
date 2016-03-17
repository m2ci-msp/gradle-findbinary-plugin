package org.m2ci.msp.gradle.tasks.findbinary

import org.gradle.api.Project

// Specialized class for finding binaries on a windows system
class FindBinaryWindows extends FindBinary {

  public FindBinaryWindows(Project project) {

    super(project)

    // activate recursive search
    super.recursive = true

    // add program files paths
    def programFiles = System.getenv('ProgramFiles')
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
