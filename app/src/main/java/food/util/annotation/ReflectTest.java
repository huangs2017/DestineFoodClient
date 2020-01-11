package food.util.annotation;

// 反射
public class ReflectTest {

    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        /* 三种方式获取class对象
         *  1: 对象.getClass
         *  2: 类.class
         *  3：Class.forName("包名.类名")
         */
        Iphone iphone = new Iphone();
        Class clazz;
        clazz = iphone.getClass();
        clazz = Iphone.class;
        clazz = Class.forName("food.util.annotation.Iphone");

        Iphone iphone2 = (Iphone) clazz.newInstance();
    }

}

class Iphone {
    public Iphone() {

    }
}
