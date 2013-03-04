package com.geoapi.api.server.services.implementations;

import com.geoapi.api.entities.APIResponse;
import com.geoapi.api.server.BaseService;
import com.scottbyrns.utilities.FatalMappingException;
import com.scottbyrns.utilities.JSONObjectMapper;
import org.apache.cxf.jaxrs.ext.MessageContext;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;

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
 * Created 5/15/12 12:38 AM
 */
@Path("/health")
@Produces(MediaType.APPLICATION_JSON)
public class HealthService extends BaseService
{
    @Context
    private MessageContext context;

    @GET
    @Path("/ping")
    public APIResponse ping ()
    {
        Map<String, Object> stringObjectMap = new HashMap<String, Object>();
        stringObjectMap.put("ping", "pong");
        APIResponse apiResponse = new APIResponse(stringObjectMap, 200);

        return apiResponse;
    }

    @GET
    @Path("/status")
    public APIResponse getStatus () {
        APIResponse apiResponse = new APIResponse(new ServerStatus(), 200);

        try {
            apiResponse.setData(JSONObjectMapper.convertEntityToJSON(apiResponse));
        }
        catch (FatalMappingException e) {

        }
        return apiResponse;
    }
}
