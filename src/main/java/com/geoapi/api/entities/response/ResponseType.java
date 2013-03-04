package com.geoapi.api.entities.response;

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
 * Created 7/5/12 12:13 AM
 */
public enum ResponseType
{
    SUCCESS(200),
    RESOURCE_NOT_FOUND(404),
    ENDPOINT_IS_OFFLINE(405),
    SERVER_ERROR(500),
    UNABLE_TO_SET_RESOURCE(201),
    UNABLE_TO_GET_RESOURCE(202),
    UNABLE_TO_CREATE_RESOURCE_BAD_JSON(203),
    NOT_AUTHORIZED(400);


    private ResponseType(int responseCode)
    {
        this.responseCode = responseCode;
    }

    private final int responseCode;

    public int responseCode()
    {
        return responseCode;
    }
}
