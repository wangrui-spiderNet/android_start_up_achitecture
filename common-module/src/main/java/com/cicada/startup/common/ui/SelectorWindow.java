package com.cicada.startup.common.ui;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.cicada.startup.common.R;
import com.cicada.startup.common.utils.DeviceUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 选择下拉框
 * <p/>
 * 创建时间: 15/9/17 下午3:20 <br/>
 *
 * @author zhaohaiyang
 * @since v0.0.1
 */
public class SelectorWindow extends PopupWindow implements AdapterView.OnItemClickListener {

    private Context mContext;
    private ListView listView;
    private List<String> classList = new ArrayList<String>();

    private OnClassSelectedListener onClassSelectedListener;
    private View conentView;

    public void setOnClassSelectedListener(OnClassSelectedListener onClassSelectedListener) {
        this.onClassSelectedListener = onClassSelectedListener;
    }


    public SelectorWindow(Activity mContext, List<String> classList) {
        this.mContext = mContext;
        this.classList = classList;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        conentView = inflater.inflate(R.layout.popup_class_select, null);
        conentView.setBackgroundResource(R.drawable.background_pop_arrow_t_r);
        listView = (ListView) conentView.findViewById(R.id.lv_class_select);
        listView.setAdapter(new ArrayAdapter(mContext, R.layout.list_item_simple_text, R.id.tv_list_view_item, classList));
        // 设置SelectPicPopupWindow的View
        this.setContentView(conentView);
        int w = mContext.getWindowManager().getDefaultDisplay().getWidth();
        // 设置SelectPicPopupWindow弹出窗体的高
        int h = ViewGroup.LayoutParams.WRAP_CONTENT;
        if (classList.size() > 8) {
            h = DeviceUtils.dip2Px(mContext, 300);
        }
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(w/2);
        this.setHeight(h);
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        // 刷新状态
        this.update();
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0000000000);
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        this.setBackgroundDrawable(dw);
        listView.setOnItemClickListener(this);
    }

    public SelectorWindow(Activity mContext, List<String> classList, String selectedValue) {
        this.mContext = mContext;
        this.classList = classList;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        conentView = inflater.inflate(R.layout.popup_class_select, null);
        listView = (ListView) conentView.findViewById(R.id.lv_class_select);
        SelectorAdapter selectorAdapter = new SelectorAdapter(classList, selectedValue);
        listView.setBackgroundResource(R.drawable.square_gray_frame_white_bg);
        listView.setAdapter(selectorAdapter);

        int w = mContext.getWindowManager().getDefaultDisplay().getWidth();
        // 设置SelectPicPopupWindow弹出窗体的高
        int h = ViewGroup.LayoutParams.WRAP_CONTENT;
        if (classList.size() > 8) {
            h = DeviceUtils.dip2Px(mContext, 300);
        }
        // 设置SelectPicPopupWindow弹出窗体的宽
        listView.setLayoutParams(new LinearLayout.LayoutParams(w, h));

        // 设置SelectPicPopupWindow的View
        this.setContentView(conentView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(w);
        this.setHeight(DeviceUtils.getScreenHeight(mContext));

        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        // 刷新状态
        this.update();
        // 实例化一个ColorDrawable颜色为半透明
        final ColorDrawable dw = new ColorDrawable(mContext.getResources().getColor(R.color.transparent_gray));
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        this.setBackgroundDrawable(dw);
        conentView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                dismiss();
                return false;
            }
        });

        listView.setOnItemClickListener(this);
    }

    public void setContentBackGroundDrawable(int drawableRes) {
        conentView.setBackgroundResource(drawableRes);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        this.dismiss();
        if (null != onClassSelectedListener) {
            onClassSelectedListener.onClassSelect(position);
        }
    }

    public void show(View parent) {
        if (!this.isShowing()) {
            // 以下拉方式显示popupwindow
            showAsDropDown(parent, parent.getWidth() / 2 - this.getWidth() / 2, 0);
        } else {
            this.dismiss();
        }
    }

    public interface OnClassSelectedListener {
        void onClassSelect(int position);
    }

    private class SelectorAdapter extends BaseAdapter {
        private List<String> valueList;
        private String selectedValue;

        public SelectorAdapter(List<String> valueList, String selectedValue) {
            this.valueList = valueList;
            this.selectedValue = selectedValue;
        }

        @Override
        public int getCount() {
            return valueList.size();
        }

        @Override
        public Object getItem(int position) {
            return valueList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = View.inflate(mContext,
                        R.layout.list_item_simple_text, null);
                holder.textViewName = (TextView) convertView
                        .findViewById(R.id.tv_list_view_item);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            String posValue = valueList.get(position);
            holder.textViewName.setText(posValue);
            if (!TextUtils.isEmpty(selectedValue) && selectedValue.equalsIgnoreCase(posValue)) {
                holder.textViewName.setTextColor(mContext.getResources().getColor(R.color.text_color_blue));
            } else {
                holder.textViewName.setTextColor(mContext.getResources().getColor(R.color.text_color_common_black));
            }
            return convertView;
        }

        private final class ViewHolder {
            TextView textViewName;
        }
    }
}
