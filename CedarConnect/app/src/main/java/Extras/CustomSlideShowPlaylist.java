package Extras;

import com.marvinlabs.widget.slideshow.playlist.SequentialPlayList;

/**
 * Created by Saboor Salaam on 3/7/2015.
 */
public class CustomSlideShowPlaylist extends SequentialPlayList {
    public CustomSlideShowPlaylist() {
        super();
    }

    @Override
    public int getFirstSlide() {
        return super.getFirstSlide();
    }

    @Override
    public int getCurrentSlide() {
        return super.getCurrentSlide();
    }

    @Override
    public int getNextSlide() {
        return super.getNextSlide();
    }

    @Override
    public int getPreviousSlide() {
        return super.getPreviousSlide();
    }

    @Override
    public void rewind() {
        super.rewind();
    }

    @Override
    public int next() {
        return super.next();
    }

    @Override
    public int previous() {
        return super.previous();
    }

    @Override
    public void onSlideCountChanged(int newSlideCount) {
        super.onSlideCountChanged(newSlideCount);
    }

    @Override
    public boolean isAutoAdvanceEnabled() {
        return super.isAutoAdvanceEnabled();
    }

    @Override
    public void setAutoAdvanceEnabled(boolean isAutoAdvanceEnabled) {
        super.setAutoAdvanceEnabled(isAutoAdvanceEnabled);
    }

    @Override
    public long getSlideDuration(int position) {
        return super.getSlideDuration(position);
    }

    @Override
    public void setSlideDuration(long slideDuration) {
        super.setSlideDuration(slideDuration);
    }

    @Override
    public boolean isLooping() {
        return super.isLooping();
    }

    @Override
    public void setLooping(boolean isLooping) {
        super.setLooping(isLooping);
    }
}
