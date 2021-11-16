package com.github.tanghuibo.remotedebuglikelocal.ui.factory;

import com.github.tanghuibo.remotedebuglikelocal.ui.compoment.DebugDashbordJComponent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;


/**
 * DebugLogToolWindowFactory
 *
 * @author tanghuibo
 * @date 2021/9/27 21:12
 */
public class DebugToolWindowFactory implements ToolWindowFactory {
    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        // 将显示面板添加到显示区
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        JComponent consoleLogJComponent = new DebugDashbordJComponent(project);
        Content content = contentFactory.createContent(consoleLogJComponent, "Control", false);
        toolWindow.getContentManager().addContent(content);
    }
}
