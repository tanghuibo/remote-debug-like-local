package com.github.tanghuibo.remotedebuglikelocal.ui.compoment;

import com.github.tanghuibo.remotedebuglikelocal.utils.ViewDebugUtils;
import com.intellij.execution.filters.TextConsoleBuilderFactory;
import com.intellij.execution.ui.ConsoleView;
import com.intellij.execution.ui.ConsoleViewContentType;
import com.intellij.ide.CommonActionsManager;
import com.intellij.ide.OccurenceNavigator;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.actionSystem.impl.ActionToolbarImpl;
import com.intellij.openapi.project.Project;
import com.intellij.ui.IconManager;
import com.intellij.ui.IconWrapperWithToolTip;
import com.intellij.ui.components.panels.Wrapper;
import com.intellij.util.ui.JBUI;
import com.intellij.xdebugger.impl.actions.XDebuggerActions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

/**
 * ConsoleLogJComponent
 *
 * @author tanghuibo
 * @date 2021/9/27 21:18
 */
public class TestJComponent extends JComponent {



    public TestJComponent(Project project) {
        Wrapper wrapper = new Wrapper();

        final DefaultActionGroup framesGroup = new DefaultActionGroup();

        framesGroup.add(new AnAction(IconManager.getInstance().getIcon("actions/refresh.svg", IconWrapperWithToolTip.class)) {
            @Override
            public void actionPerformed(@NotNull AnActionEvent e) {
            }

        });


        final ActionToolbarImpl toolbar =
                (ActionToolbarImpl)ActionManager.getInstance().createActionToolbar("consoleFresss", framesGroup, false);
        toolbar.setReservePlaceAutoPopupIcon(false);
        toolbar.getComponent().setBorder(JBUI.Borders.empty(1));


        wrapper.add(toolbar, BorderLayout.WEST);

        wrapper.setSize(500, 500);

        JButton jButton1 = new JButton("test1");
        jButton1.setSize(200, 200);
        wrapper.add(jButton1);


        add(wrapper);

    }
}
