package com.cicada.startup.common.ui.view.wheelview;

import java.util.List;

public class StringWheelAdapter implements WheelAdapter {

    // Values
    private int minValue;

    // format
    private List<String> mList;

    /**
     * Constructor
     */
    public StringWheelAdapter(List<String> list) {
        this.mList = list;
    }

    @Override
    public String getItem(int index) {
        if (index >= 0 && index < getItemsCount()) {
            int value = minValue + index;
            return mList.get(value);
        }
        return null;
    }

    @Override
    public int getItemsCount() {
        return mList.size();
    }

    @Override
    public int getMaximumLength() {

        return 0;
    }
}
