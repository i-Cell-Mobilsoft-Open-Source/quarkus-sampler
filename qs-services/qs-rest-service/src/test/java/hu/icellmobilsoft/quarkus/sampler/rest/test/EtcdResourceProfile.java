/*-
 * #%L
 * Quarkus-sampler
 * %%
 * Copyright (C) 2024 i-Cell Mobilsoft Zrt.
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
package hu.icellmobilsoft.quarkus.sampler.rest.test;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.wait.strategy.Wait;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;

public class EtcdResourceProfile implements QuarkusTestResourceLifecycleManager {

    private static final String ETCD_DOCKER_IMAGE = "bitnami/etcd:3";
    protected static GenericContainer etcd;
    public static Network network = Network.newNetwork();

    static {
        etcd = new GenericContainer(ETCD_DOCKER_IMAGE).withEnv("ALLOW_NONE_AUTHENTICATION", "yes")
                .withExposedPorts(2379)
                .withNetwork(network)
                .withNetworkAliases("qs-etcd");
        etcd.start();
        String command = "etcdctl --endpoints=http://qs-etcd:2379 put hello world";

        GenericContainer etcdSetup = new GenericContainer(ETCD_DOCKER_IMAGE).dependsOn(etcd).withCommand(command).withNetwork(network);
        etcdSetup.start();
        etcdSetup.stop();
    }

    public Map<String, String> start() {
        Map<String, String> conf = new HashMap<>();
        conf.put("coffee.etcd.default.url", "http://localhost:" + etcd.getMappedPort(2379));
        System.setProperty("coffee.etcd.default.url", "http://localhost:" + etcd.getMappedPort(2379));
        return conf;
    }

    @Override
    public void stop() {
        if (etcd != null) {
            etcd.stop();
        }
    }
}
