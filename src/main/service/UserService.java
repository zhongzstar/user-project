package main.service;


import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import main.domain.User;

public class UserService {
    //classpath 相对路径
    private String question_path = "/用户密保.properties";
    private String user_path = "/用户和密码.properties";
    //增：增加用户
    public void rigist(String username,String password) throws Exception{
        // String line = null;
        boolean whether_exist = false;
        //try(InputStream input = getClass().getResourceAsStream(user1_path)){
            //BufferedReader read = new BufferedReader(new InputStreamReader(input,"UTF-8"));
        String filepath = "src/main/resources" + user_path;
        try(BufferedReader read = new BufferedReader(new FileReader(filepath))){
            //properties遍历方式
            Properties properties = new Properties();
            properties.load(read);
            if(properties.containsKey(username)){
                whether_exist = true;
            }
        }
        if(whether_exist){
                System.out.println("用户已经存在！请重新添加");
        }
        else{
            try(BufferedWriter writer = new BufferedWriter(new FileWriter(filepath, true))){
                writer.write(username+"="+password);
                writer.newLine();
                System.out.println("用户已经添加！");
            }
        }
    }
    public void rigist(User user) throws Exception{
        User _user = new User();
        Properties properties = new Properties();

        boolean flag = false;
        String filepath = "src/main/resources" + user_path;
        try(BufferedReader read = new BufferedReader(new FileReader(filepath))){
            properties.load(read);
            if(properties.containsKey(user.getName())){
                flag = true;
            }
        }
        if(flag){
            System.out.println("该用户已经存在！");
        }
        else{
            try(BufferedWriter writer = new BufferedWriter(new FileWriter(filepath))){
                writer.write(user.getName() +"="+user.getPassword());
                writer.newLine();
            }
            System.out.println("用户已经添加！");
        }
    }
    //查：
    //1.登录
    public String login(String username,String password) throws Exception{
        String filepath = "src/main/resources" + user_path;
        boolean have_user_flag = false;
        boolean password_right_or_false = false;
        try(BufferedReader read  = new BufferedReader(new FileReader(filepath))){
            Properties properties = new Properties();
            properties.load(read);
            if(properties.containsKey(username)){
                have_user_flag = true;
                if(properties.getProperty(username).equals(password)){
                    password_right_or_false = true;
                }
            }
        }
        if(have_user_flag && password_right_or_false){
            return "用户存在，密码正确！登录成功！";
        }
        else if(have_user_flag){
            return "用户存在，密码错误！登录失败！";
        }
        else{
            return "用户不存在";
        }
    }
    public User login(User user) throws Exception{
        User _user = new User();
        _user = null;

        boolean have_user_flag = false;
        boolean password_right_or_false = false;
        String filepath = "src/main/resources" + user_path;
        try(BufferedReader read  = new BufferedReader(new FileReader(filepath))){
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
    /*
    public void FindAll() throws Exception{
        String filepath = "src/main/resources" + user_path;
        try(BufferedReader read = new BufferedReader(new FileReader(filepath))){
            Properties properties = new Properties();
            properties.load(read);
            if(properties.isEmpty()){
                System.out.println("用户列表为空！");
            }
            else{
                for(Map.Entry<Object,Object> entry : properties.entrySet()){
                    System.out.println(entry.getKey() + "=" + entry.getValue());
                }
            }
        }
    }
    */
    public List<User> FindAll() throws Exception{
        List<User> userlist = new ArrayList<>();
        String filepath = "src/main/resources" + user_path;
        try(BufferedReader read = new BufferedReader(new FileReader(filepath))){
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
    //3.查询特定用户密保问题
    /*
    public String forgetPassword_question(String username) throws Exception{
        String filename = "src/main/resources" + question_path;
        String question = null;
        String questionKey = username + ".question";
        boolean whether_exist = false;
        try(BufferedReader read = new BufferedReader(new FileReader(filename))){
            Properties properties = new Properties();
            properties.load(read);
            if(properties.containsKey(questionKey)){
                whether_exist = true;
            }
        }
        //2.存在则打印问题，不存在打印用户不存在。
        if(whether_exist){
            Properties properties = new Properties();
            try(BufferedReader reader = new BufferedReader(new FileReader(filename))){
                properties.load(reader);
                question = properties.getProperty(questionKey);
            }
            return question == null ? "用户没有密保问题！" : question ;
        }
        else{
            return "用户不存在";
        }
    }*/
    public User forgetPassword_question(User user) throws Exception{
        User _user = new User();

        boolean userExists = false;
        String questionKey = user.getName() + ".question";
        String filename = "src/main/resources" + question_path;
        Properties properties = new Properties();
        try (BufferedReader read = new BufferedReader(new FileReader(filename))){
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
    /*
    public void forgetPassword_answer(String username,String answer,String new_password) throws Exception{
        String question_filename = "src/main/resources" + question_path;
        String user_filename = "src/main/resources" + user_path;
        boolean whether_right = false;
        try(BufferedReader read = new BufferedReader(new FileReader(question_filename))){
            Properties properties = new Properties();
            properties.load(read);
            String questionKey = username + ".answer";
            if(answer.equals(properties.getProperty(questionKey))){
                whether_right = true;
            }
        }
        if(whether_right){
            System.out.println("密保答案正确！开始修改密码");
            //找到特定的键更改密码，然后更新一下文件
            Properties properties = new Properties();
            try(BufferedReader read = new BufferedReader(new FileReader(user_filename))){
                properties.load(read);
            }
            properties.setProperty(username,new_password);
            try(BufferedWriter write = new BufferedWriter(new FileWriter(user_filename))){
                properties.store(write, "更改了" + username + "的密码");
            }
            System.out.println("已经修改密码！");
        }
        else{
            System.out.println("密保答案错误！不可以修改密码,请重新输入！");
        }
    }*/
    public void forgetPassword_answer(User user) throws Exception{
        String question_filename = "src/main/resources" + question_path;
        String user_filename = "src/main/resources" + user_path;
        boolean whether_right = false;
        try(BufferedReader read = new BufferedReader(new FileReader(question_filename))){
            Properties properties = new Properties();
            properties.load(read);
            if(user.getAnswer().equals(properties.getProperty(user.getName() + ".answer"))){
                whether_right = true;
            }
        }
        if(whether_right){
            System.out.println("密保答案正确！开始修改密码");

            Properties properties = new Properties();
            try(BufferedReader read = new BufferedReader(new FileReader(user_filename))){
                properties.load(read);
                properties.setProperty(user.getName(),user.getPassword());
            }
            try(BufferedWriter write = new BufferedWriter(new FileWriter(user_filename))){
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
    public void delete_question(User user) throws Exception{
        String question_filename = "src/main/resources" + question_path;
        Properties properties = new Properties();
        boolean whether_right = false;
        try(BufferedReader read = new BufferedReader(new FileReader(question_filename))){
            properties.load(read);
            String questionKey1 = user.getName() + ".question";
            String questionKey2 = user.getName() + ".answer";
            String questionKey3 = user.getName() + ".createdAt";
            if(properties.containsKey((questionKey1))){
                properties.remove(questionKey1);
                properties.remove(questionKey2);
                properties.remove(questionKey3);
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(question_filename))) {
                    properties.store(writer, "Updated security questions");
                }
                whether_right = true;
            }
        }
        if(whether_right){
            System.out.println("已经删除！");
        }
        else{
            System.out.println("不存在这个问题！");
        }
    }
}
