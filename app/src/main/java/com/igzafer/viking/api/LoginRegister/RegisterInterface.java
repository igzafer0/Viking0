package com.igzafer.viking.api.LoginRegister;

import com.igzafer.viking.Model.ErrorModels.ErrorModel;

public interface RegisterInterface {
    void registerResponse(Boolean succsess, ErrorModel errorModel);
}
