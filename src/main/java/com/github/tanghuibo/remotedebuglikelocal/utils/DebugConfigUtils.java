package com.github.tanghuibo.remotedebuglikelocal.utils;

import com.github.tanghuibo.remotedebuglikelocal.dto.RemoteDebugDto;
import com.intellij.execution.Executor;
import com.intellij.execution.ProgramRunnerUtil;
import com.intellij.execution.RunManager;
import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.execution.executors.DefaultDebugExecutor;
import com.intellij.execution.impl.RunnerAndConfigurationSettingsImpl;
import com.intellij.execution.remote.RemoteConfiguration;
import com.intellij.execution.remote.RemoteConfigurationType;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.project.Project;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

/**
 * DebugConfigUtils
 *
 * @author tanghuibo
 * @date 2021/9/26 10:29
 */
public class DebugConfigUtils {

    /**
     * 添加debug
     * @param project
     * @param remoteDebugDto
     */
    public static void addDebug(Project project, RemoteDebugDto remoteDebugDto) {
        ConfigurationFactory configurationFactory = RemoteConfigurationType.getInstance();
        RunManager runManager = RunManager.getInstance(project);
        RunnerAndConfigurationSettingsImpl configuration = runManager.getConfigurationSettingsList(RemoteConfigurationType.class)
                .stream()
                .filter(item -> StringUtils.equals(item.getName(), remoteDebugDto.getConfigName()))
                .filter(item -> item instanceof RunnerAndConfigurationSettingsImpl)
                .map(item -> (RunnerAndConfigurationSettingsImpl) item)
                .findFirst()
                .orElse(null);

        if(configuration != null) {
            setRemoteConfiguration(configuration.getConfiguration(), remoteDebugDto, project);
            runManager.setSelectedConfiguration(configuration);
            ProgramRunnerUtil.executeConfiguration(configuration, getDefaultRunExecutor());
            return;
        }

        configuration = ((RunnerAndConfigurationSettingsImpl) runManager.createConfiguration(remoteDebugDto.getConfigName(), configurationFactory));
        RemoteConfiguration remoteConfiguration = new RemoteConfiguration(project, configurationFactory);
        setRemoteConfiguration(remoteConfiguration, remoteDebugDto, project);
        configuration.setConfiguration(remoteConfiguration);
        configuration.setName(remoteDebugDto.getConfigName());
        runManager.addConfiguration(configuration);
        runManager.setSelectedConfiguration(configuration);
        ProgramRunnerUtil.executeConfiguration(configuration, getDefaultRunExecutor());
    }

    private static Executor getDefaultRunExecutor() {
        Executor executor = DefaultDebugExecutor.getDebugExecutorInstance();
        if(executor == null) {
            executor = new DefaultDebugExecutor();
        }
        return executor;
    }

    private static void setRemoteConfiguration(RunConfiguration runConfiguration, RemoteDebugDto remoteDebugDto, Project project) {
        if(!(runConfiguration instanceof RemoteConfiguration)) {
            return;
        }
        RemoteConfiguration remoteConfiguration = (RemoteConfiguration) runConfiguration;
        remoteConfiguration.HOST = remoteDebugDto.getHost();
        remoteConfiguration.PORT = remoteDebugDto.getPort().toString();

        ModuleManager moduleManager = ModuleManager.getInstance(project);
        Module[] modules = moduleManager.getModules();
        if(modules.length > 0) {
            Module module = Arrays.stream(modules).filter(item -> StringUtils.equals(remoteDebugDto.getModuleName(), item.getName())).findFirst().orElse(modules[0]);
            remoteConfiguration.setModule(module);
        }
    }
}
