import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

public class TestJackJson {

    public static void main(String[] args) {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = new HashMap<>();
        map.put("name", "John Doe");
        map.put("age", 30);
        map.put("email", "john.doe@example.com");
        map.put("nullField", null);
        try {
            String json = mapper.writeValueAsString(map);
            System.out.println(json);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
