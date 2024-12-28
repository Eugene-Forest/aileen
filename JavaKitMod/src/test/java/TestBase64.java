import org.aileen.mod.kit.Base64Kit;

/**
 * @author Eugene-Forest
 */
public class TestBase64 {

    public static void main(String[] args) {
        String str = "hello world";
        String encode = Base64Kit.encode(str);
        System.out.println(encode);
        String decode = Base64Kit.decode(encode);
        System.out.println(decode);
    }
}
