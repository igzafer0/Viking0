package com.igzafer.viking.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.igzafer.viking.Model.ErrorModels.ErrorModel;
import com.igzafer.viking.Model.UserDetailModels.myDetailsModel;
import com.igzafer.viking.R;
import com.igzafer.viking.RestApi.BaseUrl;
import com.igzafer.viking.TasarimsalDuzenlemeler.Dialog;
import com.igzafer.viking.TasarimsalDuzenlemeler.LoadinDialog;
import com.igzafer.viking.TasarimsalDuzenlemeler.StatusAndNavbar;

import com.igzafer.viking.api.AuthGerektiren.UpdateMyDetails;
import com.igzafer.viking.api.AuthGerektiren.UpdateMyDetailsInterface;
import com.igzafer.viking.api.AuthGerektiren.UpdateMyPp;
import com.igzafer.viking.api.AuthGerektiren.getMyDetails;
import com.igzafer.viking.api.AuthGerektiren.getMyDetailsInterface;
import com.squareup.picasso.Callback;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import okhttp3.OkHttpClient;

public class EditProfile extends AppCompatActivity {
    ImageView pps,onay,cancel;
    EditText ad,soyad,kadi,bio,email;
    String pp_link;
    LinearLayout pp_change;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fedit_profile);
        status_Bar();
        settools();
        LoadinDialog.isVisible(this,true);
        getData();
    }
    private void status_Bar() {
        StatusAndNavbar.setColor(getApplicationContext(),getWindow(),R.color.bgs,R.color.bgs);
    }
    private static final String TAG = MainActivity.class.getSimpleName();
    public static final int REQUEST_IMAGE = 100;
    private void settools(){
        ad=findViewById(R.id.ad);
        soyad=findViewById(R.id.soyad);
        kadi=findViewById(R.id.kadi);
        bio=findViewById(R.id.bio);
        email=findViewById(R.id.email);
        pps = findViewById(R.id.pp);
        cancel=findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        onay=findViewById(R.id.okay);
        onay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadinDialog.isVisible(v.getContext(), true);
                updateData();
            }
        });
        pp_change=findViewById(R.id.pp_change);
        pp_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("winter","ss");
                try {

                    View sheetView = LayoutInflater.from(EditProfile.this).inflate(R.layout.bottom, null);
                    BottomSheetDialog pp = new BottomSheetDialog(EditProfile.this);
                    pp.setContentView(sheetView);
                    pp.show();
                    // Remove default white color background
                    LinearLayout kamera=sheetView.findViewById(R.id.kamera);
                    LinearLayout galeri=sheetView.findViewById(R.id.galeri);
                    LinearLayout kapat=sheetView.findViewById(R.id.kapat);
                    kamera.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            launchCameraIntent();
                            pp.dismiss();
                        }
                    });
                    galeri.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            launchGalleryIntent();
                            pp.dismiss();
                        }
                    });
                    kapat.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            pp.dismiss();
                        }
                    });
                    FrameLayout bottomSheet = pp.findViewById(R.id.design_bottom_sheet);
                    bottomSheet.setBackground(null);
                }catch (Exception e){
                 Log.d("winter",e.getMessage());
                }

            }
        });


    }
    private void launchCameraIntent() {
        Intent intent = new Intent(getApplicationContext(), ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_IMAGE_CAPTURE);

        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);

        // setting maximum bitmap width and height
        intent.putExtra(ImagePickerActivity.INTENT_SET_BITMAP_MAX_WIDTH_HEIGHT, true);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_WIDTH, 1000);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_HEIGHT, 1000);

        startActivityForResult(intent, REQUEST_IMAGE);
    }
    private void launchGalleryIntent() {
        Intent intent = new Intent(getApplicationContext(), ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_GALLERY_IMAGE);

        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);
        startActivityForResult(intent, REQUEST_IMAGE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                if(data!=null){
                    Uri uri = data.getParcelableExtra("path");
                    updatePp(uri);
                }

            }
        }

    }
    UpdateMyPp updateMyPp;
    private void updatePp(Uri uri){
        updateMyPp.update(getApplicationContext(), uri, new UpdateMyDetailsInterface() {
            @Override
            public void result(Boolean succsess, ErrorModel errorModel) {
                if(succsess){
                    finish();
                }else{
                    Dialog.createDialog(getWindow(),errorModel.getIslem(),errorModel.getHata(),0);
                }
            }
        });


    }

    private void getData(){
        getMyDetails.get(getApplicationContext(), new getMyDetailsInterface() {
            @Override
            public void result(Boolean succsess, myDetailsModel myDetails, ErrorModel errorModel) {
                if(succsess){
                    try {
                        Picasso picasso = new Picasso.Builder(getApplicationContext()).build();
                        picasso.setLoggingEnabled(true);
                        picasso.load(BaseUrl.pp_Url+myDetails.getAvatar())
                                .into(pps, new Callback() {
                                    @Override
                                    public void onSuccess() {
                                        if(myDetails.getAd()!=null){
                                            ad.setText(myDetails.getAd());
                                        }
                                        if(myDetails.getSoyad()!=null){
                                            soyad.setText(myDetails.getSoyad());
                                        }
                                        kadi.setText(myDetails.getNick());
                                        email.setText(myDetails.getEmail());
                                        if(myDetails.getBiyografi()!=null){
                                            bio.setText(myDetails.getBiyografi());
                                        }
                                        LoadinDialog.isVisible(getApplicationContext(),false);
                                    }

                                    @Override
                                    public void onError(Exception e) {
                                        if(myDetails.getAd()!=null){
                                            ad.setText(myDetails.getAd());
                                        }
                                        if(myDetails.getSoyad()!=null){
                                            soyad.setText(myDetails.getSoyad());
                                        }
                                        kadi.setText(myDetails.getNick());
                                        email.setText(myDetails.getEmail());
                                        if(myDetails.getBiyografi()!=null){
                                            bio.setText(myDetails.getBiyografi());
                                        }
                                        LoadinDialog.isVisible(getApplicationContext(),false);
                                        Log.d("winter",e.getMessage());
                                    }
                                });

                    }
                    catch (Exception e){
                        if(myDetails.getAd()!=null){
                            ad.setText(myDetails.getAd());
                        }
                        if(myDetails.getSoyad()!=null){
                            soyad.setText(myDetails.getSoyad());
                        }
                        kadi.setText(myDetails.getNick());
                        email.setText(myDetails.getEmail());
                        if(myDetails.getBiyografi()!=null){
                            bio.setText(myDetails.getBiyografi());
                        }
                        LoadinDialog.isVisible(getApplicationContext(),false);
                    }
                }else {
                    Dialog.createDialog(getWindow(),errorModel.getHata(),errorModel.getIslem(),0);
                }
            }
        });

    }
    private void updateData() {
        LoadinDialog.isVisible(getApplicationContext(),true);
        myDetailsModel myDetailsModel=new myDetailsModel(kadi.getText().toString(),ad.getText().toString(),soyad.getText().toString(),email.getText().toString(),null,bio.getText().toString(),0);
        UpdateMyDetails.update(getApplicationContext(), myDetailsModel, new UpdateMyDetailsInterface() {
            @Override
            public void result(Boolean succsess, ErrorModel errorModel) {
                if(succsess){
                    LoadinDialog.isVisible(getApplicationContext(),false);
                    finish();
                }else{
                    try {
                        Dialog.createDialog(getWindow(),errorModel.getIslem(),errorModel.getHata(),0);
                    }catch (Exception e){
                        Dialog.createDialog(getWindow(),"Hata","Geçersiz karakter bulundu",0);
                    }
                    LoadinDialog.isVisible(getApplicationContext(),false);

                }

            }
        });
    }
}
