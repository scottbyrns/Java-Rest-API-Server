package com.geoapi.api.server.services.implementations;

import org.springframework.core.io.ClassPathResource;

import javax.activation.MimetypesFileTypeMap;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;

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
 * Created 5/17/12 2:28 PM
 */
@Path("/")
@Produces(MediaType.TEXT_HTML)
public class WebViewService
{

    @GET
    @Path("/")
    public String homepage ()
    {
        ClassPathResource classPathResource = new ClassPathResource("com/geoapi/webview/homepage.html");
        try {
            File file = classPathResource.getFile();
            return readFileAsString(file);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        String homepage = readFileAsString(classPathResource.getPath());
        return homepage;
    }


    @GET
    @Path("/doc/")
    public String doc ()
    {
        ClassPathResource classPathResource = new ClassPathResource("com/geoapi/webview/doc.html");
        try {
            File file = classPathResource.getFile();
            return readFileAsString(file);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        String homepage = readFileAsString(classPathResource.getPath());
        return homepage;
    }

    @GET
    @Path("css/{name}")
    @Produces("text/css")
    public String css (@PathParam("name") String name)
    {
        ClassPathResource classPathResource = new ClassPathResource("com/geoapi/webview/css/" + name);
        try {
            File file = classPathResource.getFile();
            return readFileAsString(file);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        String css = readFileAsString(classPathResource.getPath());
        return css;
    }

    @GET
    @Path("javascript/{name}")
    @Produces("application/javascript")
    public String javascript (@PathParam("name") String name)
    {
        ClassPathResource classPathResource = new ClassPathResource("com/geoapi/webview/javascript/" + name);
        try {
            File file = classPathResource.getFile();
            return readFileAsString(file);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        String css = readFileAsString(classPathResource.getPath());
        return css;
    }

    @GET
    @Path("js/{parent}/{child}")
    @Produces("application/javascript")
    public String js (@PathParam("parent") String parent, @PathParam("child") String child)
    {
        return javascript(parent.concat("/").concat(child));
    }

    @GET
    @Path("js/{parent}")
    @Produces("application/javascript")
    public String js (@PathParam("parent") String parent)
    {
        return javascript(parent);
    }

    @GET
    @Path("textures/planets/{name}")
    @Produces("image/*")
    public Response planetTextures (@PathParam("name") String name)
    {

        ClassPathResource classPathResource = new ClassPathResource("com/geoapi/webview/planets/" + name);
        try {
            File file = classPathResource.getFile();

            if (!file.exists()) {
                throw new WebApplicationException(404);
            }

            String mt = new MimetypesFileTypeMap().getContentType(file);
            return Response.ok(file, mt).build();
        }
        catch (IOException e) {
            e.printStackTrace();
            throw new WebApplicationException(404);
        }
    }

    /** @param filePath the name of the file to open. Not sure if it can accept URLs or just filenames. Path handling could be better, and buffer sizes are hardcoded
    */
    private static String readFileAsString(String filePath) {
        try {
            StringBuffer fileData = new StringBuffer(1000);
            BufferedReader reader = new BufferedReader(
                    new FileReader(filePath));
            char[] buf = new char[1024];
            int numRead=0;
            while((numRead=reader.read(buf)) != -1){
                String readData = String.valueOf(buf, 0, numRead);
                fileData.append(readData);
                buf = new char[1024];
            }
            reader.close();
            return fileData.toString();
        }
        catch (IOException e) {
            e.printStackTrace();
            return "Failed to open file.";
        }

    }

    /** @param filePath the name of the file to open. Not sure if it can accept URLs or just filenames. Path handling could be better, and buffer sizes are hardcoded
    */
    private static String readFileAsString(File filePath) {
        try {
            StringBuffer fileData = new StringBuffer(1000);
            BufferedReader reader = new BufferedReader(
                    new FileReader(filePath));
            char[] buf = new char[1024];
            int numRead=0;
            while((numRead=reader.read(buf)) != -1){
                String readData = String.valueOf(buf, 0, numRead);
                fileData.append(readData);
                buf = new char[1024];
            }
            reader.close();
            return fileData.toString();
        }
        catch (IOException e) {
            e.printStackTrace();
            return "Failed to open file.";
        }

    }

}

