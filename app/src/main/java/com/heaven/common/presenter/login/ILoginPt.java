package com.heaven.common.presenter.login;

import com.heaven.common.presenter.IBasePresenter;
import com.heaven.common.presenter.IBaseView;

/**
 * Created by heaven on 2016/4/19.
 */
public interface ILoginPt {
    /**
     * UI Action interface
     */
    interface View extends IBaseView<Presenter> {
        /**
         * request checkCode
         * @param checkCode checkCode
         */
        void resCheckCode(String checkCode);

        /**
         * request login
         * @param isSuccess identify the status of login
         */
        void resLoginSuccess(boolean isSuccess);

        /**
         * callback the http error message
         * @param error error objcet
         */
        void resError(Object error);
    }

    /**
     * handle business interface(when you need http request or deal with logic business)
     */
    interface Presenter extends IBasePresenter {
        /**
         * request the checkCode that login need
         * @param userName user account
         * @param password user password
         */
        void reqCheckCode(String userName, String password);

        /**
         * request login
         * @param userName user account
         * @param password user password
         * @param checkCode checkCode
         */
        void reqLogin( String userName, String password, String checkCode);
    }
}
