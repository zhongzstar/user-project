package test;


import main.domain.DateSource;
import main.service.DateSourceService;


public class DataResourceTest {
    public static void main(String[] args) {
        DateSourceService service = new DateSourceService();
        try{
            DateSource dateSource1 = new DateSource(6,"脱敏数据源6","csv","src/main/resources",5,"张三");
            // add
            service.add(dateSource1);
//            // findBy
//            DateSource found = service.findById(newDateSource.getDataid());
//            if(found != null) {
//                System.out.println("找到数据源: " + found);
//            } else {
//                System.out.println("未找到数据源");
//            }
//            // update
//            service.update(newDateSource, "dataname", "txt");
//
//            // findBy
//            found = service.findById(newDateSource.getDataid());
//            if(found != null) {
//                System.out.println("更新后数据源: " + found);
//            } else {
//                System.out.println("未找到数据源");
//            }
//            //findAll
//            System.out.println(service.findAll());
//
//            //delete
//            DateSource deleteSource = new DateSource();
//            deleteSource.setDataid(2);
//            service.delete(deleteSource);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}