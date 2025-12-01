package main.service;


import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import main.domain.User;

public class UserService {
    //classpath 相对路径
    private String question_path = "src/main/resources/用户密保.properties";
    private String user_path = "src/main/resources/用户和密码.properties";
    //增：增加用户
    public void rigist(User user) throws Exception{
        User _user = new User();
        Properties properties = new Properties();
        boolean flag = false;
        try(BufferedReader read = new BufferedReader(new FileReader(user_path))){
            properties.load(read);
            if(properties.containsKey(user.getName())){
                flag = true;
            }
        }
        if(flag){
            System.out.println("该用户已经存在！");
        }
        else{
            try(BufferedWriter writer = new BufferedWriter(new FileWriter(user_path, true))){
                writer.write(user.getName() +"="+user.getPassword());
                writer.newLine();
            }
            try(BufferedWriter writer = new BufferedWriter(new FileWriter(question_path, true))){
                writer.write(user.getName() + ".question=" + user.getQuestion());
                writer.newLine();
                writer.write(user.getName() + ".answer=" + user.getAnswer());
                writer.newLine();
                writer.write(user.getName() + ".createdAt=" + new java.util.Date().toString());
                writer.newLine();
            }
            System.out.println("用户已经添加！");
        }
    }
    //查：
    //1.登录
    public User login(User user) throws Exception{
        User _user = new User();
        _user = null;
        boolean have_user_flag = false;
        boolean password_right_or_false = false;
        try(BufferedReader read  = new BufferedReader(new FileReader(user_path))){
            Properties properties = new Properties();
            properties.load(read);
            if(properties.containsKey(user.getName())){
                have_user_flag = true;
                if(properties.getProperty(user.getName()).equals(user.getPassword())){
                    _user.setName(user.getName());
                    _user.setPassword(user.getPassword());
                    password_right_or_false = true;
                }
            }
        }
        if(have_user_flag && password_right_or_false){
            System.out.println("用户存在，密码正确！登录成功！");
        }
        else if(have_user_flag){
            System.out.println("用户存在，密码错误！登录失败！");
        }
        else{
           System.out.println("用户不存在");
        }
        return _user;
    }
    //2.查询所有用户信息
    public List<User> FindAll() throws Exception{
        List<User> userlist = new ArrayList<>();
        try(BufferedReader read = new BufferedReader(new FileReader(user_path))){
            Properties properties = new Properties();
            properties.load(read);
            if(properties.isEmpty()){
                System.out.println("用户列表为空！");
            }
            else{
                for(Map.Entry<Object,Object> entry : properties.entrySet()){
                    User user = new User();
                    user.setName((String)entry.getKey());
                    user.setPassword((String) entry.getValue());
                    userlist.add(user);
                }
            }
        }
        return userlist;
    }
    public User findByName(User user) throws Exception{
        User _user = new User();
        boolean userExist = false;
        try(BufferedReader read = new BufferedReader(new FileReader(user_path))){
            Properties properties = new Properties();
            properties.load(read);
            if(properties.containsKey(user.getName())){
                _user.setName(user.getName());
                _user.setPassword(properties.getProperty(user.getName()));
                userExist = true;
            }
        }
        if(userExist){
            System.out.println("用户存在，已返回用户");
        }
        else{
            System.out.println("用户不存在，请重新考虑！");
        }
        return _user;
    }
    //3.查询特定用户密保问题
    public User forgetPasswordQuestion(User user) throws Exception{
        User _user = new User();
        boolean userExists = false;
        String questionKey = user.getName() + ".question";
        Properties properties = new Properties();
        try (BufferedReader read = new BufferedReader(new FileReader(question_path))){
            properties.load(read);
            if(properties.containsKey(questionKey)){
                userExists = true;
                _user.setName(user.getName());
                _user.setQuestion(properties.getProperty(questionKey));
            }
        }
        if(!userExists){
            System.out.println("用户不存在！");
            return null;
        }
        return _user;
    }
    //改
    //1.修改用户密码
    public void forgetPasswordAnswer(User user) throws Exception{
        boolean whether_right = false;
        try(BufferedReader read = new BufferedReader(new FileReader(question_path))){
            Properties properties = new Properties();
            properties.load(read);
            if(user.getAnswer().equals(properties.getProperty(user.getName() + ".answer"))){
                whether_right = true;
            }
        }
        if(whether_right){
            System.out.println("密保答案正确！开始修改密码");
            Properties properties = new Properties();
            try(BufferedReader read = new BufferedReader(new FileReader(user_path))){
                properties.load(read);
                properties.setProperty(user.getName(),user.getPassword());
            }
            try(BufferedWriter write = new BufferedWriter(new FileWriter(user_path))){
                properties.store(write, "更改了" + user.getName() + "的密码");
            }
            System.out.println("已经修改密码！");
        }
        else{
            System.out.println("密保答案错误！不可以修改密码,请重新输入！");
        }
    }
    //删：
    // 1.删除用户密保问题
    public void deleteQuestion(User user) throws Exception{
        Properties properties = new Properties();
        boolean whether_right = false;
        try(BufferedReader read = new BufferedReader(new FileReader(question_path))){
            properties.load(read);
            String questionKey1 = user.getName() + ".question";
            String questionKey2 = user.getName() + ".answer";
            String questionKey3 = user.getName() + ".createdAt";
            if(properties.containsKey((questionKey1))){
                properties.remove(questionKey1);
                properties.remove(questionKey2);
                properties.remove(questionKey3);
                whether_right = true;

            }
        }
        if(whether_right){
            try(BufferedWriter writer = new BufferedWriter(new FileWriter(question_path))){
                properties.store(writer, "Updated security questions");
            }
            System.out.println("已经删除！");
        }
        else{
            System.out.println("不存在这个问题！");
        }
    }
}
