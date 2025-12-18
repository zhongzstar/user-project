package test;

import main.domain.MskMethod;
import main.service.MskMethodService;

import java.util.List;

public class MskMethodTest {
    public static void main(String[] args) {
        try {
            // 创建MskMethodService实例
            MskMethodService service = new MskMethodService();

            // 测试添加方法
            System.out.println("=== 测试添加脱敏策略 ===");
            MskMethod method1 = new MskMethod(1, "手机号脱敏", "phone", "替换", "前3后4", "user1");
            service.add(method1);

            MskMethod method2 = new MskMethod(2, "邮箱脱敏", "email", "替换", "前2后3", "user1");
            service.add(method2);

            MskMethod method3 = new MskMethod(3, "身份证脱敏", "idcard", "替换", "前6后4", "user2");
            service.add(method3);

            // 测试findAll方法
            System.out.println("\n=== 测试查找所有脱敏策略 ===");
            List<MskMethod> allMethods = service.findAll();
            if (allMethods != null) {
                for (MskMethod m : allMethods) {
                    System.out.println("ID: " + m.getMethodid() + ", 名称: " + m.getMethodname() +
                            ", 类型: " + m.getType() + ", 用户: " + m.getUsername());
                }
            }

            // 测试findByType方法
            System.out.println("\n=== 测试按类型查找脱敏策略 ===");
            List<MskMethod> phoneMethods = service.findByType("phone");
            if (phoneMethods != null && !phoneMethods.isEmpty()) {
                System.out.println("找到phone类型的脱敏策略:");
                for (MskMethod m : phoneMethods) {
                    System.out.println("ID: " + m.getMethodid() + ", 名称: " + m.getMethodname());
                }
            }

            // 测试findByUsername方法
            System.out.println("\n=== 测试按用户名查找脱敏策略 ===");
            List<MskMethod> user1Methods = service.findByUsername("user1");
            if (user1Methods != null && !user1Methods.isEmpty()) {
                System.out.println("找到user1的脱敏策略:");
                for (MskMethod m : user1Methods) {
                    System.out.println("ID: " + m.getMethodid() + ", 名称: " + m.getMethodname() +
                            ", 类型: " + m.getType());
                }
            }

            // 测试findByMskMethodId方法
            System.out.println("\n=== 测试按ID查找脱敏策略 ===");
            MskMethod foundMethod = service.findByMskMethodId(2);
            if (foundMethod != null && foundMethod.getMethodid() != 0) {
                System.out.println("找到ID为2的脱敏策略: " + foundMethod.getMethodname());
            } else {
                System.out.println("未找到ID为2的脱敏策略");
            }

            // 测试更新方法
            System.out.println("\n=== 测试更新脱敏策略 ===");
            service.updata(1, "methodname", "移动手机号脱敏");
            MskMethod updatedMethod = service.findByMskMethodId(1);
            if (updatedMethod != null) {
                System.out.println("更新后的策略名称: " + updatedMethod.getMethodname());
            }

            // 测试maxInsertId方法
            System.out.println("\n=== 测试获取最大ID ===");
            int maxId = service.maxInsertId();
            System.out.println("当前最大ID: " + maxId);

            // 测试删除方法
            System.out.println("\n=== 测试删除脱敏策略 ===");
            service.delete(3);
            MskMethod deletedMethod = service.findByMskMethodId(3);
            if (deletedMethod == null || deletedMethod.getMethodid() == 0) {
                System.out.println("ID为3的脱敏策略已成功删除");
            } else {
                System.out.println("ID为3的脱敏策略删除失败");
            }

            // 再次显示所有策略
            System.out.println("\n=== 删除后的所有脱敏策略 ===");
            allMethods = service.findAll();
            if (allMethods != null) {
                for (MskMethod m : allMethods) {
                    System.out.println("ID: " + m.getMethodid() + ", 名称: " + m.getMethodname() +
                            ", 类型: " + m.getType() + ", 用户: " + m.getUsername());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}