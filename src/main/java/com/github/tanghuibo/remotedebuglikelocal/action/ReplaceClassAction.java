package com.github.tanghuibo.remotedebuglikelocal.action;

import com.github.tanghuibo.remotedebuglikelocal.utils.LogUtils;
import com.intellij.debugger.DebuggerManager;
import com.intellij.debugger.engine.DebugProcessImpl;
import com.intellij.debugger.engine.DebuggerManagerThreadImpl;
import com.intellij.debugger.impl.DebuggerManagerImpl;
import com.intellij.debugger.impl.DebuggerSession;
import com.intellij.debugger.impl.PrioritizedTask;
import com.intellij.debugger.jdi.VirtualMachineProxyImpl;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.util.Disposer;
import com.intellij.openapi.vfs.VirtualFile;
import com.sun.jdi.ReferenceType;
import com.sun.jdi.VirtualMachine;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.org.objectweb.asm.ClassReader;

import java.util.*;

/**
 * @author tanghuibo
 */
public class ReplaceClassAction extends AnAction {

    private final Disposable myDisposable = Disposer.newDisposable();
    @Override
    public void actionPerformed(AnActionEvent event) {
        DebuggerManagerThreadImpl testInstance = DebuggerManagerThreadImpl.createTestInstance(myDisposable, event.getProject());
        testInstance.invoke(PrioritizedTask.Priority.HIGH, () -> redefineClass(event));
    }

    private void redefineClass(AnActionEvent event) {
        byte[] bytes = getFileByte(event);

        if(bytes == null) {
            LogUtils.logError("读取文件失败");
            return;
        }
        String className = getClassName(bytes);

        if(StringUtils.isEmpty(className)) {
            LogUtils.logError("读取 class 文件失败");
            return;
        }

        DebuggerManager debuggerManager = DebuggerManager.getInstance(event.getProject());
        if(debuggerManager instanceof DebuggerManagerImpl) {
            Collection<DebuggerSession> sessions = ((DebuggerManagerImpl) debuggerManager).getSessions();
            if(sessions.size() == 0) {
                LogUtils.logError("缺少 debug 会话，请建立会话后重试");
                return;
            }
            for (DebuggerSession session : sessions) {
                try {
                    redefineClass(bytes, className, session);
                } catch (Exception e) {
                    LogUtils.logError(session.getSessionName() + ":替换 class 失败", e);
                }
            }
        } else {
            LogUtils.logError("获取 DebuggerManagerImpl 失败");
        }
    }

    @Nullable
    private String getClassName(byte[] bytes) {
        try {
            ClassReader classReader = new ClassReader(bytes);
            return classReader.getClassName().replace("/", ".");
        } catch (Exception e) {
            LogUtils.logError("读取class文件失败", e);
            return null;
        }
    }

    private byte[] getFileByte(AnActionEvent event) {
        return Optional.ofNullable(event.getData(CommonDataKeys.VIRTUAL_FILE)).map(file -> {
            try {
                return file.contentsToByteArray();
            } catch (Exception e) {
                LogUtils.logError("读取文件失败", e);
                return null;
            }
        }).orElse(null);
    }

    private void redefineClass(byte[] bytes, String className, DebuggerSession session) {
        VirtualMachine virtualMachine = Optional.of(session)
                .map(DebuggerSession::getProcess)
                .map(DebugProcessImpl::getVirtualMachineProxy)
                .map(VirtualMachineProxyImpl::getVirtualMachine)
                .orElse(null);
        if(virtualMachine == null || !virtualMachine.canRedefineClasses()) {
            LogUtils.logInfo(session.getSessionName() + ":不允许改 class");
            return;
        }
        Map<ReferenceType, byte[]> redefineMap = new HashMap<>(1);
        List<ReferenceType> referenceTypes = virtualMachine.classesByName(className);
        if(referenceTypes == null || referenceTypes.size() == 0) {
            LogUtils.logInfo(session.getSessionName() + ":没有 class" + className);
            return;
        }
        for (ReferenceType referenceType : referenceTypes) {
            redefineMap.put(referenceType, bytes);
        }
        virtualMachine.redefineClasses(redefineMap);
        LogUtils.logInfo(session.getSessionName() + ":替换 class " + className + " 成功");
    }

    @Override
    public void update(AnActionEvent event) {
        String extension = Optional.ofNullable(event.getData(CommonDataKeys.VIRTUAL_FILE)).map(VirtualFile::getExtension).orElse(null);
        event.getPresentation().setVisible("class".equals(extension));
    }




}
