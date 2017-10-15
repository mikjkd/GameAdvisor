package com.miche.gameadvisorprova3;
import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.miche.gameadvisorprova3.Model.DatabaseLinkParcel;
import com.miche.gameadvisorprova3.View.GenereFragment;
import com.miche.gameadvisorprova3.View.GiochiFragment;

import static com.miche.gameadvisorprova3.R.id.AccediBtn;
import static com.miche.gameadvisorprova3.R.id.etEmail;
import static com.miche.gameadvisorprova3.R.id.etPsw;
import static com.miche.gameadvisorprova3.R.id.utente;

public class MainActivity extends AppCompatActivity {
    private transient DatabaseLinkParcel archivioMain  = new DatabaseLinkParcel();
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Bundle argBundle = new Bundle();
    private final String EXTRA_GIOCHI = "GIOCHI";
    private final String EXTRA_GENERI = "GENERI";
    private AlertDialogLogin adl;


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
        adl = new AlertDialogLogin(MainActivity.this);
        adl.show();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.utente:
                View vitem = this.findViewById(R.id.utente);
                PopupMenu popup = new PopupMenu(this,vitem);
                popup.getMenuInflater().inflate(R.menu.popupmenu, popup.getMenu());

                popup.getMenu().add("wewe");
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        Toast.makeText(MainActivity.this,"You Clicked : " + item.getTitle(),Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
                popup.show();
                break;
        }

        return true;
    }
}
