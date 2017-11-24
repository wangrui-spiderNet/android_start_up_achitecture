package com.cicada.startup.common.ui.wight;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.cicada.startup.common.AppContext;
import com.cicada.startup.common.R;
import com.cicada.startup.common.manager.AppManager;

/**
 * 自定义Dialog，根据布局文件灵活控制样式。
 * <p>
 * 创建时间: 2014年8月22日 上午11:41:34 <br/>
 *
 * @author byao
 * @since v0.0.1
 */
public class LoadingDialog extends AlertDialog {


    public LoadingDialog(Context context, int theme) {
        super(context, theme);
    }

    private LoadingDialog(Context context) {
        super(context);
    }

    private boolean mIsCanceled;

    private void setCanceled(boolean canceled) {
        this.mIsCanceled = canceled;
    }

    /**
     * @return true, 如果请求已经被取消了; false,请求没有取消.
     */
    public static void dismiss(LoadingDialog customLoadDataDialog) {
        if (null != customLoadDataDialog) {
            if (customLoadDataDialog.isShowing()) {
                customLoadDataDialog.dismiss();
            }
        }
    }

    /**
     * @return true, 如果请求已经被取消了; false,请求没有取消.
     */
    public boolean isCanceled() {
        return mIsCanceled;
    }

    public static interface OnCancelListener {
        void onCancel();
    }

    public static class Builder {
        private String title;
        private boolean canCancel = true;
        private OnCancelListener cancelListener;
        private boolean canceledOnTouchOutside = false;
        private int background = R.color.transparent;

        public Builder() {
        }

        public Builder setContentBackground(int background) {
            this.background = background;
            return this;
        }

        public Builder setMessage(String msg) {
            this.title = msg;
            /** 屏蔽加载提示语 */
            this.title = "";
            return this;
        }

        public Builder setCanCancel(boolean canCancel) {
            this.canCancel = canCancel;
            return this;
        }

        public Builder setCanceledOnTouchOutside(boolean canceledOnTouchOutside) {
            this.canceledOnTouchOutside = canceledOnTouchOutside;
            return this;
        }

        public Builder setOnCancelListener(OnCancelListener listener) {
            this.cancelListener = listener;
            return this;
        }

        public LoadingDialog create(Context context) {
            final LoadingDialog progressDialog = new LoadingDialog(context, R.style.MyLoadingDialog);
            // 触摸对话框外面关闭对话框
            progressDialog.setCanceledOnTouchOutside(this.canceledOnTouchOutside);
            progressDialog.setCancelable(this.canCancel);
            final boolean canCanceled = canCancel;

            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

                @Override
                public void onCancel(DialogInterface dialog) {
                    if (canCanceled) {
                        if (cancelListener != null) {
                            cancelListener.onCancel();
                        }
                        progressDialog.setCanceled(true);
                        progressDialog.dismiss();
                    }
                }
            });

            if (null == AppManager.getInstance().currentActivity()
                    || !AppManager.getInstance().currentActivity().getClass().equals(context.getClass())) {
                progressDialog.dismiss();
                return progressDialog;
            }

            Activity activity = (Activity) context;
            if (activity.isFinishing()) {
                progressDialog.dismiss();
                return progressDialog;
            }

            progressDialog.show();
            // 注意此处要放在show之后 否则会报异常
            progressDialog.setContentView(R.layout.dialog_with_progress);
            WindowManager windowManager = (WindowManager) AppContext.getContext().getSystemService(Context.WINDOW_SERVICE);
            Display display = windowManager.getDefaultDisplay();
            WindowManager.LayoutParams lp = progressDialog.getWindow().getAttributes();
            lp.width = (display.getWidth()); // 设置宽度
            progressDialog.getWindow().setAttributes(lp);
            if (!TextUtils.isEmpty(this.title)) {
                progressDialog.findViewById(R.id.text_dialog_progress_msg).setVisibility(View.VISIBLE);
                TextView load = (TextView) progressDialog.findViewById(R.id.text_dialog_progress_msg);
                load.setText(this.title);
            }
            progressDialog.findViewById(R.id.linearLayoutContent).setBackgroundResource(background);
            return progressDialog;
        }
    }

}
