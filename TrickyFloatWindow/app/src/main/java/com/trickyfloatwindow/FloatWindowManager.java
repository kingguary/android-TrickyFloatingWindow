package com.trickyfloatwindow;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import static android.view.WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;

/**
 * Created by guary on 2017/8/20.
 */

public class FloatWindowManager {
    private static final String TAG = FloatWindowManager.class.getSimpleName();

    private static FloatWindowManager mInstance;
    private Context mAppContext;
    private WindowManager wm;
    private WindowManager.LayoutParams params;
    private PopClickListener mOnClickListener;
    private View mDeniedAdView;

    public synchronized static FloatWindowManager getInstance() {
        if (null == mInstance) {
            mInstance = new FloatWindowManager();
        }

        return mInstance;
    }

    private FloatWindowManager() {
        this.mAppContext = App.getInstance().getApplicationContext();
        wm = (WindowManager) mAppContext.getSystemService(Context.WINDOW_SERVICE);
    }

    public void showDeniedFloatWindow(PopClickListener listener) {
        this.mOnClickListener = listener;
        clearAllViewsBefore();
        mDeniedAdView = View.inflate(App.getInstance().getApplicationContext(), R.layout.hihi, null);
        ImageView imgClose = (ImageView) mDeniedAdView.findViewById(R.id.close);
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wm.removeView(mDeniedAdView);
                if (null != mOnClickListener) {
                    mOnClickListener.onPopClicked();
                }
            }
        });
        ImageView imgAd = (ImageView) mDeniedAdView.findViewById(R.id.img);
        imgAd.setImageResource(R.mipmap.bd_logo);
        imgAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wm.removeView(mDeniedAdView);
            }
        });
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        params = new WindowManager.LayoutParams();
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.y = DensityUtil.dip2px(mAppContext, 45);
        params.gravity = Gravity.BOTTOM;
        params.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON | FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
        // TYPE_TOAST or TYPE_SYSTEM_ERROR is ok
        params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR; //TYPE_SYSTEM_ERROR; // TYPE_SYSTEM_OVERLAY;
        wm.addView(mDeniedAdView, params);
    }

    public void clearAllViewsBefore() {
        if (null != mDeniedAdView) {
            try {
                wm.removeView(mDeniedAdView);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public interface PopClickListener {
        void onPopClicked();
    }
}
