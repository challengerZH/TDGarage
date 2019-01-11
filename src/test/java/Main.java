import com.sgai.ds.dtgarage.entity.Apps;
import com.sgai.ds.dtgarage.entity.Property;
import com.sgai.ds.dtgarage.prepare.Prepare;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException {
        Prepare prepare = new Prepare();
        XSSFWorkbook xlsx = new XSSFWorkbook(
                new FileInputStream("C:\\Users\\zhouhua\\Desktop\\首自信-work\\立体车库无障碍出车\\00.xlsx"));
        Map<String, List<String[]>> data = prepare.parseToMap(xlsx);


        Map<String, List<String[]>> map;
        Apps a = new Apps();
        map = a.simulatedOperation(data, new Property(5, 5, 5, 40, 40, 15));

        System.out.println("***********************************");


        int num = 0;
        if (map != null)
            for (String key : map.keySet()) {
                System.out.println("------------" + key + "-----------------");
                for (int i = 0; i < map.get(key).size(); i++) {
                    for(String s : map.get(key).get(i))
                        System.out.print(s + "  ");
                    System.out.println();
                    num++;
                }
                System.out.println("###############################");

            }
        System.out.println("&&&&&&    " + num + "     $$$$$$$$$");
    }
}
