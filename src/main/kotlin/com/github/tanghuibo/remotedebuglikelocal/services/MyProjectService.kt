package com.github.tanghuibo.remotedebuglikelocal.services

import com.intellij.openapi.project.Project
import com.github.tanghuibo.remotedebuglikelocal.MyBundle

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}
