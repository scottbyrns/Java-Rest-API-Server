package com.geoapi.api.entities.response;

import com.geoapi.api.entities.APIResponse;
import com.geoapi.api.entities.ResponseError;

/**
 * Copyright (C) 2012 by Scott Byrns
 * http://github.com/scottbyrns
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 * <p/>
 * Created 7/5/12 12:11 AM
 */
public class ResponseFactory
{
    private ResponseFactory()
    {
    }

    private static ResponseFactory instance;

    public static ResponseFactory getInstance()
    {
        if (null == instance) {
            instance = new ResponseFactory();
        }
        return instance;
    }

    public static APIResponse createResponse(ResponseType responseType) {
        return getInstance().createTypedResponse(responseType);
    }

    public APIResponse createTypedResponse(ResponseType responseType) {
        APIResponse apiResponse = new APIResponse();
        apiResponse.setStatus(responseType.responseCode());
        ResponseError responseError;

        switch (responseType) {
            case ENDPOINT_IS_OFFLINE:
                responseError = new ResponseError();
                responseError.setMessage("The endpoint you requested is offline.");
                apiResponse.setData(responseError);
                break;
            case UNABLE_TO_GET_RESOURCE:
                responseError = new ResponseError();
                responseError.setMessage("Unable to get the resource you requested.");
                apiResponse.setData(responseError);
                break;
            case NOT_AUTHORIZED:
                responseError = new ResponseError();
                responseError.setMessage("You are not authorized to access this resource.");
                apiResponse.setData(responseError);
                break;
            case UNABLE_TO_CREATE_RESOURCE_BAD_JSON:
                responseError = new ResponseError();
                responseError.setMessage("We were unable to save your resource. You provided an invalid json serialization of your object.");
                apiResponse.setData(responseError);
        }

        return apiResponse;
    }
}
