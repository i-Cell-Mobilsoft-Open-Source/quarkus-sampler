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

import hu.icellmobilsoft.qs.grpc.api.sample.BaseMessage;
import hu.icellmobilsoft.qs.grpc.api.sample.DummyRequest;
import hu.icellmobilsoft.qs.grpc.api.sample.DummyResponse;
import hu.icellmobilsoft.qs.grpc.api.sample.DummyServiceGrpc;
import io.grpc.stub.StreamObserver;
import io.quarkus.grpc.GrpcService;

/**
 * Sample gRPC service implementation
 * 
 * @author czenczl
 * @since 0.1.0
 */
@GrpcService
public class SampleGrpcService extends DummyServiceGrpc.DummyServiceImplBase {

    @Override
    public void getDummy(DummyRequest request, StreamObserver<DummyResponse> responseObserver) {
        DummyResponse response = toResponse(request);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    private DummyResponse toResponse(DummyRequest request) {
        BaseMessage baseMessage = request.getRequest();

        DummyResponse.Builder builder = DummyResponse.newBuilder();

        BaseMessage.Builder baseMessageBuilder = BaseMessage.newBuilder();
        baseMessageBuilder.setAmount(baseMessage.getAmount());
        baseMessageBuilder.setFirstName(baseMessage.getFirstName());
        baseMessageBuilder.setIsActive(baseMessage.getIsActive());
        baseMessageBuilder.setCount(baseMessage.getCount());
        baseMessageBuilder.setDate(baseMessage.getDate());

        builder.setResponse(baseMessageBuilder.build());

        return builder.build();
    }

}
