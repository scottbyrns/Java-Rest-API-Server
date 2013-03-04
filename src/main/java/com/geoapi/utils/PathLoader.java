package com.geoapi.utils;

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
 * Created 7/6/12 2:26 PM
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class PathLoader
{

    public static String Read(String filePath) throws java.io.IOException
    {
        StringBuffer contents = new StringBuffer();
        BufferedReader reader = null;
        try
        {
            String foundFile = FindFile(filePath);
            File file = new File(foundFile);
            reader = new BufferedReader(new FileReader(file));
            String text = null;
            // repeat until all lines is read
            while ((text = reader.readLine()) != null)
            {
                contents.append(text).append("\n");
            }
        }
        catch (Exception e)
        {
            contents.append(e.getMessage());
        }
        finally
        {
            try
            {
                if (reader != null)
                {
                    reader.close();
                }
            }
            catch (Exception e)
            {
                contents.append(e.getMessage());
                e.printStackTrace();
            }
        }
        return contents.toString();
    }

    public static String FindFile(String fileName)
    {
        String path = searchFolder(".",
                                   fileName);
        return path;
    }

    private static String searchFolder(String folder, String fileName)
    {
        String fullPath = "";
        File dir = new File(folder);
        if (dir.isDirectory())
        {
            String[] children = dir.list();
            if (children != null)
            {
                for (int i = 0; i < children.length; i++)
                {
                    // Get filename of file or directory
                    if (children[i].toLowerCase().indexOf(fileName.toLowerCase()) >= 0)
                    {
                        fullPath = folder + "/" + fileName;
                        return fullPath;
                    }
                    else if (children[i].indexOf("..") < 0)
                    {
                        String subSearch = searchFolder(folder + "/" + children[i],
                                                        fileName);
                        if (subSearch.toLowerCase().indexOf(fileName.toLowerCase()) >= 0)
                        {
                            return subSearch;
                        }
                    }
                }
            }
        } return "File not found!";
    }
}