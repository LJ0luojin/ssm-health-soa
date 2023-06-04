import com.lh.utils.QiniuUtils;
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

}
