package com.github.tanghuibo.remotedebuglikelocal.properties;

/**
 * ConfigDebubDetailProperties
 *
 * @author tanghuibo
 * @date 2021/11/16 16:23
 */
public class ConfigDebugDetailProperties {

    private String name;

    private String env;

    private String ip;

    private Integer port;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }
}
