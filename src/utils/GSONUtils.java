package utils;

import java.io.*;

/**
 * @author 林俊
 * @create 2018/5/24.
 * @desc
 **/
public class GSONUtils {
    /**
     *
     * @param s
     *            需要序列化的对象
     * @return
     * @throws IOException
     */
    public static byte[] write(Serializable s) {
        ObjectOutputStream oos = null;
        ByteArrayOutputStream arrayOutputStream = null;
        try {
            arrayOutputStream = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(arrayOutputStream);
            oos.writeObject(s);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            CloseUtils.closeCloseable(oos, arrayOutputStream);
        }
        return arrayOutputStream.toByteArray();
    }

    /**
     *
     * @param data
     *            对象序列化后的自己数组
     * @param clazz
     *            对象
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static <T> T read(byte[] data, Class<T> clazz) {
        ObjectInputStream ois = null;
        ByteArrayInputStream inputStream = null;
        Object o = null;
        try {
            inputStream = new ByteArrayInputStream(data);
            ois = new ObjectInputStream(inputStream);
            o = ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            CloseUtils.closeCloseable(ois, inputStream);
        }
        return (T) o;
    }
}
