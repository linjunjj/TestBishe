package client;

import config.Config;
import model.Data;
import model.SendData;
import utils.GSONUtils;
import utils.Utils;

import java.io.*;
import java.math.BigInteger;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.security.MessageDigest;
import java.util.List;

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
                File file = new File(Config.imgpath);
                fis = new FileInputStream(file);
                sendBytes = new byte[1024];
                while ((length = fis.read(sendBytes, 0, sendBytes.length)) > 0) {
                    dos.write(sendBytes, 0, length);
                    dos.flush();
                }
                Data data=Utils.count(Config.imgpath,Config.filepath);
                SendData sendData=new SendData();
                sendData.setObject(data);
                sendData.setType(1);
               sendDataToServer(dos,GSONUtils.write(sendData));

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

    private static void sendDataToServer(OutputStream out, byte[] requestInfo) {
        try {
            out.write(requestInfo);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}