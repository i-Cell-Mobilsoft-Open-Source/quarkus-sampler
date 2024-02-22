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
package hu.icellmobilsoft.quarkus.sampler.jpa.action;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import jakarta.enterprise.inject.Model;
import jakarta.inject.Inject;

import org.apache.commons.collections.CollectionUtils;

import hu.icellmobilsoft.coffee.cdi.logger.AppLogger;
import hu.icellmobilsoft.coffee.cdi.logger.ThisLogger;
import hu.icellmobilsoft.coffee.dto.common.commonservice.BaseRequest;
import hu.icellmobilsoft.coffee.dto.exception.BaseException;
import hu.icellmobilsoft.coffee.dto.exception.InvalidParameterException;
import hu.icellmobilsoft.coffee.dto.exception.TechnicalException;
import hu.icellmobilsoft.coffee.dto.exception.enums.CoffeeFaultType;
import hu.icellmobilsoft.coffee.jpa.helper.TransactionHelper;
import hu.icellmobilsoft.coffee.jpa.sql.batch.enums.Status;
import hu.icellmobilsoft.quarkus.sampler.common.jpa.EntityHelper;
import hu.icellmobilsoft.quarkus.sampler.common.jpa.service.BatchService;
import hu.icellmobilsoft.quarkus.sampler.common.rest.action.BaseAction;
import hu.icellmobilsoft.quarkus.sampler.dto.test.post.SampleResponse;
import hu.icellmobilsoft.quarkus.sampler.jpa.converter.SampleTypeConverter;
import hu.icellmobilsoft.quarkus.sampler.jpa.service.SampleEntityService;
import hu.icellmobilsoft.quarkus.sampler.model.jpatest.SampleEntity;
import hu.icellmobilsoft.quarkus.sampler.model.jpatest.enums.SampleStatus;
import hu.icellmobilsoft.quarkus.sampler.model.jpatest.enums.SampleValue;

/**
 * Sample query action
 * 
 * @author imre.scheffer
 * @since 0.1.0
 */
@Model
public class JpaSamplePostAction extends BaseAction {

    private static final String TEST_SYSTEM_USER = "55";

    @Inject
    SampleEntityService sampleEntityService;

    @Inject
    SampleTypeConverter sampleTypeConverter;

    @Inject
    TransactionHelper transactionHelper;

    @Inject
    BatchService batchService;

    @Inject
    @ThisLogger
    AppLogger log;

    /**
     * Dummy sample write and read data from DB
     * 
     * @param sampleRequest
     *            validated http entity body
     * @return Sample response with random readed data
     * @throws BaseException
     *             if error
     */
    public SampleResponse sampleWriteRead(BaseRequest sampleRequest) throws BaseException {

        // try {
        // CDI.current().select(JpaSamplePostAction.class).get().createOneNeedTransaction();
        // throw new SamplerException("Unexpected successful save without @Transactional");
        // } catch (SamplerException e) {
        // throw e;
        // } catch (BaseException e) {
        // log.info("Expected exception - no transaction: [{0}]", e.getLocalizedMessage());
        // }
        // create entity
        SampleEntity created = transactionHelper.executeWithTransaction(this::createOneNeedTransaction);

        // modify entity
        created.setStatus(SampleStatus.DONE);
        created = transactionHelper.executeWithTransaction(sampleEntityService::save, created);

        // use repository
        List<SampleEntity> samples = sampleEntityService.findAllByStatus(SampleStatus.DONE);
        if (CollectionUtils.isEmpty(samples)) {
            throw new TechnicalException("Unexpected data integrity error, cant find sample entity with DONE status!");
        }

        SampleEntity readed = sampleEntityService.findById(created.getId(), SampleEntity.class);
        if (!created.getId().equals(readed.getId()) || created.getCreationDate() == null
                || !created.getCreationDate().equals(readed.getCreationDate()) || created.getCreatorUser() == null
                || !created.getCreatorUser().equals(readed.getCreatorUser()) || created.getStatus() != readed.getStatus()) {
            throw new TechnicalException("Unexpected data integrity error, some mandatory field is empty or not equal!");
        }

        // BatchService insert/update testing
        // insert entity
        SampleEntity entityToCreate = initSampleEntity();
        entityToCreate.setModLocalDate(LocalDate.now()); // insertable false
        created = transactionHelper.executeWithTransaction(() -> batchInsertNative(entityToCreate));
        if (created.getModLocalDate() != null) {
            throw new TechnicalException("Unexpected data integrity error, insertable false field inserted!");
        }

        // update entity
        created.setStatus(SampleStatus.DONE);
        created.setCreatorUser(TEST_SYSTEM_USER); // updatable false
        created.setModLocalDate(LocalDate.now());
        SampleEntity entityToModify = created;
        SampleEntity modified = transactionHelper.executeWithTransaction(() -> batchUpdateNative(entityToModify));
        if (TEST_SYSTEM_USER.equals(modified.getCreatorUser())) {
            throw new TechnicalException("Unexpected data integrity error, updatable false field updated!");
        }

        readed = sampleEntityService.findById(created.getId(), SampleEntity.class);
        if (!created.getId().equals(readed.getId()) || created.getCreationDate() == null
                || !created.getCreationDate().equals(readed.getCreationDate()) || created.getCreatorUser() == null
                || !EntityHelper.DEFAULT_SYSTEM_USER.equals(readed.getCreatorUser()) || SampleStatus.DONE != readed.getStatus()
                || !created.getModLocalDate().equals(readed.getModLocalDate())) {
            throw new TechnicalException("Unexpected data integrity error, some mandatory field is empty or not equal!");
        }

        SampleResponse response = new SampleResponse();
        response.setSample(sampleTypeConverter.convert(readed));

        handleSuccessResultType(response, sampleRequest);
        return response;
    }

    /**
     * Create one entity with dummy data. Need transaction for success.
     *
     * @return created, persisted entity
     * @throws BaseException
     *             if error
     */
    public SampleEntity createOneNeedTransaction() throws BaseException {
        SampleEntity entity = initSampleEntity();
        return sampleEntityService.save(entity);
    }

    private SampleEntity initSampleEntity() {
        SampleEntity entity = new SampleEntity();
        entity.setLocalDateTime(LocalDateTime.now());
        entity.setStatus(SampleStatus.PROCESSING);
        entity.setInputValue("Generated");
        entity.setValue(SampleValue.VALUE_B);
        return entity;
    }

    /**
     * Creates the given entity by {@link BatchService#batchInsertNative(Collection, Class)}. Need transaction for success.
     * 
     * @param sampleEntity
     *            the entity to create
     * @return created, persisted entity
     * @throws BaseException
     *             if error
     */
    public SampleEntity batchInsertNative(SampleEntity sampleEntity) throws BaseException {
        if (sampleEntity == null) {
            throw new InvalidParameterException(CoffeeFaultType.INVALID_INPUT, "sampleEntity is missing");
        }
        Map<String, Status> resultMap = batchService.batchInsertNative(List.of(sampleEntity), SampleEntity.class);
        Optional<String> firstKey = resultMap.keySet().stream().findFirst();

        if (firstKey.isPresent()) {
            sampleEntity = sampleEntityService.findById(firstKey.get(), SampleEntity.class);
        }

        return sampleEntity;
    }

    /**
     * Updates one entity by {@link BatchService#batchUpdateNative(Collection, Class)}. Need transaction for success.
     * 
     * @param sampleEntity
     *            the entity to update
     * @return modified, persisted entity
     * @throws BaseException
     *             if error
     */
    public SampleEntity batchUpdateNative(SampleEntity sampleEntity) throws BaseException {
        if (sampleEntity == null) {
            throw new InvalidParameterException(CoffeeFaultType.INVALID_INPUT, "sampleEntity is missing");
        }
        Map<String, Status> resultMap = batchService.batchUpdateNative(List.of(sampleEntity), SampleEntity.class);
        Optional<String> firstKey = resultMap.keySet().stream().findFirst();

        if (firstKey.isPresent()) {
            sampleEntity = sampleEntityService.findById(firstKey.get(), SampleEntity.class);
        }

        return sampleEntity;
    }

}
