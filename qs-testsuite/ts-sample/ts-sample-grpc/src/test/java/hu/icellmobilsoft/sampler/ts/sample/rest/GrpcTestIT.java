/*-
 * #%L
 * Sampler
 * %%
 * Copyright (C) 2022 i-Cell Mobilsoft Zrt.
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
package hu.icellmobilsoft.sampler.ts.sample.rest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;

import jakarta.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import com.google.protobuf.Timestamp;

import hu.icellmobilsoft.qs.grpc.api.sample.BaseMessage;
import hu.icellmobilsoft.qs.grpc.api.sample.DummyRequest;
import hu.icellmobilsoft.qs.grpc.api.sample.DummyResponse;
import hu.icellmobilsoft.qs.grpc.api.sample.DummyServiceGrpc;
import hu.icellmobilsoft.roaster.api.TestSuiteGroup;
import hu.icellmobilsoft.ts.quarkus.sample.common.base.BaseSampleIT;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

/**
 * Sample grpc test
 *
 * @author czenczl
 * @since 0.1.0
 */
@DisplayName("Testing Sample service grpc")
@Tag(TestSuiteGroup.JAXRS)
@TestInstance(Lifecycle.PER_CLASS)
class GrpcTestIT extends BaseSampleIT {

    @Inject
    @ConfigProperty(name = "sampler.service.grpc.server.host", defaultValue = "localhost")
    private String grpcServerHost;

    @Inject
    @ConfigProperty(name = "sampler.service.grpc.server.port", defaultValue = 9000 + "")
    private Integer grpcServerPort;

    private ManagedChannel channel;

    @BeforeAll
    public void init() {
        channel = ManagedChannelBuilder.forAddress(grpcServerHost, grpcServerPort).usePlaintext().build();
    }

    @AfterAll
    public void close() {
        channel.shutdown();
    }

    @Test
    @DisplayName("test dummy grpc service")
    void testDummyGrpcService() {

        DummyRequest.Builder reqBuilder = DummyRequest.newBuilder();

        BaseMessage.Builder baseMessageBuilder = BaseMessage.newBuilder();
        baseMessageBuilder.setAmount(3.14);
        baseMessageBuilder.setFirstName("first");
        baseMessageBuilder.setIsActive(true);
        baseMessageBuilder.setCount(50);

        LocalDate date = LocalDate.of(2022, 04, 14);
        Timestamp timestamp = Timestamp.newBuilder().setSeconds(date.toEpochSecond(LocalTime.now(), ZoneOffset.UTC)).build();

        baseMessageBuilder.setDate(timestamp);
        reqBuilder.setRequest(baseMessageBuilder.build());

        DummyRequest dummyRequest = reqBuilder.build();
        DummyServiceGrpc.DummyServiceBlockingStub stub = DummyServiceGrpc.newBlockingStub(channel);
        DummyResponse helloResponse = stub.getDummy(dummyRequest);

        Assertions.assertEquals("first", helloResponse.getResponse().getFirstName());
        Assertions.assertEquals(true, helloResponse.getResponse().getIsActive());
        Assertions.assertEquals(3.14, helloResponse.getResponse().getAmount());
        Assertions.assertEquals(50, helloResponse.getResponse().getCount());
        Assertions.assertNotNull(helloResponse.getResponse().getDate());

    }

}
