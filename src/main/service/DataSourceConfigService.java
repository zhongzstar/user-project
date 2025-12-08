package main.service;


import main.domain.DataSourceConfig;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class DataSourceConfigService {
    private String filename = "src/main/resources/DataSourceConfig.csv";
    private File file = new File(filename);
    private List<DataSourceConfig> datalist = null;
    public DataSourceConfigService(){
        try{
            datalist = readCsv();
        }
        catch (Exception e)
        {
            datalist = new ArrayList<>();
            System.out.println("读取文件失败！");
        }
    }
    /**
     * 添加数据源配置
     * @param dataSourceConfig
     */
    public void add(DataSourceConfig dataSourceConfig){
        DataSourceConfig test = findById(dataSourceConfig.getConfigid());
        if(test != null){
            System.out.println("配置源已经存在，不可重复添加！");
        }
        else{
            try{
                datalist.add(dataSourceConfig);
                writeCsv(datalist);
                System.out.println("配置源不存在，添加成功！");
            }
            catch (Exception e){
                System.out.println("配置源不存在，添加失败！");
            }
        }
    }
    /**
     * 删除数据源配置
     * @param configid
     */
    public void delete(int configid){
        DataSourceConfig test = findById(configid);
        if(test != null){
            try{
                //移除符合ID的对象
                datalist.removeIf(config ->  config.getConfigid()== configid);
                writeCsv(datalist);
                System.out.println("删除成功！");
            } catch (Exception e) {
                System.out.println("删除失败！");
                throw new RuntimeException(e);
            }
        }
        else{
            System.out.println("配置源不存在！");
        }
    }
    /**
     * 根据列名和内容查找数据源配置
     * @param colName
     * @param content
     * @return result
     */
    public List<DataSourceConfig> findBy(String colName,String content){
        List<DataSourceConfig> result = new ArrayList<>();
        try{
            for(DataSourceConfig ds : datalist){
                Method method = ds.getClass().getMethod("get" + capitalize(colName));
                if(method.invoke(ds).equals(content)){
                    result.add(ds);
                }
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }
    /**
     * 根据用户名和类型找数据源配置
     * @param userName
     * @param type
     * @return
     */
    public List<DataSourceConfig> findByUsernameAndType(String userName,String type){
        List<DataSourceConfig> result = new ArrayList<>();
        try{
            for(DataSourceConfig ds : datalist){
                if(ds.getUsername().equals(userName) && ds.getConfigtype().equals(type)){
                    result.add(ds);
                }
            }
        }
        catch (Exception e){
            throw new RuntimeException();
        }
        return result;
    }
    /**
     * 根据ID查找数据源对象
     * @param id 数据源ID
     * @return 一个数据源对象
     */
    public DataSourceConfig findById(int id){
        try{
            //获取指定ID的对象
            for(DataSourceConfig dR : datalist){
                if(dR.getConfigid() == id){
                    return dR;
                }
            }
            return null;
        }
        catch(Exception e){
            System.out.println("查找数据源配置出错" + e.getMessage());
            throw e;
        }
    }


    /**
     * 读取csv文件
     * @return List<DataSourceConfig>
     * @throws IOException
     */
    private List<DataSourceConfig> readCsv() throws IOException{
        List<DataSourceConfig> configs = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new FileReader(file))){
            CSVParser parser = new CSVParser(reader,CSVFormat.DEFAULT.withFirstRecordAsHeader());
            for(CSVRecord record:parser){
                DataSourceConfig config = new DataSourceConfig(Integer.parseInt(record.get("configid")),
                        record.get("configtype"),record.get("configusername"),
                        record.get("configpassword"),record.get("confighost"),
                        record.get("configport"),record.get("configpath"),
                        record.get("username"));
                configs.add(config);
            }
        }
        return  configs;
    }
    /**
     * 写入csv文件
     * @param configs
     * @throws IOException
     */
    private void writeCsv(List<DataSourceConfig> configs) throws  IOException{
        try(BufferedWriter writer = new BufferedWriter(new FileWriter((file)))){
            CSVPrinter printer = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader("configid", "configtype", "configusername", "configpassword", "confighost", "configport", "configpath", "username"));
            for(DataSourceConfig config:configs){
                printer.printRecord(
                        config.getConfigid(),
                        config.getConfigtype(),
                        config.getConfigusername(),
                        config.getConfigpassword(),
                        config.getConfighost(),
                        config.getConfigport(),
                        config.getConfigpath(),
                        config.getUsername()
                );
            }
        }
    }
    private String capitalize(String str){
        if(str == null || str.isEmpty()){
            return  str;
        }
        return str.substring(0,1).toUpperCase() + str.substring(1);
    }
}

