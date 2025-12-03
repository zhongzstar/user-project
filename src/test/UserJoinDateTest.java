package test;

import main.service.DateSourceService;
import main.service.UserService;
import main.domain.DateSource;
import main.domain.User;

import java.util.List;

public class UserJoinDateTest {

    public static void main(String[] args) {
        UserService userService = new UserService();
        DateSourceService dateSourceService = new DateSourceService();
        try{
            //登录
            User zhangSan = new User("张三","123456","22","你的年龄是多少？");
            userService.login(zhangSan);
            //1.查：查看当前用户的数据源
            System.out.println(dateSourceService.findByUsername(zhangSan));
            //2.改：用户只能操控自己的数据源
             //2.1改当前对象的数据源
            System.out.println("2.1");
            DateSource dateSource = new DateSource(8,"脱敏数据源8","txt","src/main/resources/DateSource1.csv",8,"张三");
            DateSource dateSource1 = new DateSource(6,"脱敏数据源6","csv","src/main/resources",5,"张三");
            dateSourceService.update(dateSource, zhangSan,"configid","7");
            System.out.println(dateSourceService.findByUsername(zhangSan));
             //2.2改不是当前对象的数据源
            System.out.println("2.2");
            DateSource dateSource2 = new DateSource(4,"脱敏数据源4","txt","src/main/resources/DateSource3.csv",4,"zz");
            dateSourceService.update(dateSource2, zhangSan,"configid","7");
            //3.删：只能删自己的
             //3.1删自己的数据源
            System.out.println("3.1");
            dateSourceService.delete(dateSource1,zhangSan);
            System.out.println("3.2");
             //3.2删别人的数据源
            dateSourceService.delete(dateSource2, zhangSan);
            System.out.println("结束");
            //4.修改真实的数据源文件
            //张三改数据源1


        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
