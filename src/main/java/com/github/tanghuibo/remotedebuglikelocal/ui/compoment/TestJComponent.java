package com.github.tanghuibo.remotedebuglikelocal.ui.compoment;

import com.github.tanghuibo.remotedebuglikelocal.utils.ViewDebugUtils;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.actionSystem.impl.ActionToolbarImpl;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Splitter;
import com.intellij.openapi.wm.WindowManager;
import com.intellij.openapi.wm.impl.IdeGlassPaneImpl;
import com.intellij.ui.IconManager;
import com.intellij.ui.IconWrapperWithToolTip;
import com.intellij.ui.JBSplitter;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.components.panels.Wrapper;
import com.intellij.util.ui.JBEmptyBorder;
import org.apache.batik.gvt.event.AWTEventDispatcher;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.AWTEventListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * ConsoleLogJComponent
 *
 * @author tanghuibo
 * @date 2021/9/27 21:18
 */
public class TestJComponent extends JComponent {



    public TestJComponent(Project project) {
        Wrapper wrapper = new Wrapper();

        wrapper.setSize(getSize());
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                wrapper.setSize(getSize());
            }
        });

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
        toolbar.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.decode("#323232")));


        wrapper.add(toolbar, BorderLayout.WEST);


        JBSplitter jbSplitter = new JBSplitter();

        jbSplitter.setDividerPositionStrategy(Splitter.DividerPositionStrategy.KEEP_FIRST_SIZE);


        JComponent jComponent = RunLogListWrapper.build(project);
        JBScrollPane jbScrollPane = new JBScrollPane(jComponent);
        jbScrollPane.setSize(50, jbScrollPane.getHeight());
        jbSplitter.setFirstComponent(jbScrollPane);

        JButton jButton2 = new JButton("test2");
        jButton2.setSize(200, 200);
        jbSplitter.setSecondComponent(jButton2);




        wrapper.add(jbSplitter);

        add(wrapper);



    }


}
