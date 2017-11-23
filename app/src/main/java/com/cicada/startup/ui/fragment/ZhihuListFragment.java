package com.cicada.startup.ui.fragment;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.cicada.startup.MyApplication;
import com.cicada.startup.R;
import com.cicada.startup.data.Injection;
import com.cicada.startup.data.local.db.entity.ZhihuStory;
import com.cicada.startup.ui.activity.ZhihuActivity;
import com.cicada.startup.ui.adapter.ZhihuListAdapter;
import com.cicada.startup.ui.listener.OnItemClickListener;
import com.cicada.startup.utils.L;
import com.cicada.startup.utils.Util;
import com.cicada.startup.viewmodel.ZhihuListViewModel;

import java.util.List;

/**
 * ZhihuListFragment.java
 * <p>
 * Created by lijiankun on 17/7/30.
 */

public class ZhihuListFragment extends Fragment {

    // ZhihuListFragment 所对应的 ViewModel 类的对象
    private ZhihuListViewModel mListViewModel = null;

    private SwipeRefreshLayout mRefreshLayout = null;

    private ZhihuListAdapter mAdapter = null;

    private ProgressBar mLoadMorebar = null;

    private View mRLZhihuRoot = null;

    // 自定义接口，将 RecyclerView 的 Adapter 对其中每个 Item 的点击事件会传到 ZhihuListFragment 中。
    private final OnItemClickListener<ZhihuStory> mZhihuOnItemClickListener =
            new OnItemClickListener<ZhihuStory>() {
                @Override
                public void onClick(ZhihuStory zhihuStory) {
                    if (Util.isNetworkConnected(MyApplication.getInstance())) {
                        ZhihuActivity.startZhihuActivity(getActivity(), zhihuStory.getId(),
                                zhihuStory.getTitle());
                    } else {
                        Util.showSnackbar(mRLZhihuRoot, getString(R.string.network_error));
                    }
                }
            };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_zhihu_list, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        subscribeUI();
    }

    /**
     * 将 ZhihuListFragment 对应的 ZhihuListViewModel 类中的 LiveData 添加注册监听到
     * 此 ZhihuListFragment
     */
    private void subscribeUI() {
        // 通过 ViewModelProviders 创建对应的 ZhihuListViewModel 对象
        ZhihuListViewModel.Factory factory = new ZhihuListViewModel
                .Factory(MyApplication.getInstance()
                , Injection.getDataRepository(MyApplication.getInstance()));
        mListViewModel = ViewModelProviders.of(ZhihuListFragment.this, factory).get(ZhihuListViewModel.class);
        mListViewModel.getZhihuList().observe(this, new Observer<List<ZhihuStory>>() {
            @Override
            public void onChanged(@Nullable List<ZhihuStory> stories) {
                if (stories == null || stories.size() <= 0) {
                    return;
                }
                L.i("size is " + stories.size());
                mAdapter.setStoryList(stories);
            }
        });
        mListViewModel.isLoadingZhihuList().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (aBoolean == null) {
                    return;
                }
                L.i("state " + aBoolean);
                mRefreshLayout.setRefreshing(false);
                mLoadMorebar.setVisibility(aBoolean ? View.VISIBLE : View.INVISIBLE);
            }
        });
        mListViewModel.refreshZhihusData();
    }

    /**
     * 初始化页面 UI
     *
     * @param view Fragment 的 View
     */
    private void initView(View view) {
        if (view == null) {
            return;
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mAdapter = new ZhihuListAdapter(getActivity(), mZhihuOnItemClickListener);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rv_zhihu_list);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(new ZhihuOnScrollListener());

        mRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.srl_zhihu);
        mRefreshLayout.setOnRefreshListener(new ZhihuSwipeListener());
        mRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        mLoadMorebar = (ProgressBar) view.findViewById(R.id.bar_load_more_zhihu);
        mRLZhihuRoot = view.findViewById(R.id.rl_zhihu_root);
    }

    /**
     * ZhihuSwipeListener 用于 SwipeRefreshLayout 下拉刷新操作
     */
    private class ZhihuSwipeListener implements SwipeRefreshLayout.OnRefreshListener {
        @Override
        public void onRefresh() {
            mAdapter.clearStoryList();
            mListViewModel.refreshZhihusData();
        }
    }

    /**
     * ZhihuOnScrollListener 用于 RecyclerView 下拉到最低端时的上拉加载更多操作
     */
    private class ZhihuOnScrollListener extends RecyclerView.OnScrollListener {

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            LinearLayoutManager layoutManager = (LinearLayoutManager)
                    recyclerView.getLayoutManager();
            int lastPosition = layoutManager
                    .findLastCompletelyVisibleItemPosition();
            if (lastPosition == mAdapter.getItemCount() - 1) {
                // 上拉加载更多数据
                mListViewModel.loadNextPageZhihu(lastPosition);
            }
        }
    }
}
