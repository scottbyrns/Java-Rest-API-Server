package com.geoapi.api.server;

import com.geoapi.api.server.reflection.anotations.ServiceDescription;
import com.geoapi.api.entities.APIResponse;
import com.geoapi.api.entities.ResponseError;
import com.geoapi.api.entities.response.ResponseFactory;
import com.geoapi.api.entities.response.ResponseType;
import com.geoapi.api.server.reflection.ServiceInspector;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Copyright (C) 2012 by Michael Scott Byrns & Carmelo Uria
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
 * Created 5/16/12 9:41 PM
 * <p/>
 * User Stories:
 * I would like to be able to describe an aggragate of data that I would like to consume regularrly.
 * I would like to be able to download the described aggragate at any time by giving you the
 * name of the aggragate so that I do not have to describe it in each request
 */
public abstract class BaseService
{
    @GET
    @Path("/")
    public APIResponse getMethods()
    {
        return ServiceInspector.getMethodsThroughReflection(this.getClass());
    }

    @GET
    @Path("/doc/{method}")
    @Produces(MediaType.TEXT_HTML)
    public String documentEndpoint(@PathParam("method") String methodPath)
    {
        return ServiceInspector.documentEndpoint(methodPath, this.getClass());
    }

    @GET
    @Path("/doc")
    @Produces(MediaType.TEXT_HTML)
    public String documentService () {
        return ServiceInspector.documentService(this.getClass());
    }



    protected APIResponse createNewAPIResponse()
    {
        return new APIResponse();
    }

    protected APIResponse createAPIResponse(Object object) {
        APIResponse apiResponse = createNewAPIResponse();
        apiResponse.setData(object);
        return apiResponse;
    }

    /**
     * Create an api response around a response error.
     *
     * @param error     The response error to send to the client.
     * @param errorCode The error code to set in the response status.
     * @return The api response rapped around a response error.
     */
    protected APIResponse createAPIResponseFromError(ResponseError error, ResponseType errorCode)
    {
        APIResponse apiResponse = ResponseFactory.createResponse(errorCode);
        apiResponse.setData(error);

        return apiResponse;
    }
}
