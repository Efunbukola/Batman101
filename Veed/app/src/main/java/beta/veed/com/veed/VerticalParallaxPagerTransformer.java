package beta.veed.com.veed;

/**
 * Created by Saboor Salaam on 8/23/2014.
 */
import android.support.v4.view.ViewPager;
import android.view.View;

public class VerticalParallaxPagerTransformer implements ViewPager.PageTransformer {

    private int id;
    private int border = 0;
    private float speed = 0.2f;

    public VerticalParallaxPagerTransformer(int id) {
        this.id = id;
    }

    @Override
    public void transformPage(View view, float position) {

        View parallaxView = view.findViewById(id);

        if (parallaxView != null) {
            if (position > -1 && position < 1) {
                float height = parallaxView.getHeight();
                parallaxView.setTranslationY(-(position * height * speed));
                float sc = ((float)view.getHeight() - border)/ view.getHeight();
                if (position == 0) {
                    view.setScaleX(1);
                    view.setScaleY(1);
                } else {
                    view.setScaleX(sc);
                    view.setScaleY(sc);
                }
            }
        }
    }

    public void setBorder(int px) {
        border = px;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }


}