package main.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import main.domain.DateSource;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


public class DateSourceService {
    private String filename = "src/main/resources/DateSource.json";
    private ObjectMapper objectMapper = new ObjectMapper();

    //增
    /**
     * 添加数据源到Json文件
     * @param dateSource 要添加的数据源对象
     * @throws Exception 读写文件或者解析JSON文件失败
     */
    public void add(DateSource dateSource) throws Exception{
        File file = new File(filename);
        List<DateSource> dataList;
        //1.若文件存在且不为空，把json数据读取到datalist列表中。
        if(file.exists() && file.length() > 0){
            dataList = objectMapper.readValue(file,objectMapper.getTypeFactory().constructCollectionType(List.class, DateSource.class));
        }
        //2.若文件不存在或为空，重新创建一个datalist列表
        else{
            dataList = new ArrayList<>();
        }

        //3.检查ID是否已存在
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
    public void delete(DateSource dateSource)throws Exception{
        File file = new File(filename);
        if(!file.exists() || file.length() == 0){
            System.out.println("文件不存在！无法删除");
            return;
        }
        List<DateSource> dataList = objectMapper.readValue(file,
                objectMapper.getTypeFactory().constructCollectionType(List.class, DateSource.class));
        
        // 根据ID查找并删除对象
        DateSource existing = findById(dateSource.getDataid());
        if(existing != null){
            dataList.remove(existing);
            objectMapper.writeValue(file, dataList);
            System.out.println("删除成功！");
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
    public void update(DateSource dateSource, String columName, String content){
        try{
            File file = new File(filename);
            List<DateSource> dataList;
            boolean flag = false;
            //1.读取数据库或创建空datalist
            if (file.exists() && file.length() > 0) {
                dataList = objectMapper.readValue(file,
                        objectMapper.getTypeFactory().constructCollectionType(List.class, DateSource.class));
            } else {
                dataList = new ArrayList<>();
            }
            //2.根据ID查找对象并更新
            for(DateSource dS : dataList){
                if(dS.getDataid() == dateSource.getDataid()){
                    Method method = dS.getClass().getMethod("set" + capitalize(columName),String.class);
                    method.invoke(dS,content);
                    flag = true;
                }
            }
            //3.更新数据库
            if(flag){
                objectMapper.writeValue(file,dataList);
                System.out.println("数据已经更新！");
            }
            else{
                System.out.println("数据添加失败！");
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    //查

    /**
     * 返回数据源对象列表
     * @return dateList 数据源对象列表
     * @throws Exception
     */
    public List<DateSource> findAll()throws Exception{
        List<DateSource> dateList = new ArrayList();
        File file = new File(filename);
        dateList = objectMapper.readValue(file, objectMapper.getTypeFactory().constructCollectionType(List.class, DateSource.class));
        return dateList;
    }
    /**
     * 根据ID查找数据源对象
     * @param id 数据源ID
     * @return 找到返回数据源对象，未找到返回null
     */
    public DateSource findById(int id) throws Exception{
        File file = new File(filename);
        List<DateSource> dataList;
        //1.避免因文件不存在导致的异常
        if(file.exists() && file.length()>0){
            dataList = objectMapper.readValue(file, objectMapper.getTypeFactory().constructCollectionType(List.class, DateSource.class));
        }
        else{
            return null;
        }
        //2.获取指定ID的对象
        for(DateSource dR : dataList){
            if(dR.getDataid() == id){
                return dR;
            }
        }
        return null;
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
}