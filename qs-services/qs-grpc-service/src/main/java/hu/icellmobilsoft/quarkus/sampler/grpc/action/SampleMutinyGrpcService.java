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

import io.grpc.stub.StreamObserver;
import io.smallrye.mutiny.Uni;

/**
 * Mutiny example, its working if quarkus generates the classes from proto
 * 
 * @author czenczl
 * @since 0.1.0
 */
// @GrpcService
public class SampleMutinyGrpcService
// implements Greeter
{

    // @Override
    // public Uni<HelloReply> sayHello(HelloRequest request) {
    // return Uni.createFrom().item(() -> HelloReply.newBuilder().setMessage("Hello " + request.getName()).build());
    // }

}
