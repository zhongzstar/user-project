package test;

import main.domain.TaskList;
import main.service.TaskListService;

import java.util.List;

public class TaskListTest {
    public static void main(String[] args) {
        try{
            // 创建TaskListService实例
            TaskListService service = new TaskListService();
            
            System.out.println("=== 测试TaskListService功能 ===");
            
            // 测试findAll方法
            System.out.println("\n1. 测试findAll方法:");
            List<TaskList> allTasks = service.findAll();
            if (allTasks != null) {
                System.out.println("找到 " + allTasks.size() + " 个任务");
                for (TaskList task : allTasks) {
                    System.out.println("  Task ID: " + task.getTaskid() + ", Username: " + task.getUsername() + ", Type: " + task.getType());
                }
            } else {
                System.out.println("未找到任何任务或任务列表为空");
            }
            
            // 测试添加功能
            System.out.println("\n2. 测试添加功能:");
            TaskList newTask = new TaskList(1001, 2001, "测试数据名称", "test_type", "active", "test_user", "2025-12-16", "test_param");
            service.add(newTask);
            System.out.println("添加测试任务完成");
            
            // 测试findBy方法
            System.out.println("\n3. 测试findBy方法:");
            List<TaskList> foundTasks = service.findBy("username", "test_user");
            System.out.println("根据用户名'test_user'查找，找到 " + foundTasks.size() + " 个任务");
            for (TaskList task : foundTasks) {
                System.out.println("  Task ID: " + task.getTaskid() + ", Username: " + task.getUsername() + ", Type: " + task.getType());
            }
            
            // 测试更新功能
            System.out.println("\n4. 测试更新功能:");
            service.updata(1001, "state", "completed");
            System.out.println("更新任务1001的状态为'completed'");
            
            // 再次查找验证更新结果
            List<TaskList> updatedTasks = service.findBy("state", "completed");
            System.out.println("根据状态'completed'查找，找到 " + updatedTasks.size() + " 个任务");
            for (TaskList task : updatedTasks) {
                System.out.println("  Task ID: " + task.getTaskid() + ", State: " + task.getState());
            }
            
            // 测试删除功能
            System.out.println("\n5. 测试删除功能:");
            service.delete(1001);
            System.out.println("删除任务1001");
            
            // 验证删除结果
            List<TaskList> afterDelete = service.findBy("username", "test_user");
            System.out.println("删除后查找用户名'test_user'的任务，剩余 " + afterDelete.size() + " 个任务");
            
            System.out.println("\n=== 测试完成 ===");
            
        }
        catch (Exception e){
            System.err.println("测试过程中出现异常: " + e.getMessage());
            e.printStackTrace();
        }
    }
}