package com.geoapi.utils;

import com.geoapi.api.entities.APIResponse;
import com.geoapi.api.entities.ResponseError;

import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.nio.charset.Charset;

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
 * Created 5/17/12 8:44 PM
 */
@Provider
@Produces(MediaType.APPLICATION_JSON)
public class APIResponseMessageBodyWriter implements MessageBodyWriter<APIResponse>
{
    public boolean isWriteable(Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType)
    {
        return  aClass.equals(APIResponse.class);
    }

    public long getSize(APIResponse apiResponse, Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType)
    {
        // Let the underlying http layer do it for us.
        return -1;
    }

    public void writeTo(APIResponse apiResponse, Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> stringObjectMultivaluedMap, OutputStream outputStream) throws IOException, WebApplicationException
    {
        if (apiResponse.getData() == null) {
            ResponseError responseError = new ResponseError();
            responseError.setMessage("The server was unable to produce a result set for your request. Not sure why. The outbound interceptor noticed the response body was null.");
            apiResponse.setData(responseError);
            apiResponse.setStatus(500);
        }
        outputStream.write(apiResponse.toResponseString().getBytes(Charset.forName("UTF-8")));
    }
}
