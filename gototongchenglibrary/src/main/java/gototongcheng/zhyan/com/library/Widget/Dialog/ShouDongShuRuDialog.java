package gototongcheng.zhyan.com.library.Widget.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import gototongcheng.zhyan.com.library.R;
import gototongcheng.zhyan.com.library.Widget.EditText.EditTextWithDel;


/**
 * Created by admin on 2017/3/11.
 */

public class ShouDongShuRuDialog  extends Dialog {


    Context context;

    public interface DialogCallBackListener{//通过该接口回调Dialog需要传递的值
        public void callBack(String msgName);//具体方法
    }
    public ShouDongShuRuDialog(Context context) {
        super(context);
        this.context = context;
    }
    public ShouDongShuRuDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
    }

    protected ShouDongShuRuDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.context = context;
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    public Builder Build = new Builder(context);
    //用Builder模式来构造Dialog
    public  class Builder {
        private Context mContext;
        private View contentView;
        private String title;
        private String message;
        private String positiveText;
        private String negativeText;
        private OnClickListener positiviOnclickListener;
        private OnClickListener negativeOnclickListener;
        private DialogCallBackListener mDialogCallBackListener;
        public Builder(Context mContext) {
            this.mContext = mContext;
        }

        public Builder setContentView(View contentView) {//设置dialog的主view
            this.contentView = contentView;
            return this;
        }

        public Builder setTitle(String title) {//设置dialog的标题
            this.title = title;
            return this;
        }

        public Builder setMessage(String msg) {//设置dialig的内容
            this.message = msg;
            return this;
        }

        public Builder setPositiveButton(String text, OnClickListener positiviOnclickListener) {//dialog的确认按钮
            this.positiveText = text;
            this.positiviOnclickListener = positiviOnclickListener;
            return this;
        }

        public Builder setNegativeButton(String text, OnClickListener negativeOnclickListener) {//dialog的取消按钮
            this.negativeText = text;
            this.negativeOnclickListener = negativeOnclickListener;
            return this;
        }

        public Builder setCallBackListener(DialogCallBackListener mDialogCallBackListener){//设置回调
            this.mDialogCallBackListener = mDialogCallBackListener;
            return this;
        }
        /**
         * 1,加载要显示的布局
         * 2，通过dialog的addContentView将布局添加到window中
         * 3，基本逻辑处理
         * 4，显示dialog的布局
         */
        public ShouDongShuRuDialog build(Context context) {
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final ShouDongShuRuDialog shouDongShuRuDialog = new ShouDongShuRuDialog(context, R.style.MyDialogStyle);//默认调用带style的构造
            shouDongShuRuDialog.setCanceledOnTouchOutside(false);//默认点击布局外不能取消dialog
            View view = mInflater.inflate(R.layout.dialog_shoudongshuru_lly, null);
            shouDongShuRuDialog.addContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            final EditTextWithDel etdInputItem = (EditTextWithDel) view.findViewById(R.id.etd_dialog_shoudongshuru);
            if (!TextUtils.isEmpty(positiveText)) {//这是确认按钮
                RelativeLayout rly_cofirm = (RelativeLayout) view.findViewById(R.id.rly_dialog_shoudongshuru_query);
                if (positiviOnclickListener != null) {
                    rly_cofirm.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            positiviOnclickListener.onClick(shouDongShuRuDialog, BUTTON_POSITIVE);
                            if(mDialogCallBackListener != null )
                                mDialogCallBackListener.callBack(etdInputItem.getText().toString());  //触发回调
                        }
                    });
                }
            } else {
                view.findViewById(R.id.rly_dialog_add_goods_query).setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(negativeText)) {//这是取消按钮逻辑处理
                RelativeLayout btn_cancle = (RelativeLayout) view.findViewById(R.id.rly_dialog_shoudongshuru_cancel);
                if (negativeOnclickListener != null) {
                    btn_cancle.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            negativeOnclickListener.onClick(shouDongShuRuDialog, BUTTON_NEGATIVE);
                        }
                    });
                }
            } else {
                view.findViewById(R.id.rly_dialog_add_goods_cancel).setVisibility(View.GONE);
            }

            shouDongShuRuDialog.setContentView(view);
            return shouDongShuRuDialog;
        }

    }


}