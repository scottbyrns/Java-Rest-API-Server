package com.geoapi.api.server.reflection;

import com.geoapi.api.server.reflection.anotations.EndpointDescription;
import com.geoapi.api.server.reflection.anotations.ParameterInformation;
import com.geoapi.api.server.reflection.anotations.ServiceDescription;
import com.geoapi.api.entities.APIMethod;
import com.geoapi.api.entities.APIMethodParameter;
import com.geoapi.api.entities.APIResponse;

import javax.ws.rs.PathParam;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Inspect services to gather and aggregate information about the service and it's methods.
 *
 *
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
 * Created 7/10/12 9:05 PM
 */
public class ServiceInspector
{

    /**
     * Extract the {@link ServiceDescription} annotation from a service and the {@link EndpointDescription}
     * from a services methods and render HTML documentation as a byproduct.
     *
     * @param clazz The service class.
     * @return HTML documentation of a service.
     */
    public static String documentService(Class<?> clazz) {

        ServiceDescription serviceDescription;
        Method[] methods;

        try {
            serviceDescription = clazz.getInterfaces()[0].getAnnotation(ServiceDescription.class);
        }
        catch (NullPointerException e) {
            serviceDescription = clazz.getAnnotation(ServiceDescription.class);
        }

        try {
            methods = clazz.getInterfaces()[0].getMethods();
        }
        catch (NullPointerException e) {
            methods = clazz.getMethods();
        }

        return ServiceDescriptionBuilder.describeServiceAsHTML(serviceDescription, methods);
    }

    /**
     * Document an Endpoint on a Service.
     * This documentation will be generated through reflection of the endpoint
     * as described in {@link EndpointDescription}
     *
     * @param methodPath The path as described in the {@link EndpointDescription}.
     * @return Documentation HTML document.
     */
    public static String documentEndpoint(String methodPath, Class<?> clazz) {
        Method[] methods;

        try {
            methods = clazz.getInterfaces()[0].getMethods();
        }
        catch (NullPointerException e) {
            methods = clazz.getMethods();
        }

        boolean methodFound = false;

        for (Method method : methods)
        {
            if (methodFound) {
                continue;
            }

            try {
                Annotation[][] anotations = method.getParameterAnnotations();
                Class[] parameters = method.getParameterTypes();
                Annotation[] annotations = method.getAnnotations();

                Annotation[] annotations1 = method.getAnnotations();
                EndpointDescription endpointDescription = method.getAnnotation(EndpointDescription.class);
                if (endpointDescription.signature().startsWith("/" + methodPath)) {
                    methodFound = true;

                    List<APIMethodParameter> apiMethodParameters = new ArrayList<APIMethodParameter>();


                    for (int i = 0; i < parameters.length; i += 1)
                    {
                        try
                        {
                            APIMethodParameter apiMethodParameter = new APIMethodParameter();
                            apiMethodParameter.setName(((PathParam) anotations[i][0]).value());
                            apiMethodParameter.setType(parameters[i].getSimpleName());
                            apiMethodParameters.add(apiMethodParameter);

                            apiMethodParameter.setType(((ParameterInformation) anotations[i][1]).type());
                            apiMethodParameter.setDescription(((ParameterInformation) anotations[i][1]).description());
                            apiMethodParameter.setMinimumValue(((ParameterInformation) anotations[i][1]).minimumValue());
                            apiMethodParameter.setMaximumValue(((ParameterInformation) anotations[i][1]).maximumValue());
                        }
                        catch (ArrayIndexOutOfBoundsException e)
                        {
                            e.printStackTrace();
                        }
                    }

                    return ServiceDescriptionBuilder.describeEndpointAsHTML(endpointDescription,
                                                                            apiMethodParameters);
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }


        return "Endpoint does not exist.";
    }
//
//    /**
//     * Populate an {@link APIMethodParameter} with its values.
//     *
//     * @return Populated APIMethodParameter
//     */
//    public APIMethodParameter populateAPIMethodParameter () {
//        APIMethodParameter apiMethodParameter = new APIMethodParameter();
//        apiMethodParameter.setName(((PathParam) anotations[i][0]).value());
//        apiMethodParameter.setType(parameters[i].getSimpleName());
//        apiMethodParameters.add(apiMethodParameter);
//
//        apiMethodParameter.setType(((ParameterInformation) anotations[i][1]).type());
//        apiMethodParameter.setDescription(((ParameterInformation) anotations[i][1]).description());
//        apiMethodParameter.setMinimumValue(((ParameterInformation) anotations[i][1]).minimumValue());
//        apiMethodParameter.setMaximumValue(((ParameterInformation) anotations[i][1]).maximumValue());
//    }
//
//    public void addMethodToList(APIMethodParameter apiMethodParameter, List<APIMethodParameter> apiMethodParameters) {
//        APIMethodParameter apiMethodParameter = new APIMethodParameter();
//        apiMethodParameter.setName(((PathParam) anotations[i][0]).value());
//        apiMethodParameter.setType(parameters[i].getSimpleName());
//        apiMethodParameters.add(apiMethodParameter);
//
//        apiMethodParameter.setType(((ParameterInformation) anotations[i][1]).type());
//        apiMethodParameter.setDescription(((ParameterInformation) anotations[i][1]).description());
//        apiMethodParameter.setMinimumValue(((ParameterInformation) anotations[i][1]).minimumValue());
//        apiMethodParameter.setMaximumValue(((ParameterInformation) anotations[i][1]).maximumValue());
//    }

    /**
     * Get all of the Path interfaces for class methods.
     *
     * @return The API methods available through this service.
     */
    public static APIResponse getMethodsThroughReflection(Class<?> clazz)
    {

        ServiceDescription serviceDescription = clazz.getAnnotation(ServiceDescription.class);

        List<APIMethod> stringList = new ArrayList<APIMethod>();
        APIMethod apiMethod = new APIMethod();
        //        apiMethod.setSignature("/");
        //        apiMethod.setStatus(serviceDescription.status());
        //        apiMethod.setVersion(Double.toString(serviceDescription.version()));
        //        stringList.add(apiMethod);


        Method[] methods;

        try {
            methods = clazz.getInterfaces()[0].getMethods();
        }
        catch (NullPointerException e) {
            methods = clazz.getMethods();
        }

        for (Method method : methods)
        {

            Annotation[][] anotations = method.getParameterAnnotations();
            Class[] parameters = method.getParameterTypes();
            Annotation[] annotations = method.getAnnotations();
            try
            {
                Annotation[] annotations1 = method.getAnnotations();
                EndpointDescription endpointDescription = method.getAnnotation(EndpointDescription.class);

                apiMethod = new APIMethod();

                List<APIMethodParameter> apiMethodParameters = new ArrayList<APIMethodParameter>();


                for (int i = 0; i < parameters.length; i += 1)
                {
                    try
                    {
                        APIMethodParameter apiMethodParameter = new APIMethodParameter();
                        apiMethodParameter.setName(((PathParam) anotations[i][0]).value());
                        apiMethodParameter.setType(parameters[i].getSimpleName());
                        apiMethodParameters.add(apiMethodParameter);

                        apiMethodParameter.setType(((ParameterInformation) anotations[i][1]).type());
                        apiMethodParameter.setMinimumValue(((ParameterInformation) anotations[i][1]).minimumValue());
                        apiMethodParameter.setMaximumValue(((ParameterInformation) anotations[i][1]).maximumValue());
                    }
                    catch (ArrayIndexOutOfBoundsException e)
                    {
                        e.printStackTrace();
                    }
                }


                apiMethod.setParameters(apiMethodParameters);

                apiMethod.setSignature(endpointDescription.signature());
                apiMethod.setStatus(endpointDescription.status());
                apiMethod.setVersion(Double.toString(endpointDescription.version()));
                stringList.add(apiMethod);

            }
            catch (NullPointerException e)
            {
                e.printStackTrace();
            }
        }

        Map<String, Object> stringObjectMap = new HashMap<String, Object>();

        stringObjectMap.put("stats",
                            "online");
        stringObjectMap.put("methods",
                            stringList);
        APIResponse apiResponse = new APIResponse(stringObjectMap,
                                                  200);


        return apiResponse;
    }
}
