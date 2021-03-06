/*
 * #%L
 * %%
 * Copyright (C) 2018 BMW Car IT GmbH
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package io.joynr.examples.customheaders;

import static io.joynr.util.JoynrUtil.createUuidString;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.joynr.messaging.MessagingQos;

@Path("/control")
@Produces(MediaType.APPLICATION_JSON)
public class RestEndpoint {

    private static final Logger logger = LoggerFactory.getLogger(RestEndpoint.class);

    @Inject
    private HeaderPingClient headerPingClient;

    @GET
    @Path("/trigger")
    public String trigger() {
        String customHeaderValue = CustomHeaderUtils.APP_CUSTOM_HEADER_VALUE_PREFIX + createUuidString();
        logger.info("Calling header ping service with application custom header: {}.", customHeaderValue);
        MessagingQos messagingQos = new MessagingQos();
        messagingQos.getCustomMessageHeaders().put(CustomHeaderUtils.APP_CUSTOM_HEADER_KEY, customHeaderValue);
        String res = headerPingClient.get().ping(messagingQos);
        logger.info("Return result is {}", res);
        return res;
    }

}
