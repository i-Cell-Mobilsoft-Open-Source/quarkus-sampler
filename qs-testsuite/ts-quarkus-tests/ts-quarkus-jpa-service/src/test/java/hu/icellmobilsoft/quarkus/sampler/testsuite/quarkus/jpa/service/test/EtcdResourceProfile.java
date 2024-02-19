package hu.icellmobilsoft.quarkus.sampler.testsuite.quarkus.jpa.service.test;

import java.util.HashMap;
import java.util.Map;

import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import org.testcontainers.containers.wait.strategy.Wait;

/**
 * Quarkus test resource lifecycle manager with etcd resource
 *
 * @author speter555
 * @since 0.1.0
 */
public class EtcdResourceProfile implements QuarkusTestResourceLifecycleManager {

    private static final String ETCD_DOCKER_IMAGE = "public.ecr.aws/bitnami/etcd:3";
    protected static GenericContainer etcd;
    public static Network network = Network.newNetwork();

    static {
        etcd = new GenericContainer(ETCD_DOCKER_IMAGE).withEnv("ALLOW_NONE_AUTHENTICATION", "yes")
                .waitingFor(Wait.forSuccessfulCommand("etcdctl endpoint health"))
                .withExposedPorts(2379)
                .withNetwork(network)
                .withNetworkAliases("qs-etcd");
        etcd.start();
        String command = "etcdctl --endpoints=http://qs-etcd:2379 put hello world";
        System.setProperty("coffee.etcd.default.url", "http://localhost:" + etcd.getMappedPort(2379));
        GenericContainer etcdSetup = new GenericContainer(ETCD_DOCKER_IMAGE).dependsOn(etcd).withCommand(command).withNetwork(network);
        etcdSetup.start();
        etcdSetup.stop();
    }

    public Map<String, String> start() {
        Map<String, String> conf = new HashMap<>();
        conf.put("coffee.etcd.default.url", "http://localhost:" + etcd.getMappedPort(2379));

        return conf;
    }

    @Override
    public void stop() {
        if (etcd != null) {
            etcd.stop();
        }
    }
}
