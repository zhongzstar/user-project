package main.domain;

public class User {
    private String name;
    private String password;
    private String answer;
    private String question;
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

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", answer='" + answer + '\'' +
                ", question='" + question + '\'' +
                '}';
    }
}
