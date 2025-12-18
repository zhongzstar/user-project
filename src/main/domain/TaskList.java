package main.domain;

public class TaskList {
    private int taskid;
    private int dataid;
    private String dataname;
    private String type;
    private String date;
    private String username;
    private String state;
    private String parameter;

    public int getTaskid() {
        return taskid;
    }
    public void setTaskid(int taskid) {
        this.taskid = taskid;
    }
    public int getDataid() {
        return dataid;
    }
    public void setDataid(int dataid) {
        this.dataid = dataid;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

    public String getDataname() {
        return dataname;
    }
    public void setDataname(String dataname) {
        this.dataname = dataname;
    }
    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }
    public TaskList(){}
    public TaskList(int taskid,int dataid, String dataname, String type,
                    String state, String username,
                    String date, String parameter) {
        this.taskid = taskid;
        this.dataid = dataid;
        this.dataname = dataname;
        this.type = type;
        this.username = username;
        this.state = state;
        this.date = date;
        this.parameter = parameter;
    }
}
