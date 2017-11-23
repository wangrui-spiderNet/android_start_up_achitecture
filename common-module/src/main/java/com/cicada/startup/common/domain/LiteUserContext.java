package com.cicada.startup.common.domain;

import java.io.Serializable;

/**
 * Created by zhangyuanhong on 2017/4/8.
 */

public class LiteUserContext implements Serializable{
    private int customerType;
    private int roleType;

    public int getCustomerType() {
        return customerType;
    }

    public void setCustomerType(int customerType) {
        this.customerType = customerType;
    }

    public int getRoleType() {
        return roleType;
    }

    public void setRoleType(int roleType) {
        this.roleType = roleType;
    }
}
