/**
 * Project Name:CommonStruct
 * File Name:SwitchManager.java
 * Package Name:com.heaven.commonstruct.core
 * Date:2016年3月23日下午2:48:55
 * Copyright (c) 2016
 */

package base.core.heaven.manager;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentManager.BackStackEntry;
import android.app.FragmentTransaction;
import android.content.Intent;

import base.core.heaven.Interface.IOnPageBackResult;
import base.core.heaven.baseui.BaseActivity;
import base.core.heaven.baseui.BaseFragment;
import base.core.heaven.main.R;
import base.core.heaven.param.PageParam;

/**
 * ClassName:SwitchManager <br/>
 * Function: fragment页面切换控制器. <br/>
 * Reason:   负责页面切换. <br/>
 * Date:     2016年3月23日 下午2:48:55 <br/>
 *
 * @author neusoft liu.hongtao
 * @version 1.0
 * @since JDK 1.6
 */
public class SwitchManager implements IOnPageBackResult {
    private static SwitchManager instance = null;
    //需要返回数据的fragment
    private BaseFragment mResultFragment = null;
    //请求码
    private int requestCode = -1;
    //结果码
    private int resultCode = -1;
    //当前显示的页面
    private BaseFragment currentPage = null;

    private SwitchManager() {

    }

    public static SwitchManager getInstance() {
        if (instance == null) {
            instance = new SwitchManager();
        }
        return instance;
    }

    //启动一个页面并返回需要的结果
    public void startPageForResult(BaseActivity activity, PageParam targetPage, BaseFragment resultFragment) {
        if (targetPage != null && resultFragment != null) {
            if (targetPage.isNewActivity) {
                //启动一个新的 activity
                startActivityForResult(activity, targetPage);
            } else {
                //显示一个fragment并返回结果
                showPageForResult(activity, targetPage);
            }
            this.requestCode = targetPage.requestCode;
            this.resultCode = targetPage.resultCode;
            this.mResultFragment = resultFragment;
        }
    }

    //启动activity并返回结果
    private void startActivityForResult(Activity activity, PageParam targetPage) {
        try {
            if (targetPage != null) {
                if (targetPage.pageClazz != null) {
                    Intent intent = new Intent();
                    intent.setClassName(activity, targetPage.pageClazz);
                    intent.putExtra("PageParam", targetPage);
                    intent.putExtra("startActivityForResult", "true");
                    activity.startActivityForResult(intent, targetPage.requestCode);

                    int[] animations = targetPage.anim;
                    if (animations != null && animations.length >= 2) {
                        activity.overridePendingTransition(animations[0], animations[1]);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * showPageForResult:(显示页面 并返回数据结果). <br/>
     *
     * @param targetPage 页面参数
     * @since JDK 1.6
     */
    private void showPageForResult(BaseActivity activity, PageParam targetPage) {
        if (targetPage != null) {
            //取得目标页面实例
            BaseFragment target = getTargetFragment(activity, targetPage);
            if (target != null) {
                target.mFragmentName = targetPage.pageName;
                //是否有传递数据
                if (targetPage.data != null) {
                    target.setArguments(targetPage.data);
                }
                //设置页面参数
                target.setPageParm(targetPage);
                //设置回调数据监听
                target.setOnBackResultListener(this);
                //页面显示过程处理
                processTransaction(activity, target, targetPage);
            }
        }

    }

    /**
     * showPage:(显示目标页面). <br/>
     *
     * @param targetPage 目标页面的相关参数
     * @since JDK 1.6
     */
    public void showPage(BaseActivity activity, PageParam targetPage) {
        if (activity != null && targetPage != null) {
            if (targetPage.isNewActivity) {
                showActivity(activity, targetPage);
            } else {
                showFragmentPage(activity, targetPage);
            }
        }
    }

    /**
     * 显示fragment
     *
     * @param activity   上下文
     * @param targetPage 目标fragment的参数
     */
    private void showFragmentPage(BaseActivity activity, PageParam targetPage) {
        //取得目标页面实例
        BaseFragment target = getTargetFragment(activity, targetPage);
        if (target != null) {
            target.mFragmentName = targetPage.pageName;
            //是否有传递数据
            if (targetPage.data != null) {
                target.setArguments(targetPage.data);
            }
            //页面显示过程处理
            processTransaction(activity, target, targetPage);
        }
    }

    /**
     * 启动一个新的activity
     *
     * @param activity   上下文
     * @param targetPage 目标参数
     */
    private void showActivity(Activity activity, PageParam targetPage) {
        if (targetPage != null) {
            if (targetPage.pageClazz != null) {
                Intent intent = new Intent();
                intent.setClassName(activity, targetPage.pageClazz);
                intent.putExtra("PageParam", targetPage);
                intent.putExtra("startActivityForResult", "true");
                activity.startActivity(intent);
                int[] animations = targetPage.anim;
                if (animations != null && animations.length >= 2) {
                    activity.overridePendingTransition(animations[0], animations[1]);
                }

            }
        }
    }


    //执行fragment显示隐藏处理
    private void processTransaction(BaseActivity activity, BaseFragment target, PageParam targetPage) {
        FragmentTransaction transaction = activity.getFragmentManager().beginTransaction();
        if (target != null && targetPage != null) {
            //fragment切换是否需要动画
            if (targetPage.anim != null && targetPage.anim.length >= 4) {
                transaction.setCustomAnimations(targetPage.anim[0], targetPage.anim[1], targetPage.anim[2], targetPage.anim[3]);
            }
            //是否需要隐藏上一个页面
            if (targetPage.needHide) {
                if (currentPage != null) {
                    transaction.hide(currentPage);
                }
            }
            //目标页面是否已经加入如果已经加入就显示
            if (target.isAdded()) {
                transaction.show(target);
            } else {
                //没有加入就加入到主容器中
                transaction.add(R.id.main_container, target, targetPage.pageName);
            }
            //是否需要加入到回退栈中
            if (targetPage.needAddToBackStack) {
                transaction.addToBackStack(targetPage.pageName);
            }
            //提交处理过程
            transaction.commitAllowingStateLoss();
            currentPage = target;
        }
    }

    /**
     * getTargetFragment:(取得目标fragment). <br/>
     *
     * @param targetPage 页面参数
     * @return 取得目标页面
     * @since JDK 1.6
     */
    private BaseFragment getTargetFragment(BaseActivity activity, PageParam targetPage) {
        BaseFragment target = null;
        if (targetPage != null) {
            //是否新实例华一个fragment
            if (targetPage.isNewInstance) {
                target = newInstance(targetPage);
            } else {
                //查找是否已经存在
                target = (BaseFragment) activity.getFragmentManager().findFragmentByTag(targetPage.pageName);
                if (target == null) {
                    //不存在实例华一个
                    target = newInstance(targetPage);
                }
            }
        }
        return target;
    }

    /**
     * newInstance:(实例化一个新的). <br/>
     *
     * @param targetPage 页面参数
     * @return 页面实例
     * @since JDK 1.6
     */
    private BaseFragment newInstance(PageParam targetPage) {
        BaseFragment target = null;
        try {
            Object obj = Class.forName(targetPage.pageClazz).newInstance();
            if (obj != null) {
                //只用fragment才能被实例化,activity直接启动
                if (obj instanceof BaseFragment) {
                    target = (BaseFragment) obj;
                }
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return target;
    }

    /**
     * popPage:(fragment弹栈操作). <br/>
     *
     * @since JDK 1.6
     */
    private void popPage(BaseActivity activity) {
        if (activity != null) {
            if (activity.isFinishing()) {
                return;
            }
            FragmentManager fgManager = activity.getFragmentManager();
            int backStackCount = fgManager.getBackStackEntryCount();
            if (backStackCount > 1) {
                boolean popOk = fgManager.popBackStackImmediate();
                if (popOk) {
                    currentPage = getActiveFragment(activity);
                }
            } else {
                activity.finish();
            }
        }
    }

    /**
     * 获得当前活动fragmnet
     *
     * @return 当前活动Fragment对象
     */
    public BaseFragment getActiveFragment(BaseActivity activity) {
        if (activity.isFinishing()) {
            return null;
        }
        FragmentManager manager = activity.getFragmentManager();
        if (manager != null) {
            int count = manager.getBackStackEntryCount();
            if (count > 0) {
                String tag = manager.getBackStackEntryAt(count - 1).getName();
                return (BaseFragment) manager.findFragmentByTag(tag);
            }
        }
        return null;
    }

    /**
     * fragment返回的数据
     * 接受返回的数据.
     */
    @Override
    public void onPageBackResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == this.requestCode && resultCode == this.resultCode) {
            if (mResultFragment != null) {
                mResultFragment.onActivityResult(requestCode, resultCode, data);
                mResultFragment = null;
                this.requestCode = -1;
                this.resultCode = -1;
            }
        }
    }

    /**
     * onActivityResult:(activity返回的数据). <br/>
     *
     * @param activity    fragment所属activity
     * @param requestCode 请求码
     * @param resultCode  结果码
     * @param data        结果数据
     * @since JDK 1.6
     */
    public void onActivityResult(BaseActivity activity, int requestCode, int resultCode, Intent data) {
        if (requestCode == this.requestCode && resultCode == this.resultCode) {
            FragmentManager fgManager = activity.getFragmentManager();
            int count = fgManager.getBackStackEntryCount();
            if (count > 1) {
                BackStackEntry entry = fgManager.getBackStackEntryAt(count - 1);
                if (entry != null) {
                    BaseFragment fragment = (BaseFragment) fgManager.findFragmentByTag(entry.getName());
                    fragment.onActivityResult(requestCode, resultCode, data);
                    mResultFragment = null;
                    this.requestCode = -1;
                    this.resultCode = -1;
                }
            }
        }
    }

    /**
     * 按回退键
     * @param activity 宿主activity
     */
    public void OnBackPress(BaseActivity activity) {
        popPage(activity);
    }
}
 
