package test;


import main.domain.DateSource;
import main.service.DateSourceService;


public class DataResourceTest {
    public static void main(String[] args) {
        DateSourceService service = new DateSourceService();
        try{
            DateSource newDateSource = new DateSource(5,"脱敏数据源5","csv","/src/main/resources",5,"zz");
            // add
            service.add(newDateSource);
            // findBy
            DateSource found = service.findById(newDateSource.getDataid());
            if(found != null) {
                System.out.println("找到数据源: " + found);
            } else {
                System.out.println("未找到数据源");
            }
            // update
            service.update(newDateSource, "dataname", "txt");

            // findBy
            found = service.findById(newDateSource.getDataid());
            if(found != null) {
                System.out.println("更新后数据源: " + found);
            } else {
                System.out.println("未找到数据源");
            }
            //findAll
            System.out.println(service.findAll());

            //delete
            DateSource deleteSource = new DateSource();
            deleteSource.setDataid(2);
            service.delete(deleteSource);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}