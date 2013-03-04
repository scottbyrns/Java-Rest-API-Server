package com.geoapi.api.server.filters;



import com.geoapi.api.entities.response.ResponseFactory;
import com.geoapi.api.entities.response.ResponseType;
import com.geoapi.api.server.reflection.anotations.EndpointDescription;
import com.geoapi.api.server.reflection.anotations.ResourceAvailability;
import org.apache.cxf.jaxrs.ext.RequestHandler;
import org.apache.cxf.jaxrs.model.ClassResourceInfo;
import org.apache.cxf.jaxrs.model.OperationResourceInfo;
import org.apache.cxf.message.Message;

import javax.ws.rs.core.Response;
import java.lang.reflect.Method;
import java.util.Iterator;

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
 * Created 7/12/12 8:15 AM
 */
public class EndpointStatusFilter implements RequestHandler
{

    public Response handleRequest(Message message, ClassResourceInfo classResourceInfo)
    {

        try
        {
            Method method = null;
            Iterator<OperationResourceInfo> iterator = classResourceInfo.getMethodDispatcher().getOperationResourceInfos().iterator();
            OperationResourceInfo operationResourceInfo;

            String methodName = ((Method) message.get("org.apache.cxf.resource.method")).getName();
            String resourceMethodName;

            while (iterator.hasNext())
            {
                operationResourceInfo = iterator.next();
                resourceMethodName = operationResourceInfo.getAnnotatedMethod().getName();
                method = operationResourceInfo.getAnnotatedMethod();

                if (resourceMethodName.equals(methodName))
                {
                    EndpointDescription endpointDescription = method.getAnnotation(EndpointDescription.class);

                    if (endpointDescription.availability().equals(ResourceAvailability.OFFLINE))
                    {
                        Response response = Response.status(Response.Status.SERVICE_UNAVAILABLE).entity(ResponseFactory.createResponse(ResponseType.ENDPOINT_IS_OFFLINE)).build();
                        return response;
                    }
                }
            }
        }
        catch (NullPointerException e)
        {
            e.printStackTrace();
        }

        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
