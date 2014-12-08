package org.m2ci.msp.gradle.tasks.findbinary

import java.io.IOException;

import groovy.lang.Closure;
import org.gradle.util.Configurable;
import org.gradle.util.ConfigureUtil;


import org.gradle.api.Project

class FindBinaryExtension implements Configurable<FindBinaryExtension> {

    private String path;

    @Override
    public FindBinaryExtension configure(@SuppressWarnings("rawtypes") Closure cl) {

      def binary_finder = ConfigureUtil.configure(cl, new FindBinaryLinux());

      try {

        this.path = binary_finder.search();

      } catch (IOException e) {
        throw new IllegalStateException("Path could not be found: ", e);
      }
      return this;
    }

    public String get_path() {
      return this.path
    }

    public FindBinaryExtension(Project project) {
    }

}
