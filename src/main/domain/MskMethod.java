package main.domain;

public class MskMethod {
    private int methodid;
    private String methodname;
    private String type;
    private String maskmethod;
    private String parameter;
    private String username;

    public int getMethodid() {
        return methodid;
    }

    public void setMethodid(int methodid) {
        this.methodid = methodid;
    }

    public String getMethodname() {
        return methodname;
    }

    public void setMethodname(String methodname) {
        this.methodname = methodname;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMaskmethod() {
        return maskmethod;
    }

    public void setMaskmethod(String maskmethod) {
        this.maskmethod = maskmethod;
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

    public MskMethod(int methodid,String methodname, String type, String maskmethod, String parameter, String username) {
        super();
        this.methodid = methodid;
        this.methodname = methodname;
        this.type = type;
        this.maskmethod = maskmethod;
        this.parameter = parameter;
        this.username = username;
    }
    public MskMethod(){}
}