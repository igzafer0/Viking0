package com.igzafer.viking.api.LoginRegister;

import com.igzafer.viking.Model.ErrorModels.ErrorModel;

public interface LoginInterface {
    void LoginResponse(Boolean succsess, ErrorModel errorModel);
}
