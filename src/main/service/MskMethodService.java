package main.service;


import main.domain.MskMethod;
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


public class MskMethodService {
    private String filename = "src/main/resources/MskMethod.csv";
    private File file = new File(filename);
    private List<MskMethod> dataList = null;
    public MskMethodService(){
        dataList = readCsv();
    }
    public List<MskMethod> findByType(String type){
        boolean flag = false;
        if(fileNotExist()){
            System.out.println("文件不存在或文件为空");
            return null;
        }
        List<MskMethod> res = new ArrayList<>();
        for(MskMethod mskMethod : dataList){
            if(mskMethod.getType().equals(type)){
                res.add(mskMethod);
                flag = true;
            }
        }
        if(flag){
            System.out.println("已成功返回当前用户的脱敏策略");
        }
        else{
            System.out.println("当前用户无脱敏策略");
        }
        return res;
    }
    public void add(MskMethod mskMethod)throws Exception{
        if(file == null){
            System.out.println("文件不存在！无法add");
            return;
        }
        boolean flag = false;
        for(MskMethod msk : dataList){
            if(msk.getMethodid() == mskMethod.getMethodid()){
                flag = true;
                break;
            }
        }
        if(flag){
            System.out.println("已经存在相同主键，无法添加");
        }
        else{
            dataList.add(mskMethod);
            writeCsv();
            System.out.println("已添加当前脱敏策略");
        }
    }
    public void updata(int mskId, String colName, String content)throws Exception{
        boolean flag = false;
        if(dataList == null){
            System.out.println("更新失败,脱敏策略表无任何策略");
        }
        else{
            for(MskMethod msk:dataList){
                if(msk.getMethodid() == mskId){
                    Method method = msk.getClass().getMethod("set" + capilization(colName), String.class);
                    method.invoke(msk,content);
                    flag = true;
                    break;
                }
            }
        }
        if(flag) {
            writeCsv();
            System.out.println("更新成功!");
        }else{
            System.out.println("更新失败，没有找到策略id为"+ mskId);
        }
    }
    public void delete(int mskId)throws Exception{
        boolean flag = false;
        if(dataList == null || dataList.isEmpty()){
            System.out.println("任务列表为空，无法删除！");
        }
        else{
            // 使用迭代器安全删除
            Iterator<MskMethod> iterator = dataList.iterator();
            while (iterator.hasNext()) {
                MskMethod msk = iterator.next();
                if (mskId == msk.getMethodid()) {
                    iterator.remove();
                    flag = true;
                    break;
                }
            }
        }
        if(flag){
            writeCsv();
            System.out.println("任务存在，删除成功");
        }
        else{
            System.out.println("任务不存在，删除失败");
        }
    }
    public MskMethod findByMskMethodId(int methodId){
        boolean flag = false;
        if(fileNotExist()){
            System.out.println("文件不存在或文件为空");
            return null;
        }
        MskMethod res = new MskMethod();
        for(MskMethod mskMethod:dataList){
            if(mskMethod.getMethodid() == methodId){
                res = mskMethod;
                flag = true;
            }
        }
        if(flag){
            System.out.println("已成功返回当前用户的脱敏策略");
        }
        else{
            System.out.println("当前用户无脱敏策略");
        }
        return res;
    }
    public List<MskMethod> findByUsername(String name){
        boolean flag = false;
        if(fileNotExist()){
            System.out.println("文件不存在或文件为空");
            return null;
        }
        List<MskMethod> res = new ArrayList<>();
        for(MskMethod mskMethod : dataList){
            if(mskMethod.getUsername().equals(name)){
                res.add(mskMethod);
                flag = true;
            }
        }
        if(flag){
            System.out.println("已成功返回当前用户的脱敏策略");
        }
        else{
            System.out.println("当前用户无脱敏策略");
        }
        return res;
    }
    public List<MskMethod> findAll(){
        if(fileNotExist()){
            
            return null;
        }
        return dataList;
    }
    public int maxInsertId(){
        if(fileNotExist()){
            return 0;
        }
        int res = Integer.MIN_VALUE;
        for(MskMethod mskMethod : dataList){
            res = Math.max(res,mskMethod.getMethodid());
        }
        return res;
    }
    private List<MskMethod> readCsv(){

        if(file == null){
            return null;
        }
        List<MskMethod> res = new ArrayList<>();

        try(BufferedReader reader = new BufferedReader(new FileReader(file))){
            CSVParser parser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader());
            for(CSVRecord record : parser){
                MskMethod method = new MskMethod(
                        Integer.parseInt(record.get("methodid")),
                        record.get("methodname"),
                        record.get("type"),
                        record.get("maskmethod"),
                        record.get("parameter"),
                        record.get("username")
                );
                res.add(method);
            }
        }
        catch (Exception e){

        }
        return res;
    }
    private void writeCsv()throws Exception{
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(file))){
            CSVPrinter printer = new CSVPrinter(writer,
                    CSVFormat.DEFAULT.withHeader("methodid", "methodname", "type", "maskmethod", "parameter", "username"));
            for (MskMethod method : dataList) {
                printer.printRecord(
                        method.getMethodid(),
                        method.getMethodname(),
                        method.getType(),
                        method.getMaskmethod(),
                        method.getParameter(),
                        method.getUsername()
                );
            }
            System.out.println("writeCsv成功！");
        }
    }
    private boolean fileNotExist(){
        return (file == null || dataList == null);
    }
    private String capilization(String s){
        if(s == null || s.isEmpty()){
            return null;
        }
        String temp = s.substring(0,1);
        return temp.toUpperCase() + s.substring(1);
    }
}
