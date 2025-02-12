
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.aileen.lc.LCM3v81;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = LCM3v81.class)
public class TestFreeMark {

    @Autowired
    private Configuration cfg;

    @Test
    public void testTemplateRendering() throws IOException, TemplateException {
        // 获取模板
        Template template = cfg.getTemplate("Hello.ftl");

        // 准备数据模型
        Map<String, Object> dataModel = new HashMap<>();
        dataModel.put("user", new User("John Doe", "john.doe@example.com"));
        dataModel.put("name", "User Information");
        dataModel.put("email", "This is the content of the email.");
        // 渲染模板
        StringWriter out = new StringWriter();
        template.process(dataModel, out);

        System.out.println(out.getBuffer().toString());

        // 预期输出
//        String expectedOutput = "<!DOCTYPE html>\n" +
//                                "<html>\n" +
//                                "<head>\n" +
//                                "    <title>User Information</title>\n" +
//                                "</head>\n" +
//                                "<body>\n" +
//                                "    <h1>User Information</h1>\n" +
//                                "    <p>Name: John Doe</p>\n" +
//                                "    <p>Email: john.doe@example.com</p>\n" +
//                                "</body>\n" +
//                                "</html>";
//
//        // 断言输出是否符合预期
//        assertEquals(expectedOutput.trim(), out.getBuffer().toString().trim());
    }

    @Test
    public void testPath() throws IOException, TemplateException {
        // 获取模板
        Template template = cfg.getTemplate("/sql/sql.ftl");

        // 准备数据模型
        Map<String, Object> dataModel = new HashMap<>();
        dataModel.put("user", new User("John Doe", "john.doe@example.com"));
        dataModel.put("name", "User Information");
        dataModel.put("email", "This is the content of the email.");
        // 渲染模板
        StringWriter out = new StringWriter();
        template.process(dataModel, out);

        System.out.println(out.getBuffer().toString());

    }
}
