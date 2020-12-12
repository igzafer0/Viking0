package com.igzafer.viking.api.AuthGerektiren;

import com.igzafer.viking.Model.ErrorModels.ErrorModel;
import com.igzafer.viking.Model.UserDetailModels.myDetailsModel;

public interface UpdateMyDetailsInterface {
    void result(Boolean succsess, ErrorModel errorModel);
}
