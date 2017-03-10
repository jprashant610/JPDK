package com.jp.carpool.Helpers;

        import android.content.Context;
        import android.graphics.Color;
        import android.graphics.drawable.ColorDrawable;

        import com.baoyz.swipemenulistview.SwipeMenu;
        import com.baoyz.swipemenulistview.SwipeMenuCreator;
        import com.baoyz.swipemenulistview.SwipeMenuItem;
        import com.baoyz.swipemenulistview.SwipeMenuLayout;
        import com.baoyz.swipemenulistview.SwipeMenuListView;

/**
 * Created by dk on 3/10/17.
 */

public class SwipperHelper implements SwipeMenuCreator {
    public static int count;

    SwipeMenu Swprmenu;
    private Context appContext;

    public SwipperHelper(Context context) {
        appContext = context;
        count=0;
    }

    @Override
    public void create(SwipeMenu menu) {
        //create an action that will be showed on swiping an item in the list
        Swprmenu = menu;
        switch (menu.getViewType()) {
            case 0: //Menutype 1 for Others post
                   //create menu of type 0//
                    SwipeMenuItem item1 = new SwipeMenuItem(getApplicationContext());
                    item1.setBackground(new ColorDrawable(Color.parseColor("#00bcd4")));
                    // set width of an option (px)
                    item1.setWidth(200);
                    item1.setTitle("Confirm");
                    item1.setTitleSize(18);
                    item1.setTitleColor(Color.WHITE);
                    menu.addMenuItem(item1);

                    SwipeMenuItem item2 = new SwipeMenuItem(
                    getApplicationContext());
                    // set item background
                    item2.setBackground(new ColorDrawable(Color.parseColor("#03a9f4")));
                    item2.setWidth(200);
                    item2.setTitle("Call");
                    item2.setTitleSize(18);
                    item2.setTitleColor(Color.WHITE);
                    menu.addMenuItem(item2);

                break;
            case 1: //Menu type for Self Post
                // create menu of type 1
                SwipeMenuItem item3 = new SwipeMenuItem(getApplicationContext());
                item3.setBackground(new ColorDrawable(Color.parseColor("#AED581")));
                // set width of an option (px)
                item3.setWidth(200);
                item3.setTitle("Edit");
                item3.setTitleSize(18);
                item3.setTitleColor(Color.WHITE);
                menu.addMenuItem(item3);

                SwipeMenuItem item4 = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                item4.setBackground(new ColorDrawable(Color.parseColor("#e57373")));
                item4.setWidth(200);
                item4.setTitle("Delete");
                item4.setTitleSize(18);
                item4.setTitleColor(Color.WHITE);
                menu.addMenuItem(item4);
                break;

        }
    }



    public Context getApplicationContext() {
        return appContext;
    }
}
