package com.igzafer.viking.RestApi;

public class BaseManager {
    protected RestApi getRestApiClient(){
        RestApiClient restApiClient = new RestApiClient(BaseUrl.site_url);
        return restApiClient.getRestApi();
    }
}
