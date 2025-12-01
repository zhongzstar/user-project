package test;
import main.domain.User;
import main.service.UserService;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;



public class UserTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        UserService service = new UserService();
        try {
            //增：增加用户
            System.out.println("test:rigist + findByName");
            User new_user = new User();
            new_user.setName("zhongzhe9");
            new_user.setPassword("wojiaozhongzhe");
            new_user.setQuestion("你的年龄是多少？");
            new_user.setAnswer("22");
            service.rigist(new_user);
            System.out.println(service.findByName(new_user) + "\n");
            //删：删除密保问题
            System.out.println("test:deleteQuestion");
            User delete_user = new User();
            delete_user.setName("zhongzhe8");
            delete_user.setPassword("wojiaozhongzhe");
            delete_user.setQuestion("你的年龄是多少？");
            delete_user.setAnswer("22");
            service.deleteQuestion(delete_user);
            System.out.println("\n");
            //改：打印密码问题，更改密码
            System.out.println("test:forgetPasswordQuestion + forgetPasswordAnswer");
            User user = new User();
            user.setName("zhongzhe9");
            User _user = service.forgetPasswordQuestion(user);
            if(_user != null){
                System.out.println(_user.getQuestion());
            }
            System.out.println("输入答案：");//22
            String answer = scanner.nextLine();
            System.out.println("输入你打算改的新密码：");
            String new_password = scanner.nextLine();
            _user.setAnswer(answer);
            _user.setPassword(new_password);
            service.forgetPasswordAnswer(_user);
            System.out.println("\n");
            //查：查询所有
            List<User> list = new ArrayList<>();
            list = service.FindAll();
            System.out.println(list);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
