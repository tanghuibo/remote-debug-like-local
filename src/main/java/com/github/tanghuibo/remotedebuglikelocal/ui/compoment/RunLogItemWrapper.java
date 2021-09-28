package com.github.tanghuibo.remotedebuglikelocal.ui.compoment;

import com.github.tanghuibo.remotedebuglikelocal.utils.ViewDebugUtils;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.actionSystem.impl.ActionButton;
import com.intellij.openapi.actionSystem.impl.ActionButtonWithText;
import com.intellij.openapi.actionSystem.impl.ActionToolbarImpl;
import com.intellij.ui.IconManager;
import com.intellij.ui.IconWrapperWithToolTip;
import com.intellij.ui.components.panels.HorizontalLayout;
import com.intellij.ui.components.panels.VerticalLayout;
import com.intellij.ui.components.panels.Wrapper;
import com.intellij.util.ui.JBEmptyBorder;
import org.apache.batik.gvt.event.AWTEventDispatcher;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.Field;

/**
 * RunLogItemWrapper
 *
 * @author tanghuibo
 * @date 2021/9/28 15:43
 */
public class RunLogItemWrapper {
    private JComponent btn;
    private Wrapper root;
    private JLabel text;

    static JComponent build() {
        RunLogItemWrapper runLogItemWrapper = new RunLogItemWrapper();
        runLogItemWrapper.root = new Wrapper();
        BorderLayout horizontalLayout = new BorderLayout();
        runLogItemWrapper.root.setLayout(horizontalLayout);

        Icon icon = IconManager.getInstance().getIcon("actions/execute.svg", IconWrapperWithToolTip.class);

        AnAction anAction = new AnAction(icon) {
            @Override
            public void actionPerformed(@NotNull AnActionEvent e) {
                Icon icon = IconManager.getInstance().getIcon("actions/pause.svg", IconWrapperWithToolTip.class);
                this.getTemplatePresentation().setIcon(icon);
            }
        };
        Presentation templatePresentation = anAction.getTemplatePresentation();
        runLogItemWrapper.btn = new ActionButton(anAction, templatePresentation,"thb-test", new Dimension(30, 30)) {
            @Override
            public boolean isEnabled() {
                return true;
            }
        };
        templatePresentation.setEnabled(true);
        runLogItemWrapper.root.add(runLogItemWrapper.btn, BorderLayout.WEST);


        runLogItemWrapper.text = new JLabel();
        runLogItemWrapper.text.addMouseListener(new AWTEventDispatcher() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                System.out.println(runLogItemWrapper);
                System.out.println(templatePresentation);
            }
        });
        runLogItemWrapper.text.setText("你好adasdasdasdasdasdasasdasdasdassssssssssssss啊");


        runLogItemWrapper.root.add(runLogItemWrapper.text, BorderLayout.CENTER);

        return runLogItemWrapper.root;
    }
}
