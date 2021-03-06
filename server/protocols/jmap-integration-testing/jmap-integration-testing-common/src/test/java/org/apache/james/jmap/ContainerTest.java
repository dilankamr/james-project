/****************************************************************
 * Licensed to the Apache Software Foundation (ASF) under one   *
 * or more contributor license agreements.  See the NOTICE file *
 * distributed with this work for additional information        *
 * regarding copyright ownership.  The ASF licenses this file   *
 * to you under the Apache License, Version 2.0 (the            *
 * "License"); you may not use this file except in compliance   *
 * with the License.  You may obtain a copy of the License at   *
 *                                                              *
 *   http://www.apache.org/licenses/LICENSE-2.0                 *
 *                                                              *
 * Unless required by applicable law or agreed to in writing,   *
 * software distributed under the License is distributed on an  *
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY       *
 * KIND, either express or implied.  See the License for the    *
 * specific language governing permissions and limitations      *
 * under the License.                                           *
 ****************************************************************/
package org.apache.james.jmap;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.apache.http.client.utils.URIBuilder;
import org.apache.james.util.streams.SwarmGenericContainer;
import org.junit.Rule;
import org.junit.Test;

public class ContainerTest {

    @Rule
    public SwarmGenericContainer container = new SwarmGenericContainer("nginx:1.7.1")
            .withAffinityToContainer()
            .withExposedPorts(80);

    @Test
    public void containerShouldBeReachableOnExposedPort() throws IOException, URISyntaxException {
        String containerIpAddress = container.getContainerIpAddress();
        Integer containerPort = container.getMappedPort(80);
        Response response = Request.Get(new URIBuilder().setScheme("http").setHost(containerIpAddress).setPort(containerPort).build()).execute();
        assertThat(response.returnResponse().getStatusLine().getStatusCode()).isEqualTo(200);
    }
}
