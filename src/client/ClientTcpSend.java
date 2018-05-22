package client;

import java.io.*;
import java.math.BigInteger;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.security.MessageDigest;

/**
 * @author 林俊
 * @create 2018/5/22.
 * @desc
 **/
public class ClientTcpSend {
    public static void main(String[] args) {
        int length = 0;
        byte[] sendBytes = null;
        Socket socket = null;
        DataOutputStream dos = null;
        FileInputStream fis = null;

        try {
            try {
                socket = new Socket();
                socket.connect(new InetSocketAddress("127.0.0.1", 33456),
                        10 * 1000);
                dos = new DataOutputStream(socket.getOutputStream());
                File file = new File("/root/6674541037_c3a9c8b64c_b.jpg");
                fis = new FileInputStream(file);
                sendBytes = new byte[1024];
                while ((length = fis.read(sendBytes, 0, sendBytes.length)) > 0) {
                    dos.write(sendBytes, 0, length);
                    dos.flush();
                }
            } finally {
                if (dos != null)
                    dos.close();
                if (fis != null)
                    fis.close();
                if (socket != null)
                    socket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }






}