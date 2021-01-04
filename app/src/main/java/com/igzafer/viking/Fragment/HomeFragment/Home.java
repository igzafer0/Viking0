package com.igzafer.viking.Fragment.HomeFragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.dinuscxj.refresh.RecyclerRefreshLayout;
import com.github.ybq.android.spinkit.SpinKitView;
import com.igzafer.viking.Adapter.PlaceholderAdapter;
import com.igzafer.viking.Adapter.RecylerAdapter;
import com.igzafer.viking.Interfaces.IMainResponse;
import com.igzafer.viking.LocalDatabase.HomeStaticDb;
import com.igzafer.viking.Model.BlogModels.BlogModel;
import com.igzafer.viking.Model.ErrorModels.ErrorModel;
import com.igzafer.viking.R;
import com.igzafer.viking.TasarimsalDuzenlemeler.Dialog;
import com.igzafer.viking.api.AuthGerektiren.Blogs;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;


public class Home extends Fragment{




    public Home() {
        // Required empty public constructor
    }

    RecyclerView recyclerView;
    List<BlogModel>ShimmerList;
    SpinKitView spin_kit;
    RecylerAdapter recylerAdapter;
    PlaceholderAdapter placeholderAdapter;
    RecyclerRefreshLayout refreshLayout;
    NestedScrollView nestedScrollView;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fhome, container, false);
        setUpTools();

        Handler hndler= new Handler();
        hndler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(HomeStaticDb.homeFirst){
                    setShimmerEffectandLoad();
                }else{
                    getBlogWithStaticDb();
                }
            }
            // Kodların ne kadar süre sonra çalışacağını belirttik. Burada 1000 değeri ms (milisaniye)
        },105);

        return view;
    }
    private void setUpTools(){
        try {
            ShimmerList= new ArrayList<>();

            spin_kit=view.findViewById(R.id.spin_kit);
            recyclerView=view.findViewById(R.id.recy);
            refreshLayout=view.findViewById(R.id.refresh_layout);
            refreshLayout.setRefreshStyle(RecyclerRefreshLayout.RefreshStyle.NORMAL);
            refreshLayout.setOnRefreshListener(new RecyclerRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    Blogs.pageNumber=1;
                    HomeStaticDb.blogModel.clear();
                    getBlogWithApi();
                    //Refresh yaparken her şeyi 0'lıyorum.
                }
            });
            //nested tanımlandı ve scroll eventi kontrol edildi
            nestedScrollView = view.findViewById(R.id.nested);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                nestedScrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                    @Override
                    public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                        HomeStaticDb.kaydirma=scrollY;
                        if(v.getMeasuredHeight() == ((nestedScrollView.getChildAt(0).getMeasuredHeight())-(scrollY))){
                            if(Blogs.pageNumber<Blogs.maxPage){
                                Blogs.pageNumber= Blogs.pageNumber+1;
                                getBlogWithApi();
                                spin_kit.setVisibility(View.VISIBLE);
                            }else{
                                spin_kit.setVisibility(View.GONE);
                            }
                            //sayfa değiştirme komutu gönderildi ve spinner visible oldu
                        }

                    }
                });
            }

        }
        catch (Exception e){
            if(getActivity()!=null){
                new Dialog().createDialog(getActivity().getWindow(),0);
            }

        }
    }
    private void setShimmerEffectandLoad() {
        ShimmerList.clear();
        for(int i=0;i<20;i++){
            BlogModel blogModel=new BlogModel(0,"","");
            ShimmerList.add(blogModel);
        }
        setUpRecy();
        placeholderAdapter= new PlaceholderAdapter(getContext(),ShimmerList);
        recyclerView.setAdapter(placeholderAdapter);
        getBlogWithApi();

    }
    private void getBlogWithStaticDb(){
        try {
            blogs=HomeStaticDb.blogModel;
            setUpRecy();
            recylerAdapter=new RecylerAdapter(getContext(),blogs, requireActivity().getWindow());
            recyclerView.setAdapter(recylerAdapter);
            refreshLayout.setRefreshing(false);
            spin_kit.setVisibility(View.INVISIBLE);
            Handler hndler= new Handler();
            hndler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    nestedScrollView.scrollTo(0, HomeStaticDb.kaydirma);
                }
                // Bazı sorunlardan dolayı 1ms gecikme ekledim, 1ms sonra nestedScrollView Static database'deki posisyona gidecek
                // Bu sayede sayfa değiştirince geri geldiğimizde kaldığımız yerden devam edeceğiz
            },1);
        }catch (Exception ignored){

        }

    }
    RecylerAdapter adapter;
    List<BlogModel> blogs=new ArrayList<>();
    private void getBlogWithApi(){
        assert requireActivity()!=null;
        new Blogs().get(getContext(),new IMainResponse() {
            @Override
            public <T> void Succsess(Response<T> _response) {
                blogs.addAll((List<BlogModel>) _response.body());
                adapter= new RecylerAdapter(getContext(),blogs,requireActivity().getWindow());
                recyclerView.setHasFixedSize(true);
                recyclerView.setItemViewCacheSize(20);
                recyclerView.setDrawingCacheEnabled(true);
                recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
                recyclerView.setAdapter(adapter);
                refreshLayout.setRefreshing(false);
                spin_kit.setVisibility(View.INVISIBLE);
            }

            @Override
            public void Error(ErrorModel _eresponse) {
                new Dialog().createDialog(requireActivity().getWindow(),0);
            }
        });


    }


    private void setUpRecy() {
        try {
            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
            recyclerView.setHasFixedSize(false);
        }catch (Exception e){
            new Dialog().createDialog(requireActivity().getWindow(),0);
        }

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 1) {
            if(resultCode == 1) {
                spin_kit.setVisibility(View.VISIBLE);
                getBlogWithApi();
                //İnternet hatası olunca çıkan diyalog dissmis olunca buna düşüyor bende tekrar loadlıyorum.
            }
        }
    }


}