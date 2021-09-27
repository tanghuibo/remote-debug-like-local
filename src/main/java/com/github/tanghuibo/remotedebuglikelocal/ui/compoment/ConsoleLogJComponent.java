package com.github.tanghuibo.remotedebuglikelocal.ui.compoment;

import com.github.tanghuibo.remotedebuglikelocal.utils.ViewDebugUtils;
import com.intellij.execution.filters.TextConsoleBuilderFactory;
import com.intellij.execution.ui.ConsoleView;
import com.intellij.execution.ui.ConsoleViewContentType;
import com.intellij.openapi.project.Project;

import javax.swing.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

/**
 * ConsoleLogJComponent
 *
 * @author tanghuibo
 * @date 2021/9/27 21:18
 */
public class ConsoleLogJComponent extends JComponent {



    public ConsoleLogJComponent(Project project) {
        ConsoleView consoleView = TextConsoleBuilderFactory.getInstance().createBuilder(project).getConsole();
        JComponent component = consoleView.getComponent();
        component.setSize(getSize().width, getSize().height);
        addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                component.setSize(getSize().width, getSize().height);
            }

            @Override
            public void componentMoved(ComponentEvent e) {

            }

            @Override
            public void componentShown(ComponentEvent e) {

            }

            @Override
            public void componentHidden(ComponentEvent e) {

            }
        });
        add(component);
        new Thread(() -> {
            ViewDebugUtils viewDebugUtils = null;
            while (true) {
                try {
                    Thread.sleep(1000);
                    consoleView.print("hello\n", ConsoleViewContentType.LOG_ERROR_OUTPUT);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }).start();
    }
}
