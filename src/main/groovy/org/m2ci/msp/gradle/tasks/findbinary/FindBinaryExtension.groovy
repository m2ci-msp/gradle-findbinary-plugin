package org.m2ci.msp.gradle.tasks.findbinary

import org.apache.commons.lang3.SystemUtils

import groovy.lang.Closure;
import org.gradle.util.Configurable;
import org.gradle.util.ConfigureUtil;


import org.gradle.api.Project

class FindBinaryExtension implements Configurable<FindBinaryExtension> {

    private project = null
    private String path = null;

    @Override
    public FindBinaryExtension configure(@SuppressWarnings("rawtypes") Closure cl) {

      def binaryFinder = null

      if( SystemUtils.IS_OS_WINDOWS ) {
        binaryFinder = ConfigureUtil.configure(cl, new FindBinaryWindows(this.project));
      }
      else if ( SystemUtils.IS_OS_MAC) {
        binaryFinder = ConfigureUtil.configure(cl, new FindBinaryMac(this.project));
      }
      else {
        binaryFinder = ConfigureUtil.configure(cl, new FindBinary(this.project));
      }

      this.path = binaryFinder.search();

      if( this.path == null ) {
        println "Warning: Binary was not found."
      }

      return this;
    }

    public String getPath() {
      return this.path
    }

    public FindBinaryExtension(Project project) {
      this.project = project
    }

}
