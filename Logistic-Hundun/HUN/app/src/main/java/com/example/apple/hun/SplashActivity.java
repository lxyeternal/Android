package com.example.apple.hun;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.RelativeLayout;

public class SplashActivity extends Activity {

    /**
     * 闪屏页
     */
    private RelativeLayout rlSplash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash_activity);

        rlSplash = (RelativeLayout) findViewById(R.id.rl_splash);

        startAnim();
    }

    /**
     * 启动动画
     */
    private void startAnim() {
        // 渐变动画,从完全透明到完全不透明
        AlphaAnimation alpha = new AlphaAnimation(0, 1);
        // 持续时间 2 秒
        alpha.setDuration(2000);
        // 动画结束后，保持动画状态
        alpha.setFillAfter(true);

        // 设置动画监听器
        alpha.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            // 动画结束时回调此方法
            @Override
            public void onAnimationEnd(Animation animation) {
                // 跳转到下一个页面
                jumpNextPage();
            }
        });

        // 启动动画
        rlSplash.startAnimation(alpha);
    }

    /**
     * 跳转到下一个页面
     */
    private void jumpNextPage() {
        // startActivity(new Intent(SplashActivity.this, GuideActivity.class));
        // finish();

        // 判断之前有没有展示过功能引导
        boolean guideShowed = PrefUtils.getBoolean(SplashActivity.this,
                PrefUtils.GUIDE_SHOWED, false);

        if (!guideShowed) {
            // 跳转到功能引导页
            startActivity(new Intent(SplashActivity.this, GuideActivity.class));
        } else {
            // 跳转到主页面
            startActivity(new Intent(SplashActivity.this, StartActivity.class));
        }

        finish();
    }
}
