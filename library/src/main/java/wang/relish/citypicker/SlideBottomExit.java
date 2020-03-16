/*
 * 杭州智晗信息技术有限公司 版权所有.
 * Copyright © 2020. Zhihan Co., Ltd. All Rights Reserved.
 *
 */

package wang.relish.citypicker;

import android.animation.ObjectAnimator;
import android.util.DisplayMetrics;
import android.view.View;

public class SlideBottomExit extends BaseAnimatorSet {
    @Override
    public void setAnimation(View view) {
        DisplayMetrics dm = view.getContext().getResources().getDisplayMetrics();
        animatorSet.playTogether(
                ObjectAnimator.ofFloat(view, "translationY", 0, 350 * dm.density));
    }
}
