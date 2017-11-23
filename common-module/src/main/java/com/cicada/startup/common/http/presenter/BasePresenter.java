package com.cicada.startup.common.http.presenter;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by zyh on 2016/8/17.
 */
public class BasePresenter implements BasePresenterImpl {

    private CompositeSubscription mCompositeSubscription;

    protected void addSubscription(Subscription subscription) {
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = new CompositeSubscription();
        }
        this.mCompositeSubscription.add(subscription);
    }

    @Override
    public void unsubcrible() {
        if (this.mCompositeSubscription != null) {
            this.mCompositeSubscription.unsubscribe();
        }
    }
}
