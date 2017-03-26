package gototongcheng.zhyan.com.library.ThirdPay.WX;

/**
 * 创建人 : skyCracks<br>
 * 创建时间 : 2016-7-18上午10:12:59<br>
 * 版本 :	[v1.0]<br>
 * 类描述 : 微信支付所需参数及配置<br>
 *https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=9_1
 *     https://github.com/skyCracks/Payment
 */
public class WeChatConstans {
	
	/** 应用从官方网站申请到的合法appId */
    public static final String APP_ID = "wxf180adf1575a69e0";
	/** 微信开放平台和商户约定的密钥 */
    public static final String APP_SECRET = "1b1451be63fc1a994f304b61b08b6955";
    /** 商家向财付通申请的商家id */
    public static final String PARTNER_ID = "1445053302";
    /** 微信公众平台商户模块和商户约定的密钥 */
    public static final String PARTNER_KEY = "AtYI3984uwOn9635ypOE7291oeBn8428";
    /** 微信统一下单接口 */
	public static final String WECHAT_UNIFIED_ORDER = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    /** 回调接口 */
    public static final String NOTIFY_URL = "http://wxpay.wxutil.com/pub_v2/pay/notify.v2.php";

}
