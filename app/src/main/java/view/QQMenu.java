package view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

/**
 * Created by 24436 on 2017/12/22.
 */

public class QQMenu extends HorizontalScrollView {

    private LinearLayout mScrollView;       //定义横向滚动条布局
    private ViewGroup mMenu;        //定义菜单区域
    private ViewGroup mContent;     //定义主显示区域
    private int mScreenWidth;        //定义屏幕宽度
    private int mMenuRightPadding = 50;     //定义菜单右边距为50dp
    private boolean call;           //标记只定义一次横向滚动视图与子视图的宽度
    private int mMenuWidth;         //定义菜单宽度

    //构造方法
    public QQMenu(Context context, AttributeSet attrs) {
        super(context, attrs);

        //获取窗口管理服务
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        //创建显示尺寸对象
        DisplayMetrics outMetrics = new DisplayMetrics();
        //获取当前屏幕的宽高尺寸
        windowManager.getDefaultDisplay().getMetrics(outMetrics);
        //为屏幕尺寸复制
        mScreenWidth = outMetrics.widthPixels;
        //将50dp转换成像素
        mMenuRightPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,50,context.getResources().getDisplayMetrics());
    }


    //设置横向滚动视图中的主显示区域和隐藏区域的宽度
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        mScrollView = (LinearLayout) getChildAt(0);     //获取横向滚动视图

        mMenu = (ViewGroup) mScrollView.getChildAt(0);      //获取菜单显示区域
        mContent = (ViewGroup) mScrollView.getChildAt(1);   //获取主显示区域
        //设置菜单宽度
        mMenuWidth = mMenu.getLayoutParams().width = mScreenWidth - mMenuRightPadding;
        //设置主显示区域宽度
        mContent.getLayoutParams().width = mScreenWidth;
        //已经定义了宽度
        call = true;

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    //设置偏移量让菜单隐藏
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed) {
            this.scrollTo(mMenuWidth,0);
        }
    }

    //判断手指抬起时隐藏菜单还是显示菜单

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action){
            case MotionEvent.ACTION_UP:
                /***
                 *  getScrollX()总的偏移值，而不是单次的移动值
                 *  例如当onLayout()后偏移量是943，而不是0
                 */
                int scrollX = getScrollX();
                Log.d("TAG","当前偏移：x = " + scrollX);
                if (scrollX > mMenuWidth / 2){
                    this.scrollTo(mMenuWidth,0);    //显示主显示区域
                }else{
                    this.scrollTo(0,0);          //显示菜单
                }
                return true;
        }
        return super.onTouchEvent(ev);
    }
}
