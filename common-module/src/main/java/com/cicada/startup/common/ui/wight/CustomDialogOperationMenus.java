package com.cicada.startup.common.ui.wight;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.cicada.startup.common.R;

/**
 * 页面弹出选择菜单，主要处理删除、复制、保存、转发等操作
 */
public class CustomDialogOperationMenus {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private OperationInterface m_OperationInterface;
    private CustomDialog dialog;
    private int intOperationPosition = -1;

    private String customOptText;

    private CustomOptListener customOptListener;
    private final View viewOperationMenus;

    public void addCustomOption(String customOptText, final CustomOptListener customOptListener) {
        this.customOptListener = customOptListener;
        this.customOptText = customOptText;

        viewOperationMenus.findViewById(R.id.viewCustomMenuLine).setVisibility(View.VISIBLE);
        TextView customTextView = (TextView) viewOperationMenus.findViewById(R.id.textViewCustomMenu);
        customTextView.setVisibility(View.VISIBLE);
        customTextView.setText(customOptText);
        customTextView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                customOptListener.onOption(intOperationPosition);
                dialog.dismiss();
            }
        });
    }

    public CustomDialogOperationMenus(Context context, int position,
                                      boolean showOperationSave,
                                      boolean showOperationCopy,
                                      boolean showOperationDelete,
                                      boolean showOperationEdit,
                                      boolean showOperationForwarding) {
        mContext = context;
        intOperationPosition = position;
        mLayoutInflater = LayoutInflater.from(context);
        viewOperationMenus = mLayoutInflater.inflate(R.layout.dialog_operation_menus, null);

        dialog = new CustomDialog(mContext, R.style.MyDialog);
        dialog.addContentView(viewOperationMenus, new LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
        dialog.setContentView(viewOperationMenus);
        dialog.getWindow().setGravity(Gravity.CENTER);
        CustomDialog.setDialogWidth(dialog, 0.8f);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);

        TextView textViewSave = (TextView) viewOperationMenus.findViewById(R.id.textViewSave);
        textViewSave.setOnClickListener(myOnClickListener);
        if (showOperationSave) {
            textViewSave.setVisibility(View.VISIBLE);
            viewOperationMenus.findViewById(R.id.viewSaveLine).setVisibility(View.VISIBLE);
        } else {
            viewOperationMenus.findViewById(R.id.viewSaveLine).setVisibility(View.GONE);
            textViewSave.setVisibility(View.GONE);
        }
        TextView textViewCopy = (TextView) viewOperationMenus.findViewById(R.id.textViewCopy);
        textViewCopy.setOnClickListener(myOnClickListener);
        if (showOperationCopy) {
            textViewCopy.setVisibility(View.VISIBLE);
            viewOperationMenus.findViewById(R.id.viewCopyLine).setVisibility(View.VISIBLE);
        } else {
            textViewCopy.setVisibility(View.GONE);
            viewOperationMenus.findViewById(R.id.viewCopyLine).setVisibility(View.GONE);
        }
        TextView textViewDelete = (TextView) viewOperationMenus.findViewById(R.id.textViewDelete);
        textViewDelete.setOnClickListener(myOnClickListener);
        if (showOperationDelete) {
            textViewDelete.setVisibility(View.VISIBLE);
            viewOperationMenus.findViewById(R.id.viewDeleteLine).setVisibility(View.VISIBLE);
        } else {
            textViewDelete.setVisibility(View.GONE);
            viewOperationMenus.findViewById(R.id.viewDeleteLine).setVisibility(View.GONE);
        }
        TextView textViewEdit = (TextView) viewOperationMenus.findViewById(R.id.textViewEdit);
        textViewEdit.setOnClickListener(myOnClickListener);
        if (showOperationEdit) {
            textViewEdit.setVisibility(View.VISIBLE);
            viewOperationMenus.findViewById(R.id.viewEditLine).setVisibility(View.VISIBLE);
        } else {
            textViewEdit.setVisibility(View.GONE);
            viewOperationMenus.findViewById(R.id.viewEditLine).setVisibility(View.GONE);
        }
        TextView textViewForwarding = (TextView) viewOperationMenus.findViewById(R.id.textViewForwarding);
        textViewForwarding.setOnClickListener(myOnClickListener);
        if (showOperationForwarding) {
            textViewForwarding.setVisibility(View.VISIBLE);
            viewOperationMenus.findViewById(R.id.viewForwardingLine).setVisibility(View.VISIBLE);
        } else {
            textViewForwarding.setVisibility(View.GONE);
            viewOperationMenus.findViewById(R.id.viewForwardingLine).setVisibility(View.GONE);
        }
        // 清除最后一个显示的线条
        if (showOperationForwarding) {
            viewOperationMenus.findViewById(R.id.viewForwardingLine).setVisibility(View.GONE);
            return;
        }
        if (showOperationEdit) {
            viewOperationMenus.findViewById(R.id.viewEditLine).setVisibility(View.GONE);
            return;
        }
        if (showOperationDelete) {
            viewOperationMenus.findViewById(R.id.viewDeleteLine).setVisibility(View.GONE);
            return;
        }
        if (showOperationCopy) {
            viewOperationMenus.findViewById(R.id.viewCopyLine).setVisibility(View.GONE);
            return;
        }
        if (showOperationSave) {
            viewOperationMenus.findViewById(R.id.viewSaveLine).setVisibility(View.GONE);
            return;
        }
    }

    OnClickListener myOnClickListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.textViewSave) {
                dialog.dismiss();
                if (m_OperationInterface != null) {
                    m_OperationInterface.onOperationSave(intOperationPosition);
                }
            } else if (v.getId() == R.id.textViewCopy) {
                if (m_OperationInterface != null) {
                    m_OperationInterface.onOperationCopy(intOperationPosition);
                }
                dialog.dismiss();
            } else if (v.getId() == R.id.textViewDelete) {
                if (m_OperationInterface != null) {
                    m_OperationInterface.onOperationDelete(intOperationPosition);
                }
                dialog.dismiss();
            } else if (v.getId() == R.id.textViewEdit) {
                if (m_OperationInterface != null) {
                    m_OperationInterface.onOperationEdit(intOperationPosition);
                }
                dialog.dismiss();
            } else if (v.getId() == R.id.textViewForwarding) {
                if (m_OperationInterface != null) {
                    m_OperationInterface.onOperationForwarding(intOperationPosition);
                }
                dialog.dismiss();
            }
        }
    };

    public void show() {
        if (!dialog.isShowing()) {
            dialog.show();
        }
    }

    public void setOperationInterface(OperationInterface operationinterface) {
        this.m_OperationInterface = operationinterface;
    }


    /**
     * 自定义菜单操作
     */
    public interface CustomOptListener {
        void onOption(int position);
    }

    public interface OperationInterface {

        /**
         * 保存
         *
         * @param position
         */
        void onOperationSave(int position);

        /**
         * 复制
         *
         * @param position
         */
        void onOperationCopy(int position);

        /**
         * 删除
         *
         * @param position
         */
        void onOperationDelete(int position);

        /**
         * 编辑
         *
         * @param position
         */
        void onOperationEdit(int position);

        /**
         * 转发
         *
         * @param position
         */
        void onOperationForwarding(int position);
    }
}
