package gototongcheng.zhyan.com.library.Utils;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import java.util.Date;

/**
 * Created by admin on 2017/3/10.
 */

public class PriceUtil {
    private Activity activity;
    public PriceUtil(Activity activity1){
        activity = activity1;
    }
   /* 走兔代购价格表:时间10点-21点  代购数量为1件物品的时候:3km以内10元、3+n km价格10+2n ：n小于等于17km、超20km价格为2*(10+2n)。代购数量为1+m件的时候：3km以内10+3m元、3+n km价格10+3m+2n ：n小于等于17km、超20km价格为2*(10+3m+2n)
                    时间为21点到临晨1点  代购数量为1件物品的时候:3km以内10*2元、3+n km价格（10+2n）*2 ：n小于等于17km、超20km价格为2*(10+2n)*2。代购数量为1+m件的时候：3km以内（10+3m）*2元、3+n km价格（10+3m+2n）*2 ：n小于等于17km、超20km价格为2*(10+3m+2n)*2*/
    public String gotoHelpMeBuylFee(float dis1,int num){
        /*String price = "￥";*/
        String temp = ""+dis1;
        int indexOf = temp.indexOf(".");
        int dis = 0;
        if(indexOf >0){
            temp = temp.substring(0,indexOf);
            dis = Integer.parseInt(temp);
            dis += 1;
        }

        String price = "";
        TimeUtil timeUtil = new TimeUtil();
        int hour = timeUtil.getHour(new Date());
        Log.i("gotoHelpMeBuylFee",""+hour);
        if(hour < 0){
            return "";
        }
        if((hour >=10)&&(hour <=21)){
            if(num == 1){
                if(dis <= 3) {
                    price += "10";
                }else if((dis >= 3)&&(dis <= 20)){
                    float fee = 10 + 2*(dis -3);
                    price +=  ""+fee;
                }else if(dis > 20){
                    float fee =2*(10 + 2*(dis -3));
                    price += ""+fee;
                }
            }else if(num > 1){
                if(dis <= 3) {
                    float fee = 10 + ((num -1)*3);
                    price += ""+fee;
                }else if((dis >= 3)&&(dis <= 20)){
                    float fee = 10 +((num -1)*3) + 2*(dis -3);
                    price +=  ""+fee;
                }else if(dis > 20){
                    float fee =2*(10 + ((num -1)*3) + 2*(dis -3));
                    price += ""+fee;
                }
            }
        }else if((hour > 21)||(hour <= 1)){
            if(num == 1){
                if(dis <= 3) {
                    float fee = 10*2;
                    price += "" + fee;
                }else if((dis >= 3)&&(dis <= 20)){
                    float fee = (10 + 2*(dis -3))*2;
                    price +=  ""+fee;
                }else if(dis > 20){
                    float fee =2*(10 + (2*(dis -3)))*2;
                    price += ""+fee;
                }
            }else if(num > 1){
                if(dis <= 3) {
                    float fee = (10 + ((num -1)*3))*2;
                    price += ""+fee;
                }else if((dis >= 3)&&(dis <= 20)){
                    float fee = (10 +((num -1)*3) + 2*(dis -3))*2;
                    price +=  ""+fee;
                }else if(dis > 20){
                    float fee =2*(10 + ((num -1)*3) + 2*(dis -3))*2;
                    price += ""+fee;
                }
            }
        }else if((hour > 1)&&(hour < 10)){
            Toast.makeText(activity,"尊敬的客户，我们兔子还没有出门呢（快递员还没有上班，10点开始上班）",Toast.LENGTH_LONG).show();
        }
        Log.i("gotoHelpMeBuylFee",""+price);
        if(price.length() < 2){
            price = "";
        }
        String tempPrice = price;
        int index = tempPrice.indexOf(".");
        if(index > 0){
            tempPrice = tempPrice.substring(0,index);
        }
        return tempPrice;
    }

/*
    走兔帮我送价格表:时间10点-21点  配送重量为10kg内的物品的时候:3km以内7元、3+n km价格7+2n ：n小于等于17km、超20km价格为2*(7+2n)。配送重量为（10+m）kg的时候：3km以内7+2m元、3+n km价格10+2m+2n ：n小于等于17km、超20km价格为2*(7+2m+2n)
    时间为21点到临晨1点  配送重量为10kg内的物品的时候:3km以内7*2元，3+n km价格（7+2n）*2 ：n小于等于17km，超20km价格为2*(7+2n)*2。配送重量为（10+m）kg的时候：3km以内（7+2m）*2元，3+n km价格（7+2m+2n）*2 ：n小于等于17km、超20km价格为2*(7+2m+2n)*2*//*


*/



    public String gotoHelpMeSendlFee(float dis1/*,float weight*/){
        /*String price = "￥";*/
        String temp = ""+dis1;
        int indexOf = temp.indexOf(".");
        int dis = 0;
        if(indexOf >0){
            temp = temp.substring(0,indexOf);
            dis = Integer.parseInt(temp);
            dis += 1;
        }
        String price = "";
        TimeUtil timeUtil = new TimeUtil();
        int hour = timeUtil.getHour(new Date());
        Log.i("gotoHelpMeSendlFee",""+hour);
        if(hour < 0){
            return "";
        }

        if((hour >=10)&&(hour <=21)){
           /* if(weight <= 10){*/
                if(dis <= 3) {
                    price += "7";
                }else if((dis >= 3)&&(dis <= 20)){
                    float fee = 7 + 2*(dis -3);
                    price +=  ""+fee;
                }else if(dis > 20){
                    float fee =2*(7 + 2*(dis -3));
                    price += ""+fee;
                }
          /*  }else if(weight > 10){
                if(dis <= 3) {
                    float fee = 7 + ((weight -10)*2);
                    price += ""+fee;
                }else if((dis >= 3)&&(dis <= 20)){
                    float fee = 7 +((weight -10)*2) + (2*(dis -3));
                    price +=  ""+fee;
                }else if(dis > 20){
                    float fee =2*(7 + ((weight -10)*2) + (2*(dis -3)));
                    price += ""+fee;
                }
            }*/
        }else if((hour > 21)||(hour <= 1)){


           /* if(weight <= 10) {*/
                if (dis <= 3) {
                    float fee = 7 * 2;
                    price += "" + fee;
                } else if ((dis >= 3) && (dis <= 20)) {
                    float fee = (7 + 2 * (dis - 3)) * 2;
                    price += "" + fee;
                } else if (dis > 20) {
                    float fee = 2 * (7 + 2 * (dis - 3)) * 2;
                    price += "" + fee;
                }
        /*    }else if(weight > 10){

                    if(dis <= 3) {
                        float fee = (7 + ((weight -10)*2))*2;
                        price += ""+fee;
                    }else if((dis >= 3)&&(dis <= 20)){
                        float fee = (7 +((weight -10)*2) + (2*(dis -3)))*2;
                        price +=  ""+fee;
                    }else if(dis > 20){
                        float fee =2*(7 + ((weight -10)*2) + (2*(dis -3)))*2;
                        price += ""+fee;
                    }

            }*/
        }else if((hour > 1)&&(hour < 10)){
            Toast.makeText(activity,"尊敬的客户，我们兔子还没有出门呢（快递员还没有上班，10点开始上班）",Toast.LENGTH_LONG).show();
        }

        Log.i("gotoHelpMeSendlFee",""+price);
        /*if(price.length() < 2){
            price = "";
        }*/
        String tempPrice = price;
        int index = tempPrice.indexOf(".");
        if(index > 0){
            tempPrice = tempPrice.substring(0,index);
        }
        return tempPrice;
    }
}
