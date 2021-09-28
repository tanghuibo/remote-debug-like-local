package com.github.tanghuibo.remotedebuglikelocal.ui.compoment;



import com.intellij.ui.components.panels.VerticalLayout;
import com.intellij.ui.components.panels.Wrapper;

import javax.swing.*;

/**
 * RunLogLisgtWrapper
 *
 * @author tanghuibo
 * @date 2021/9/28 15:51
 */
public class RunLogListWrapper {

    public static JComponent build() {
        Wrapper wrapper = new Wrapper();
        VerticalLayout verticalLayout = new VerticalLayout(3);
        wrapper.setLayout(verticalLayout);
        wrapper.add(RunLogItemWrapper.build());
        wrapper.add(RunLogItemWrapper.build());
        wrapper.add(RunLogItemWrapper.build());
        wrapper.add(RunLogItemWrapper.build());
        wrapper.add(RunLogItemWrapper.build());
        return wrapper;
    }
}
