package com.github.tanghuibo.remotedebuglikelocal.dto;

/**
 * RemoteDebugDto
 *
 * @author tanghuibo
 * @date 2021/9/26 10:26
 */
public class RemoteDebugDto {

    /**
     * 主机名
     */
    private String host;

    /**
     * 端口
     */
    private Integer port;

    /**
     * 配置名称
     */
    private String configName;

    /**
     * 模块
     */
    private String moduleName;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getConfigName() {
        return configName;
    }

    public void setConfigName(String configName) {
        this.configName = configName;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }
}
