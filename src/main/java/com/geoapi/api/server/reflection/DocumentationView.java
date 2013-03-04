package com.geoapi.api.server.reflection;

import com.geoapi.api.entities.APIMethodParameter;
import com.geoapi.api.server.reflection.anotations.EndpointDescription;
import com.geoapi.api.server.reflection.anotations.ServiceDescription;

import java.util.Iterator;
import java.util.List;

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
 * Created 7/10/12 8:24 PM
 */
public class DocumentationView
{
    public static String documentEndpoint(EndpointDescription endpointDescription, List<APIMethodParameter> apiMethodParameter)
    {
        StringBuilder doc = new StringBuilder();

        appendDocumentationHeader(doc);

        doc.append("<h1>");
        doc.append(endpointDescription.name());
        doc.append("</h1>");

        doc.append("<h2>");
        doc.append("Status");
        doc.append("</h2>");

        doc.append(endpointDescription.status());

        doc.append("<h2>");
        doc.append("Version");
        doc.append("</h2>");

        doc.append(endpointDescription.version());

        doc.append("<h2>");
        doc.append("Description");
        doc.append("</h2>");

        doc.append("<p>");
        doc.append(endpointDescription.description());
        doc.append("</p>");

        doc.append("<h2>Parameters</h2>");
        doc.append("<table>");
//        doc.append("<ul>");

        for (APIMethodParameter parameter : apiMethodParameter)
        {
            doc.append("<tr>");
            doc.append("<td colspan=2>");
//            doc.append("<h4>");
            doc.append(parameter.getName());
//            doc.append("</h4>");
            doc.append("</td></tr>");
//            doc.append("<h5>Description</h5>");
            doc.append("<tr><td>Description</td><td>");
//            doc.append("<p>");
            doc.append(parameter.getDescription());
//            doc.append("</p>");
            doc.append("</td></tr>");
            doc.append("<tr><td>Type</td><td>");

//            doc.append("<p>");
            doc.append(parameter.getType());
//            doc.append("</p>");
            doc.append("</td></tr>");

            doc.append("<tr><td>Parameter Range</td><td>");
//            doc.append("<h5>");

            doc.append("<p><strong>Minimum</strong>:");
            doc.append(parameter.getMinimumValue());
            doc.append("<br /><strong>Maximum</strong>:");
            doc.append(parameter.getMaximumValue());
            doc.append("</p>");
            doc.append("</td></tr>");
//            doc.append("</li>");
        }

        doc.append("</table>");

        doc.append("<h2>Return Entity</h2>");
        doc.append("<p>");
        doc.append(endpointDescription.returnEntity());
        doc.append("</p>");

        appendDocumentFooter(doc);

        return doc.toString();

    }

    public static StringBuilder appendDocumentationHeader (StringBuilder doc) {
        doc.append("<html>");
        doc.append("<head><title>Reflective Documentation</title>");
        doc.append("<link rel=\"stylesheet\" type=\"text/css\" media=\"all\" href=\"../../css/doc.css\"/>");
        doc.append("</head>");
        doc.append("<body>");
        return doc;
    }

    public static StringBuilder appendDocumentFooter (StringBuilder doc) {
        doc.append("</body>");
        doc.append("</html>");
        return doc;
    }

    public static String documentService (ServiceDescription serviceDescription, List<EndpointDescription> methods) {

        StringBuilder doc = new StringBuilder();

        Iterator<EndpointDescription> descriptionIterator = methods.iterator();

        EndpointDescription endpointDescription;

        appendDocumentationHeader(doc);

        doc.append("<h1>").append(serviceDescription.name()).append("</h1>");
        doc.append("<p>").append(serviceDescription.description()).append("</p>");

        doc.append("<table><th>Endpoint</th><th>Version</th><th>Status</th>");

        while (descriptionIterator.hasNext()) {
            endpointDescription = descriptionIterator.next();
            doc.append("<tr><td>");
            doc.append("<a href=\"doc").append(endpointDescription.signature()).append("\">").append(endpointDescription.name()).append("</a>");
            doc.append("</td><td>");
            doc.append(endpointDescription.version());
            doc.append("</td><td>");
            doc.append(endpointDescription.status());
            doc.append("</td></tr>");
        }

        doc.append("</table>");

        appendDocumentFooter(doc);

        return doc.toString();
    }
}
