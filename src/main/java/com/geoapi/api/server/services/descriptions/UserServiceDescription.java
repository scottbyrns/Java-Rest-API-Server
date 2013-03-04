package com.geoapi.api.server.services.descriptions;



import com.geoapi.api.entities.APIResponse;
import com.geoapi.api.server.reflection.anotations.EndpointDescription;
import com.geoapi.api.server.reflection.anotations.ResourceAvailability;
import com.geoapi.api.server.reflection.anotations.ServiceDescription;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Copyright (C) 2013 by Scott Byrns
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
 * Created 3/3/13 1:27 PM
 */
@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
@ServiceDescription(
        name = "User Service",
        description = "Manage the applications users",
        availability = ResourceAvailability.ONLINE,
        version = 1.0
)
public interface UserServiceDescription
{

    @GET
    @Path("/login:{username}&{password}")
    @EndpointDescription(
            name = "Login",
            description = "Log a user in.",
            signature = "/login:{username}&{password}",
            availability = ResourceAvailability.BETA,
            version = 1.0,
            returnEntity = "User")
    public APIResponse login(@PathParam("username") String username, @PathParam("password") String password);

    @GET
    @Path("/get:{id}")
    @EndpointDescription(
            name = "Get User By Id",
            description = "Get a user by the users id.",
            signature = "/get:{id}",
            availability = ResourceAvailability.BETA,
            version = 1.0,
            returnEntity = "User")
    public APIResponse getById(@PathParam("id") String id);



}
