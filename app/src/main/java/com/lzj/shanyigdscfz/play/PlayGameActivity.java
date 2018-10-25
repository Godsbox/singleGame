/*
 * Copyright (c) 2017-present 3000.com All Rights Reserved.
 */
package com.lzj.shanyigdscfz.play;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnAttachStateChangeListener;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.lzj.arch.app.PassiveActivity;
import com.lzj.arch.bus.BaseMessageEvent;
import com.lzj.arch.bus.BusManager;
import com.lzj.arch.util.DisplayUtils;
import com.lzj.arch.util.ViewUtils;
import com.lzj.arch.widget.OnSwipeListener;
import com.lzj.shanyigdscfz.BaWei;
import com.lzj.shanyigdscfz.R;
import com.lzj.shanyigdscfz.browser.BrowserFragment;

import java.util.Random;

import static android.R.attr.max;
import static android.animation.ObjectAnimator.ofFloat;
import static android.animation.ValueAnimator.INFINITE;
import static android.animation.ValueAnimator.RESTART;
import static android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN;
import static com.lzj.arch.app.web.WebConstant.EXTRA_LAYOUT_ID;
import static com.lzj.arch.util.ResourceUtils.getDimensionPixelSize;
import static com.lzj.arch.util.ViewUtils.inflate;

/**
 * 玩游戏屏幕。
 *
 * @author 吴吉林
 */
public class PlayGameActivity
        extends PassiveActivity<PlayGameContract.Presenter>
        implements PlayGameContract.PassiveView, View.OnClickListener {

    /**
     * 当前界面视图。
     */
    private FrameLayout container;

    /**
     * 确认退出视图。
     */
    private View quitConfirm;

    /**
     * 退出引导。
     */
    private View quitGuide;

    /**
     * 退出按钮。
     */
    private ImageView quit;

    private TextView tips;

    /**
     * 手势检测器。
     */
    private GestureDetector gestureDetector;

    {
        setFullscreen(true);
    }

    @Override
    protected void onCreate(@Nullable Bundle state) {
        super.onCreate(state);
        BaWei.getInstance().addActivity_(this);
    }

    @Override
    protected void onFragmentTransaction(FragmentTransaction transaction) {
        BrowserFragment fragment = new BrowserFragment();
        fragment.setArgument(EXTRA_LAYOUT_ID, R.layout.app_fragment_web_content);
        setFragment(fragment);
        super.onFragmentTransaction(transaction);
    }

    @Override
    public void onFindView() {
        super.onFindView();
        container = findView(R.id.fragment_container);
    }

    @Override
    public void onInitView(Bundle state) {
        super.onInitView(state);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        int miniVelocity = getDimensionPixelSize(R.dimen.mini_fling_velocity);
        gestureDetector = new GestureDetector(this, new OnSwipeListener(100, miniVelocity * 2) {
            @Override
            public boolean onSwipe(OnSwipeListener.Direction direction) {
                if (direction == Direction.RIGHT) {
                    getPresenter().onBackPressed();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        getPresenter().onBackPressed();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        gestureDetector.onTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void toggleQuitGuide(boolean show) {
        if (!show) {
            container.removeView(quitGuide);
            return;
        }
        quitGuide = inflate(R.layout.app_view_game_play_quit_guide, container, false);
        ImageView hand = ViewUtils.findView(quitGuide, R.id.hand);
        hand.addOnAttachStateChangeListener(new HandAttachStateChangeListener());
        quitGuide.setOnClickListener(this);
        container.postDelayed(new Runnable() {
            @Override
            public void run() {
                container.addView(quitGuide);
            }
        }, 200);
    }

    @Override
    public void toggleQuitConfirm(boolean show) {
        if (!show) {
            container.removeView(quitConfirm);
            DisplayUtils.setTransparentNavigation(this);
            getWindow().addFlags(FLAG_FULLSCREEN);
            BusManager.postResponse(new BaseMessageEvent(BaseMessageEvent.PLAYER_MENU,false));
            return;
        }
        Random random = new Random();
        int s = random.nextInt(4)%(max) + 1;
        String tip = getString(R.string.quit_play_game_confirm_message);
        switch (s){
            case 1:
                tip = getString(R.string.quit_play_game_confirm_message);
                break;
            case 2:
                tip = getString(R.string.quit_play_game_confirm_message_save);
                break;
            case 3:
                tip = getString(R.string.quit_play_game_confirm_message_gift);
                break;
            case 4:
                tip = getString(R.string.quit_play_game_confirm_message_collect);
                break;
        }

        if (quitConfirm == null) {
            quitConfirm = inflate(R.layout.app_view_game_play_quit_confirm, container, false);
            quit = ViewUtils.findView(quitConfirm, R.id.quit);
            quit.setOnClickListener(this);
            quitConfirm.setOnClickListener(this);

            tips = ViewUtils.findView(quitConfirm,R.id.quit_tips);

        }
        if(tips != null){
            tips.setText(tip);
        }
        container.addView(quitConfirm);
        getWindow().clearFlags(FLAG_FULLSCREEN);
        DisplayUtils.setTransparentStatus(this);
        BusManager.postResponse(new BaseMessageEvent(BaseMessageEvent.PLAYER_MENU,true));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.quit_confirm:
                getPresenter().onBackPressed();
                break;
            case R.id.quit_guide:
                getPresenter().onBackPressed();
                break;
            case R.id.quit:
                // 结束计时
                DisplayUtils.setTransparentNavigation(this);
                super.onBackPressed();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        BaWei.getInstance().removeActivity_(this);
        super.onDestroy();
    }

    /**
     * 引导手视图绑定状态监听器。
     */
    private static class HandAttachStateChangeListener implements OnAttachStateChangeListener {

        /**
         * 手动画。
         */
        private ObjectAnimator animator;

        @Override
        public void onViewAttachedToWindow(View v) {
            animator = ofFloat(v, "translationX", DisplayUtils.dp2px(30));
            animator.setDuration(1000);
            animator.setRepeatMode(RESTART);
            animator.setRepeatCount(INFINITE);
            animator.start();
        }

        @Override
        public void onViewDetachedFromWindow(View v) {
            if (animator != null) {
                animator.cancel();
                animator = null;
            }
        }
    }
}