package main.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import main.domain.DateSource;
import main.domain.User;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.lang.reflect.Method;
import java.util.*;


public class DateSourceService {
    private ObjectMapper objectMapper = new ObjectMapper();
    private String filename = "src/main/resources/DateSource.json";
    private File file = new File(filename);
    private List<DateSource> dataList = null;
    private Map<String, Class<?>> fieldTypes = new HashMap<>();
    public DateSourceService() {
        fieldTypes = new HashMap<>();
        fieldTypes.put("configid", int.class);
        fieldTypes.put("dataid", int.class);
        fieldTypes.put("dataname", String.class);
        fieldTypes.put("type", String.class);
        fieldTypes.put("parameter", String.class);
        fieldTypes.put("username", String.class);
        try{
            dataList = getDataList();
        }
        catch (Exception e){
            System.out.println("读取文件失败！");
            dataList = new ArrayList<>();
        }
    }
    //增
    /**
     * 添加数据源到Json文件
     * @param dateSource 要添加的数据源对象
     * @throws Exception 读写文件或者解析JSON文件失败
     */
    public void add(DateSource dateSource) throws Exception{
        //检查ID是否已存在
        if(findById(dateSource.getDataid()) != null){
            System.out.println("该数据源ID已经存在！");
        }
        else{
            dataList.add(dateSource);
            objectMapper.writeValue(file, dataList);
            System.out.println("已经添加数据源！");
        }
    }
    //删
    /**
     * 删除指定数据源
     * @param dateSource 需要删除的数据源
     * @throws Exception 读写文件会出错
     */
    public void delete(DateSource dateSource,User user)throws Exception{
        if(!file.exists() || file.length() == 0){
            System.out.println("文件不存在！无法删除");
            return;
        }
        // 根据ID查找并删除对象
        DateSource existing = findById(dateSource.getDataid());
        if(existing != null){
            if(existing.getUsername().equals(user.getName())){
                dataList.remove(existing);
                objectMapper.writeValue(file,dataList);
                System.out.println("已成功删除当前用户的资源");
            }
            else{
                System.out.println("当前用户没有权限，无法删除！");
            }
        }
        else{
            System.out.println("数据源不存在!无法删除");
        }
    }
    //改
    /**
     * 更改某个数据源某一列的内容
     * @param dateSource 数据源对象
     * @param columName 列名
     * @param content 要更改的内容
     */
    public void update(DateSource dateSource,User user, String columName, String content)throws Exception{
        boolean flag = false;
        try{
            //1.所有用户数据源中找到这个用户这个id的数据源并且更改
            for(DateSource ds : dataList){
                if((ds.getDataid() == dateSource.getDataid()) && (ds.getUsername().equals(user.getName()))){
                    // 1. 确定字段类型
                    Class<?> paramType = fieldTypes.get(columName);
                    Object value = null;
                    // 2. 根据类型进行转换
                    if (paramType == Integer.class || paramType == int.class) {
                        value = Integer.parseInt(content);
                    }
                    else if (paramType == String.class) {
                        value = content;
                    }
                    else {
                        throw new IllegalArgumentException("不支持的字段类型：" + paramType);
                    }
                    // 3. 获取并调用 setter 方法
                    Method method = ds.getClass().getMethod("set" + capitalize(columName), paramType);
                    method.invoke(ds, value);
                    flag = true;
                    //找到了退出循环
                    break;
                }
            }
            //2.根据1.更新数据库
            if(flag){
                objectMapper.writeValue(file,dataList);
                System.out.println("数据已经更新！");
            }
            else{
                System.out.println("数据更新失败!用户数据源不匹配!");
            }
        }
        catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }
    public void readDataSource(User user, DateSource dateSource) throws Exception {
        // 查找指定用户和ID的数据源
        DateSource target = null;
        for(DateSource ds : dataList){
            if(ds.getDataid() == dateSource.getDataid() && ds.getUsername().equals(user.getName())){
                target = ds;
                break;
            }
        }
        if(target == null){
            System.out.println("当前用户没有这个数据源！");
        }
        else{
            System.out.println("已找到，读取这个数据源是：");
            List<String[]> csvData = readCsv(target.getParameter());
            for(String[] row : csvData){
                System.out.println(Arrays.toString(row));
            }
        }

    }
    //查

    /**
     * 返回数据源对象列表
     * @return dateList 数据源对象列表
     * @throws Exception
     */
    public List<DateSource> findAll()throws Exception{
        return dataList;
    }
    /**
     * 根据ID查找数据源对象
     * @param id 数据源ID
     * @return 一个数据源对象
     */
    public DateSource findById(int id) throws Exception{
       try{
           //获取指定ID的对象
           for(DateSource dR : dataList){
               if(dR.getDataid() == id){
                   return dR;
               }
           }
           return null;
       }
       catch(Exception e){
           System.out.println("查找数据源出错" + e.getMessage());
           throw e;
       }
    }
    /**
     * 根据用户对象找到数据源
     * @param user
     * @return 一个用户数据源列表
     * @throws Exception
     */
    public List<DateSource> findByUsername(User user) throws Exception{

        List<DateSource> res = new ArrayList<>();
        if(user != null && user.getName()!=null) {
            for(DateSource ds : dataList){
                if(ds.getUsername().equals(user.getName())){
                    res.add(ds);
                }
            }
        }
        return res;
    }

    /**
     * 根据后缀得到文件类型
     * @param filename
     * @return csv txt等
     */
    private String getFileType(String filename){
        int index = filename.lastIndexOf(".");
        if(index == -1 || index == filename.length()-1){
            return "";
        }
        return filename.substring(index+1);
    }

    /**
     * 读取csv文件
     * @param filePath
     * @return csv文件列表
     * @throws IOException
     */
    private List<String[]> readCsv(String filePath) throws IOException {
        List<String[]> result = new ArrayList<>();
        try (CSVParser parser = new CSVParser(new FileReader(filePath), CSVFormat.DEFAULT.withFirstRecordAsHeader())) {
            for (CSVRecord record : parser) {
                String[] row = record.values();
                result.add(row);
            }
        }
        return result;
    }

    /**
     * 字符串首字母大写
     * @param str
     * @return str
     */
    private String capitalize(String str){
        if(str == null || str.isEmpty()){
            return  str;
        }
        return str.substring(0,1).toUpperCase() + str.substring(1);
    }
    /**
     * 得到所有用户的所有数据源列表
     * @return 数据源列表
     * @throws Exception
     */
    private List<DateSource> getDataList() throws Exception{
            if(dataList == null){// 第一次检查：快速判断是否已初始化
                synchronized (this){ // 加锁，防止竞争，只有当前线程可以访问
                    if(dataList == null){// 第二次检查：确认是否真的需要初始化
                        dataList = objectMapper.readValue(file,
                                objectMapper.getTypeFactory().constructCollectionType(List.class, DateSource.class));
                    }
                    else{
                        dataList = new ArrayList<>();
                    }
                }
            }
            return dataList;
        }
    }


