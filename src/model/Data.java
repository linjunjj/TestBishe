package model;

import java.io.Serializable;

/**
 * @author 林俊
 * @create 2018/5/24.
 * @desc
 **/
public class Data implements Serializable {
    private  String hashvalue;
    private  Long crca1;
    private  Long crca2;
    private  Long crca3;

    public  static  Long a;

    public String getHashvalue() {
        return hashvalue;
    }

    public void setHashvalue(String hashvalue) {
        this.hashvalue = hashvalue;
    }

    public Long getCrca1() {
        return crca1;
    }

    public void setCrca1(Long crca1) {
        this.crca1 = crca1;
    }

    public Long getCrca2() {
        return crca2;
    }

    public void setCrca2(Long crca2) {
        a=crca2;
        this.crca2 = crca2;
    }

    public Long getCrca3() {
        return crca3;
    }

    public void setCrca3(Long crca3) {
        this.crca3 = crca3;
    }
}
