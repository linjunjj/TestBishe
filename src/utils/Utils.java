package utils;

import model.Data;

import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;

/**
 * @author 林俊
 * @create 2018/5/22.
 * @desc
 **/
public class Utils {
//    计算hash值
    public static String  HashCode(InputStream fis) {
        try {
            //拿到一个MD5转换器,如果想使用SHA-1或SHA-256，则传入SHA-1,SHA-256
            MessageDigest md = MessageDigest.getInstance("SHA-1");

            //分多次将一个文件读入，对于大型文件而言，比较推荐这种方式，占用内存比较少。
            byte[] buffer = new byte[1024];
            int length = -1;
            while ((length = fis.read(buffer, 0, 1024)) != -1) {
                md.update(buffer, 0, length);
            }
            fis.close();
            //转换并返回包含16个元素字节数组,返回数值范围为-128到127
            byte[] md5Bytes  = md.digest();
            BigInteger bigInt = new BigInteger(1, md5Bytes);//1代表绝对值
            return bigInt.toString(16);//转换为16进制
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static List<String> split(String src, String dest){

        if("".equals(src)||src==null||"".equals(dest)||dest==null){
            System.out.println("分割失败");
        }

        File srcFile = new File(src);//源文件

        long srcSize = srcFile.length();//源文件的大小
        long destSize = 102;//目标文件的大小（分割后每个文件的大小）

        int number = (int)(srcSize/destSize);
        number = srcSize%destSize==0?number:number+1;//分割后文件的数目
        String fileName = "temp";//源文件名
        InputStream in = null;//输入字节流
        BufferedInputStream bis = null;//输入缓冲流
        byte[] bytes = new byte[1024*1024];//每次读取文件的大小为1MB
        int len = -1;//每次读取的长度值
          List<String> list=new ArrayList<>();
        try {
            in = new FileInputStream(srcFile);
            bis = new BufferedInputStream(in);
            for(int i=0;i<number;i++){

                String destName = dest+File.separator+fileName+"-"+i+".dat";
                list.add(destName);
                OutputStream out = new FileOutputStream(destName);
                BufferedOutputStream bos = new BufferedOutputStream(out);
                int count = 0;
                while((len = bis.read(bytes))!=-1){
                    bos.write(bytes, 0, len);//把字节数据写入目标文件中
                    count+=len;
                    if(count>=destSize){
                        break;
                    }
                }
                bos.flush();//刷新
                bos.close();
                out.close();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            //关闭流
            try {
                if(bis!=null)bis.close();
                if(in!=null)in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public static List<String> cut(String src,String dest) {
        File file = new File(src);
        int num = 3;//分割文件的数量
        List<String> list=new ArrayList<>();

        long lon = file.length() / num + 1L;//使文件字节数+1，保证取到所有的字节
        try {
            RandomAccessFile raf1 = new RandomAccessFile(file, "r");

            byte[] bytes = new byte[1024];//值设置越小，则各个文件的字节数越接近平均值，但效率会降低，这里折中，取1024
            int len = -1;
            for (int i = 0; i < num; i++) {
                String name = dest+File.separator+"temp"+"-"+i+".dat";
                list.add(name);
                File file2 = new File(name);
                RandomAccessFile raf2 = new RandomAccessFile(file2, "rw");

                while ((len = raf1.read(bytes)) != -1){//读到文件末尾时，len返回-1，结束循环
                    raf2.write(bytes, 0, len);
                    if (raf2.length() > lon)//当生成的新文件字节数大于lon时，结束循环
                        break;
                }
                raf2.close();
            }
            raf1.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }




    public static Data count(String path,String filepath){
        Data data=new Data();

        String img1;String img2; String img3;

        File file =new File(path);
        try {
            InputStream inputStream=new FileInputStream(file);
            data.setHashvalue(Utils.HashCode(inputStream));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        List<String> iamgespath= Utils.cut(path,filepath);
        for ( int i=0;i<iamgespath.size();i++) {
            System.out.println(iamgespath.get(i));
            try {
                Long value=    Utils.getCRC32(iamgespath.get(i));


                System.out.println(value);
                if (i==0){
                    data.setCrca1(value);
                }else if (i==1){
                    data.setCrca2(value);
                }else {
                    data.setCrca2(value);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return  data;
    }




    public static Long getCRC32(String filepath) throws IOException {
        CRC32 crc32 = new CRC32();
        FileInputStream fileinputstream = new FileInputStream(new File(filepath));
        CheckedInputStream checkedinputstream = new CheckedInputStream(fileinputstream, crc32);
        while (checkedinputstream.read() != -1) {
        }
        checkedinputstream.close();
        return crc32.getValue();
    }


}
