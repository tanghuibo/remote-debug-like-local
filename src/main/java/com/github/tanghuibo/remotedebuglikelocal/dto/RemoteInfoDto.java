package com.github.tanghuibo.remotedebuglikelocal.dto;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * RemoteInfoDto
 *
 * @author tanghuibo
 * @date 2021/9/29 13:24
 */
public class RemoteInfoDto {

    /**
     * 环境
     */
    private String env;

    /**
     * 主机
     */
    private String host;

    /**
     * 端口
     */
    private String port;

    /**
     * 日志状态
     */
    private Integer logStatus;

    private List<Runnable> updateListenerList = new ArrayList<>();

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    @Override
    public String toString() {
        return env + "-" + host + (StringUtils.isEmpty(port) ? "": (":" + port));
    }

    public Integer getLogStatus() {
        return logStatus;
    }

    public void setLogStatus(Integer logStatus) {
        if(!logStatus.equals(this.logStatus)) {
            update();
        }
        this.logStatus = logStatus;
    }

    private void update() {
        for (Runnable runnable : updateListenerList) {
            runnable.run();
        }
    }

    public void addUpdateListener(Runnable runnable) {
        this.updateListenerList.add(runnable);
    }
}
