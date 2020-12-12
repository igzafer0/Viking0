package com.igzafer.viking.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;


import com.igzafer.viking.Fragment.HomeFragment.Account;
import com.igzafer.viking.Fragment.HomeFragment.Home;
import com.igzafer.viking.Fragment.HomeFragment.Search;
import com.igzafer.viking.LocalDatabase.LocalDatabase;
import com.igzafer.viking.Model.ErrorModels.ErrorModel;
import com.igzafer.viking.Model.UserDetailModels.myDetailsModel;
import com.igzafer.viking.R;
import com.igzafer.viking.amaleler.Dialog;
import com.igzafer.viking.api.AuthGerektiren.getMyDetails;
import com.igzafer.viking.api.AuthGerektiren.getMyDetailsInterface;

import me.ibrahimsn.lib.SmoothBottomBar;


public class Viking extends AppCompatActivity {
    SmoothBottomBar bs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viking);
        bs=findViewById(R.id.bottom_nav);
        loadFragment(new Home(),1);
        nav();
        getMyDetails.get(getApplicationContext(), new getMyDetailsInterface() {
            @Override
            public void result(Boolean succsess, myDetailsModel myDetails, ErrorModel errorModel) {
                if(succsess){
                    LocalDatabase.setUserDetails(getApplicationContext(),myDetails);
                }else{
                    Dialog.createDialog(getWindow(),"Hata","Hata, Kullanıcı bilgileriniz alınamadı.",0);
                }
            }
        });
    }
    int s_b=1;
    private void nav() {
        bs.setOnItemSelectedListener(i -> {
            Fragment fragment = null;
            switch (i) {
                case 0:
                    Log.d("winter","ss");
                    fragment = new Home();
                    s_b=1;
                    break;
                case 1:
                    fragment = new Search();
                    break;
                case 2:
                    fragment = new Account();
                    s_b=1;
                    break;
            }
            return loadFragment(fragment,s_b);
        });

    }
    private boolean loadFragment(Fragment fragment,int s_b) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame, fragment)
                    .commit();
            status_Bar(s_b);
            return true;
        }
        return false;
    }

    private void status_Bar(int mod) {
        Window window=this.getWindow();

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        if (mod==1){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                window.setNavigationBarColor(ContextCompat.getColor(this,R.color.bgs));
            }
        }
        else if(mod==2) {


            window.setNavigationBarColor(ContextCompat.getColor(this, R.color.bgs));

        }


    }


}