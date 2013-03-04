package com.geoapi.api.entities;

import java.util.List;

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
 * Created 5/16/12 10:15 PM
 */
public class APIMethod
{
    private String signature;
    /**
     * @TODO Make an enumeration of various method statuses.
     */
    private String status;
    private String version;
    private List<APIMethodParameter> parameters;

    /**
     * Get the methods signature.
     *
     * @return Methods signature.
     */
    public String getSignature()
    {
        return signature;
    }

    public void setSignature(String signature)
    {
        this.signature = signature;
    }

    /**
     * Get the methods status.
     *
     * @return The methods status.
     */
    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getVersion()
    {
        return version;
    }

    public void setVersion(String version)
    {
        this.version = version;
    }

    public List<APIMethodParameter> getParameters()
    {
        return parameters;
    }

    public void setParameters(List<APIMethodParameter> parameters)
    {
        this.parameters = parameters;
    }
}
