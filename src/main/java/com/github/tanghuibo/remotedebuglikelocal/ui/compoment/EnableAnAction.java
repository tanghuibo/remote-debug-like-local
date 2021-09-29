package com.github.tanghuibo.remotedebuglikelocal.ui.compoment;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.ui.IconManager;
import com.intellij.ui.IconWrapperWithToolTip;
import org.jetbrains.annotations.NotNull;

/**
 * EnableAnAction
 *
 * @author tanghuibo
 * @date 2021/9/29 16:46
 */
public abstract class EnableAnAction extends AnAction {

    private Boolean enable;

    public EnableAnAction(String iconPath) {
        super(IconManager.getInstance().getIcon(iconPath, IconWrapperWithToolTip.class));
    }

    @Override
    public void update(@NotNull AnActionEvent e) {
        e.getPresentation().setEnabled(enable);
        super.update(e);
    }

    public void setEnabled(boolean enable) {
        this.enable = enable;
        getTemplatePresentation().setEnabled(enable);
    }
}
