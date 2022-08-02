package com.skillbox.searchengine.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "config")
public class SearchConfig {
    private String prefix;
    private String agent;
    private String webInterfaceLogin;
    private String webInterfacePassword;
    private String webinterface;
    private List<HashMap<String, String>> site;

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public String getWebinterface() {
        return webinterface;
    }

    public void setWebinterface(String webinterface) {
        this.webinterface = webinterface;
    }

    public List<HashMap<String, String>> getSite() {
        return site;
    }

    public void setSite(List<HashMap<String, String>> site) {
        this.site = site;
    }

    public String getWebInterfaceLogin() {
        return webInterfaceLogin;
    }

    public void setWebInterfaceLogin(String webInterfaceLogin) {
        this.webInterfaceLogin = webInterfaceLogin;
    }

    public String getWebInterfacePassword() {
        return webInterfacePassword;
    }

    public void setWebInterfacePassword(String webInterfacePassword) {
        this.webInterfacePassword = webInterfacePassword;
    }
}
