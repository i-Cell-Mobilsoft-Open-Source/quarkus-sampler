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
package hu.icellmobilsoft.sampler.ts.sample.panache;

import jakarta.inject.Inject;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import hu.icellmobilsoft.coffee.dto.common.commonservice.BaseRequest;
import hu.icellmobilsoft.coffee.dto.common.commonservice.FunctionCodeType;
import hu.icellmobilsoft.coffee.dto.common.commonservice.InvalidRequestFault;
import hu.icellmobilsoft.coffee.se.api.exception.BaseException;
import hu.icellmobilsoft.quarkus.sampler.api.jakarta.test.ITestQuarkusSamplerServiceRest;
import hu.icellmobilsoft.quarkus.sampler.dto.test.post.SampleRequest;
import hu.icellmobilsoft.quarkus.sampler.dto.test.post.SampleResponse;
import hu.icellmobilsoft.quarkus.sampler.ts.common.rest.DtoHelper;
import hu.icellmobilsoft.roaster.api.TestSuiteGroup;
import hu.icellmobilsoft.roaster.jaxrs.response.producer.RestProcessor;
import hu.icellmobilsoft.roaster.restassured.response.producer.impl.ConfigurableResponseProcessor;
import hu.icellmobilsoft.sampler.ts.sample.panache.builder.SampleRequestBuilder;
import hu.icellmobilsoft.sampler.ts.sample.panache.client.IXmlTestQuarkusSamplerServiceRest;
import hu.icellmobilsoft.ts.quarkus.sample.common.base.BaseSampleIT;

/**
 * Sample service {@link ITestQuarkusSamplerServiceRest#postTest(BaseRequest, String, Integer, Long, Boolean)} test
 *
 * @author speter555
 * @since 0.1.0
 */
@DisplayName("Testing Sample service post")
@Tag(TestSuiteGroup.JAXRS)
class PostSampleIT extends BaseSampleIT {

    private static final String REST_CONFIG_KEY = "testsuite.rest.sampleService.sample";

    @Inject
    private SampleRequestBuilder requestBuilder;

    @Inject
    @RestProcessor(configKey = REST_CONFIG_KEY)
    private ConfigurableResponseProcessor<SampleResponse> responseProcessor;

    @Inject
    @RestProcessor(configKey = REST_CONFIG_KEY, expectedStatusCode = 400)
    private ConfigurableResponseProcessor<InvalidRequestFault> responseProcessorError;

    @Nested
    @DisplayName("Testing JSON request")
    class Json {

        @Test
        @DisplayName("Testing JSON: empty request")
        void testEmptyJsonBaseRequest() throws BaseException {
            SampleResponse response = getRestClient(ITestQuarkusSamplerServiceRest.class)
                    .postTest(new BaseRequest().withContext(DtoHelper.createContext()), "testString", Integer.MAX_VALUE, Long.MAX_VALUE, Boolean.TRUE);
            Assertions.assertNotNull(response);
            Assertions.assertEquals(FunctionCodeType.OK, response.getFuncCode());
            Assertions.assertNotNull(response.getSample());
        }

        @Test
        @DisplayName("Testing JSON: empty request")
        void testEmptyJson() {
            // given

            // when
            SampleResponse response = responseProcessor.postJson(requestBuilder.getDefault(), SampleResponse.class);

            // then
            Assertions.assertEquals(FunctionCodeType.OK, response.getFuncCode());
        }

        @Test
        @DisplayName("Testing JSON: valid dto success")
        void testJsonValidDto() {
            // given
            SampleRequest request = requestBuilder.getDefault();

            // when
            SampleResponse response = responseProcessor.postJson(request, SampleResponse.class);

            // then
            Assertions.assertEquals(FunctionCodeType.OK, response.getFuncCode());
        }

        @Test
        @DisplayName("Testing JSON: valid json string success")
        void testJsonValidString() {
            // given
            String request = """
                    {
                        "context": {
                            "requestId": "4EEMHK5QH4FVX601",
                            "timestamp": "2023-12-05T22:11:46.382Z"
                        },
                            "sample": {
                            "columnA": "colA",
                            "columnB": "VALUE_B",
                            "columnC": "VALUE_C"
                        }
                    }
                    """;

            // when
            SampleResponse response = responseProcessor.postJson(request, SampleResponse.class);

            // then
            Assertions.assertEquals(FunctionCodeType.OK, response.getFuncCode());
        }

        @Test
        @DisplayName("Testing JSON: invalid json string with UNKNOWN field should throw validation errors")
        void testJsonUnknownField() {
            // given
            String request = """
                    {
                        "context": {
                            "requestId": "4EEMHK5QH4FVX601",
                            "timestamp": "2023-12-05T22:11:46.382Z"
                        },
                            "sample": {
                            "columnA": "colA",
                            "columnB": "VALUE_B",
                            "columnC": "VALUE_C",
                            "valamiIsmeretlen": "VALUE_C"
                        }
                    }
                    """;

            // when
            var response = responseProcessorError.postJson(request, InvalidRequestFault.class);

            // then
            Assertions.assertEquals(FunctionCodeType.ERROR, response.getFuncCode());
        }

        @Test
        @DisplayName("Testing JSON: invalid json string with UNKNOWN ENUM value should throw validation error")
        void testJsonUnknownEnumValue() {
            // given
            String request = """
                    {
                        "context": {
                            "requestId": "4EEMHK5QH4FVX601",
                            "timestamp": "2023-12-05T22:11:46.382Z"
                        },
                            "sample": {
                            "columnA": "colA",
                            "columnB": "valamiIsmeretlen",
                            "columnC": "VALUE_C"
                        }
                    }
                    """;

            // when
            var response = responseProcessorError.postJson(request, InvalidRequestFault.class);

            // then
            Assertions.assertEquals(FunctionCodeType.ERROR, response.getFuncCode());
        }

        @Test
        @DisplayName("Testing JSON: invalid json string with UNKNOWN optional ENUM value should throw validation error")
        void testJsonUnknownOptionalEnumValue() {
            // given
            String request = """
                    {
                        "context": {
                            "requestId": "4EEMHK5QH4FVX601",
                            "timestamp": "2023-12-05T22:11:46.382Z"
                        },
                            "sample": {
                            "columnA": "colA",
                            "columnB": "VALUE_B",
                            "columnC": "valamiIsmeretlen"
                        }
                    }
                    """;

            // when
            var response = responseProcessorError.postJson(request, InvalidRequestFault.class);

            // then
            Assertions.assertEquals(FunctionCodeType.ERROR, response.getFuncCode());
        }

        @Test
        @DisplayName("Testing JSON: invalid json string using ENUM ordinal value should throw validation error")
        void testJsonEnumOrdinalValue() {
            // given
            String request = """
                    {
                        "context": {
                            "requestId": "4EEMHK5QH4FVX601",
                            "timestamp": "2023-12-05T22:11:46.382Z"
                        },
                            "sample": {
                            "columnA": "colA",
                            "columnB": "0",
                            "columnC": "1"
                        }
                    }
                    """;

            // when
            var response = responseProcessorError.postJson(request, InvalidRequestFault.class);

            // then
            Assertions.assertEquals(FunctionCodeType.ERROR, response.getFuncCode());
        }

        @Test
        @DisplayName("Testing JSON: invalid json string using wrong TIME format should throw validation error")
        void testJsonWrongTimeFormat() {
            // given
            String request = """
                    {
                        "context": {
                            "requestId": "4EEMHK5QH4FVX601",
                            "timestamp": "2022-03-10T12:15"
                        },
                            "sample": {
                            "columnA": "colA",
                            "columnB": "VALUE_B",
                            "columnC": "VALUE_C"
                        }
                    }
                    """;

            // when
            var response = responseProcessorError.postJson(request, InvalidRequestFault.class);

            // then
            Assertions.assertEquals(FunctionCodeType.ERROR, response.getFuncCode());
        }
    }

    @Nested
    @DisplayName("Testing XML request")
    class Xml {

        @Test
        @DisplayName("Testing XML: empty request")
        void testEmptyXmlBaseRequest() throws BaseException {
            SampleResponse response = getRestClient(IXmlTestQuarkusSamplerServiceRest.class)
                    .postTest(new BaseRequest().withContext(DtoHelper.createContext()), "testString", Integer.MAX_VALUE, Long.MAX_VALUE, Boolean.TRUE);
            Assertions.assertNotNull(response);
            Assertions.assertEquals(FunctionCodeType.OK, response.getFuncCode());
            Assertions.assertNotNull(response.getSample());
        }

        @Test
        @DisplayName("Testing XML: empty request")
        void testEmptyXml() {
            // given

            // when
            SampleResponse response = responseProcessor.postXml(requestBuilder.getDefault(), SampleResponse.class);

            // then
            Assertions.assertEquals(FunctionCodeType.OK, response.getFuncCode());
        }

        @Test
        @DisplayName("Testing XML: valid dto success")
        void testXmlValidDto() {
            // given
            SampleRequest request = requestBuilder.getDefault();

            // when
            var response = responseProcessor.postXml(request, SampleResponse.class);

            // then
            Assertions.assertEquals(FunctionCodeType.OK, response.getFuncCode());
        }

        @Test
        @DisplayName("Testing XML: valid xml string success")
        void testXmlValidString() {
            // given
            String request = """
                    <ns2:SampleRequest xmlns="http://common.dto.coffee.icellmobilsoft.hu/commonservice" xmlns:ns2="http://test.dto.sampler.quarkus.icellmobilsoft.hu/post">
                      <context>
                        <requestId>4QDHNLOI9C51JJ04</requestId>
                        <timestamp>2024-10-02T11:05:12.066Z</timestamp>
                      </context>
                      <ns2:sample>
                        <ns2:columnA>colA</ns2:columnA>
                        <ns2:columnB>VALUE_B</ns2:columnB>
                      </ns2:sample>
                    </ns2:SampleRequest>
                    """;

            // when
            var response = responseProcessor.postXml(request, SampleResponse.class);

            // then
            Assertions.assertEquals(FunctionCodeType.OK, response.getFuncCode());
        }

        @Test
        @DisplayName("Testing XML: invalid xml string with UNKNOWN field should throw validation error")
        void testXmlUnknownField() {
            // given
            String request = """
                    <ns2:SampleRequest xmlns="http://common.dto.coffee.icellmobilsoft.hu/commonservice" xmlns:ns2="http://dto.sampler.icellmobilsoft.hu/sample/rest/post">
                      <context>
                        <requestId>4EEMW6I0QS7JKQ01</requestId>
                        <timestamp>2023-12-05T22:23:08.52Z</timestamp>
                      </context>
                      <ns2:sample>
                        <ns2:columnA>colA</ns2:columnA>
                        <ns2:columnB>VALUE_B</ns2:columnB>
                        <ns2:columnC>VALUE_C</ns2:columnC>
                        <ns2:valamiIsmeretlen>VALUE_C</ns2:valamiIsmeretlen>
                      </ns2:sample>
                    </ns2:SampleRequest>
                    """;

            // when
            var response = responseProcessorError.postXml(request, InvalidRequestFault.class);

            // then
            Assertions.assertEquals(FunctionCodeType.ERROR, response.getFuncCode());
        }

        @Test
        @DisplayName("Testing XML: invalid xml string with UNKNOWN ENUM value should throw validation error")
        void testXmlUnknownEnumValue() {
            // given
            String request = """
                    <ns2:SampleRequest xmlns="http://common.dto.coffee.icellmobilsoft.hu/commonservice" xmlns:ns2="http://dto.sampler.icellmobilsoft.hu/sample/rest/post">
                      <context>
                        <requestId>4EEMW6I0QS7JKQ01</requestId>
                        <timestamp>2023-12-05T22:23:08.52Z</timestamp>
                      </context>
                      <ns2:sample>
                        <ns2:columnA>colA</ns2:columnA>
                        <ns2:columnB>valamiIsmeretlen</ns2:columnB>
                        <ns2:columnC>VALUE_C</ns2:columnC>
                      </ns2:sample>
                    </ns2:SampleRequest>
                    """;

            // when
            var response = responseProcessorError.postXml(request, InvalidRequestFault.class);

            // then
            Assertions.assertEquals(FunctionCodeType.ERROR, response.getFuncCode());
        }

        @Test
        @DisplayName("Testing XML: invalid xml string with UNKNOWN optional ENUM value should throw validation error")
        void testXmlUnknownOptionalEnumValue() {
            // given
            String request = """
                    <ns2:SampleRequest xmlns="http://common.dto.coffee.icellmobilsoft.hu/commonservice" xmlns:ns2="http://dto.sampler.icellmobilsoft.hu/sample/rest/post">
                      <context>
                        <requestId>4EEMW6I0QS7JKQ01</requestId>
                        <timestamp>2023-12-05T22:23:08.52Z</timestamp>
                      </context>
                      <ns2:sample>
                        <ns2:columnA>colA</ns2:columnA>
                        <ns2:columnB>VALUE_B</ns2:columnB>
                        <ns2:columnC>valamiIsmeretlen</ns2:columnC>
                      </ns2:sample>
                    </ns2:SampleRequest>
                    """;

            // when
            var response = responseProcessorError.postXml(request, InvalidRequestFault.class);

            // then
            Assertions.assertEquals(FunctionCodeType.ERROR, response.getFuncCode());
        }

        @Test
        @DisplayName("Testing XML: invalid xml string using ENUM ordinal value should throw validation error")
        void testXmlEnumOrdinalValue() {
            // given
            String request = """
                    <ns2:SampleRequest xmlns="http://common.dto.coffee.icellmobilsoft.hu/commonservice" xmlns:ns2="http://dto.sampler.icellmobilsoft.hu/sample/rest/post">
                      <context>
                        <requestId>4EEMW6I0QS7JKQ01</requestId>
                        <timestamp>2023-12-05T22:23:08.52Z</timestamp>
                      </context>
                      <ns2:sample>
                        <ns2:columnA>colA</ns2:columnA>
                        <ns2:columnB>1</ns2:columnB>
                        <ns2:columnC>2</ns2:columnC>
                      </ns2:sample>
                    </ns2:SampleRequest>
                    """;

            // when
            var response = responseProcessorError.postXml(request, InvalidRequestFault.class);

            // then
            Assertions.assertEquals(FunctionCodeType.ERROR, response.getFuncCode());
        }

        @Test
        @DisplayName("Testing XML: invalid xml string using wrong TIME format should throw validation error")
        void testXmlWrongTimeFormat() {
            // given
            String request = """
                    <ns2:SampleRequest xmlns="http://common.dto.coffee.icellmobilsoft.hu/commonservice" xmlns:ns2="http://dto.sampler.icellmobilsoft.hu/sample/rest/post">
                      <context>
                        <requestId>4EEMW6I0QS7JKQ01</requestId>
                        <timestamp>2022-03-10T12:15</timestamp>
                      </context>
                      <ns2:sample>
                        <ns2:columnA>colA</ns2:columnA>
                        <ns2:columnB>VALUE_B</ns2:columnB>
                        <ns2:columnC>VALUE_C</ns2:columnC>
                      </ns2:sample>
                    </ns2:SampleRequest>
                    """;

            // when
            var response = responseProcessorError.postXml(request, InvalidRequestFault.class);

            // then
            Assertions.assertEquals(FunctionCodeType.ERROR, response.getFuncCode());
        }

    }

}
