package com.cicada.startup.common.ui.wight;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.cicada.startup.common.R;

import java.util.ArrayList;

/**
 * TODO 类描述:自定义Dialog，实现了Dialog提示框，可以控制有无标题等
 * <p/>
 * 创建时间: 2014年8月22日 上午11:35:23 <br/>
 *
 * @author byao
 * @since v0.0.1
 */
public class CustomDialog extends Dialog {
    private static int messageGravity = Gravity.LEFT;

    public CustomDialog(Context context, int theme) {
        super(context, theme);
    }

    public CustomDialog(Context context) {
        super(context);
    }


    public static class Builder {

        private Context context;
        private String title;
        private CharSequence message;
        private String positiveButtonText;
        private int positiveButtonTextColor;
        private String negativeButtonText;
        private int negativeButtonTextColor;

        // private boolean isCancelable = true;
        private boolean isPositiveDismiss = true;// 默认点击确定按钮关闭对话框

        private DialogInterface.OnClickListener positiveButtonClickListener, negativeButtonClickListener, messageClickListener;
        private DialogInterface.OnCancelListener onCancelListener;
        private IDialogListener customListener;
        private ArrayList<Integer> IDs;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setMessage(CharSequence message, DialogInterface.OnClickListener listener) {
            this.message = message;
            this.messageClickListener = listener;
            return this;
        }

        public Builder setMessage(CharSequence message) {
            this.message = message;
            return this;
        }

        public Builder setMessage(int message) {
            this.message = context.getText(message);
            return this;
        }

        public Builder setMessageGravity(int gravity) {
            messageGravity = gravity;
            return this;
        }

        public Builder setTitle(int title) {
            this.title = (String) context.getText(title);
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setPositiveButton(int positiveButtonText, DialogInterface.OnClickListener listener) {
            this.positiveButtonText = (String) context.getText(positiveButtonText);
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setPositiveButton(String positiveButtonText, DialogInterface.OnClickListener listener) {
            this.positiveButtonText = positiveButtonText;
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setPositiveButton(String positiveButtonText, DialogInterface.OnClickListener listener, int textColor) {
            this.positiveButtonText = positiveButtonText;
            this.positiveButtonClickListener = listener;
            this.positiveButtonTextColor = textColor;
            return this;
        }

        public Builder setNegativeButton(int negativeButtonText, DialogInterface.OnClickListener listener) {
            this.negativeButtonText = (String) context.getText(negativeButtonText);
            this.negativeButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(String negativeButtonText, DialogInterface.OnClickListener listener) {
            this.negativeButtonText = negativeButtonText;
            this.negativeButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(String negativeButtonText, DialogInterface.OnClickListener listener, int textColor) {
            this.negativeButtonText = negativeButtonText;
            this.negativeButtonClickListener = listener;
            this.negativeButtonTextColor = textColor;
            return this;
        }

        public Builder setOnCancelListener(DialogInterface.OnCancelListener cancelListener) {
            this.onCancelListener = cancelListener;
            return this;
        }

        // public Builder setCancelable(boolean flag) {
        // this.isCancelable = flag;
        // return this;
        // }

        // 设置点击确定按钮关闭对话框
        public Builder setPositiveDismiss(boolean flag) {
            this.isPositiveDismiss = flag;
            return this;
        }

        public void initView(IDialogListener listener, ArrayList<Integer> ids) {
            this.customListener = listener;
            this.IDs = ids;
        }

        public CustomDialog create() {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // instantiate the dialog with the custom Theme
            final CustomDialog dialog = new CustomDialog(context, R.style.MyDialog);
            View viewDialogView = inflater.inflate(R.layout.dialog, null);
            View line = viewDialogView.findViewById(R.id.line);
            dialog.addContentView(viewDialogView, new LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
            // set the dialog title

            if (TextUtils.isEmpty(title)) {
                ((TextView) viewDialogView.findViewById(R.id.textViewTitle)).setVisibility(View.GONE);
            } else {
                ((TextView) viewDialogView.findViewById(R.id.textViewTitle)).setVisibility(View.VISIBLE);
                ((TextView) viewDialogView.findViewById(R.id.textViewTitle)).setText(title);
            }
            if (positiveButtonText != null) {
                Button positiveButton = (Button) viewDialogView.findViewById(R.id.dialog_ok);
                positiveButton.setText(positiveButtonText);
                if (positiveButtonTextColor != 0) {
                    positiveButton.setTextColor(positiveButtonTextColor);
                }
                if (positiveButtonClickListener != null) {
                    positiveButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            positiveButtonClickListener.onClick(dialog, DialogInterface.BUTTON_POSITIVE);
                            if (isPositiveDismiss) {
                                dialog.dismiss();
                            }
                        }
                    });
                }
                if (negativeButtonText == null) {
                    line.setVisibility(View.GONE);
                    positiveButton.setBackgroundResource(R.drawable.selector_button_dialog_single);
                }
            } else {
                // if no confirm button just set the visibility to GONE
                viewDialogView.findViewById(R.id.dialog_ok).setVisibility(View.GONE);
            }
            // set the cancel button
            if (negativeButtonText != null) {
                Button negativeButton = (Button) viewDialogView.findViewById(R.id.dialog_cancel);
                negativeButton.setText(negativeButtonText);
                if (negativeButtonTextColor != 0) {
                    negativeButton.setTextColor(negativeButtonTextColor);
                }
                if (negativeButtonClickListener != null) {
                    negativeButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            negativeButtonClickListener.onClick(dialog, DialogInterface.BUTTON_NEGATIVE);
                            dialog.dismiss();
                        }
                    });
                }
                if (positiveButtonText == null) {
                    line.setVisibility(View.GONE);
                    negativeButton.setBackgroundResource(R.drawable.selector_button_dialog_single);
                }
            } else {
                // if no confirm button just set the visibility to GONE
                viewDialogView.findViewById(R.id.dialog_cancel).setVisibility(View.GONE);
            }
            // set the content message
            if (message != null) {

                ((TextView) viewDialogView.findViewById(R.id.text_dialog_tipText)).setGravity(messageGravity);
                ((TextView) viewDialogView.findViewById(R.id.text_dialog_tipText)).setText(message);
                ((TextView) viewDialogView.findViewById(R.id.text_dialog_tipText)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        messageClickListener.onClick(dialog, DialogInterface.BUTTON_NEUTRAL);
                    }
                });
            }
            dialog.setContentView(viewDialogView);
            // dialog.setCancelable(isCancelable);
            // dialog.setCanceledOnTouchOutside(isCancelable);
            // 设置对话框的宽度为屏幕的8/10.
            setDialogWidth(dialog, 0.8F);
            return dialog;
        }


        /**
         * 添加自定义view
         *
         * @param context
         * @param view
         * @return
         */
        public Dialog create(Context context, final View view) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final CustomDialog dialog = new CustomDialog(context, R.style.MyDialog);
            View viewDialogView = inflater.inflate(R.layout.dialog_base_layout, null);
            LinearLayout content = (LinearLayout) viewDialogView.findViewById(R.id.content);
            content.addView(view);

            dialog.addContentView(viewDialogView, new LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT, android.view.ViewGroup.LayoutParams.FILL_PARENT));
            recursionFindView(((ViewGroup) view), dialog);

            Window win = dialog.getWindow();
            win.getDecorView().setPadding(0, 0, 0, 0);
            win.setGravity(Gravity.CENTER);
            WindowManager.LayoutParams lp = win.getAttributes();
            lp.width = android.view.ViewGroup.LayoutParams.FILL_PARENT;
            lp.height = android.view.ViewGroup.LayoutParams.FILL_PARENT;
            win.setAttributes(lp);

            return dialog;
        }

        /**
         * 递归查找操作view
         *
         * @param view
         * @param dialog
         */
        public void recursionFindView(ViewGroup view, final Dialog dialog) {
            for (int i = 0; i < view.getChildCount(); i++) {
                for (int j = 0; j < IDs.size(); j++) {
                    if (IDs.get(j) == view.getChildAt(i).getId()) {
                        view.getChildAt(i).setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View arg0) {
                                dialog.dismiss();
                                customListener.dialogListener(arg0);
                            }
                        });
                    }
                }
                if (view.getChildAt(i) instanceof ViewGroup) {
                    recursionFindView((ViewGroup) view.getChildAt(i), dialog);
                }
            }
        }
    }


    @Override
    public void show() {
        super.show();
    }

    /**
     * 设置对话框的宽度为(屏幕宽度*factor).
     *
     * @param dialog
     * @param factor 对话框宽度站屏幕宽度的比例[0, 1].
     */
    public static void setDialogWidth(Dialog dialog, float factor) {
        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics dm = new DisplayMetrics();
        dialogWindow.getWindowManager().getDefaultDisplay().getMetrics(dm);
        lp.width = (int) (dm.widthPixels * factor);
        dialogWindow.setAttributes(lp);
    }

    public interface IDialogListener {
        void dialogListener(Object object);
    }

}
