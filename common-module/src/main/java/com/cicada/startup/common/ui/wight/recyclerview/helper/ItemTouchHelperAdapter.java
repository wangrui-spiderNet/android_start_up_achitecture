

package com.cicada.startup.common.ui.wight.recyclerview.helper;

public interface ItemTouchHelperAdapter {
    void onItemMoving(int fromPosition, int toPosition);

    void onItemMoved(int fromPosition, int toPosition);

    void onItemDismiss(int position);
}
