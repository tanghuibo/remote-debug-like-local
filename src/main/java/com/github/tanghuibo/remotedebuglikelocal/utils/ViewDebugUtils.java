package com.github.tanghuibo.remotedebuglikelocal.utils;

import com.intellij.openapi.wm.ToolWindow;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Method;
import java.util.*;
import java.util.List;

/**
 * ViewDebugUtils
 *
 * @author tanghuibo
 * @date 2021/9/28 0:44
 */
public class ViewDebugUtils {

    private ViewDebugInfo viewDebugInfo;

    private Map<String, Component> componentMap;

    public ViewDebugUtils(ToolWindow toolWindow) {
        componentMap = new HashMap<>();
        viewDebugInfo = new ViewDebugInfo();
        JComponent component = toolWindow.getComponent();
        viewDebugInfo.setClassName(component.getClass().getTypeName());
        viewDebugInfo.setName(component.getName());
        String uuid = UUID.randomUUID().toString();
        componentMap.put(uuid, component);
        viewDebugInfo.setList(new ArrayList<>());
        viewDebugInfo.setUuid(uuid);
        Component[] components = component.getComponents();
        if(components != null) {
            for (Component cp : components) {
                addToList(viewDebugInfo.getList(), cp);
            }
        }

    }

    public ViewDebugInfo getViewDebugInfo() {
        return viewDebugInfo;
    }

    public void setViewDebugInfo(ViewDebugInfo viewDebugInfo) {
        this.viewDebugInfo = viewDebugInfo;
    }

    private void addToList(List<ViewDebugInfo> list, Component component) {

        ViewDebugInfo viewDebugInfo = new ViewDebugInfo();
        viewDebugInfo.setClassName(component.getClass().getTypeName());
        viewDebugInfo.setName(component.getName());
        String uuid = UUID.randomUUID().toString();
        componentMap.put(uuid, component);
        viewDebugInfo.setList(new ArrayList<>());
        viewDebugInfo.setUuid(uuid);
        viewDebugInfo.setText(getText(component));

        list.add(viewDebugInfo);


        if(component instanceof Container) {
            Component[] components = ((Container) component).getComponents();
            if(components != null) {
                for (Component cp : components) {
                    addToList(viewDebugInfo.getList(), cp);
                }
            }
        }


    }

    public Map<String, Component> getComponentMap() {
        return componentMap;
    }

    public void setComponentMap(Map<String, Component> componentMap) {
        this.componentMap = componentMap;
    }

    public static class ViewDebugInfo {
        private String className;

        private String name;

        private String uuid;

        private String text;
        
        private List<ViewDebugInfo> list;

        public String getClassName() {
            return className;
        }

        public void setClassName(String className) {
            this.className = className;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<ViewDebugInfo> getList() {
            return list;
        }

        public void setList(List<ViewDebugInfo> list) {
            this.list = list;
        }

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }

    static String getText(Object o) {
        try {
            Method method = o.getClass().getDeclaredMethod("getText");
            Object invoke = method.invoke(o);
            return invoke.toString();
        } catch (Exception e) {
            return null;
        }
    }

}
