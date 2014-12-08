package org.m2ci.msp.gradle.tasks.findbinary

import org.gradle.api.Plugin
import org.gradle.api.Project

public class FindBinaryTaskPlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {
        project.extensions.create("findbinary", FindBinaryExtension, project)
    }

}
