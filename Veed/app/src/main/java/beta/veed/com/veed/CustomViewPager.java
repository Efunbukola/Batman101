package beta.veed.com.veed;

import android.content.Context;
import android.support.v4.view.ViewPager;

/**
 * Created by Saboor Salaam on 8/26/2014.
 */
public class CustomViewPager extends ViewPager {

    OverScrollListener osl;

    public CustomViewPager(Context context) {
        super(context);

    }

    public void setOverScrollListener(OverScrollListener osl)
    {
        this.osl = osl;
    }

    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
        if(osl != null) {
            osl.onOverScrollBeginning();
        }
    }

    interface OverScrollListener
    {
        void onOverScrollBeginning();
        void getOnOverScrollEnd();
    }


}
