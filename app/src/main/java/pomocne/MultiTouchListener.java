package pomocne;

/**
 * Created by Luka on 5.8.2016..
 */

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.RelativeLayout;
import moje.src.Main2Activity;

public class MultiTouchListener implements OnTouchListener {

    private float mPrevX;
    private float mPrevY;
    private boolean isMove = false;
    public Main2Activity mainActivity;

    public MultiTouchListener(Main2Activity mainActivity1) {
        mainActivity = mainActivity1;
    }


    @Override
    public boolean onTouch(View view, MotionEvent event) {
        float currX, currY;
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN: {
                mPrevX = event.getX();
                mPrevY = event.getY()+125;
                isMove = false;
                break;
            }

            case MotionEvent.ACTION_MOVE: {
                currX = event.getRawX();
                currY = event.getRawY();
                MarginLayoutParams marginParams = new MarginLayoutParams(view.getLayoutParams());
                marginParams.setMargins((int) (currX - mPrevX), (int) (currY - mPrevY), 0, 0);
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(marginParams);
                view.setLayoutParams(layoutParams);
                isMove = true;
                break;
            }

            case MotionEvent.ACTION_CANCEL:
                break;

            case MotionEvent.ACTION_UP:
                currX = event.getRawX();
                currY = event.getRawY();
                if (!isMove || (((currX - mPrevX)) < 15 && (currY - mPrevY ) < 15)) {
                    mainActivity.otvoriDrawer();
                    break;

                }
        }
        return true;
    }
}