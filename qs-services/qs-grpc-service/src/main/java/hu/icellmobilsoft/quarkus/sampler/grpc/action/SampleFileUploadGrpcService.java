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
package hu.icellmobilsoft.quarkus.sampler.grpc.action;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import jakarta.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import hu.icellmobilsoft.coffee.se.logging.Logger;
import hu.icellmobilsoft.qs.grpc.api.sample.upload.FileServiceGrpc;
import hu.icellmobilsoft.qs.grpc.api.sample.upload.FileUploadRequest;
import hu.icellmobilsoft.qs.grpc.api.sample.upload.FileUploadResponse;
import hu.icellmobilsoft.qs.grpc.api.sample.upload.Status;
import io.grpc.stub.StreamObserver;
import io.quarkus.grpc.GrpcService;

/**
 * Sample gRPC service for file upload
 * 
 * @author czenczl
 * @since 0.1.0
 */
@GrpcService
public class SampleFileUploadGrpcService extends FileServiceGrpc.FileServiceImplBase {

    @Inject
    Logger log;

    /**
     * File upload first handles metadata, then processes the file bytes
     */
    @Override
    public StreamObserver<FileUploadRequest> upload(StreamObserver<FileUploadResponse> responseObserver) {
        return new StreamObserver<>() {
            Status status = Status.IN_PROGRESS;
            Path tempFile;

            @Override
            public void onNext(FileUploadRequest fileUploadRequest) {
                try {
                    if (StringUtils.isNotBlank(fileUploadRequest.getMetadata().getName())) {
                        tempFile = Files
                                .createTempFile(fileUploadRequest.getMetadata().getName() + "." + fileUploadRequest.getMetadata().getType(), null);
                    } else {
                        Files.write(tempFile, fileUploadRequest.getFile().getContent().toByteArray(), StandardOpenOption.APPEND);
                    }
                } catch (IOException e) {
                    this.onError(e);
                }
            }

            @Override
            public void onError(Throwable throwable) {
                status = Status.FAILED;
                this.onCompleted();
            }

            @Override
            public void onCompleted() {
                deleteFile(tempFile);
                status = Status.IN_PROGRESS.equals(status) ? Status.SUCCESS : status;
                FileUploadResponse response = FileUploadResponse.newBuilder().setStatus(status).build();
                responseObserver.onNext(response);
                responseObserver.onCompleted();
            }
        };
    }

    private void deleteFile(Path tempFile) {
        try {
            Files.delete(tempFile);
        } catch (IOException e) {
            log.error("Cant delete Temp file.", e);
        }
    }

}
