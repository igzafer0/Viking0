package com.igzafer.viking.DialogFragment;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.github.ybq.android.spinkit.SpinKitView;
import com.igzafer.viking.Interfaces.IMainResponse;
import com.igzafer.viking.Model.ErrorModels.ErrorModel;
import com.igzafer.viking.R;
import com.igzafer.viking.api.Test.ServerControl;

import retrofit2.Response;

public class InternetError extends DialogFragment {


    public static DialogFragment newInstance() {
        return new InternetError();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.readTheme);
    }
    public interface succ {
        void success();
    }
    private succ succListener;
    View view;
    Button retry;
    SpinKitView spinKitView;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof succ) {
            succListener = (succ) context;
        }

    }
    @Override
    public void onStart() {
        super.onStart();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.f503error, container, false);

        retry=view.findViewById(R.id.retry);
        spinKitView=view.findViewById(R.id.spin_kit);
        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinKitView.setVisibility(View.VISIBLE);
                retry.setVisibility(View.GONE);
                new ServerControl().Control(new IMainResponse() {
                    @Override
                    public <T> void Succsess(Response<T> _response) {
                        try {
                            succListener.success();
                            dismiss();
                        } catch (Exception e) {
                            try {
                                getTargetFragment().onActivityResult(getTargetRequestCode(), 1, getActivity().getIntent());
                                dismiss();
                            }catch (Exception s){

                                getParentFragment();
                                onDismiss(getDialog());

                                dismiss();
                            }
                        }

                        }

                    @Override
                    public void Error(ErrorModel _eresponse) {
                        retry.setVisibility(View.VISIBLE);
                        spinKitView.setVisibility(View.GONE);
                    }
                });
            }
        });
         return view;
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
    }
}