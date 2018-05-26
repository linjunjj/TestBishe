package model;

import java.io.Serializable;

/**
 * @author 林俊
 * @create 2018/5/24.
 * @desc
 **/
public class SendData implements Serializable {
    private  int  type;
    private  Object object;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
