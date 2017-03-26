package gototongcheng.zhyan.com.library.Bean;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import java.io.Serializable;
import java.util.List;



/**
 * Created by zhyan on 2017/3/1.
 */

public class GoodsBean implements Parcelable {
    private String name ;
    private String num;
/*    private String price;*/


    public static final Creator<GoodsBean> CREATOR = new Creator<GoodsBean>() {
        @Override
        public GoodsBean createFromParcel(Parcel in) {
            GoodsBean bean = new GoodsBean();
            bean.setName(in.readString());
            bean.setNum(in.readString());

            return bean;
        }

        @Override
        public GoodsBean[] newArray(int size) {
            return new GoodsBean[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(num);
    }

  /*  public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }*/



}
