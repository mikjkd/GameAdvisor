package com.miche.gameadvisorprova3;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.miche.gameadvisorprova3.Model.AuthenticationClass;
import com.miche.gameadvisorprova3.Model.DatabaseLink;
import com.miche.gameadvisorprova3.View.Adapter.ViewPagerAdapter;
import com.miche.gameadvisorprova3.View.AlertDialog.AlertDialogLogin;
import com.miche.gameadvisorprova3.View.AlertDialog.AlertDialogUtente;
import com.miche.gameadvisorprova3.View.Fragment.GenereFragment;
import com.miche.gameadvisorprova3.View.Fragment.GiochiFragment;


public class MainActivity extends AppCompatActivity  {
    private DatabaseLink archivioMain;
    private AuthenticationClass utenteMain;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private static final String FRAGMENT_GIOCHI = "GIOCHI";
    private static final String FRAGMENT_GENERI = "GENERI";

    private AlertDialogLogin adl;
    private GiochiFragment giochiFragment;
    private GenereFragment genereFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        archivioMain = DatabaseLink.getInstance();

        utenteMain = AuthenticationClass.getInstance(MainActivity.this);

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

        adl = new AlertDialogLogin(MainActivity.this);
        adl.show();
        setupViewPager(viewPager);
    }


    private void setupViewPager(ViewPager viewPager){
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        giochiFragment = new GiochiFragment();
        genereFragment = new GenereFragment();

        adapter.addFragment(giochiFragment,FRAGMENT_GIOCHI);
        adapter.addFragment(genereFragment,FRAGMENT_GENERI);

        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.utente:
                AlertDialogUtente adu = new AlertDialogUtente(MainActivity.this);
                adu.show();
                break;
        }

        return true;
    }
}
