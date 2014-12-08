package org.m2ci.msp.gradle.tasks.findpath

import org.gradle.api.Plugin
import org.gradle.api.Project

public class FindBinaryTaskPlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {
        project.extensions.create("findpath", FindBinaryExtension, project)
    }

}
