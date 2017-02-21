package com.guang.app.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.guang.app.R;
import com.guang.app.model.Schedule;

import java.util.ArrayList;
import java.util.List;

/**
 * 课程表界面
 * http://blog.csdn.net/shallcheek/article/details/44303197
 * Created by xiaoguang on 2017/2/21.
 */
public class ScheduleView extends LinearLayout {
    /**
     * 配色数组
     */
    public static int colors[] = {R.drawable.sehedule_label_san,
            R.drawable.sehedule_label_er, R.drawable.sehedule_label_si,
            R.drawable.sehedule_label_wu, R.drawable.sehedule_label_liu,
            R.drawable.sehedule_label_qi, R.drawable.sehedule_label_ba,
            R.drawable.sehedule_label_jiu, R.drawable.sehedule_label_sss,
            R.drawable.sehedule_label_se, R.drawable.sehedule_label_yiw,
            R.drawable.sehedule_label_sy, R.drawable.sehedule_label_yiwu,
            R.drawable.sehedule_label_yi, R.drawable.sehedule_label_wuw};
    private final static int START = 0;
    //最大节数
    public final static int MAXNUM = 12;
    //显示到星期几
    public final static int WEEKNUM = 7;
    //单个View高度
    private final static int TimeTableHeight = 50;
    //线的高度
    private final static int TimeTableLineHeight = 2;
    private final static int TimeTableNumWidth = 20;
    private final static int TimeTableWeekNameHeight = 30;
    private LinearLayout mHorizontalWeekLayout;//第一行的星期显示
    private LinearLayout mVerticalWeekLaout;//课程格子
    private String[] weekname = {"一", "二", "三", "四", "五", "六", "七"};
    public static String[] colorStr = new String[20];
    int colornum = 0;
    //数据源
    private List<Schedule> mListTimeTable = new ArrayList<Schedule>();

    public ScheduleView(Context context) {
        super(context);
    }

    public ScheduleView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    /**
     * 横的分界线
     *
     * @return
     */
    private View getWeekTransverseLine() {
        TextView mWeekline = new TextView(getContext());
        mWeekline.setBackgroundColor(getResources().getColor(R.color.view_line));
        mWeekline.setHeight(TimeTableLineHeight);
        mWeekline.setWidth(LayoutParams.FILL_PARENT);
        return mWeekline;
    }

    /**
     * 竖向分界线
     *
     * @return
     */
    private View getWeekVerticalLine() {
        TextView mWeekline = new TextView(getContext());
        mWeekline.setBackgroundColor(getResources().getColor(R.color.view_line));
        mWeekline.setHeight(dip2px(TimeTableWeekNameHeight));
        mWeekline.setWidth((TimeTableLineHeight));
        return mWeekline;
    }


    private void initView() {

        mHorizontalWeekLayout = new LinearLayout(getContext());
        mHorizontalWeekLayout.setOrientation(HORIZONTAL);

        mVerticalWeekLaout = new LinearLayout(getContext());
        mVerticalWeekLaout.setOrientation(HORIZONTAL);
        //表格
        for (int i = 0; i <= WEEKNUM; i++) {
            switch (i) {
                case 0:
                    //课表出的0,0格子 空白的
                    TextView mTime = new TextView(getContext());
                    mTime.setHeight(dip2px(TimeTableWeekNameHeight));
                    mTime.setWidth((dip2px(TimeTableNumWidth)));
                    mHorizontalWeekLayout.addView(mTime);

                    //绘制1~MAXNUM
                    LinearLayout mMonday = new LinearLayout(getContext());
                    ViewGroup.LayoutParams mm = new ViewGroup.LayoutParams(dip2px(TimeTableNumWidth), dip2px(MAXNUM * TimeTableHeight) + MAXNUM * 2);
                    mMonday.setLayoutParams(mm);
                    mMonday.setOrientation(VERTICAL);
                    for (int j = 1; j <= MAXNUM; j++) {
                        TextView mNum = new TextView(getContext());
                        mNum.setGravity(Gravity.CENTER);
                        mNum.setTextColor(getResources().getColor(R.color.text_color));
                        mNum.setHeight(dip2px(TimeTableHeight));
                        mNum.setWidth(dip2px(TimeTableNumWidth));
                        mNum.setTextSize(14);
                        mNum.setText(j + "");
                        mMonday.addView(mNum);
                        mMonday.addView(getWeekTransverseLine());
                    }
                    mVerticalWeekLaout.addView(mMonday);
                    break;
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                    // 设置显示星期一 到星期天
                    LinearLayout mHoriView = new LinearLayout(getContext());
                    mHoriView.setOrientation(VERTICAL);
                    TextView mWeekName = new TextView(getContext());
                    mWeekName.setTextColor(getResources().getColor(R.color.text_color));
                    mWeekName.setWidth(((getViewWidth() - dip2px(TimeTableNumWidth))) / WEEKNUM);
                    mWeekName.setHeight(dip2px(TimeTableWeekNameHeight));
                    mWeekName.setGravity(Gravity.CENTER);
                    mWeekName.setTextSize(16);
                    mWeekName.setText(weekname[i - 1]);
                    mHoriView.addView(mWeekName);
                    mHorizontalWeekLayout.addView(mHoriView);

                    List<Schedule> mListMon = new ArrayList<>();
                    //遍历出星期1~7的课表
                    for (Schedule Schedule : mListTimeTable) {
                        if (Schedule.getDayInWeek() == i) {
                            mListMon.add(Schedule);
                        }
                    }
                    //添加
                    LinearLayout mLayout = getScheduleView(mListMon, i);
                    mLayout.setOrientation(VERTICAL);
                    ViewGroup.LayoutParams linearParams = new ViewGroup.LayoutParams((getViewWidth() - dip2px(20)) / WEEKNUM, LayoutParams.FILL_PARENT);
                    mLayout.setLayoutParams(linearParams);
                    mLayout.setWeightSum(1);
                    mVerticalWeekLaout.addView(mLayout);
                    break;

                default:
                    break;
            }
            TextView l = new TextView(getContext());
            l.setHeight(dip2px(TimeTableHeight * MAXNUM) + MAXNUM * 2);
            l.setWidth(2);
            l.setBackgroundColor(getResources().getColor(R.color.view_line));
            mVerticalWeekLaout.addView(l);
            mHorizontalWeekLayout.addView(getWeekVerticalLine());
        }
        addView(mHorizontalWeekLayout);
        addView(getWeekTransverseLine());
        addView(mVerticalWeekLaout);
        addView(getWeekTransverseLine());
    }

    private int getViewWidth() {
        WindowManager wm = (WindowManager) getContext().getSystemService(
                Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getWidth();
    }

    private View addStartView(int startnum, final int week, final int start) {
        LinearLayout mStartView = new LinearLayout(getContext());
        mStartView.setOrientation(VERTICAL);
        for (int i = 1; i < startnum; i++) {
            TextView mTime = new TextView(getContext());
            mTime.setGravity(Gravity.CENTER);
            mTime.setHeight(dip2px(TimeTableHeight));
            mTime.setWidth(dip2px(TimeTableHeight));
            mStartView.addView(mTime);
            mStartView.addView(getWeekTransverseLine());
            final int num = i;
            //这里可以处理空白处点击添加课表
            mTime.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), "星期" + week + "第" + (start + num) + "节", Toast.LENGTH_SHORT).show();
                }
            });

        }
        return mStartView;
    }

    /**
     * 星期一到星期天的课表
     *
     * @param model
     * @param week
     * @return
     */
    private LinearLayout getScheduleView(List<Schedule> model, int week) {
        LinearLayout mScheduleView = new LinearLayout(getContext());
        mScheduleView.setOrientation(VERTICAL);
        int modesize = model.size();
        if (modesize <= 0) {
            mScheduleView.addView(addStartView(MAXNUM + 1, week, 0));
        } else {
            for (int i = 0; i < modesize; i++) {
                if (i == 0) {
                    //添加的0到开始节数的空格
                    mScheduleView.addView(addStartView(model.get(0).getStartSec(), week, 0));
                    mScheduleView.addView(getMode(model.get(0)));
                } else if (model.get(i).getStartSec() - model.get(i - 1).getStartSec() > 0) {
                    //填充
                    mScheduleView.addView(addStartView(model.get(i).getStartSec() - model.get(i - 1).getEndSec(), week, model.get(i - 1).getEndSec()));
                    mScheduleView.addView(getMode(model.get(i)));
                }
                if (i + 1 == modesize) {
                    mScheduleView.addView(addStartView(MAXNUM - model.get(i).getEndSec(), week, model.get(i).getEndSec()));
                }
            }
        }
        return mScheduleView;
    }

    /**
     * 获取单个课表View 也可以自定义我这个
     *
     * @param model 数据类型
     * @return
     */
    @SuppressWarnings("deprecation")
    private View getMode(final Schedule model) {
        LinearLayout mScheduleView = new LinearLayout(getContext());
        mScheduleView.setOrientation(VERTICAL);
        TextView mTimeTableNameView = new TextView(getContext());
        int num = model.getEndSec() - model.getStartSec();
        mTimeTableNameView.setHeight(dip2px((num + 1) * TimeTableHeight) + num * 2);
        mTimeTableNameView.setTextColor(getContext().getResources().getColor(
                android.R.color.white));
        mTimeTableNameView.setWidth(dip2px(50));
        mTimeTableNameView.setTextSize(16);
        mTimeTableNameView.setGravity(Gravity.CENTER);
        mTimeTableNameView.setText(model.getName() + "\n" + model.getLocation());
        mScheduleView.addView(mTimeTableNameView);
        mScheduleView.addView(getWeekTransverseLine());
        mScheduleView.setBackgroundDrawable(getContext().getResources()
                .getDrawable(colors[getColorNum(model.getName())]));
        mScheduleView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), model.getName() + "\n" + model.getLocation(), Toast.LENGTH_SHORT).show();
            }
        });
        return mScheduleView;
    }

    /**
     * 转换dp
     *
     * @param dpValue
     * @return
     */
    public int dip2px(float dpValue) {
        float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public void setScheduleData(List<Schedule> mlist) {
        this.mListTimeTable = mlist;
        for (Schedule Schedule : mlist) {
            addTimeName(Schedule.getName());
        }
        initView();
        invalidate();
    }
    public void cleanScheduleData() {
        mHorizontalWeekLayout.removeAllViews();
        mVerticalWeekLaout.removeAllViews();
        this.mListTimeTable = new ArrayList<Schedule>();
        invalidate();
    }
    /**
     * 输入课表名循环判断是否数组存在该课表 如果存在输出true并退出循环 如果不存在则存入colorSt[20]数组
     *
     * @param name
     */
    private void addTimeName(String name) {
        boolean isRepeat = true;
        for (int i = 0; i < 20; i++) {
            if (name.equals(colorStr[i])) {
                isRepeat = true;
                break;
            } else {
                isRepeat = false;
            }
        }
        if (!isRepeat) {
            colorStr[colornum] = name;
            colornum++;
        }
    }

    /**
     * 获取数组中的课程名
     *
     * @param name
     * @return
     */
    public static int getColorNum(String name) {
        int num = 0;
        for (int i = 0; i < 20; i++) {
            if (name.equals(colorStr[i])) {
                num = i;
            }
        }
        return num;
    }
}