package com.cicada.startup.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.cicada.startup.R;
import com.cicada.startup.about.AboutActivity;

/**
 * BaseActivity.java
 * <p>
 * Created by lijiankun on 17/8/5.
 */

public class BaseActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (this instanceof AboutActivity) {
            return super.onCreateOptionsMenu(menu);
        }
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_main_about:
                startActivity(new Intent(BaseActivity.this, AboutActivity.class));
                break;
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                BaseActivity.this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 初始化 Toolbar
     *
     * @param toolbar                   要初始化的 Toolbar 对象
     * @param setDisplayHomeAsUpEnabled 是否显示返回键
     * @param resId                     标题 Title 的 resId
     */
    protected void initToolbar(Toolbar toolbar, boolean setDisplayHomeAsUpEnabled, int resId) {
        initToolbar(toolbar, setDisplayHomeAsUpEnabled, BaseActivity.this.getString(resId));
    }

    /**
     * 初始化 Toolbar
     *
     * @param toolbar                   要初始化的 Toolbar 对象
     * @param setDisplayHomeAsUpEnabled 是否显示返回键
     * @param title                     标题 Title
     */
    protected void initToolbar(Toolbar toolbar, boolean setDisplayHomeAsUpEnabled, String title) {
        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(this);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(setDisplayHomeAsUpEnabled);
            getSupportActionBar().setTitle(title);
        }
    }
}
