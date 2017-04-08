package gototongcheng.zhyan.com.library.Utils;

/**
 * Created by admin on 2017/2/21.
 */

public class ContentUtils {


    /*服务器地址*/
    public final String ServiceUrl = "http://www.gotogoto.com";
    /*public final String ServiceUrl = "http://192.168.0.111:8074/COMOTO";*/

    /*用户登录
    * parame:userName(String) userPassword(String)
    * back:result userName usid
    *{"result":"登录成功","userName":null,"usid":"asfasdfasdfasd"}
    * */
    public final String UserLogin = "/appusers/applogin.do";

    /*用户注册
    * param:userName(String),userPassword(String)
    * back:result userName
    * {"result":"注册成功","userName":null}
    * */
    public final String UserReg = "/appusers/appreg.do";
    /*用户退出
    * param:none
    *
    * */
    public final String UserExit = "/appusers/appexit.do";

    /*跑腿员登录接口
    * param:angelname(String),password(String)
    * back:result,angelname,anid
    *{"result":"注册成功","angelname":null,"anid":"sdf"}
    * */
    public final String Angel = "/appangel/applogin.do";

    /*跑腿员注册接口
    * param:angelname(String),password(String)
    * back:result,angelname
    * {"result":"注册成功","angelname":null,"}
    * */
    public final String AngelReg = "/appangel/appreg.do";

}
