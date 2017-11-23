package com.cicada.startup.common.ui.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cicada.startup.common.R;

import java.util.ArrayList;

public class DialogView extends Dialog {

	public DialogView(Context context, boolean cancelable,
			OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
		// TODO Auto-generated constructor stub
	}

	public DialogView(Context context, int layout) {
		super(context, R.style.AlertDlgStyle);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(layout);
	}

	public DialogView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public static class AlertDlgBuilder implements View.OnClickListener {

		private Context context;
		private String title;
		private String content;
		private String left;
		private String right;
		private IDialogClick listener;
		private ArrayList<Integer> IDs;

		private TextView titleView;
		private TextView contentView;
		private TextView leftButton;
		private TextView rightButton;
		
		private RelativeLayout standlerDialog;
		private LinearLayout customDialog;
		

		private DialogView dialog;

		public AlertDlgBuilder(Context context) {
			this.context = context;
		}

		/**
		 * 
		 * @param title  标题
		 * @param content  内容
		 * @param left  左文案
		 * @param right  右文案
		 * @param listener  控件回调监听
		 * @param ids 自定义控件id集合
		 */
		public void initView(String title, String content, String left,
				String right, IDialogClick listener,ArrayList<Integer> ids) {
			this.title = title;
			this.content = content;
			this.left = left;
			this.right = right;
			this.listener = listener;
			this.IDs = ids;
		}

		public DialogView create() {
			return dialog;
		}

		public DialogView create(View view){
			dialog = new DialogView(context, R.layout.dialog_layout);
			customDialog = (LinearLayout) dialog.findViewById(R.id.custom_dialog);
			customDialog.setVisibility(View.VISIBLE);
			customDialog.addView(view,new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));
		    recursionFindView(((ViewGroup)view),dialog);
			Window win = dialog.getWindow();
		    win.getDecorView().setPadding(0, 0, 0, 0);
		    win.setGravity(Gravity.CENTER);
		    WindowManager.LayoutParams lp = win.getAttributes();
		    lp.width = WindowManager.LayoutParams.MATCH_PARENT;
		    lp.height = WindowManager.LayoutParams.MATCH_PARENT;
		    win.setAttributes(lp);
			return dialog;
		}
		
		/**
		 * 递归查找操作view
		 * @param view
		 * @param dialog
		 */
		public  void recursionFindView(ViewGroup view,final Dialog dialog){
			for(int i=0;i<view.getChildCount();i++){
				for(int j=0;j<IDs.size();j++){
					if(IDs.get(j) == view.getChildAt(i).getId()){
						view.getChildAt(i).setOnClickListener(new View.OnClickListener() {
							
							@Override
							public void onClick(View arg0) {
								dialog.dismiss();
								listener.onDialogClick(arg0);
							}
						});
					}
				}
				if(view.getChildAt(i) instanceof ViewGroup){
					recursionFindView((ViewGroup)view.getChildAt(i), dialog);
				}
			}
		}
		
		@Override
		public void onClick(View v) {
			dialog.dismiss();
			listener.onDialogClick(v);
		}

		public interface IDialogClick {
			/**
			 * 对话框点击接口回调
			 * 
			 * @param
			 */
			void onDialogClick(Object object);
		}
	}
}