package com.igzafer.viking.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.igzafer.viking.DialogFragment.InternetError;
import com.igzafer.viking.Interfaces.IMainResponse;
import com.igzafer.viking.Model.ErrorModels.ErrorModel;
import com.igzafer.viking.Model.LoginRegisterModels.AuthModel;
import com.igzafer.viking.R;
import com.igzafer.viking.TasarimsalDuzenlemeler.Dialog;
import com.igzafer.viking.TasarimsalDuzenlemeler.LoadinDialog;
import com.igzafer.viking.TasarimsalDuzenlemeler.StatusAndNavbar;
import com.igzafer.viking.api.Auth.Auth;

import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements InternetError.succ{

    Boolean ana_sayfa=true;
    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.amain);

        status_Bar(1);
        ana_sayfa=true;
        //ana sayfa değişkeni eğer anasayfadaysa geri çıkıldığında gerçekten geri çıksın ana sayfada değilse ana sayfaya dönsün diye yapıldı
        loginControl();
    }

    private void loginControl(){
        new Auth().Control(getApplicationContext(), new IMainResponse() {
            @Override
            public <T> void Succsess(Response<T> _response) {
                Intent intent = new Intent(getApplicationContext(), Viking.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void Error(ErrorModel _eresponse) {
                setContentView(R.layout.screenmain);
                Log.d("winter","basarisiz");
                firstScreen();
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
            imageView.setImageDrawable(getApplicationContext().getDrawable(R.drawable.svgmain));
        }
       login=findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                    return;
                }
                setContentView(R.layout.screenlogin);
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
                setContentView(R.layout.screenlogin);
                login_fragment();
            }
        });
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoadinDialog.isVisible(view.getContext(),true);
                AuthModel registerModels= new AuthModel(kadi.getText().toString(),pass.getText().toString(),eposta.getText().toString());
                new Auth().Register(getApplicationContext(), registerModels, new IMainResponse() {
                    @Override
                    public <T> void Succsess(Response<T> _response) {
                        LoadinDialog.isVisible(view.getContext(),false);
                        Intent intent = new Intent(getApplicationContext(), Viking.class);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void Error(ErrorModel _eresponse) {
                        new Dialog().createDialog(getWindow(),_eresponse.getBody(),0);
                        LoadinDialog.isVisible(view.getContext(),false);
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
            AuthModel authModel=new AuthModel(eposta.getText().toString(),pass.getText().toString());
            //Log.d("winter",authModel.getEmail());
            LoadinDialog.isVisible(v.getContext(),true);
            new Auth().Login(getApplicationContext(), authModel, new IMainResponse() {
                @Override
                public <T> void Succsess(Response<T> _response) {
                    LoadinDialog.isVisible(v.getContext(),false);
                    Intent intent = new Intent(getApplicationContext(), Viking.class);
                    startActivity(intent);
                    finish();
                }

                @Override
                public void Error(ErrorModel _eresponse) {
                    new Dialog().createDialog(getWindow(),_eresponse.getBody(),0);
                    LoadinDialog.isVisible(v.getContext(),false);
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