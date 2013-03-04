package com.geoapi.api.server.services.implementations;

import javax.management.MBeanServerConnection;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.util.Date;
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
 * Created 5/16/12 10:25 PM
 */
public class ServerStatus
{
    public static final String OFFLINE = "{\"error\":\"offline\"}";
    private double load = 0;
    private long serverTime;
    private String version;
    private String name;

    private Map<String, Long> stringMap;

    public ServerStatus()
    {
        stringMap = new HashMap<String, Long>();
        Runtime runtime = Runtime.getRuntime();

        long maxMemory = runtime.maxMemory();
        long allocatedMemory = runtime.totalMemory();
        long freeMemory = runtime.freeMemory();

        stringMap.put("freeMemory", freeMemory / 1024);
        stringMap.put("allocatedMemory", allocatedMemory / 1024);
        stringMap.put("maxMemory", maxMemory /1024);
        stringMap.put("totalFreeMemory", (freeMemory + (maxMemory - allocatedMemory)) / 1024);

        MBeanServerConnection mbsc = ManagementFactory.getPlatformMBeanServer();

        OperatingSystemMXBean osMBean = null;
        try
        {
            osMBean = ManagementFactory.newPlatformMXBeanProxy(mbsc,
                                                               ManagementFactory.OPERATING_SYSTEM_MXBEAN_NAME,
                                                               OperatingSystemMXBean.class);
            setLoad(osMBean.getSystemLoadAverage());
            setVersion(osMBean.getVersion());
            setName(osMBean.getName());
        }
        catch (IOException e)
        {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


        setServerTime(new Date().getTime());
    }

    public double getLoad()
    {
        return load;
    }

    public void setLoad(double load)
    {
        this.load = load;
    }

    public long getServerTime()
    {
        return serverTime;
    }

    public void setServerTime(long serverTime)
    {
        this.serverTime = serverTime;
    }

    public String getVersion()
    {
        return version;
    }

    public void setVersion(String version)
    {
        this.version = version;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}
