package com.igzafer.viking.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;


import com.igzafer.viking.DialogFragment.InternetError;
import com.igzafer.viking.Model.ErrorModels.ErrorModel;
import com.igzafer.viking.Model.LoginRegisterModels.LoginModel;
import com.igzafer.viking.Model.LoginRegisterModels.RegisterModel;
import com.igzafer.viking.R;
import com.igzafer.viking.TasarimsalDuzenlemeler.Dialog;
import com.igzafer.viking.TasarimsalDuzenlemeler.LoadinDialog;
import com.igzafer.viking.TasarimsalDuzenlemeler.StatusAndNavbar;
import com.igzafer.viking.api.LoginRegister.TokenControl;
import com.igzafer.viking.api.LoginRegister.TokenControlInterface;
import com.igzafer.viking.api.LoginRegister.Login;
import com.igzafer.viking.api.LoginRegister.LoginInterface;
import com.igzafer.viking.api.LoginRegister.Register;
import com.igzafer.viking.api.LoginRegister.RegisterInterface;

public class MainActivity extends AppCompatActivity implements InternetError.succ{
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Boolean ana_sayfa=true;
    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = preferences.edit();
        status_Bar(1);
        ana_sayfa=true;
        //ana sayfa değişkeni eğer anasayfadaysa geri çıkıldığında gerçekten geri çıksın ana sayfada değilse ana sayfaya dönsün diye yapıldı
        loginControl();
    }

    private void loginControl(){
        TokenControl.LoginControl(getApplicationContext(), new TokenControlInterface() {
            @Override
            public void LoginSuccsess(Boolean success) {
                if(success){
                    Intent intent = new Intent(getApplicationContext(), Viking.class);
                    startActivity(intent);
                    finish();
                }else {
                    setContentView(R.layout.floginorsign);
                    Log.d("winter","basarisiz");
                    firstScreen();
                }
            }
        });
    }
    private void status_Bar(int mod) {
        if (mod==1){
            StatusAndNavbar.setColor(getApplicationContext(),getWindow(),R.color.colorBlack,R.color.colorBlack);
        }
        else if(mod==2){
            StatusAndNavbar.setColor(getApplicationContext(),getWindow(),R.color.bgs,R.color.bgs);
        }
    }
    Button login,register;
    private long mLastClickTime = 0;
    ImageView imageView;
    private void firstScreen(){
        status_Bar(2);
        imageView=findViewById(R.id.im1);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            imageView.setImageDrawable(getApplicationContext().getDrawable(R.drawable.loginsvg));
        }
       login=findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                    return;
                }
                setContentView(R.layout.flogin);
                ana_sayfa=false;
                login_fragment();

            }
        });
        register=findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setContentView(R.layout.fregister);
                ana_sayfa=false;
                register_fragment();
            }
        });

    }
    private void register_fragment(){
        StatusAndNavbar.setColor(getApplicationContext(),getWindow(),R.color.bgs,R.color.bgs);
        ImageView back;
        Button reg,login;
        reg=findViewById(R.id.regi);
        login=findViewById(R.id.login);
        EditText kadi,pass,eposta;
        kadi=findViewById(R.id.kadi);
        eposta=findViewById(R.id.email);
        pass=findViewById(R.id.pass);
        back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();

            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.flogin);
                login_fragment();
            }
        });
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoadinDialog.isVisible(view.getContext(),true);
                RegisterModel registerModels= new RegisterModel(eposta.getText().toString(),pass.getText().toString(),kadi.getText().toString());
                Register.register(getApplicationContext(), registerModels, new RegisterInterface() {
                    @Override
                    public void registerResponse(Boolean succsess, ErrorModel errorModel) {
                        if(succsess){
                            LoadinDialog.isVisible(view.getContext(),false);
                            Intent intent = new Intent(getApplicationContext(), Viking.class);
                            startActivity(intent);
                            finish();
                        }else{
                            Dialog.createDialog(getWindow(),errorModel.getIslem(),errorModel.getHata(),0);
                            LoadinDialog.isVisible(view.getContext(),false);
                        }
                    }

                });

            }
        });

    }


    private void login_fragment(){
        StatusAndNavbar.setColor(getApplicationContext(),getWindow(),R.color.bgs,R.color.bgs);
        ImageView back;
        Button reg,login;
        EditText pass,eposta;
        back=findViewById(R.id.back);
        reg=findViewById(R.id.regi);
        login=findViewById(R.id.login);
        reg.setOnClickListener(v -> {
            setContentView(R.layout.fregister);
            register_fragment();
        });
        eposta=findViewById(R.id.email);
        pass=findViewById(R.id.pass);
        imageView=findViewById(R.id.im1);
        back.setOnClickListener(v -> {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                });
        login.setOnClickListener(v -> {
            LoginModel loginModel=new LoginModel(eposta.getText().toString(),pass.getText().toString());
            LoadinDialog.isVisible(v.getContext(),true);
            Login.login(getApplicationContext(), loginModel, new LoginInterface() {
                @Override
                public void LoginResponse(Boolean succsess, ErrorModel errorModel) {
                    if(succsess){
                        LoadinDialog.isVisible(v.getContext(),false);
                        Intent intent = new Intent(getApplicationContext(), Viking.class);
                        startActivity(intent);
                        finish();
                    }else {
                        Dialog.createDialog(getWindow(),errorModel.getIslem(),errorModel.getHata(),0);
                        LoadinDialog.isVisible(v.getContext(),false);
                    }
                }

            });
        });
    }


    @Override
    public void onBackPressed() {
        if(ana_sayfa){
            super.onBackPressed();
        }else{
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void success() {
        loginControl();
    }

}