/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License
 * 2.0; you may not use this file except in compliance with the Elastic License
 * 2.0.
 */

package org.elasticsearch.xpack.security.rest.action.settings;

import org.elasticsearch.client.internal.node.NodeClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.license.XPackLicenseState;
import org.elasticsearch.rest.RestRequest;
import org.elasticsearch.rest.RestUtils;
import org.elasticsearch.rest.action.RestToXContentListener;
import org.elasticsearch.xpack.core.security.action.settings.UpdateSecuritySettingsAction;
import org.elasticsearch.xpack.security.rest.action.SecurityBaseRestHandler;

import java.io.IOException;
import java.util.List;

import static org.elasticsearch.rest.RestRequest.Method.PUT;

public class RestUpdateSecuritySettingsAction extends SecurityBaseRestHandler {

    public RestUpdateSecuritySettingsAction(Settings settings, XPackLicenseState licenseState) {
        super(settings, licenseState);
    }

    @Override
    public String getName() {
        return "security_update_settings";
    }

    @Override
    public List<Route> routes() {
        return List.of(new Route(PUT, "/_security/settings"));
    }

    @Override
    protected RestChannelConsumer innerPrepareRequest(RestRequest request, NodeClient client) throws IOException {
        final UpdateSecuritySettingsAction.Request req;
        try (var parser = request.contentParser()) {
            req = UpdateSecuritySettingsAction.Request.parse(
                parser,
                (mainIndexSettings, tokensIndexSettings, profilesIndexSettings) -> new UpdateSecuritySettingsAction.Request(
                    RestUtils.getMasterNodeTimeout(request),
                    RestUtils.getAckTimeout(request),
                    mainIndexSettings,
                    tokensIndexSettings,
                    profilesIndexSettings
                )
            );
        }
        return restChannel -> client.execute(UpdateSecuritySettingsAction.INSTANCE, req, new RestToXContentListener<>(restChannel));
    }
}
