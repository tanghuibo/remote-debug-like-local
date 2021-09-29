package com.github.tanghuibo.remotedebuglikelocal.ui.compoment;

import com.alibaba.fastjson.JSON;
import com.github.tanghuibo.remotedebuglikelocal.dto.RemoteInfoDto;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.actionSystem.impl.ActionToolbarImpl;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Splitter;
import com.intellij.ui.JBSplitter;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.components.panels.Wrapper;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.List;

/**
 * ConsoleLogJComponent
 *
 * @author tanghuibo
 * @date 2021/9/27 21:18
 */
public class TestJComponent extends JComponent {

    private final EnableAnAction logAnAction;
    private final EnableAnAction stopAction;
    private final RunLogListWrapper runLogListWrapper;
    private final ActionToolbarImpl toolbar;
    private final EnableAnAction refreshAction;
    private final DefaultActionGroup framesGroup;

    List<RemoteInfoDto> remoteInfoDtoList;




    public TestJComponent(Project project) {
        remoteInfoDtoList = JSON.parseArray("[{\n" +
                "  \"env\": \"dev\",\n" +
                "  \"host\": \"127.0.0.1\",\n" +
                "  \"port\": \"8080\",\n" +
                "  \"logStatus\": 0\n" +
                "},{\n" +
                "  \"env\": \"test\",\n" +
                "  \"host\": \"10.40.1.105\",\n" +
                "  \"port\": \"80\",\n" +
                "  \"logStatus\": 0\n" +
                "},{\n" +
                "  \"env\": \"prod\",\n" +
                "  \"host\": \"www.test.com\",\n" +
                "  \"port\": \"80\",\n" +
                "  \"logStatus\": 0\n" +
                "}]", RemoteInfoDto.class);
        Wrapper wrapper = new Wrapper();

        wrapper.setSize(getSize());
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                wrapper.setSize(getSize());
            }
        });


        this.refreshAction = new EnableAnAction("actions/refresh.svg") {

            @Override
            public void actionPerformed(@NotNull AnActionEvent e) {

            }
        };

        this.stopAction = new EnableAnAction("actions/suspend.svg") {

            @Override
            public void actionPerformed(@NotNull AnActionEvent e) {
                Integer logStatus = 0;
                runLogListWrapper.getSelected().setLogStatus(logStatus);
                updateLogStatus(logStatus);
                runLogListWrapper.updateUi();
            }
        };

        this.logAnAction = new EnableAnAction("actions/find.svg") {

            @Override
            public void actionPerformed(@NotNull AnActionEvent e) {
                Integer logStatus = 1;
                runLogListWrapper.getSelected().setLogStatus(logStatus);
                updateLogStatus(logStatus);
                runLogListWrapper.updateUi();
            }

            @Override
            public void update(@NotNull AnActionEvent e) {
                super.update(e);
            }
        };


        this.framesGroup = new DefaultActionGroup();
        logAnAction.setEnabled(false);
        stopAction.setEnabled(false);
        refreshAction.setEnabled(true);
        framesGroup.add(logAnAction);
        framesGroup.add(stopAction);
        framesGroup.addSeparator();
        framesGroup.add(refreshAction);

        this.toolbar =
                (ActionToolbarImpl)ActionManager.getInstance().createActionToolbar("thb-test1", framesGroup, false);

        wrapper.add(toolbar, BorderLayout.WEST);


        JBSplitter jbSplitter = new JBSplitter();

        jbSplitter.setDividerPositionStrategy(Splitter.DividerPositionStrategy.KEEP_FIRST_SIZE);

        this.runLogListWrapper = new RunLogListWrapper(project, remoteInfoDtoList);
        runLogListWrapper.addSelectListen(item -> updateLogStatus(item.getLogStatus()));
        JBScrollPane jbScrollPane = new JBScrollPane(runLogListWrapper.getComponent());
        jbScrollPane.setSize(50, jbScrollPane.getHeight());
        jbSplitter.setFirstComponent(jbScrollPane);

        JButton jButton2 = new JButton("test2");
        jButton2.setSize(200, 200);
        jbSplitter.setSecondComponent(jButton2);
        wrapper.add(jbSplitter);
        add(wrapper);

    }

    public void updateLogStatus(Integer logStatus) {
        if(logStatus == 0) {
            logAnAction.setEnabled(true);
            stopAction.setEnabled(false);

        } else if(logStatus == 1) {
            logAnAction.setEnabled(false);
            stopAction.setEnabled(true);
        }
    }


}
