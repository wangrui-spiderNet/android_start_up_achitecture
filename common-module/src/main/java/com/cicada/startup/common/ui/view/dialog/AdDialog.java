package com.cicada.startup.common.ui.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cicada.startup.common.R;
import com.cicada.startup.common.glide.GlideImageDisplayer;
import com.cicada.startup.common.glide.transform.GlideRoundTransform;


/**
 * 弹屏广告
 * <p>
 * 创建时间: 16/7/25 下午3:04 <br/>
 *
 * @author zhaohaiyang
 * @since v0.0.1
 */
public class AdDialog extends Dialog implements View.OnClickListener {


    private Context context;
    private ImageView photoView;
    private TextView updateInfoView;
    private static View selfView;
    private View dialogView;
    private String imageUrl;
    private int imageRes;

    private ImageView updateIconImageView;
    private View updateView;
    private Button goBtn;
    private ImageView closeBtn;
    private String updateIntro;
    private LinearLayout llGo;

    private DialogType type = DialogType.COMMON_AD;

    private OnActionListener onActionListener;

    public AdDialog(Context context) {
        super(context, R.style.DialogTheme);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.context = context;
        setCancelable(true);
        setCanceledOnTouchOutside(false);

    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        showImageUrl();
    }

    public void setImageRes(int imageRes) {
        this.imageRes = imageRes;
        showImageRes();
    }

    private void showImageUrl() {
        if (null != photoView) {
            GlideImageDisplayer.display(context, photoView, imageUrl, new GlideRoundTransform(context, 8));
        }
    }

    private void showImageRes() {
        if (null != photoView) {
            GlideImageDisplayer.display(context, photoView, imageRes, new GlideRoundTransform(context, 8));
        }
    }

    public void setOnActionListener(OnActionListener onActionListener) {
        this.onActionListener = onActionListener;
    }

    public void setType(DialogType type) {
        this.type = type;
    }

    public void setUpdateIntro(String updateIntro) {
        this.updateIntro = updateIntro;
    }


    public static class Builder {
        private String imageUrl;
        private int imageRes;
        private OnActionListener onActionListener;
        private Context context;

        private String updateIntro;
        private DialogType type = DialogType.COMMON_AD;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder withImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public Builder withImageRes(int imageRes) {
            this.imageRes = imageRes;
            return this;
        }

        public Builder withOnActionListener(OnActionListener onActionListener) {
            this.onActionListener = onActionListener;
            return this;
        }


        public Builder withType(DialogType type) {
            this.type = type;
            return this;
        }

        public Builder withUpdateIntro(String updateIntro) {
            this.updateIntro = updateIntro;
            return this;
        }


        public AdDialog build() {
            AdDialog adDialog = new AdDialog(this.context);
            try {
                adDialog.setType(this.type);
                adDialog.setUpdateIntro(this.updateIntro);
                adDialog.show();
                adDialog.setOnActionListener(this.onActionListener);
                adDialog.setImageRes(this.imageRes);
                adDialog.setImageUrl(this.imageUrl);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return adDialog;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_ad);
        initView();
    }

    private void initView() {
        photoView = (ImageView) findViewById(R.id.iv_photo);
        updateView = findViewById(R.id.ll_update_view);
        updateInfoView = (TextView) findViewById(R.id.tv_update_info);
        updateInfoView.setMovementMethod(ScrollingMovementMethod.getInstance());
        updateIconImageView = (ImageView) findViewById(R.id.iv_update_icon);
        llGo = (LinearLayout) findViewById(R.id.ll_go);
        goBtn = (Button) findViewById(R.id.btn_go);
        goBtn.setOnClickListener(this);
        closeBtn = (ImageView) findViewById(R.id.iv_close);
        closeBtn.setOnClickListener(this);
        photoView.setOnClickListener(this);

        switch (type) {
            case COMMON_AD:
                updateView.setVisibility(View.GONE);
                updateIconImageView.setVisibility(View.GONE);
                closeBtn.setVisibility(View.VISIBLE);
                photoView.setVisibility(View.VISIBLE);
                llGo.setVisibility(View.GONE);
                break;
            case UPDATE_NOR:
                updateView.setVisibility(View.VISIBLE);
                updateIconImageView.setVisibility(View.VISIBLE);
                closeBtn.setVisibility(View.VISIBLE);
                photoView.setVisibility(View.GONE);
                llGo.setVisibility(View.VISIBLE);
                if (!TextUtils.isEmpty(updateIntro)) {
                    updateInfoView.setText(updateIntro);
                }
                break;
            case UPDATE_FORCE:
                updateView.setVisibility(View.VISIBLE);
                updateIconImageView.setVisibility(View.VISIBLE);
                closeBtn.setVisibility(View.GONE);
                photoView.setVisibility(View.GONE);
                setCancelable(false);
                llGo.setVisibility(View.VISIBLE);
                if (!TextUtils.isEmpty(updateIntro)) {
                    updateInfoView.setText(updateIntro);
                }
                break;
            default:
                break;
        }
    }


    @Override
    public void onClick(View v) {
        dismiss();
        if (v.getId() == R.id.iv_close) {
            if (null != onActionListener) {
                onActionListener.close();
            }
        } else if (v.getId() == R.id.btn_go) {
            if (DialogType.COMMON_AD == type) {
            }
            if (null != onActionListener) {
                onActionListener.action();
            }
        } else if (v.getId() == R.id.iv_photo) {
            if (null != onActionListener) {
                onActionListener.action();
            }
        }
    }


    public interface OnActionListener {
        void action();

        void close();
    }

    public enum DialogType {
        COMMON_AD,//普通弹屏
        UPDATE_NOR,//普通更新
        UPDATE_FORCE,//强制更新
    }
}
