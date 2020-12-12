package com.igzafer.viking.api.AuthGerektiren;

import com.igzafer.viking.Model.ErrorModels.ErrorModel;
import com.igzafer.viking.Model.UserDetailModels.myDetailsModel;

public interface getMyDetailsInterface {
    void result(Boolean succsess, myDetailsModel myDetails, ErrorModel errorModel);
}
