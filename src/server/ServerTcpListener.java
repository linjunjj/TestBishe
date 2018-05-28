package server;

import config.Config;
import model.Data;
import model.SendData;
import utils.CloseUtils;
import utils.GSONUtils;
import utils.Utils;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author 林俊
 * @create 2018/5/22.
 * @desc
 **/
public class ServerTcpListener  {
    private Data data1;
    private  Data data2;
    public static void main(String[] args) {
        try {
            final ServerSocket server = new ServerSocket(33456);
            Thread th = new Thread(new Runnable() {
                public void run() {
                    while (true) {
                        try {
                            System.out.println("开始监听...");
                            Socket socket = server.accept();
                            System.out.println("有链接");
                            receiveFile(socket);
                            handle(socket.getInputStream());

                        } catch (Exception e) {
                        }
                    }
                }
            });
            th.run(); //启动线程运行
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void receiveFile(Socket socket) {
        byte[] inputByte = null;
        int length = 0;
        DataInputStream dis = null;
        FileOutputStream fos = null;
        try {
            try {
                dis = new DataInputStream(socket.getInputStream());
                fos = new FileOutputStream(new File("res/image/test.jpg"));
                inputByte = new byte[1024];
                System.out.println("开始接收数据...");
                while ((length = dis.read(inputByte, 0, inputByte.length)) > 0) {
                    System.out.println(length);
                    fos.write(inputByte, 0, length);
                    fos.flush();
                }
//                     传输后封装的sha1 crc值
                Data data=Utils.count(Config.saveimgpath,Config.savepath);
                System.out.println("\n");
                System.out.println("接收到文件的杂凑函数值为"+data.getHashvalue());
                System.out.println("接收到文件的第一个CRC值为"+data.getCrca1());
                System.out.println("接收到文件的第二个CRC值为"+data.getCrca2());
                System.out.println("接收到文件的第三个CRC值为"+data.getCrca3());

                System.out.println("完成接收");
            } finally {
                if (fos != null)
                    fos.close();
                if (dis != null)
                    dis.close();
                if (socket != null)
                    socket.close();
            }
        } catch (Exception e) {
        }
    }

    private static void handle(InputStream input) {
        try {
            byte[] data = new byte[2048];
            int len = input.read(data);
            // 有数据，进行处理
            if (len > 0) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                byteArrayOutputStream.write(data, 0, len);
                // 获取客户端的请求数据
                SendData info = GSONUtils.read(
                        byteArrayOutputStream.toByteArray(), SendData.class);
                System.out.println("\n");
                System.out.println("Client发来的类型是数据: "
                        + info.getType());
                System.out.println("Client发来的数据杂凑函数值是: "
                        + ((Data)info.getObject()).getHashvalue());
                System.out.println("Client发来的数据第一个CRC值是: "
                        + ((Data)info.getObject()).getHashvalue());
                System.out.println("Client发来的数据第二个CRC值是: "
                        + ((Data)info.getObject()).getHashvalue());
                System.out.println("Client发来的数据第三个CRC值是: "
                        + ((Data)info.getObject()).getHashvalue());

            }

        } catch (IOException e) {
            // e.printStackTrace();
            // 释放资源

            return;
        }

    }


}