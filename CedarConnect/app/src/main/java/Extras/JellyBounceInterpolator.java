package Extras;

import android.view.animation.Interpolator;

/**
 * Created by Saboor Salaam on 3/7/2015.
 */
public class JellyBounceInterpolator implements Interpolator {

    @Override
    public float getInterpolation(float ratio) {
        if (ratio == 0.0f || ratio == 1.0f)
            return ratio;
        else {
            float p = 1.8f;
            float two_pi = (float) (Math.PI * 2.7f);
            return (float) Math.pow(2.0f, -10.0f * ratio) * (float) Math.sin((ratio - (p/5.0f)) * two_pi/p) + 1.0f;
        }
    }
}
