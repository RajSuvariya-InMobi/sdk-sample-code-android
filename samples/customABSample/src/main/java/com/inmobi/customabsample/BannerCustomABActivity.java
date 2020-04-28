package com.inmobi.customabsample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.inmobi.ads.AdMetaInfo;
import com.inmobi.ads.InMobiAdRequestStatus;
import com.inmobi.ads.InMobiBanner;
import com.inmobi.ads.listeners.BannerAdEventListener;

import java.util.Map;

public class BannerCustomABActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = BannerCustomABActivity.class.getSimpleName();
    InMobiBanner mInMobiBanner;

    public static final int BANNER_WIDTH = 320;
    public static final int BANNER_HEIGHT = 50;

    private Button preload, show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner_custom_a_b);
        preload = findViewById(R.id.preload);
        preload.setOnClickListener(this);
        show = findViewById(R.id.show);
        show.setOnClickListener(this);

        setupBannerAd();
    }

    private void setupBannerAd() {
        mInMobiBanner = new InMobiBanner(BannerCustomABActivity.this, PlacementId.YOUR_BANNER_PLACEMENT_ID);
        RelativeLayout adContainer = (RelativeLayout) findViewById(R.id.ad_container);
        mInMobiBanner.setAnimationType(InMobiBanner.AnimationType.ROTATE_HORIZONTAL_AXIS);
        mInMobiBanner.setEnableAutoRefresh(false);
        mInMobiBanner.setListener(new BannerAdEventListener() {
            @Override
            public void onAdLoadSucceeded(@NonNull InMobiBanner inMobiBanner,
                                          @NonNull AdMetaInfo adMetaInfo) {
                super.onAdLoadSucceeded(inMobiBanner, adMetaInfo);
                show.setVisibility(View.INVISIBLE);
                Log.d(TAG, "onAdLoadSucceeded");
            }

            @Override
            public void onAdLoadFailed(InMobiBanner inMobiBanner, InMobiAdRequestStatus inMobiAdRequestStatus) {
                super.onAdLoadFailed(inMobiBanner, inMobiAdRequestStatus);
                show.setVisibility(View.INVISIBLE);
                Log.d(TAG, "Banner ad failed to load with error: " +
                        inMobiAdRequestStatus.getMessage());
            }

            @Override
            public void onAdClicked(InMobiBanner inMobiBanner, Map<Object, Object> map) {
                super.onAdClicked(inMobiBanner, map);
                Log.d(TAG, "onAdClicked");
            }

            @Override
            public void onAdDisplayed(InMobiBanner inMobiBanner) {
                super.onAdDisplayed(inMobiBanner);
                Log.d(TAG, "onAdDisplayed");
            }

            @Override
            public void onAdDismissed(InMobiBanner inMobiBanner) {
                super.onAdDismissed(inMobiBanner);
                Log.d(TAG, "onAdDismissed");
            }

            @Override
            public void onUserLeftApplication(InMobiBanner inMobiBanner) {
                super.onUserLeftApplication(inMobiBanner);
                Log.d(TAG, "onUserLeftApplication");
            }

            @Override
            public void onRewardsUnlocked(InMobiBanner inMobiBanner, Map<Object, Object> map) {
                super.onRewardsUnlocked(inMobiBanner, map);
                Log.d(TAG, "onRewardsUnlocked");
            }

            @Override
            public void onAdFetchSuccessful(@NonNull InMobiBanner inMobiBanner, @NonNull AdMetaInfo adMetaInfo) {
                super.onAdFetchSuccessful(inMobiBanner, adMetaInfo);
                Log.d(TAG, "onAdFetchSuccessful");
                show.setVisibility(View.VISIBLE);

            }

            @Override
            public void onAdFetchFailed(@NonNull InMobiBanner inMobiBanner, @NonNull InMobiAdRequestStatus inMobiAdRequestStatus) {
                super.onAdFetchFailed(inMobiBanner, inMobiAdRequestStatus);
                Log.d(TAG, "onAdFetchFailed " + inMobiAdRequestStatus.getMessage());
                show.setVisibility(View.INVISIBLE);
            }
        });
        setBannerLayoutParams();
        adContainer.addView(mInMobiBanner);
    }

    private void setBannerLayoutParams() {
        int width = toPixelUnits(BANNER_WIDTH);
        int height = toPixelUnits(BANNER_HEIGHT);
        RelativeLayout.LayoutParams bannerLayoutParams = new RelativeLayout.LayoutParams(width, height);
        bannerLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        bannerLayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        mInMobiBanner.setLayoutParams(bannerLayoutParams);
    }

    private int toPixelUnits(int dipUnit) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round(dipUnit * density);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.preload:
                mInMobiBanner.getPreloadManager().preload();
                break;
            case R.id.show:
                mInMobiBanner.getPreloadManager().load();
                break;
        }
    }
}
