package com.geoapi.api.entities;

import com.scottbyrns.utilities.FatalMappingException;
import com.scottbyrns.utilities.JSONObjectMapper;

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
 * Created 5/16/12 9:53 PM
 */
public class APIResponse
{
    private int status = 200;
    private Object response;
//    private Map<String, Object> data = new HashMap<String, Object>();
//
//    public APIResponse addReturnValue (String key, Object value) {
//        data.put(key, value);
//        return this;
//    }


    public APIResponse()
    {
        this(null, 200);
    }

    public APIResponse(Object data, int status)
    {
        this.response = data;
        this.status = status;
    }

    public int getStatus()
    {
        return status;
    }

    public void setStatus(int status)
    {
        this.status = status;
    }

    @Deprecated
    public String toResponseString () {
        try {
            return JSONObjectMapper.convertEntityToJSON(this);
        }
        catch (FatalMappingException e) {
            return "{}";
        }
    }

    public Object getData()
    {
        return response;
    }

    public void setData(Object data)
    {
        this.response = data;
    }
}
