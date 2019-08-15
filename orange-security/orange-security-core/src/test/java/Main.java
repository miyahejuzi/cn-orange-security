import java.lang.reflect.Field;

/**
 * @author kz
 * @date 2019/8/15
 */
public class Main {

    public static void main(String[] args) {
        /*Integer a = 1;
        Integer b = 2;

        swap(a, b);
*/
/*        Integer a = 100, b = 100, c = 200, d = 200;
        System.out.println(a == b);
        System.out.println(c == d);*/
        String format = String.format("h%so, w%sd", "ell", "orl");
        System.out.println(format);
    }

    private static void swap(Integer a, Integer b) {
        try {
            Field field = Integer.class.getDeclaredField("value");
            field.setAccessible(true);
            int num = a;
            field.setInt(a, b);
            field.setInt(b, num);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
