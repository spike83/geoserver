/* Copyright (c) 2017 Boundless - http://boundlessgeo.com All rights reserved.
 * This code is licensed under the GPL 2.0 license, available at the root
 * application directory.
 */
package com.boundlessgeo.gsr.api.map;

import com.boundlessgeo.gsr.api.AbstractGSRController;
import com.boundlessgeo.gsr.model.AbstractGSRModel.Link;
import com.boundlessgeo.gsr.model.map.LayersAndTables;
import com.boundlessgeo.gsr.translate.map.LayerDAO;
import java.util.Arrays;
import java.util.Collections;
import org.geoserver.api.APIService;
import org.geoserver.api.HTMLResponseBody;
import org.geoserver.config.GeoServer;
import org.geoserver.wms.WMSInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for the Map Service layers list endpoint
 */
@APIService(
        service = "MapServer",
        version = "1.0",
        landingPage = "/gsr/services",
        serviceClass = WMSInfo.class
)
@RestController
@RequestMapping(path = "/gsr/services/{workspaceName}/MapServer", produces = MediaType.APPLICATION_JSON_VALUE)
public class LayerListController extends AbstractGSRController {

    @Autowired
    public LayerListController(@Qualifier("geoServer") GeoServer geoServer) {
        super(geoServer);
    }

    @GetMapping(path = "/layers")
    @HTMLResponseBody(templateName = "maplayers.ftl", fileName = "maplayers.html")
    public LayersAndTables layersGet(@PathVariable String workspaceName) {
        return LayerDAO.find(catalog, workspaceName, Arrays.asList(
                new Link(workspaceName, workspaceName),
                new Link(workspaceName + "/" + "MapServer", "MapServer")
        ), Collections.singletonList(new Link(workspaceName + "/" + "MapServer/layers?f=json&pretty=true", "REST")));
    }
}
