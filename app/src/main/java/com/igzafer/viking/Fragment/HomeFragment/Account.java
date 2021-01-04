package com.igzafer.viking.Fragment.HomeFragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.igzafer.viking.Activity.EditProfile;
import com.igzafer.viking.Activity.ImagePickerActivity;
import com.igzafer.viking.Activity.MainActivity;
import com.igzafer.viking.Interfaces.IMainResponse;
import com.igzafer.viking.LocalDatabase.LocalDatabase;
import com.igzafer.viking.Model.ErrorModels.ErrorModel;
import com.igzafer.viking.Model.UserDetailModels.myDetailsModel;
import com.igzafer.viking.R;
import com.igzafer.viking.RestApi.BaseUrl;
import com.igzafer.viking.TasarimsalDuzenlemeler.Dialog;
import com.igzafer.viking.api.AuthGerektiren.MyDetails;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import me.tankery.lib.circularseekbar.CircularSeekBar;
import retrofit2.Response;


public class Account extends Fragment {

    public static final int REQUEST_IMAGE = 100;
    public Account() {
        // Required empty public constructor
    }
    View view;
    ImageView imageView,more,dog;
    TextView k_adi,bio;
    BottomSheetDialog pp,more_settings;
    CircularSeekBar seekBar;
    Context ctx;
    myDetailsModel myDetails;
    MyDetails my=new MyDetails();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.faccount, container, false);
        ctx = getContext();
        setUpTools();
        //
        LoadFromLocalDatabase();
        ImagePickerActivity.clearCache(requireContext());

        return view;
    }

    public void setUpData(){

        my.getDetails(getContext(), new IMainResponse() {
                    @Override
                    public <T> void Succsess(Response<T> response) {
                        myDetails=(myDetailsModel) response.body();
                        try {

                            Picasso picasso = new Picasso.Builder(getContext()).build();
                            picasso.setLoggingEnabled(true);
                            picasso.load(BaseUrl.pp_Url+myDetails.getAvatar())
                                    .into(imageView, new Callback() {
                                        @Override
                                        public void onSuccess() {

                                            k_adi.setText(myDetails.getNick());
                                            bio.setText(myDetails.getBiyografi());
                                        }

                                        @Override
                                        public void onError(Exception e) {

                                            k_adi.setText(myDetails.getNick());
                                            bio.setText(myDetails.getBiyografi());
                                            Log.d("winter",e.getMessage());
                                        }
                                    });

                        }
                        catch (Exception e){

                            k_adi.setText(myDetails.getNick());
                            bio.setText(myDetails.getBiyografi());
                        }
                    }

                    @Override
                    public void Error(ErrorModel returnList) {
                        try {
                            new Dialog().createDialog(requireActivity().getWindow(), returnList.getBody(), 0);
                        }catch (Exception ignored){

                        }

                    }
                });

    }
    private void LoadFromLocalDatabase() {
        try {

            Picasso picasso = new Picasso.Builder(getContext()).build();
            picasso.setLoggingEnabled(true);
            picasso.load(BaseUrl.pp_Url+LocalDatabase.getUserDetails(getContext()).getAvatar())
                    .into(imageView, new Callback() {
                        @Override
                        public void onSuccess() {
                            k_adi.setText(LocalDatabase.getUserDetails(ctx).getNick());
                            bio.setText(LocalDatabase.getUserDetails(ctx).getBiyografi());
                        }

                        @Override
                        public void onError(Exception e) {

                            k_adi.setText(LocalDatabase.getUserDetails(ctx).getNick());
                            bio.setText(LocalDatabase.getUserDetails(ctx).getBiyografi());
                            Log.d("winter",e.getMessage());
                        }
                    });

        }
        catch (Exception e){
            k_adi.setText(LocalDatabase.getUserDetails(ctx).getNick());
            bio.setText(LocalDatabase.getUserDetails(ctx).getBiyografi());
        }
        setUpData();

    }

    private void setUpTools() {
        imageView = view.findViewById(R.id.pp);
        k_adi = view.findViewById(R.id.k_adi);
        seekBar = view.findViewById(R.id.progress);
        bio=view.findViewById(R.id.bio);
        more = view.findViewById(R.id.more);
        dog = view.findViewById(R.id.dog);
        imageView.setOnClickListener(v -> {
            try {
                View sheetView = getActivity().getLayoutInflater().inflate(R.layout.bottomsheetpp, null);
                pp = new BottomSheetDialog(getContext());
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
                    }
                });
                galeri.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        launchGalleryIntent();
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
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        more.setOnClickListener(v -> {
            try {
                //dialog ayarları falan
                final View sheetView = getActivity().getLayoutInflater().inflate(R.layout.bottomsheetmoresettings, null);
                more_settings = new BottomSheetDialog(getContext());
                more_settings.setContentView(sheetView);
                more_settings.show();
                //kontrollerlar
                LinearLayout exit = sheetView.findViewById(R.id.exit);
                LinearLayout close = sheetView.findViewById(R.id.kapat);
                LinearLayout edit_profile= sheetView.findViewById(R.id.edit_profile);
                //click eventleri
                edit_profile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getContext(), EditProfile.class);
                        startActivityForResult(intent,101);


                    }
                });
                exit.setOnClickListener(v1 -> {
                        LocalDatabase.Logout(getContext());
                        Intent intent = new Intent(getContext(), MainActivity.class);
                        startActivity(intent);
                        getActivity().finish();

                    });
                close.setOnClickListener(v12 -> more_settings.dismiss());
                FrameLayout bottomSheet = more_settings.findViewById(R.id.design_bottom_sheet);
                assert bottomSheet != null;
                bottomSheet.setBackground(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });


    }
    private void launchCameraIntent() {
        Intent intent = new Intent(getContext(), ImagePickerActivity.class);
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
        Intent intent = new Intent(getContext(), ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_GALLERY_IMAGE);

        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);
        startActivityForResult(intent, REQUEST_IMAGE);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                Uri uri = data.getParcelableExtra("path");
                updatePp(uri);
            }
        }else if(requestCode==101){
            more_settings.dismiss();
            setUpData();

        }
    }
    private void updatePp(Uri uri){
        pp.dismiss();

        my.changeProfilePicture(getContext(), uri, new IMainResponse() {
            @Override
            public <T> void Succsess(Response<T> response) {
                setUpData();
            }

            @Override
            public void Error(ErrorModel returnList) {
                try {
                    new Dialog().createDialog(requireActivity().getWindow(),returnList.getBody(),0);
                }catch (Exception e){
                    new Dialog().createDialog(requireActivity().getWindow(),0);
                }
            }
        });

    }

}