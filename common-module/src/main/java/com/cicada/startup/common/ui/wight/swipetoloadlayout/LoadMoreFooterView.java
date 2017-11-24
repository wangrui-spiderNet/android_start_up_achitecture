package com.cicada.startup.common.ui.wight.swipetoloadlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.SwipeLoadMoreFooterLayout;
import com.cicada.startup.common.R;

/**
 * Created by zyh on 2016/10/27 0027.
 */
public class LoadMoreFooterView extends SwipeLoadMoreFooterLayout {
    private TextView tvLoadMore;
    private ImageView ivSuccess;
    private ProgressBar progressBar;

    private int mFooterHeight;

    public LoadMoreFooterView(Context context) {
        this(context, null);
    }

    public LoadMoreFooterView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadMoreFooterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //mFooterHeight = getResources().getDimensionPixelOffset(R.dimen.dp55);
        this.post(new Runnable() {
            @Override
            public void run() {
                mFooterHeight = getHeight();
            }
        });
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        tvLoadMore = (TextView) findViewById(R.id.tvLoadMore);
        ivSuccess = (ImageView) findViewById(R.id.ivSuccess);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
    }

    @Override
    public void onPrepare() {
        ivSuccess.setVisibility(GONE);
    }

    @Override
    public void onMove(int y, boolean isComplete, boolean automatic) {
        if (!isComplete) {
            ivSuccess.setVisibility(GONE);
            progressBar.setVisibility(GONE);
            if (-y >= mFooterHeight) {
                tvLoadMore.setText(getContext().getText(R.string.releseloadmore));
            } else {
                tvLoadMore.setText(getContext().getText(R.string.pulltoloadmore));
            }
        }
    }

    @Override
    public void onLoadMore() {
        tvLoadMore.setText(getContext().getText(R.string.loadmoreing));
        progressBar.setVisibility(VISIBLE);
    }

    @Override
    public void onRelease() {

    }

    @Override
    public void onComplete() {
        progressBar.setVisibility(GONE);
//        ivSuccess.setVisibility(VISIBLE);
//        tvLoadMore.setText(getContext().getText(R.string.loadmorecomplete));
    }

    @Override
    public void onReset() {
        ivSuccess.setVisibility(GONE);
    }
}
