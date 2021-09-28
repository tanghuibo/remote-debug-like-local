package com.github.tanghuibo.remotedebuglikelocal.ui.compoment;

import com.github.tanghuibo.remotedebuglikelocal.utils.ViewDebugUtils;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.actionSystem.impl.ActionToolbarImpl;
import com.intellij.openapi.project.Project;
import com.intellij.ui.IconManager;
import com.intellij.ui.IconWrapperWithToolTip;
import com.intellij.ui.JBSplitter;
import com.intellij.ui.components.panels.Wrapper;
import com.intellij.util.ui.JBEmptyBorder;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

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

        ActionToolbarImpl toolbar =
                (ActionToolbarImpl)ActionManager.getInstance().createActionToolbar("thb-test1", framesGroup, false);


        framesGroup.add(new AnAction(IconManager.getInstance().getIcon("actions/refresh.svg", IconWrapperWithToolTip.class)) {

            ViewDebugUtils viewDebugUtils = null;

            @Override
            public void actionPerformed(@NotNull AnActionEvent e) {
                System.out.println(viewDebugUtils);
                System.out.println(toolbar);
            }

        });


        toolbar.setReservePlaceAutoPopupIcon(false);
        JBEmptyBorder jbEmptyBorder = new JBEmptyBorder(2);
        toolbar.setBorder(jbEmptyBorder);


        wrapper.add(toolbar, BorderLayout.WEST);

        wrapper.setSize(500, 500);

        JBSplitter jbSplitter = new JBSplitter();


        jbSplitter.setFirstComponent(RunLogListWrapper.build());

        JButton jButton2 = new JButton("test2");
        jButton2.setSize(200, 200);
        jbSplitter.setSecondComponent(jButton2);




        wrapper.add(jbSplitter);

        add(wrapper);

    }
}
