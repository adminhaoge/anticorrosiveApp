package com.example.imitationtaobao.fragment.home;

import android.content.Intent;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.example.imitationtaobao.R;
import com.example.imitationtaobao.adapter.MyRecommendAdapter;
import com.example.imitationtaobao.adapter.SliderAndRecycleAdapter;
import com.example.imitationtaobao.base.BaseFragment;
import com.example.imitationtaobao.bean.CpDataBody;
import com.example.imitationtaobao.bean.SlideImage;
import com.example.imitationtaobao.http.LoadingCallBack;
import com.example.imitationtaobao.http.MyOkHttp;
import com.example.imitationtaobao.http.OkHttpHelper;
import com.example.imitationtaobao.http.ServiceApi;
import com.example.imitationtaobao.http.SubscriberUtils.MySubscriber;
import com.example.imitationtaobao.utils.Constant;

import java.util.List;

import butterknife.BindView;
import okhttp3.Response;
import retrofit2.Retrofit;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class HomeFragment extends BaseFragment {


    @BindView(R.id.slider)
    SliderLayout sliderShow;
    @BindView(R.id.recycler)
    RecyclerView Recycler;
    private MyRecommendAdapter recommendAdapter;
    @Override
    protected int LayoutInit() {
        return R.layout.home_fragment;

    }

    @Override
    protected void initLogic() {
        initOkHttp();
    }


    private void initOkHttp() {

        OkHttpHelper okHttpHelper = OkHttpHelper.getmOkHttp();
        okHttpHelper.get("http://112.124.22.238:8081/course_api/campaign/recommend", new LoadingCallBack<List<CpDataBody>>(getContext()) {

            @Override
            public void onResponseSuccess(Response response, List<CpDataBody> cpDataBodies) {
                recommendAdapter = new MyRecommendAdapter(cpDataBodies);
                Recycler.setAdapter(recommendAdapter);
                GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 1);
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                Recycler.setLayoutManager(layoutManager);
            }

            @Override
            public void onResponseError(Response response, int code, Exception e) {
            }
        });

        okHttpHelper.get("http://112.124.22.238:8081/course_api/banner/query?type=1", new LoadingCallBack<List<SlideImage>>(getContext()) {

            @Override
            public void onResponseSuccess(Response response, List<SlideImage> slideImages) {
                for (SlideImage slideImage : slideImages) {
                    initSlideImage(slideImage);
                }
            }

            @Override
            public void onResponseError(Response response, int code, Exception e) {

            }
        });


//        MyOkHttp myOkHttp = new MyOkHttp("http://112.124.22.238:8081/");
//        Retrofit retrofit = myOkHttp.setRetrofit();
//        ServiceApi serviceApi1 = retrofit.create(ServiceApi.class);
//        serviceApi1.setRecommend()
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.newThread())
//                .subscribeOn(Schedulers.io())
//                .subscribe(new MySubscriber<List<CpDataBody>>() {
//                    @Override
//                    public void onNext(List<CpDataBody> cpDataBodies) {
//                        recommendAdapter = new MyRecommendAdapter(cpDataBodies);
//                        Recycler.setAdapter(recommendAdapter);
//                        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 1);
//                        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//                        Recycler.setLayoutManager(layoutManager);
//                    }
//                });
//
//
//        ServiceApi serviceApi = retrofit.create(ServiceApi.class);
//        serviceApi.setSlide(1)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.io())
//                .subscribeOn(Schedulers.newThread())
//                .subscribe(new MySubscriber<List<SlideImage>>() {
//                    @Override
//                    public void onNext(List<SlideImage> slideImages) {
//                        for (SlideImage slideImage : slideImages) {
//                            initSlideImage(slideImage);
//                        }
//                    }
//                });

    }

    private void initSlideImage(SlideImage slideImage) {
        TextSliderView textSliderView = new TextSliderView(getActivity());
        textSliderView
                .description(slideImage.getName())
                .image(slideImage.getImgUrl());
        sliderShow.startAutoCycle();
        sliderShow.addSlider(textSliderView);
        sliderShow.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        sliderShow.setPresetTransformer(SliderLayout.Transformer.ZoomOut);
        sliderShow.setDuration(3000);
    }

    @Override
    public void onStart() {
        super.onStart();
        sliderShow.stopAutoCycle();
    }
}