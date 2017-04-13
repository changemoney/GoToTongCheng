package gototongcheng.zhyan.com.library.ThirdPay.WX;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.tencent.mm.sdk.constants.Build;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.conn.util.InetAddressUtils;
import cz.msebera.android.httpclient.message.BasicNameValuePair;
import gototongcheng.zhyan.com.library.Common.XCCacheSavename;
import gototongcheng.zhyan.com.library.DBCache.XCCacheManager.xccache.XCCacheManager;

/**
 * 创建人 : skyCracks<br>
 * 创建时间 : 2016-7-18上午11:02:34<br>
 * 版本 : [v1.0]<br>
 * 类描述 : 微信支付实现服务端操作及后续调起支付<br>
 *
 *     https://github.com/skyCracks/Payment
 */
@SuppressLint("DefaultLocale")
public class WeChatPayService {

	private  final String TAG = WeChatPayService.class.getSimpleName();

	private IWXAPI api;
	private Context context;
	/** 订单类型 */
	private int type;
	/** 内部订单 */
	private String out_trade_no;
	/** 商品描述 */
	private String body;
	/** 商品金额费用, 单位是分 */
	private String total_fee;

	/**
	 * @param context
	 *            上下文环境
	 * @param out_trade_no
	 *            内部订单
	 * @param type 
	 *            订单类型(不同订单类型区分) 只有一种类型的订单时可去掉
	 * @param body
	 *            商品描述
	 * @param total_fee
	 *            商品金额费用, 单位是分
	 */
	public WeChatPayService(Context context, int type, String out_trade_no,
			String body, String total_fee) {
		this.context = context;
		this.type = type;
		this.out_trade_no = out_trade_no;
		this.body = body;
		this.total_fee = total_fee;
		/*this.api = WXAPIFactory.createWXAPI(context, WeChatConstans.APP_ID,
				false);*/
		this.api = WXAPIFactory.createWXAPI(context,null);

	}

	public void pay() {
		// 检测是否安装了微信
		boolean isWeChat = api.getWXAppSupportAPI() >= Build.PAY_SUPPORTED_SDK_INT;
		if (isWeChat) {
		/*	Toast.makeText(context,"have wx", Toast.LENGTH_SHORT).show();*/
			new GetPrepayIdTask().execute();
		}
	}
	
	/**
	 * 异步网络请求获取预付Id
	 */
	private class GetPrepayIdTask extends AsyncTask<String, Void, String> {
		@Override
		protected void onPreExecute() {
		}

		@Override
		protected void onPostExecute(String result) {
			// 第三步, 发送支付请求

			sendPayReq(result);
		}

		@Override
		protected String doInBackground(String... params) {
			// 网络请求获取预付Id
			String url = String.format(WeChatConstans.WECHAT_UNIFIED_ORDER);
			String entity = genEntity();
			byte[] buf = WeChatHttpClient.httpPost(url, entity);
			/*Toast.makeText(context,"this is prepay_id"+buf,Toast.LENGTH_SHORT).show();在async 异步调用里用toast 会引起错误*/
			if (buf != null && buf.length > 0) {
				try {
					Map map = XmlUtil.doXMLParse(new String(buf));
					return (String) map.get("prepay_id");
				} catch (Exception e) {

					e.printStackTrace();
				}
			}
			return null;
		}
	}

	/**
	 * 发送支付请求
	 *http://blog.csdn.net/supercopa/article/details/52249084
	 * https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=4_3
	 * @param prepayId 预付Id
	 */
	private void sendPayReq(String prepayId) {
		PayReq req = new PayReq();
		req.appId = WeChatConstans.APP_ID;
		req.partnerId = WeChatConstans.PARTNER_ID;
		req.prepayId = prepayId;
		req.packageValue = "Sign=WXPay";
		req.nonceStr = genNonceStr();
		req.timeStamp = String.valueOf(genTimeStamp());

		/*Log.i("this is pay prepayId","appId:"+req.appId+" partnerId:"+req.partnerId+" prepayId:"+req.prepayId+" nonceStr:"+req.nonceStr+" timeStamp:"+req.timeStamp+" packageValue:"+req.packageValue);*/
		List<NameValuePair> signParams = new LinkedList<NameValuePair>();
		signParams.add(new BasicNameValuePair("appid", req.appId));
		signParams.add(new BasicNameValuePair("noncestr", req.nonceStr));
		signParams.add(new BasicNameValuePair("package", req.packageValue));
		signParams.add(new BasicNameValuePair("partnerid", req.partnerId));
		signParams.add(new BasicNameValuePair("prepayid", req.prepayId));
		signParams.add(new BasicNameValuePair("timestamp", req.timeStamp));
		String stringA="appid="+req.appId+"&noncestr="+req.nonceStr+"&package="+req.packageValue+"&partnerid="+req.partnerId+"&prepayid="+req.prepayId+"&timestamp="+req.timeStamp;
		String stringSignTemp=stringA + "&key="+WeChatConstans.PARTNER_KEY;
		Log.i("sign",stringSignTemp);
	/*	String stringA="appid="+req.appId+"&noncestr="+req.nonceStr+"&package="+req.packageValue+"&partnerid="+req.partnerId+"&prepayid="+req.prepayId+"&timestamp="+req.timeStamp;
		String stringSignTemp=stringA + "&key="+WeChatConstans.PARTNER_KEY;
		Log.i("sign",stringSignTemp);
		String characterEncoding = "UTF-8";
		String sign= "";
		sign = MD5Util.MD5Encode(stringSignTemp,characterEncoding).toUpperCase();*/
		req.sign = genPackageSign(signParams);
		Log.i("sign",req.sign );
		// 传递的额外信息,字符串信息,自定义格式
		// req.extData = type +"#" + out_trade_no + "#" +total_fee;
		// 微信支付结果界面对调起支付Activity的处理
		// APPCache.payActivity.put("调起支付的Activity",(调起支付的Activity)context);
		// 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
		/*api.registerApp(WeChatConstans.APP_ID);*/
		api.registerApp(WeChatConstans.APP_ID);
		api.sendReq(req);//不断出现-1的解决方法是卸载微信重装 或者清空微信
		// 支付完成后微信会回调 wxapi包下 WXPayEntryActivity 的public void onResp(BaseResp
		// resp)方法，所以后续操作，放在这个回调函数中操作就可以了
	}

	/**
	 * 微信支付，构建统一下单请求参数
	 */
	public String genEntity() {
		String nonceStr = genNonceStr();
		XCCacheManager xcCacheManager = XCCacheManager.getInstance(context);
		XCCacheSavename xcCacheSavename = new XCCacheSavename();
		xcCacheManager.writeCache(xcCacheSavename.weixinPayNonceStr,nonceStr);
		List<NameValuePair> packageParams = new ArrayList<NameValuePair>();
		// APPID
		packageParams
				.add(new BasicNameValuePair("appid", WeChatConstans.APP_ID));
		// 商品描述
		packageParams.add(new BasicNameValuePair("body", body));
		// 商户ID
		packageParams.add(new BasicNameValuePair("mch_id",
				WeChatConstans.PARTNER_ID));
		// 随机字符串
		packageParams.add(new BasicNameValuePair("nonce_str", nonceStr));
		// 回调接口地址
		packageParams.add(new BasicNameValuePair("notify_url",
				WeChatConstans.NOTIFY_URL));
		// 我们的订单号
		packageParams.add(new BasicNameValuePair("out_trade_no", out_trade_no));
		// 提交用户端ip
		packageParams.add(new BasicNameValuePair("spbill_create_ip",
				getIPAddress()));
		BigDecimal totalFeeBig = new BigDecimal(total_fee);
		int totalFee = totalFeeBig.multiply(new BigDecimal(100)).intValue();
		// 总金额，单位为 分 !
		packageParams.add(new BasicNameValuePair("total_fee", String
				.valueOf(totalFee)));
		// 支付类型， APP
		packageParams.add(new BasicNameValuePair("trade_type", "APP"));
		// 生成签名
		String sign = genPackageSign(packageParams);
		packageParams.add(new BasicNameValuePair("sign", sign));
		String xmlstring = XmlUtil.toXml(packageParams);
		System.out.println("xmlstring"+xmlstring);
		try {
			return new String(xmlstring.toString().getBytes(), "ISO-8859-1");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		/*Toast.makeText(context,"this is null",Toast.LENGTH_SHORT).show();*/
		return null;
	}

	/**
	 * 生成签名
	 */
	public static String genPackageSign(List<NameValuePair> params) {
		try {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < params.size(); i++) {
				sb.append(params.get(i).getName());
				sb.append('=');
				sb.append(params.get(i).getValue());
				sb.append('&');
			}
			sb.append("key=");
			sb.append(WeChatConstans.PARTNER_KEY);

			String packageSign = MD5Util.getMessageDigest(
					sb.toString().getBytes("utf-8")).toUpperCase();
			return packageSign;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 微信支付调用统一下单接口，随机字符串
	 */
	public static String genNonceStr() {
		try {
			Random random = new Random();
			String rStr = MD5Util.getMessageDigest(String.valueOf(
					random.nextInt(10000)).getBytes("utf-8"));
			return rStr;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}


	public static String getV4IP(){
		String ip = "";
		String chinaz = "http://ip.chinaz.com";

		StringBuilder inputLine = new StringBuilder();
		String read = "";
		URL url = null;
		HttpURLConnection urlConnection = null;
		BufferedReader in = null;
		try {
			url = new URL(chinaz);
			urlConnection = (HttpURLConnection) url.openConnection();
			in = new BufferedReader( new InputStreamReader(urlConnection.getInputStream(),"UTF-8"));
			while((read=in.readLine())!=null){
				inputLine.append(read+"\r\n");
			}
			//System.out.println(inputLine.toString());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(in!=null){
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}


		Pattern p = Pattern.compile("\\<dd class\\=\"fz24\">(.*?)\\<\\/dd>");
		Matcher m = p.matcher(inputLine.toString());
		if(m.find()){
			String ipstr = m.group(1);
			ip = ipstr;
			//System.out.println(ipstr);
		}
		return ip;
	}

	/**
	 * 得到本机IP地址，WIFI下获取的是局域网IP，数据流量下获取的是公网IP
	 * 
	 * @return
	 */
	public static String getIPAddress() {
		try {
			Enumeration<NetworkInterface> en = NetworkInterface
					.getNetworkInterfaces();
			// 遍历所用的网络接口
			while (en.hasMoreElements()) {
				NetworkInterface nif = en.nextElement();// 得到每一个网络接口绑定的所有ip
				Enumeration<InetAddress> inet = nif.getInetAddresses();
				// 遍历每一个接口绑定的所有ip
				while (inet.hasMoreElements()) {
					InetAddress ip = inet.nextElement();
					if (!ip.isLoopbackAddress()
							&& InetAddressUtils.isIPv4Address(ip
									.getHostAddress())) {
						return ip.getHostAddress();
					}
				}
			}
		} catch (SocketException e) {
			Log.e("getip", "获取本地ip地址失败", e);
		}
		return null;
	}

	private long genTimeStamp() {
		return System.currentTimeMillis() / 1000;
	}







}
