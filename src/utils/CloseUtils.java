package utils;

import java.io.Closeable;
import java.io.IOException;

/**
 * @author 林俊
 * @create 2018/5/24.
 * @desc
 **/
public class CloseUtils {
    public static void closeCloseable(Closeable... closeables) {
        for (int i = 0; i < closeables.length; i++) {
            if (closeables[i] != null) {
                try {
                    closeables[i].close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
