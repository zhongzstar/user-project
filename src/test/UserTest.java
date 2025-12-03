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
            User zhangSan = new User("张三","123456","22","你的年龄是多少？");
            service.rigist(zhangSan);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
