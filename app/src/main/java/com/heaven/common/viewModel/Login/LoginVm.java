package com.heaven.common.viewModel.Login;

import android.databinding.BaseObservable;
import android.databinding.Bindable;


/**
 * Created by heaven on 2016/4/17.
 */
public class LoginVm extends BaseObservable {
    private String firstName;
    private String lastName;
    private ILoginVm mCallBack;

    public LoginVm(ILoginVm callBack) {
        this.mCallBack = callBack;
    }

    @Bindable
    public String getFirstName() {
        return this.firstName;
    }

    @Bindable
    public String getLastName() {
        return this.lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
