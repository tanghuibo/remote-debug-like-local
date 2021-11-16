package com.github.tanghuibo.remotedebuglikelocal.utils;

import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.github.tanghuibo.remotedebuglikelocal.dto.RemoteInfoDto;
import com.github.tanghuibo.remotedebuglikelocal.properties.ConfigProperties;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFileManager;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * ConfigUtils
 *
 * @author tanghuibo
 * @date 2021/11/16 16:01
 */
public class ConfigUtils {

    static YAMLMapper yamlMapper = new YAMLMapper();

    public static List<RemoteInfoDto> read(Project project) {
        try {
            File file = new File(project.getBasePath() + "/rdebug.yml");
            if(!file.exists() || !file.isFile()) {
                return Collections.emptyList();
            }
            String yml = new String(Objects.requireNonNull(VirtualFileManager.getInstance().findFileByNioPath(file.toPath())).contentsToByteArray(), StandardCharsets.UTF_8);
            ConfigProperties configProperties = yamlMapper.readValue(yml, ConfigProperties.class);
            return configProperties.getDebug().stream().map(item -> {
                RemoteInfoDto remoteInfoDto = new RemoteInfoDto();
                remoteInfoDto.setEnv(item.getEnv());
                remoteInfoDto.setHost(item.getIp());
                remoteInfoDto.setPort(item.getPort());
                remoteInfoDto.setName(item.getName());
                return remoteInfoDto;
            }).collect(Collectors.toList());
        } catch (IOException e) {
            return Collections.emptyList();
        }
    }
}
