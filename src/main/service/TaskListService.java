package main.service;

import main.domain.TaskList;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TaskListService {
    private String fileName = "src/main/resources/TaskList.csv";
    private File file = new File(fileName);
    private List<TaskList> datalist = null;
    public TaskListService(){
        datalist = readCsv();

    }
    public List<TaskList> findBy(String colName, String content)throws Exception{
        List<TaskList> res = new ArrayList<>();
        if(datalist == null){
            datalist = readCsv();
        }
        for(TaskList list : datalist){
            Method method = list.getClass().getMethod("get" + capilization(colName));
            String value = (String)method.invoke(list);
            if(value.equals(content)){
                res.add(list);
            }
        }
        return res;
    }
    public List<TaskList> findAll(){
        if(datalist == null || datalist.isEmpty()){
            return null;
        }
        List<TaskList> res = new ArrayList<>();
        for(TaskList list : datalist){
            res.add(list);
        }
        return res;
    }
    public void updata(int taskid, String colName, String content)throws Exception{
        boolean flag = false;
        if(datalist == null){
            System.out.println("更新失败，任务列表无任务");
        }
        else{
            for(TaskList list:datalist){
                if(list.getTaskid() == taskid){
                    Method method = list.getClass().getMethod("set" + capilization(colName), String.class);
                    method.invoke(list,content);
                    flag = true;
                    break;
                }
            }
        }
        if(flag) {
            writeCsv(datalist);
            System.out.println("更新成功!");
        }else{
            System.out.println("更新失败，没有找到任务id为"+ taskid);
        }
    }
    public void delete(int taskid)throws Exception{
        boolean flag = false;
        if(datalist == null || datalist.isEmpty()){
            System.out.println("任务列表为空，无法删除！");
        }
        else{
            // 使用迭代器安全删除
            Iterator<TaskList> iterator = datalist.iterator();
            while (iterator.hasNext()) {
                TaskList list = iterator.next();
                if (taskid == list.getTaskid()) {
                    iterator.remove();
                    flag = true;
                    break;
                }
            }
        }
        if(flag){
            writeCsv(datalist);
            System.out.println("任务存在，删除成功");
        }
        else{
            System.out.println("任务不存在，删除失败");
        }
    }
    public void add(TaskList taskList)throws Exception{
        boolean flag = false;
        //先看有没有
        for(TaskList task : datalist){
            if(task.getTaskid() == taskList.getTaskid()){
                flag = true;
            }
        }
        if(flag){
            System.out.println("已存在任务，无需重复添加");
        }
        else{
            datalist.add(taskList);
            writeCsv(datalist);
            System.out.println("添加成功");
        }
    }
    private List<TaskList> readCsv(){
        List<TaskList> res = new ArrayList<>();
        //1.不存在返回null
        if(file == null){
            System.out.println("readCsv文件时不存在！");
            return res;
        }
        //2.存在读取
        try(BufferedReader reader = new BufferedReader(new FileReader(file))){
            CSVParser parser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader());
            for(CSVRecord record : parser){
                TaskList task = new TaskList(
                        Integer.parseInt(record.get("taskid")),
                        Integer.parseInt(record.get("dataid")),
                        record.get("dataname"),
                        record.get("type"),
                        record.get("state"),
                        record.get("username"),
                        record.get("date"),
                        record.get("parameter")
                        );
                res.add(task);
                System.out.println("readCsv成功！");
            }
        }
        catch (Exception e){

        }
        return res;
    }
    private void writeCsv(List<TaskList> datalist) throws IOException{
        if(file == null){
            System.out.println("writeCsv失败！");
        }
        else{
            try(BufferedWriter writer = new BufferedWriter(new FileWriter(file))){
                CSVPrinter printer = new CSVPrinter(writer,CSVFormat.DEFAULT.withHeader("taskid", "dataid", "dataname", "type", "state", "username", "date", "parameter"));
                for(TaskList task : datalist){
                    printer.printRecord(
                            task.getTaskid(),
                            task.getDataid(),
                            task.getDataname(),
                            task.getType(),
                            task.getState(),      // 与表头顺序保持一致
                            task.getUsername(),
                            task.getDate(),
                            task.getParameter()
                    );
                }
                System.out.println("writeCsv成功！");
            }
        }
    }
    private String capilization(String s){
        if(s == null || s.isEmpty()){
            return null;
        }
        String temp = s.substring(0,1);
        return temp.toUpperCase() + s.substring(1);
    }


}
