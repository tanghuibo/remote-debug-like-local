package com.github.tanghuibo.remotedebuglikelocal.ui.compoment;



import com.github.tanghuibo.remotedebuglikelocal.dto.RemoteInfoDto;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.IconLoader;
import com.intellij.ui.IconManager;
import com.intellij.ui.IconWrapperWithToolTip;
import com.intellij.ui.components.JBList;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * RunLogListWrapper
 *
 * @author tanghuibo
 * @date 2021/9/28 15:51
 */
public class RunLogListWrapper {
    List<RemoteInfoDto> remoteInfoDtoList;

    List<Consumer<RemoteInfoDto>> selectListenerList;

    RemoteInfoDto selected;

    private JBList<Object> jbList;

    public RunLogListWrapper(Project project, List<RemoteInfoDto> remoteInfoDtoList) {
        this.remoteInfoDtoList = remoteInfoDtoList;
        this.selectListenerList = new ArrayList<>();
    }

    public Component getComponent() {
        this.jbList = new JBList<>();
        jbList.setListData(remoteInfoDtoList.toArray(new RemoteInfoDto[0]));
        jbList.setCellRenderer(new DefaultListCellRenderer() {
            Icon enableIcon = IconManager.getInstance().getIcon("/actions/find.svg", IconWrapperWithToolTip.class);
            Icon disableIcon = IconLoader.getDisabledIcon(enableIcon);

            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component component = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if(value instanceof RemoteInfoDto) {
                    setIcon(((RemoteInfoDto) value).getLogStatus() == 0 ? disableIcon : enableIcon);
                }
                return component;
            }
        });
        jbList.addListSelectionListener(event -> {
            Object selectedValue = jbList.getSelectedValue();
            if(selectedValue instanceof RemoteInfoDto) {
                selected = (RemoteInfoDto) selectedValue;
                for (Consumer<RemoteInfoDto> remoteInfoDtoConsumer : selectListenerList) {
                    remoteInfoDtoConsumer.accept(selected);
                }
            }


        });
        jbList.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        return jbList;
    }

    public void addSelectListen(Consumer<RemoteInfoDto> listener) {
        selectListenerList.add(listener);
    }


    public RemoteInfoDto getSelected() {
        return selected;
    }

    public void updateUi() {
        jbList.updateUI();
    }
}
