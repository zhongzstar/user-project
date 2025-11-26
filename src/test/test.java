package test;
import main.domain.User;
import main.service.UserService;


import java.util.Scanner;



public class test {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        UserService service = new UserService();

        System.out.println("开始测试");
        //打印密码问题测试
        try {
            //输入请求
            User user = new User();
            user.setName("user3");
            //通过对象传递过程信息
            User _user = service.forgetPassword_question(user);
            if(_user != null){
                System.out.println(_user.getQuestion());
            }
            System.out.println("输入答案：");
            String answer = scanner.nextLine();
            System.out.println("输入你打算改的新密码：");
            String new_password = scanner.nextLine();
            _user.setAnswer(answer);
            _user.setPassword(new_password);
            service.forgetPassword_answer(_user);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
