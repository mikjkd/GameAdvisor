package com.miche.gameadvisorprova3;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.miche.gameadvisorprova3.Model.DatabaseLinkParcel;
import com.miche.gameadvisorprova3.View.GenereFragment;
import com.miche.gameadvisorprova3.View.GiochiFragment;

public class MainActivity extends AppCompatActivity {
    private transient DatabaseLinkParcel archivioMain  = new DatabaseLinkParcel();
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Bundle argBundle = new Bundle();
    private final String EXTRA_GIOCHI = "GIOCHI";
    private final String EXTRA_GENERI = "GENERI";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(2);

        tabLayout= (TabLayout) findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }

            @Override
            public void onPageSelected(int position) { }

            @Override
            public void onPageScrollStateChanged(int state) { }
        });

        archivioMain.logInAnonimo();
        argBundle.putParcelable("ARCHIVIO",archivioMain);
        setupViewPager(viewPager,argBundle);
    }

    private void setupViewPager(ViewPager viewPager,Bundle dati){
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        GiochiFragment giochiFragment = new GiochiFragment();
        GenereFragment genereFragment = new GenereFragment();


        giochiFragment.setArguments(dati);
        genereFragment.setArguments(dati);

        adapter.addFragment(giochiFragment,EXTRA_GIOCHI);
        adapter.addFragment(genereFragment,EXTRA_GENERI);

        viewPager.setAdapter(adapter);
    }
}
