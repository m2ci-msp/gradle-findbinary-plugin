package org.m2ci.msp.gradle.tasks.findbinary

import groovy.lang.Closure;
import org.gradle.util.Configurable;
import org.gradle.util.ConfigureUtil;


import org.gradle.api.Project

class FindBinaryExtension implements Configurable<FindBinaryExtension> {

    private project = null
    private String path = null;

    @Override
    public FindBinaryExtension configure(@SuppressWarnings("rawtypes") Closure cl) {

      def binary_finder = ConfigureUtil.configure(cl, new FindBinaryLinux(this.project));

      this.path = binary_finder.search();

      return this;
    }

    public String getPath() {
      return this.path
    }

    public FindBinaryExtension(Project project) {
      this.project = project
    }

}
