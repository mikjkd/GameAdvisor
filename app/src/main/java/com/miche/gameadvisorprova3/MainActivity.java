package com.miche.gameadvisorprova3;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.miche.gameadvisorprova3.Model.DataUtente;
import com.miche.gameadvisorprova3.Model.DatabaseLinkParcel;
import com.miche.gameadvisorprova3.View.AlertDialog.AlertDialogLogin;
import com.miche.gameadvisorprova3.View.AlertDialog.AlertDialogUtente;
import com.miche.gameadvisorprova3.View.Fragment.GenereFragment;
import com.miche.gameadvisorprova3.View.Fragment.GiochiFragment;


public class MainActivity extends AppCompatActivity implements GiochiFragment.UtenteUpdate, GenereFragment.UtenteUpdateG {
    private transient DatabaseLinkParcel archivioMain  = new DatabaseLinkParcel();
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Bundle argBundle = new Bundle();
    private static final String EXTRA_GIOCHI = "GIOCHI";
    private static final String EXTRA_GENERI = "GENERI";
    private static final String Autenticazione = "AUTENTICAZIONE";
    private static final String Prefezente = "authPref";
    private static final String EXTRA_ARCHVIO = "ARCHIVIO";
    private static final String EXTRA_UTENTE = "UTENTE";
    private AlertDialogLogin adl;
    private DataUtente utente;
    private GiochiFragment giochiFragment;
    private GenereFragment genereFragment;
    @Override
    public void utenteUpdate(DataUtente utente) {
        this.utente = utente;
        giochiFragment.AggiornaUtente(this.utente);
        genereFragment.AggiornaUtente(this.utente);
    }

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
        utente = new DataUtente();
        SharedPreferences settings = MainActivity.this.getSharedPreferences(Autenticazione, 0);
        utente.setAutenticated(settings.getBoolean(Prefezente, false));
        adl = new AlertDialogLogin(MainActivity.this,utente);
        adl.show();
        argBundle.putParcelable(EXTRA_ARCHVIO,archivioMain);
        argBundle.putSerializable(EXTRA_UTENTE,utente);
        setupViewPager(viewPager,argBundle);
    }


    private void setupViewPager(ViewPager viewPager,Bundle dati){
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        giochiFragment = new GiochiFragment();
        genereFragment = new GenereFragment();


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
                AlertDialogUtente adu = new AlertDialogUtente(MainActivity.this,utente);
                adu.show();
                break;
        }

        return true;
    }
}
