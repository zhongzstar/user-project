package main.domain;

public class User {
    private String name = null;
    private String password = null;
    private String answer = null;
    private String question = null;
    public void User(){
    }

    public String getName(){
        return this.name;
    }
    public void setName(String new_name){
        this.name = new_name;
    }
    public String getPassword(){
        return this.password;
    }
    public void setPassword(String new_password){
        this.password = new_password;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String printUser(){
        return "用户名：" + this.name +"密码：" +this.password;
    }
}
