package com.github.tanghuibo.remotedebuglikelocal.ui.compoment;

import com.github.tanghuibo.remotedebuglikelocal.dto.RemoteDebugDto;
import com.github.tanghuibo.remotedebuglikelocal.dto.RemoteInfoDto;
import com.github.tanghuibo.remotedebuglikelocal.utils.ConfigUtils;
import com.github.tanghuibo.remotedebuglikelocal.utils.DebugConfigUtils;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.actionSystem.impl.ActionToolbarImpl;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.project.Project;
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
public class DebugDashbordJComponent extends JComponent {

    private final EnableAnAction debugAction;
    private final RemoteVmListWrapper remoteVmListWrapper;

    public DebugDashbordJComponent(Project project) {
        List<RemoteInfoDto> remoteInfoDtoList = ConfigUtils.read(project);
        Wrapper wrapper = new Wrapper();
        wrapper.setSize(getSize());
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                wrapper.setSize(getSize());
            }
        });


        EnableAnAction refreshAction = new EnableAnAction("actions/refresh.svg") {

            @Override
            public void actionPerformed(@NotNull AnActionEvent e) {
                List<RemoteInfoDto> remoteInfoDtoList = ConfigUtils.read(project);
                remoteVmListWrapper.setRemoteInfoDtoList(remoteInfoDtoList);
                debugAction.setEnabled(false);
            }
        };


        this.debugAction = new EnableAnAction("actions/find.svg") {

            @Override
            public void actionPerformed(@NotNull AnActionEvent e) {
                final RemoteInfoDto selected = remoteVmListWrapper.getSelected();
                RemoteDebugDto data = new RemoteDebugDto();
                data.setHost(selected.getHost());
                data.setPort(selected.getPort());
                data.setConfigName(selected.toString());
                ModuleManager moduleManager = ModuleManager.getInstance(project);
                Module[] modules = moduleManager.getModules();
                if(modules.length > 0) {
                    data.setModuleName(modules[0].getName());
                }
                DebugConfigUtils.addDebug(project, data);
            }
        };


        DefaultActionGroup framesGroup = new DefaultActionGroup();
        debugAction.setEnabled(false);
        refreshAction.setEnabled(true);
        framesGroup.add(debugAction);
        framesGroup.addSeparator();
        framesGroup.add(refreshAction);

        ActionToolbarImpl toolbar = (ActionToolbarImpl) ActionManager.getInstance().createActionToolbar("rd-tool-bar", framesGroup, false);

        wrapper.add(toolbar, BorderLayout.WEST);

        this.remoteVmListWrapper = new RemoteVmListWrapper(project, remoteInfoDtoList);
        remoteVmListWrapper.addSelectListen(item -> debugAction.setEnabled(true));
        JBScrollPane jbScrollPane = new JBScrollPane(remoteVmListWrapper.getComponent());
        wrapper.add(jbScrollPane);
        add(wrapper);

    }


}
