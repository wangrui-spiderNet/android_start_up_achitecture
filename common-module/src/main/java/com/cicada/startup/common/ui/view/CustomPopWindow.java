package com.cicada.startup.common.ui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.cicada.startup.common.R;
import com.cicada.startup.common.utils.DeviceUtils;

import static com.tendcloud.tenddata.ab.mContext;

/**
 * Created by zyh on 2016/10/28 0028.
 */
public class CustomPopWindow extends PopupWindow {
    private Context context;
    private ListView listView;
    private int[] img;
    private String[] title;
    private ImageView iv_triangle_center, iv_triangle_right;

    /**
     * 初始化控件
     */
    public CustomPopWindow(Context _context, int width, int hight, int[] img, String[] title) {
        this.context = _context;
        this.img = img;
        this.title = title;
        View view = View.inflate(context, R.layout.popwin_listiview, null);
        iv_triangle_center = (ImageView) view.findViewById(R.id.iv_triangle_center);
        iv_triangle_right = (ImageView) view.findViewById(R.id.iv_triangle_right);
        iv_triangle_center.setVisibility(View.GONE);
        iv_triangle_right.setVisibility(View.VISIBLE);
        // 设置SelectPicPopupWindow的View
        this.setContentView(view);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(width);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(hight);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        // 设置允许在外点击消失
        this.setOutsideTouchable(true);
        // 刷新状态
        this.update();
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        this.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.PopupAnimation);
        listView = (ListView) view.findViewById(R.id.pop_listview);
        listView.setAdapter(new PopAdapter());
    }

    /**
     * 初始化控件,pop宽为屏幕宽度的一半
     */
    public CustomPopWindow(Context _context, int[] img, String[] title) {
        this.context = _context;
        this.img = img;
        this.title = title;
        View view = View.inflate(context, R.layout.popwin_listiview, null);
        iv_triangle_center = (ImageView) view.findViewById(R.id.iv_triangle_center);
        iv_triangle_right = (ImageView) view.findViewById(R.id.iv_triangle_right);
        iv_triangle_center.setVisibility(View.VISIBLE);
        iv_triangle_right.setVisibility(View.GONE);
        // 设置SelectPicPopupWindow的View
        this.setContentView(view);
        int w = DeviceUtils.getScreenWidth(_context);
        // 设置SelectPicPopupWindow弹出窗体的高
        int h = ViewGroup.LayoutParams.WRAP_CONTENT;
        if (title.length > 5) {
            h = DeviceUtils.dip2Px(mContext, 240);
        }
        this.setWidth(w / 2);
        this.setHeight(h);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        // 设置允许在外点击消失
        this.setOutsideTouchable(true);
        // 刷新状态
        this.update();
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        this.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // 设置SelectPicPopupWindow弹出窗体动画效果
//        this.setAnimationStyle(R.style.PopupAnimation);
        listView = (ListView) view.findViewById(R.id.pop_listview);

        // 设置SelectPicPopupWindow弹出窗体的宽
//        listView.setLayoutParams(new LinearLayout.LayoutParams(w, h));
        listView.setAdapter(new PopAdapter());

    }

    public ListView getListView() {
        return listView;
    }

    /**
     * 显示popupWindow
     *
     * @param parent
     */
    @SuppressLint("NewApi")
    public void showPopupWindow(View parent, int xoff, int yoff) {
        if (!this.isShowing()) {
            // 以下拉方式显示popupwindow
            int xOff = parent.getWidth() - this.getWidth() - xoff;
            this.showAsDropDown(parent, xOff, yoff);
        } else {
            this.dismiss();
        }
    }

    public void showPopwindowAtCenter(View parent) {
        if (!this.isShowing()) {
            this.showAsDropDown(parent, parent.getWidth() / 2 - this.getWidth() / 2, 0);
        } else {
            this.dismiss();
        }
    }

    // 适配器
    private final class PopAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return title.length;
        }

        @Override
        public Object getItem(int position) {
            return title[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = View.inflate(context, R.layout.popwin_listiview_item, null);
                holder.groupItem = (TextView) convertView.findViewById(R.id.popwin_listitem_content);
                holder.imageView = (ImageView) convertView.findViewById(R.id.popwin_listitem_img);
                holder.item = (LinearLayout) convertView.findViewById(R.id.popwin_listitem);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            if (img == null) {
                holder.imageView.setVisibility(View.GONE);
            } else {
                holder.imageView.setImageResource(img[position]);
            }
            holder.groupItem.setText(title[position]);
            return convertView;
        }

        class ViewHolder {
            TextView groupItem;
            ImageView imageView;
            LinearLayout item;
        }
    }

}
