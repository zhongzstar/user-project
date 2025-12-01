package main.domain;

import java.util.Objects;

public class DateSource {
    private int dataid;//主键
    private String dataname;
    private String type;
    private String parameter;
    private String username;//外键:用户名
    private int configid;//外键：配置ID

    public DateSource() {
    }

    public DateSource(int dataid, String type, String dataname, String parameter, int configid, String username) {
        this.dataid = dataid;
        this.type = type;
        this.dataname = dataname;
        this.parameter = parameter;
        this.configid = configid;
        this.username = username;
    }

    public int getDataid() {
        return dataid;
    }

    public void setDataid(int dataid) {
        this.dataid = dataid;
    }

    public String getDataname() {
        return dataname;
    }

    public void setDataname(String dataname) {
        this.dataname = dataname;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getConfigid() {
        return configid;
    }

    public void setConfigid(int configid) {
        this.configid = configid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DateSource that = (DateSource) o;
        // 只比较主键ID
        return dataid == that.dataid;
    }

    @Override
    public int hashCode() {
        // 只使用主键ID计算哈希值
        return Objects.hash(dataid);
    }

    @Override
    public String toString() {
        return "DateSource{" +
                "dataid=" + dataid +
                ", dataname='" + dataname + '\'' +
                ", type='" + type + '\'' +
                ", parameter='" + parameter + '\'' +
                ", username='" + username + '\'' +
                ", configid=" + configid +
                '}';
    }

}