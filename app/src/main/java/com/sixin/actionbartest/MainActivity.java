package com.sixin.actionbartest;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.sixin.actionbartest.indicator.TabPageIndicator;
import com.sixin.actionbartest.permissionsutil.PermissionsDenied;
import com.sixin.actionbartest.permissionsutil.PermissionsGranted;
import com.sixin.actionbartest.permissionsutil.PermissionsNoNeeded;
import com.sixin.actionbartest.permissionsutil.PermissionsUtil;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    // TODO: 2017/12/25    返回栈源码分析  commit 与commitAllow...()源码分析
    // TODO: 2017/12/29    activity.onPause()  onStop()源码分析
    // TODO: 2017/12/29  碎片中onCreateOptions() 源码分析
    private static final String TAG = MainActivity.class.getName();
    private ViewPager viewPager;
    private ArrayList<String> data ;
    private TabPageIndicator indicator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        PermissionsUtil.requestPermissions(this,
//                100,
//                Manifest.permission.READ_EXTERNAL_STORAGE,
//                Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                Manifest.permission.READ_CALENDAR);
        data = new ArrayList<>();
        for(int i = 0 ; i < 10 ; i++){
            data.add("第" + i + "个");
        }
        indicator = (TabPageIndicator) findViewById(R.id.indicator);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));
        indicator.setViewPager(viewPager);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }



    @PermissionsGranted
    private void doSomething() {
        Toast.makeText(this, "000000", Toast.LENGTH_SHORT).show();
    }

    @PermissionsDenied
    private void doSomething1(List<String> permissions){
        Toast.makeText(this, "222222...."+permissions.size(), Toast.LENGTH_SHORT).show();
    }

    @PermissionsNoNeeded
    private void doSomething2(){
        Toast.makeText(this, "333333", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

   class MyAdapter extends FragmentStatePagerAdapter{

       public MyAdapter(FragmentManager fm) {
           super(fm);
       }

       @Override
       public Fragment getItem(int position) {
           return BlankFragment.newInstance();
       }

       @Override
       public int getCount() {
           return data.size();
       }

       @Override
       public CharSequence getPageTitle(int position) {
           return data.get(position);
       }


   }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionsUtil.onRequestPermissionsResult(this,requestCode,
                permissions,grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
