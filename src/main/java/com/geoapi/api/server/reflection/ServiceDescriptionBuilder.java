package com.geoapi.api.server.reflection;

import com.geoapi.api.server.reflection.anotations.EndpointDescription;
import com.geoapi.api.entities.APIMethodParameter;
import com.geoapi.api.server.reflection.anotations.ServiceDescription;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

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
 * Created 7/10/12 9:18 PM
 */
public class ServiceDescriptionBuilder
{
    public static String describeEndpointAsHTML(EndpointDescription endpointDescription, List<APIMethodParameter> apiMethodParameterList) {
        return DocumentationView.documentEndpoint(endpointDescription, apiMethodParameterList);
    }

    public static String describeServiceAsHTML(ServiceDescription serviceDescription, Method[] methods) {

        List<EndpointDescription> endpointDescriptionList = new ArrayList<EndpointDescription>();

        for (Method method : methods) {
            Annotation[][] anotations = method.getParameterAnnotations();
            Class[] parameters = method.getParameterTypes();
            Annotation[] annotations = method.getAnnotations();

            Annotation[] annotations1 = method.getAnnotations();
            EndpointDescription endpointDescription = method.getAnnotation(EndpointDescription.class);
            if (null != endpointDescription) {
                endpointDescriptionList.add(endpointDescription);
            }
        }

        return DocumentationView.documentService(serviceDescription, endpointDescriptionList);
    }

    public static void generateJavaEntities(EndpointDescription endpointDescription, List<APIMethodParameter> apiMethodParameters) {



    }
}
