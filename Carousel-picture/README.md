---
title: Android 轮播图的实现
date: 2016-10-17 10:22:47
tags: Android原创
---

## 开始之前

> 这是我的第一篇简书博客, 自己也在其他的博客网站写过 例如: [CSDN](http://blog.csdn.net/u013144863?viewmode=contents) ,  当然还有我自己的小站 [鹿鹿的博客](https://changer0.github.io/) 里面的博客质量参差不齐, 而且有很多内容没有"自主"知识, 所以心里没有什么成就感. 自己是比较喜欢简书中的 "简"字, 推荐大家多多使用和交流, 我会不定期发自己的一些有关Android功能点的实现, 望大家多多关注. 好, 废话说得太多了, 开始我们今天的内容


## 环境准备

### 开发环境
- Android Studio 2.2.1
- JDK1.7 
- API 24
- Gradle 2.2.1


## 开发开始

### 先上效果预览


![效果预览](http://upload-images.jianshu.io/upload_images/3118842-622aa62e18a996ce.gif?imageMogr2/auto-orient/strip)


### 案例分析

这个案例网上也很多, 质量参差不齐, 我也就根据自己的理解来分析分析需要实现的几个功能点: 

- 轮播图有n张图片和相对应的n个小圆点(指示器 indicator) 实现联动
- 除了可以手动滑动外, 也可以自动滚动(轮播) 可以考虑使用Handler实现
- 实现无限轮回滚动 
- 当手指按下图片后不再自动滚动

> 根据上述分析进行开发

### 接下来搭建布局


``` xml

**activity_main.xml**

<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/activity_main"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context="com.lulu.shufflingpicdemo.MainActivity">

    <LinearLayout
        android:orientation="vertical"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_alignParentBottom="true"
        android:layout_centerHorizontal="true"
        android:layout_marginBottom="275dp">
        <FrameLayout

            android:layout_width="match_parent"
            android:layout_height="220dp">
            <!--轮播图位置-->
            <android.support.v4.view.ViewPager
                android:id="@+id/live_view_pager"
                android:layout_width="match_parent"
                android:layout_height="match_parent"/>
            <!--右下角小圆点-->
            <LinearLayout
                android:layout_marginRight="5dp"
                android:layout_gravity="bottom|right"
                android:id="@+id/live_indicator"
                android:orientation="horizontal"
                android:layout_width="wrap_content"
                android:layout_height="10dp"/>
        </FrameLayout>
    </LinearLayout>
</RelativeLayout>


```

> 指示器 小点绘制文件

**indicator_select.xml**

``` xml
<?xml version="1.0" encoding="utf-8"?>
<shape xmlns:android="http://schemas.android.com/apk/res/android"
       android:shape="oval">
    <size
        android:width="20dp"
        android:height="20dp"/>
    <solid android:color="#c213b7"/>
</shape>

```

**indicator_no_select.xml**

``` xml

<?xml version="1.0" encoding="utf-8"?>
<shape xmlns:android="http://schemas.android.com/apk/res/android"
       android:shape="oval">
    <size
        android:width="20dp"
        android:height="20dp"/>
    <solid android:color="#fff"/>
</shape>

```



### ViewPager的实现


**MyPagerAdapter.java**

``` java

public class MyPagerAdapter extends PagerAdapter {

    public static final int MAX_SCROLL_VALUE = 10000;

    private List<ImageView> mItems;
    private Context mContext;
    private LayoutInflater mInflater;

    public MyPagerAdapter(List<ImageView> items, Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mItems = items;
    }

    /**
     * @param container
     * @param position
     * @return 对position进行求模操作
     * 因为当用户向左滑时position可能出现负值，所以必须进行处理
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View ret = null;

        //对ViewPager页号求摸取出View列表中要显示的项
        position %= mItems.size();
        Log.d("Adapter", "instantiateItem: position: " + position);
        ret = mItems.get(position);
        //如果View已经在之前添加到了一个父组件，则必须先remove，否则会抛出IllegalStateException。
        ViewParent viewParent = ret.getParent();
        if (viewParent != null) {
            ViewGroup parent = (ViewGroup) viewParent;
            parent.removeView(ret);
        }
        container.addView(ret);

        return ret;
    }
    /**
     * 由于我们在instantiateItem()方法中已经处理了remove的逻辑，
     * 因此这里并不需要处理。实际上，实验表明这里如果加上了remove的调用，
     * 则会出现ViewPager的内容为空的情况。
     *
     * @param container
     * @param position
     * @param object
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //警告:不要在这里调用removeView, 已经在instantiateItem中处理了
    }


    @Override
    public int getCount() {
        int ret = 0;
        if (mItems.size() > 0) {
            ret = MAX_SCROLL_VALUE;
        }
        return ret;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == (View) object;
    }
}

```

> Note: 一定不要在destroyItem中再调用removeView了, 因为咱们已经instantiateItem中做了处理



**在MainActivity.java中给ViewPager设置Adapter**


``` java
mItems = new ArrayList<>();
mViewPager.setAdapter(mAdapter);

addImageView();
mAdapter.notifyDataSetChanged();

```

``` java

private void addImageView(){
    ImageView view0 = new ImageView(this);
    view0.setImageResource(R.mipmap.pic0);
    ImageView view1 = new ImageView(this);
    view1.setImageResource(R.mipmap.pic1);
    ImageView view2 = new ImageView(this);
    view2.setImageResource(R.mipmap.pic2);

    view0.setScaleType(ImageView.ScaleType.CENTER_CROP);
    view1.setScaleType(ImageView.ScaleType.CENTER_CROP);
    view2.setScaleType(ImageView.ScaleType.CENTER_CROP);

    mItems.add(view0);
    mItems.add(view1);
    mItems.add(view2);

}

```

> Note: 因为咱们做的是Demo, 所以我们传入的是一个ImageView的集合, 真正开发时, 需要传入含有图片url的实体类, 在Adapter中可以使用加载图片的类库加载


### 实现右下角指示器

**添加指示器**

在onCreate中添加

``` java
//获取指示器(下面三个小点)
mBottomLiner = (LinearLayout) findViewById(R.id.live_indicator);
//右下方小圆点
mBottomImages = new ImageView[mItems.size()];
for (int i = 0; i < mBottomImages.length; i++) {
    ImageView imageView = new ImageView(this);
    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(20, 20);
    params.setMargins(5, 0, 5, 0);
    imageView.setLayoutParams(params);
    //如果当前是第一个 设置为选中状态
    if (i == 0) {
        imageView.setImageResource(R.drawable.indicator_select);
    } else {
        imageView.setImageResource(R.drawable.indicator_no_select);
    }
    mBottomImages[i] = imageView;
    //添加到父容器
    mBottomLiner.addView(imageView);
}

```

### 实现联动

添加ViewPager的监听事件, 实现ViewPager.OnPageChangeListener接口

``` java

mViewPager.addOnPageChangeListener(this);

```

回调事件


``` java


///////////////////////////////////////////////////////////////////////////
// ViewPager的监听事件
///////////////////////////////////////////////////////////////////////////
@Override
public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

}

@Override
public void onPageSelected(int position) {

    currentViewPagerItem = position;
    if (mItems != null) {
        position %= mBottomImages.length;
        int total = mBottomImages.length;

        for (int i = 0; i < total; i++) {
            if (i == position) {
                mBottomImages[i].setImageResource(R.drawable.indicator_select);
            } else {
                mBottomImages[i].setImageResource(R.drawable.indicator_no_select);
            }
        }
    }
}

@Override
public void onPageScrollStateChanged(int state) {

}


```


### 实现自动滚动


在mBottomImages初始化之后 开启一个线程 进行定时发送一个空消息给Handler处理, 由Handler决定切换到下一页

``` java

//让其在最大值的中间开始滑动, 一定要在 mBottomImages初始化之前完成
int mid = MyPagerAdapter.MAX_SCROLL_VALUE / 2;
mViewPager.setCurrentItem(mid);
currentViewPagerItem = mid;

//定时发送消息
mThread = new Thread(){
    @Override
    public void run() {
        super.run();
        while (true) {
            mHandler.sendEmptyMessage(0);
            try {
                Thread.sleep(MainActivity.VIEW_PAGER_DELAY);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
};
mThread.start();

```

自定义Handler 

``` java

///////////////////////////////////////////////////////////////////////////
// 为防止内存泄漏, 声明自己的Handler并弱引用Activity
///////////////////////////////////////////////////////////////////////////
private static class MyHandler extends Handler {
    private WeakReference<MainActivity> mWeakReference;

    public MyHandler(MainActivity activity) {
        mWeakReference = new WeakReference<MainActivity>(activity);
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        switch (msg.what) {
            case 0:
                MainActivity activity = mWeakReference.get();
                if (activity.isAutoPlay) {

                    activity.mViewPager.setCurrentItem(++activity.currentViewPagerItem);
                }

                break;
        }

    }
}

```

> Note: 其中isAutoPlay是一个用来判断当前是否是自动轮播的boolean值变量, 主要用于实现我们接下来说的 **当手指按下图片后不再滚动**


### 实现当手指按下图片后不再滚动

思路: 我们可以考虑对ViewPager的触摸事件进行监听, 然后设置一个上节说到的isAutoPlay的boolean变量用来让Handler判断是否进行轮播滚动

代码实现: 

ViewPager设置监听

``` java
mViewPager.setOnTouchListener(this);
```

事件回调

``` java
@Override
public boolean onTouch(View v, MotionEvent event) {
    int action = event.getAction();
    switch (action) {
        case MotionEvent.ACTION_DOWN:
            isAutoPlay = false;
            break;
        case MotionEvent.ACTION_UP:
            isAutoPlay = true;
            break;
    }
    return false;
}

```


> **注: ** 细心的同学可能会看出来, 我们没有单独说 无限循环 如何实现, 其实, 它的实现已经隐藏在了代码中, 在这里我简单的说一下思路: 
> 给ViewPager的条目个数设置个较大值, 该案例中为10000, 然后我们刚进入时选中的位置为 10000/2=5000, 也就是说我们可以向左或向右滑动约5000多张图片, 但这是不现实的, 所以就给用户造成了无限循环的假象


希望大家多多支持