package com.github.tanghuibo.remotedebuglikelocal.ui.compoment;

import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.actionSystem.impl.ActionButton;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.ex.MultiLineLabel;
import com.intellij.ui.IconManager;
import com.intellij.ui.IconWrapperWithToolTip;
import com.intellij.ui.components.panels.Wrapper;
import org.apache.batik.gvt.event.AWTEventDispatcher;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * RunLogItemWrapper
 *
 * @author tanghuibo
 * @date 2021/9/28 15:43
 */
public class RunLogItemWrapper {
    private JComponent btn;
    private Wrapper root;
    private MultiLineLabel text;

    static JComponent build(Project project) {
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

        runLogItemWrapper.btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        runLogItemWrapper.text = new MultiLineLabel();
        runLogItemWrapper.text.setCursor(new Cursor(Cursor.HAND_CURSOR));
        runLogItemWrapper.text.addMouseListener(new AWTEventDispatcher() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                System.out.println(runLogItemWrapper);
                System.out.println(templatePresentation);
            }
        });
        runLogItemWrapper.text.setText("你好adasdasdasdasdasdasasdasdasdassssssssssssss啊");
        runLogItemWrapper.text.setToolTipText("你好adasdasdasdasdasdasasdasdasdassssssssssssss啊");

        runLogItemWrapper.root.add(runLogItemWrapper.text, BorderLayout.CENTER);

        return runLogItemWrapper.root;
    }
}
