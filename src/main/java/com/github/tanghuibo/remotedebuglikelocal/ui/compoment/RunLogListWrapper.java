package com.github.tanghuibo.remotedebuglikelocal.ui.compoment;



import com.intellij.openapi.project.Project;
import com.intellij.ui.components.panels.VerticalLayout;
import com.intellij.ui.components.panels.Wrapper;

import javax.swing.*;

/**
 * RunLogLisgtWrapper
 *
 * @author tanghuibo
 * @date 2021/9/28 15:51
 */
public class RunLogListWrapper {

    public static JComponent build(Project project) {
        Wrapper wrapper = new Wrapper();
        VerticalLayout verticalLayout = new VerticalLayout(3);
        wrapper.setLayout(verticalLayout);
        wrapper.add(RunLogItemWrapper.build(project));
        wrapper.add(RunLogItemWrapper.build(project));
        wrapper.add(RunLogItemWrapper.build(project));
        wrapper.add(RunLogItemWrapper.build(project));
        wrapper.add(RunLogItemWrapper.build(project));
        return wrapper;
    }
}
