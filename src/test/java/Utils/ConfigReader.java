package Utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader{
    private static Properties properties;

    // "Static block" عشان يحمل الداتا أول ما الكلاس تشتغل
    static {
        try {
            // حدد مسار الفايل بدقة
            String path = "src/main/resources/TestData.properties";
            FileInputStream input = new FileInputStream(path);
            properties = new Properties();
            properties.load(input);
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("فشل في تحميل ملف testdata.properties");
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}