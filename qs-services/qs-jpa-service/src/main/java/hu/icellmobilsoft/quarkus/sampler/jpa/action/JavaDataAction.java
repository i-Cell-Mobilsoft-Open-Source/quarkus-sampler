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

import java.nio.charset.StandardCharsets;
import java.sql.Blob;
import java.util.List;

import javax.sql.rowset.serial.SerialBlob;

import jakarta.enterprise.inject.Model;
import jakarta.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import hu.icellmobilsoft.coffee.dto.common.commonservice.BaseRequestType;
import hu.icellmobilsoft.coffee.dto.common.commonservice.BaseResponse;
import hu.icellmobilsoft.coffee.dto.exception.InvalidParameterException;
import hu.icellmobilsoft.coffee.dto.exception.enums.CoffeeFaultType;
import hu.icellmobilsoft.coffee.jpa.helper.TransactionHelper;
import hu.icellmobilsoft.coffee.se.api.exception.BaseException;
import hu.icellmobilsoft.coffee.se.api.exception.TechnicalException;
import hu.icellmobilsoft.quarkus.sampler.common.jpa.service.ProjectHibernateBatchService;
import hu.icellmobilsoft.quarkus.sampler.common.rest.action.BaseAction;
import hu.icellmobilsoft.quarkus.sampler.dto.jpa.javadata.JavaDataInsertRequest;
import hu.icellmobilsoft.quarkus.sampler.dto.jpa.javadata.JavaDataInsertType;
import hu.icellmobilsoft.quarkus.sampler.dto.jpa.javadata.JavaDataResponse;
import hu.icellmobilsoft.quarkus.sampler.dto.jpa.javadata.JavaDataType;
import hu.icellmobilsoft.quarkus.sampler.dto.jpa.javadata.JavaDataUpdateRequest;
import hu.icellmobilsoft.quarkus.sampler.dto.jpa.javadata.JavaDataUpdateType;
import hu.icellmobilsoft.quarkus.sampler.jpa.service.JavaDataService;
import hu.icellmobilsoft.quarkus.sampler.model.jpatest.batch.JavaData;

/**
 * Action class for {@link JavaData}.
 *
 * @author csaba.balogh
 * @since 2.0.0
 */
@Model
public class JavaDataAction extends BaseAction {

    @Inject
    ProjectHibernateBatchService batchService;

    @Inject
    TransactionHelper transactionHelper;

    @Inject
    JavaDataService javaDataService;

    /**
     * Creates and inserts a {@link JavaData} entity with BatchService.
     *
     * @param javaDataInsertRequest
     *            {@link JavaDataInsertRequest}.
     * @return {@link JavaDataResponse}.
     * @throws BaseException
     *             if any exception occurs during the process.
     */
    public JavaDataResponse insertJavaData(JavaDataInsertRequest javaDataInsertRequest) throws BaseException {
        if (javaDataInsertRequest == null) {
            throw new InvalidParameterException("javaDataInsertRequest is NULL!");
        }

        JavaDataInsertType javaDataInsert = javaDataInsertRequest.getJavaData();
        boolean lobFillEnabled = javaDataInsert.isLobFillEnabled();

        String data = javaDataInsert.getData();
        byte[] dataArray = StringUtils.isNotBlank(data) ? data.getBytes(StandardCharsets.UTF_8) : null;

        JavaData javaData = new JavaData();
        fillJavaData(javaData, dataArray, lobFillEnabled);

        List<JavaData> javaDataList = List.of(javaData);

        transactionHelper.executeWithTransaction(batchService::batchInsertNative, javaDataList, JavaData.class);

        return createResponse(javaDataInsertRequest, javaData.getId(), data);
    }

    /**
     * Updates a {@link JavaData} entity with BatchService.
     *
     * @param javaDataId
     *            ID of {@link JavaData}.
     * @param javaDataUpdateRequest
     *            {@link JavaDataUpdateRequest}.
     * @return {@link JavaDataResponse}.
     * @throws BaseException
     *             if any exception occurs during the process.
     */
    public JavaDataResponse updateJavaData(String javaDataId, JavaDataUpdateRequest javaDataUpdateRequest) throws BaseException {
        if (StringUtils.isBlank(javaDataId)) {
            throw new InvalidParameterException("javaDataId is BLANK!");
        }
        if (javaDataUpdateRequest == null) {
            throw new InvalidParameterException("javaDataUpdateRequest is NULL!");
        }

        JavaDataUpdateType javaDataUpdate = javaDataUpdateRequest.getJavaData();
        boolean lobFillEnabled = javaDataUpdate.isLobFillEnabled();

        String data = javaDataUpdate.getData();
        byte[] dataArray = StringUtils.isNotBlank(data) ? data.getBytes(StandardCharsets.UTF_8) : null;

        JavaData javaData = javaDataService.findById(javaDataId);
        fillJavaData(javaData, dataArray, lobFillEnabled);

        List<JavaData> javaDataList = List.of(javaData);

        transactionHelper.executeWithTransaction(batchService::batchUpdateNative, javaDataList, JavaData.class);

        return createResponse(javaDataUpdateRequest, javaDataId, data);
    }

    /**
     * Deletes all {@link JavaData} entities with BatchService.
     *
     * @return {@link BaseResponse}.
     * @throws BaseException
     *             if any exception occurs during the process.
     */
    public BaseResponse deleteAllJavaData() throws BaseException {
        List<JavaData> javaDataList = javaDataService.findAll();
        transactionHelper.executeWithTransaction(batchService::batchDeleteNative, javaDataList, JavaData.class);
        return createBaseResponse();
    }

    private void fillJavaData(JavaData javaData, byte[] data, boolean lobFillEnabled) throws BaseException {
        if (data == null) {
            javaData.setDefaultBlob(null);
            javaData.setDefaultPrimitiveByteArray(null);
            javaData.setDefaultWrapperByteArray(null);
            javaData.setLobPrimitiveByteArray(null);
            javaData.setLobWrapperByteArray(null);
        } else {
            javaData.setDefaultPrimitiveByteArray(data);

            // "Unfortunately, the driver for PostgreSQL doesn't allow BYTEA or TEXT columns to be read via the JDBC LOB APIs.
            // On PostgreSQL, @Lob always means the OID type, so @Lob should never be used to map columns of type BYTEA or TEXT."
            // See more in the hibernate documentation.
            // Because of this, BatchService can write it, but hibernate can't read it. That's why it is not filled by default.
            if (lobFillEnabled) {
                Blob blob = createSerialBlob(data);
                javaData.setDefaultBlob(blob);
                javaData.setLobPrimitiveByteArray(data);
            }

            // By default, wrapper byte arrays are not supported after 6.2.x hibernate
            // See more: https://github.com/hibernate/hibernate-orm/blob/6.2/migration-guide.adoc#bytecharacter-mapping-changes
            // Byte[] wrapperByteArray = ArrayUtils.toObject(data);
            // javaData.setDefaultWrapperByteArray(wrapperByteArray);
            // javaData.setLobWrapperByteArray(wrapperByteArray);
        }
    }

    private Blob createSerialBlob(byte[] data) throws BaseException {
        try {
            return new SerialBlob(data);
        } catch (Exception e) {
            throw new TechnicalException(CoffeeFaultType.OPERATION_FAILED, "Could not create SerialBlob", e);
        }
    }

    private JavaDataResponse createResponse(BaseRequestType request, String javaDataId, String data) {
        JavaDataType javaData = new JavaDataType().withJavaDataId(javaDataId).withData(data);
        JavaDataResponse response = new JavaDataResponse().withJavaData(javaData);
        handleSuccessResultType(response, request);
        return response;
    }

}
