/*-
 * #%L
 * Sampler
 * %%
 * Copyright (C) 2022 - 2024 i-Cell Mobilsoft Zrt.
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
package hu.icellmobilsoft.quarkus.sampler.jpa.action;

import java.util.List;

import jakarta.enterprise.inject.Model;
import jakarta.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import hu.icellmobilsoft.coffee.dto.common.commonservice.BaseRequestType;
import hu.icellmobilsoft.coffee.dto.common.commonservice.BaseResponse;
import hu.icellmobilsoft.coffee.dto.exception.InvalidParameterException;
import hu.icellmobilsoft.coffee.jpa.helper.TransactionHelper;
import hu.icellmobilsoft.coffee.se.api.exception.BaseException;
import hu.icellmobilsoft.quarkus.sampler.common.jpa.service.ProjectHibernateBatchService;
import hu.icellmobilsoft.quarkus.sampler.common.rest.action.BaseAction;
import hu.icellmobilsoft.quarkus.sampler.dto.jpa.javabasetypes.BaseJavaBaseTypesType;
import hu.icellmobilsoft.quarkus.sampler.dto.jpa.javabasetypes.JavaBaseTypesInsertRequest;
import hu.icellmobilsoft.quarkus.sampler.dto.jpa.javabasetypes.JavaBaseTypesInsertType;
import hu.icellmobilsoft.quarkus.sampler.dto.jpa.javabasetypes.JavaBaseTypesResponse;
import hu.icellmobilsoft.quarkus.sampler.dto.jpa.javabasetypes.JavaBaseTypesType;
import hu.icellmobilsoft.quarkus.sampler.dto.jpa.javabasetypes.JavaBaseTypesUpdateRequest;
import hu.icellmobilsoft.quarkus.sampler.dto.jpa.javabasetypes.JavaBaseTypesUpdateType;
import hu.icellmobilsoft.quarkus.sampler.jpa.service.JavaBaseTypesService;
import hu.icellmobilsoft.quarkus.sampler.model.jpatest.batch.JavaBaseTypes;

/**
 * Action class for {@link JavaBaseTypes}.
 * 
 * @author csaba.balogh
 * @since 2.0.0
 */
@Model
public class JavaBaseTypesAction extends BaseAction {

    @Inject
    ProjectHibernateBatchService batchService;

    @Inject
    TransactionHelper transactionHelper;

    @Inject
    JavaBaseTypesService javaBaseTypesService;

    /**
     * Creates and inserts a {@link JavaBaseTypes} entity with BatchService.
     *
     * @param javaBaseTypesInsertRequest
     *            {@link JavaBaseTypesInsertRequest}.
     * @return {@link JavaBaseTypesResponse}.
     * @throws BaseException
     *             if any exception occurs during the process.
     */
    public JavaBaseTypesResponse insertJavaBaseTypes(JavaBaseTypesInsertRequest javaBaseTypesInsertRequest) throws BaseException {
        if (javaBaseTypesInsertRequest == null) {
            throw new InvalidParameterException("javaBaseTypesInsertRequest is NULL!");
        }

        JavaBaseTypesInsertType javaBaseTypesInsert = javaBaseTypesInsertRequest.getJavaBaseTypes();
        JavaBaseTypes javaBaseTypes = new JavaBaseTypes();
        fillJavaBaseTypes(javaBaseTypes, javaBaseTypesInsert);

        List<JavaBaseTypes> javaBaseTypesList = List.of(javaBaseTypes);

        transactionHelper.executeWithTransaction(batchService::batchInsertNative, javaBaseTypesList, JavaBaseTypes.class);

        return createResponse(javaBaseTypesInsertRequest, javaBaseTypes);
    }

    /**
     * Updates a {@link JavaBaseTypes} entity with BatchService.
     *
     * @param javaBaseTypesId
     *            ID of {@link JavaBaseTypes}.
     * @param javaBaseTypesUpdateRequest
     *            {@link JavaBaseTypesUpdateRequest}.
     * @return {@link JavaBaseTypesResponse}.
     * @throws BaseException
     *             if any exception occurs during the process.
     */
    public JavaBaseTypesResponse updateJavaBaseTypes(String javaBaseTypesId, JavaBaseTypesUpdateRequest javaBaseTypesUpdateRequest)
            throws BaseException {
        if (StringUtils.isBlank(javaBaseTypesId)) {
            throw new InvalidParameterException("javaBaseTypesId is BLANK!");
        }
        if (javaBaseTypesUpdateRequest == null) {
            throw new InvalidParameterException("javaBaseTypesUpdateRequest is NULL!");
        }

        JavaBaseTypesUpdateType javaBaseTypesUpdate = javaBaseTypesUpdateRequest.getJavaBaseTypes();
        JavaBaseTypes javaBaseTypes = javaBaseTypesService.findById(javaBaseTypesId);
        fillJavaBaseTypes(javaBaseTypes, javaBaseTypesUpdate);

        List<JavaBaseTypes> javaBaseTypesList = List.of(javaBaseTypes);

        transactionHelper.executeWithTransaction(batchService::batchUpdateNative, javaBaseTypesList, JavaBaseTypes.class);

        return createResponse(javaBaseTypesUpdateRequest, javaBaseTypes);
    }

    /**
     * Deletes all {@link JavaBaseTypes} entities with BatchService.
     *
     * @return {@link BaseResponse}.
     * @throws BaseException
     *             if any exception occurs during the process.
     */
    public BaseResponse deleteAllJavaBaseTypes() throws BaseException {
        List<JavaBaseTypes> javaBaseTypesList = javaBaseTypesService.findAll();
        transactionHelper.executeWithTransaction(batchService::batchDeleteNative, javaBaseTypesList, JavaBaseTypes.class);
        return createBaseResponse();
    }

    private void fillJavaBaseTypes(JavaBaseTypes javaBaseTypes, BaseJavaBaseTypesType baseJavaBaseTypes) {
        javaBaseTypes.setBytePrimitive(baseJavaBaseTypes.getBytePrimitive());
        javaBaseTypes.setByteWrapper(baseJavaBaseTypes.getByteWrapper());
        javaBaseTypes.setShortPrimitive(baseJavaBaseTypes.getShortPrimitive());
        javaBaseTypes.setShortWrapper(baseJavaBaseTypes.getShortWrapper());
        javaBaseTypes.setIntPrimitive(baseJavaBaseTypes.getIntPrimitive());
        javaBaseTypes.setIntWrapper(baseJavaBaseTypes.getIntWrapper());
        javaBaseTypes.setLongPrimitive(baseJavaBaseTypes.getLongPrimitive());
        javaBaseTypes.setLongWrapper(baseJavaBaseTypes.getLongWrapper());
        javaBaseTypes.setFloatPrimitive(baseJavaBaseTypes.getFloatPrimitive());
        javaBaseTypes.setFloatWrapper(baseJavaBaseTypes.getFloatWrapper());
        javaBaseTypes.setDoublePrimitive(baseJavaBaseTypes.getDoublePrimitive());
        javaBaseTypes.setDoubleWrapper(baseJavaBaseTypes.getDoubleWrapper());
        javaBaseTypes.setBooleanPrimitive(baseJavaBaseTypes.isBooleanPrimitive());
        javaBaseTypes.setBooleanWrapper(baseJavaBaseTypes.isBooleanWrapper());
        javaBaseTypes.setCharPrimitive(baseJavaBaseTypes.getCharPrimitive() == null ? '0' : baseJavaBaseTypes.getCharPrimitive().charAt(0));
        javaBaseTypes.setCharWrapper(baseJavaBaseTypes.getCharWrapper() == null ? null : baseJavaBaseTypes.getCharWrapper().charAt(0));
        javaBaseTypes.setString(baseJavaBaseTypes.getString());
        javaBaseTypes.setBigInteger(baseJavaBaseTypes.getBigInteger());
        javaBaseTypes.setBigDecimal(baseJavaBaseTypes.getBigDecimal());
    }

    private JavaBaseTypesResponse createResponse(BaseRequestType request, JavaBaseTypes javaBaseTypes) {
        JavaBaseTypesType javaBaseTypesType = new JavaBaseTypesType() //
                .withJavaBaseTypesId(javaBaseTypes.getId())
                .withBytePrimitive(javaBaseTypes.getBytePrimitive())
                .withByteWrapper(javaBaseTypes.getByteWrapper())
                .withShortPrimitive(javaBaseTypes.getShortPrimitive())
                .withShortWrapper(javaBaseTypes.getShortWrapper())
                .withIntPrimitive(javaBaseTypes.getIntPrimitive())
                .withIntWrapper(javaBaseTypes.getIntWrapper())
                .withLongPrimitive(javaBaseTypes.getLongPrimitive())
                .withLongWrapper(javaBaseTypes.getLongWrapper())
                .withFloatPrimitive(javaBaseTypes.getFloatPrimitive())
                .withFloatWrapper(javaBaseTypes.getFloatWrapper())
                .withDoublePrimitive(javaBaseTypes.getDoublePrimitive())
                .withDoubleWrapper(javaBaseTypes.getDoubleWrapper())
                .withBooleanPrimitive(javaBaseTypes.isBooleanPrimitive())
                .withBooleanWrapper(javaBaseTypes.getBooleanWrapper())
                .withCharPrimitive(String.valueOf(javaBaseTypes.getCharPrimitive()))
                .withCharWrapper(javaBaseTypes.getCharWrapper() == null ? null : String.valueOf(javaBaseTypes.getCharWrapper()))
                .withString(javaBaseTypes.getString())
                .withBigInteger(javaBaseTypes.getBigInteger())
                .withBigDecimal(javaBaseTypes.getBigDecimal());

        JavaBaseTypesResponse response = new JavaBaseTypesResponse().withJavaBaseTypes(javaBaseTypesType);
        handleSuccessResultType(response, request);
        return response;
    }
}
