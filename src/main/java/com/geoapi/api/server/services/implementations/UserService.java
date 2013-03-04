package com.geoapi.api.server.services.implementations;



import com.businessapi.entities.User;
import com.geoapi.api.entities.APIResponse;
import com.geoapi.api.server.BaseService;
import com.geoapi.api.server.services.descriptions.UserServiceDescription;

import java.util.UUID;

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
 * Created 3/3/13 1:30 PM
 */
public class UserService extends BaseService implements UserServiceDescription
{
    public APIResponse login(String username, String password)
    {

        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setName(username);
        user.setPassword(password);

        return createAPIResponse(user);

    }


    public APIResponse getById(String id)
    {

        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setName("MikeRogers");
        user.setPassword(UUID.randomUUID().toString());

        return createAPIResponse(user);

    }
}
