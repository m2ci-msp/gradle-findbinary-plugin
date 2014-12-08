package org.m2ci.msp.gradle.tasks.findbinary

import groovy.lang.Closure;
import org.gradle.util.Configurable;
import org.gradle.util.ConfigureUtil;


import org.gradle.api.Project

class FindBinaryExtension implements Configurable<FindBinaryExtension> {

    private String path = null;

    @Override
    public FindBinaryExtension configure(@SuppressWarnings("rawtypes") Closure cl) {

      def binary_finder = ConfigureUtil.configure(cl, new FindBinaryLinux());

      this.path = binary_finder.search();

      return this;
    }

    public String get_path() {
      return this.path
    }

    public FindBinaryExtension(Project project) {
    }

}
