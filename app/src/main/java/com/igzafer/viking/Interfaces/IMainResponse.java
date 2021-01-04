package com.igzafer.viking.Interfaces;

import com.igzafer.viking.Model.ErrorModels.ErrorModel;

import retrofit2.Response;

public interface IMainResponse {
    <T> void Succsess(Response<T> _response);
    void Error(ErrorModel _eresponse);
}

