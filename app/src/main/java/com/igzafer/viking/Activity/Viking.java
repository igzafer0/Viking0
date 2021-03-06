package com.igzafer.viking.Activity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.igzafer.viking.Fragment.HomeFragment.Account;
import com.igzafer.viking.Fragment.HomeFragment.Home;
import com.igzafer.viking.Fragment.HomeFragment.Search;
import com.igzafer.viking.Interfaces.IMainResponse;
import com.igzafer.viking.LocalDatabase.LocalDatabase;
import com.igzafer.viking.Model.ErrorModels.ErrorModel;
import com.igzafer.viking.Model.UserDetailModels.myDetailsModel;
import com.igzafer.viking.R;
import com.igzafer.viking.TasarimsalDuzenlemeler.Dialog;
import com.igzafer.viking.api.AuthGerektiren.MyDetails;

import me.ibrahimsn.lib.SmoothBottomBar;
import retrofit2.Response;


public class Viking extends AppCompatActivity {
    SmoothBottomBar bs;
    myDetailsModel myDetails;
    MyDetails my=new MyDetails();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aviking);
        bs=findViewById(R.id.bottom_nav);
        loadFragment(new Home(),1);
        nav();
        my.getDetails(getApplicationContext(), new IMainResponse() {
            @Override
            public <T> void Succsess(Response<T> response) {
                myDetails= (myDetailsModel) response.body();
                LocalDatabase.setUserDetails(getApplicationContext(),myDetails);
            }

            @Override
            public void Error(ErrorModel returnList) {
                try {
                   new Dialog().createDialog(getWindow(),returnList.getBody(),0);
                }catch (Exception e){
                   new Dialog().createDialog(getWindow(),0);
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
                    s_b=1;
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

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                window.setNavigationBarColor(ContextCompat.getColor(this,R.color.bgs));
            }
        }


    }


}