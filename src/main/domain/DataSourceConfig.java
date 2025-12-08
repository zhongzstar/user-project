package main.domain;

public class DataSourceConfig {
    private int configid;
    private String configtype;
    private String configusername;
    private String configpassword;
    private String confighost;
    private String configport;
    private String configpath;
    private String username;

    public int getConfigid() {
        return configid;
    }
    public void setConfigid(int configid) {
        this.configid = configid;
    }
    public String getConfigtype() {
        return configtype;
    }
    public void setConfigtype(String configtype) {
        this.configtype = configtype;
    }
    public String getConfigusername() {
        return configusername;
    }
    public void setConfigusername(String configusername) {
        this.configusername = configusername;
    }
    public String getConfigpassword() {
        return configpassword;
    }
    public void setConfigpassword(String configpassword) {
        this.configpassword = configpassword;
    }
    public String getConfighost() {
        return confighost;
    }
    public void setConfighost(String confighost) {
        this.confighost = confighost;
    }
    public String getConfigport() {
        return configport;
    }
    public void setConfigport(String configport) {
        this.configport = configport;
    }
    public String getConfigpath() {
        return configpath;
    }
    public void setConfigpath(String configpath) {
        this.configpath = configpath;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public DataSourceConfig() {
    }
    public DataSourceConfig(int configid,String configtype,
                            String configusername, String configpassword, String confighost,
                            String configport, String configpath, String username) {
        this.configid = configid;
        this.configtype = configtype;
        this.configusername = configusername;
        this.configpassword = configpassword;
        this.confighost = confighost;
        this.configport = configport;
        this.configpath = configpath;
        this.username = username;
    }
}