import com.lh.utils.QiniuUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

public class test {

    public void Test(){
        String suffix = "asdasdhjkahf.sdasd.jpg";
        System.out.println(suffix.substring(suffix.lastIndexOf(".")));
    }

    public void Test1() throws UnsupportedEncodingException {
        byte[] bytes =  "hello qiniu cloud".getBytes("utf-8");
        QiniuUtils.upload2Qiniu(bytes,"test1");
    }

    public static void main(String[] args) throws Exception {
        XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(new File("C:\\Users\\lh\\Desktop\\test.xlsx")));
        XSSFSheet sheetAt = workbook.getSheetAt(0);
        for (Row cells : sheetAt) {
            System.out.println(cells.getRowNum());
            for (Cell cell : cells) {

                System.out.println(cell.getStringCellValue());
            }
        }
    }

}
