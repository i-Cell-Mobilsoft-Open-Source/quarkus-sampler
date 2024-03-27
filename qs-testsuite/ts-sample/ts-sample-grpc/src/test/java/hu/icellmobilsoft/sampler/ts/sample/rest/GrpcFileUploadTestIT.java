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

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import jakarta.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import com.google.protobuf.ByteString;

import hu.icellmobilsoft.coffee.se.logging.Logger;
import hu.icellmobilsoft.qs.grpc.api.sample.upload.File;
import hu.icellmobilsoft.qs.grpc.api.sample.upload.FileServiceGrpc;
import hu.icellmobilsoft.qs.grpc.api.sample.upload.FileUploadRequest;
import hu.icellmobilsoft.qs.grpc.api.sample.upload.FileUploadResponse;
import hu.icellmobilsoft.qs.grpc.api.sample.upload.MetaData;
import hu.icellmobilsoft.roaster.api.TestSuiteGroup;
import hu.icellmobilsoft.ts.quarkus.sample.common.base.BaseSampleIT;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

/**
 * Sample grpc file upload test
 *
 * @author czenczl
 * @since 0.1.0
 */
@DisplayName("Testing Sample file upload service grpc")
@Tag(TestSuiteGroup.JAXRS)
@TestInstance(Lifecycle.PER_CLASS)
class GrpcFileUploadTestIT extends BaseSampleIT {

    @Inject
    @ConfigProperty(name = "sampler.service.grpc.server.host", defaultValue = "localhost")
    private String grpcServerHost;

    @Inject
    @ConfigProperty(name = "sampler.service.grpc.server.port", defaultValue = 9000 + "")
    private Integer grpcServerPort;

    @Inject
    private Logger log;

    private final int CHUNK_SIZE = 4096;

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
    @DisplayName("test file upload grpc service")
    void testFileUpload() throws IOException, InterruptedException {
        final CountDownLatch done = new CountDownLatch(1);

        FileServiceGrpc.FileServiceStub fileServiceStub = FileServiceGrpc.newStub(channel).withDeadlineAfter(5, TimeUnit.MINUTES);
        StreamObserver<FileUploadResponse> fileUploadObserver = new StreamObserver<FileUploadResponse>() {

            @Override
            public void onNext(FileUploadResponse fileUploadResponse) {
                log.info("File upload status [{0}]", fileUploadResponse.getStatus());
            }

            @Override
            public void onError(Throwable throwable) {
                log.error("Error occurs while uploading file", throwable);
                done.countDown();
            }

            @Override
            public void onCompleted() {
                log.info("File upload completed");
                done.countDown();
            }

        };

        StreamObserver<FileUploadRequest> streamObserver = fileServiceStub.upload(fileUploadObserver);
        FileUploadRequest metadata = FileUploadRequest.newBuilder()
                .setMetadata(MetaData.newBuilder().setName("outputFile").setType("jpg").build())
                .build();
        streamObserver.onNext(metadata);
        InputStream document = new RandomInputStream(15 * 1024 * 1024);
        try (InputStream inputStream = document) {
            byte[] bytes = new byte[CHUNK_SIZE];
            int size;
            while ((size = inputStream.read(bytes)) > 0) {
                FileUploadRequest uploadRequest = FileUploadRequest.newBuilder()
                        .setFile(File.newBuilder().setContent(ByteString.copyFrom(bytes, 0, size)).build())
                        .build();
                streamObserver.onNext(uploadRequest);
            }
        }

        streamObserver.onCompleted();

        done.await(3, TimeUnit.SECONDS);

    }

}
