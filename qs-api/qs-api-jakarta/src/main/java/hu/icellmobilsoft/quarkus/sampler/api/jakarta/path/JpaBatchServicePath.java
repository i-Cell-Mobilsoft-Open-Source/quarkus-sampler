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
package hu.icellmobilsoft.quarkus.sampler.api.jakarta.path;

/**
 * PATHS for jpa-batch-service
 * 
 * @author csaba.balogh
 * @since 2.0.0
 */
public class JpaBatchServicePath extends ServicePath {

    /**
     * /rest/jpaBatchService
     */
     public static final String REST_JPA_BATCH_SERVICE = REST + "/jpaBatchService";

    /**
     * /rest/jpaBatchService/emptyEntity
     */
     public static final String REST_JPA_BATCH_SERVICE_EMPTY_ENTITY = REST_JPA_BATCH_SERVICE + "/emptyEntity";

    /**
     * /rest/jpaBatchService/jpaAssociation
     */
     public static final String REST_JPA_BATCH_SERVICE_JPA_ASSOCIATION = REST_JPA_BATCH_SERVICE + "/jpaAssociation";

    /**
     * /rest/jpaBatchService/javaData
     */
     public static final String REST_JPA_BATCH_SERVICE_JAVA_DATA = REST_JPA_BATCH_SERVICE + "/javaData";

    /**
     * /rest/jpaBatchService/javaEnumTypes
     */
     public static final String REST_JPA_BATCH_SERVICE_JAVA_ENUM_TYPES = REST_JPA_BATCH_SERVICE + "/javaEnumTypes";

    /**
     * /rest/jpaBatchService/javaBaseTypes
     */
     public static final String REST_JPA_BATCH_SERVICE_JAVA_BASE_TYPES = REST_JPA_BATCH_SERVICE + "/javaBaseTypes";

    /**
     * /rest/jpaBatchService/javaDateAndTime
     */
     public static final String REST_JPA_BATCH_SERVICE_JAVA_DATE_AND_TIME = REST_JPA_BATCH_SERVICE + "/javaDateAndTime";

    /**
     * /rest/jpaBatchService/jpaConverterEntity
     */
     public static final String REST_JPA_BATCH_SERVICE_JPA_CONVERTER_ENTITY = REST_JPA_BATCH_SERVICE + "/jpaConverterEntity";

    /**
     * /insert
     */
     public static final String INSERT = "/insert";

    /**
     * /update
     */
     public static final String UPDATE = "/update";

    /**
     * /delete
     */
     public static final String DELETE = "/delete";

    /**
     * jpaAssociationId
     */
     public static final String PARAM_JPA_ASSOCIATION_ID = "jpaAssociationId";

    /**
     * /update/{jpaAssociationId}
     */
     public static final String UPDATE_JPA_ASSOCIATION_ID = UPDATE + "/{" + PARAM_JPA_ASSOCIATION_ID + "}";

    /**
     * javaDataId
     */
     public static final String PARAM_JAVA_DATA_ID = "javaDataId";

    /**
     * /update/{javaDataId}
     */
     public static final String UPDATE_JAVA_DATA_ID = UPDATE + "/{" + PARAM_JAVA_DATA_ID + "}";

    /**
     * javaEnumTypesId
     */
     public static final String PARAM_JAVA_ENUM_TYPES_ID = "javaEnumTypesId";

    /**
     * /update/{javaEnumTypesId}
     */
     public static final String UPDATE_JAVA_ENUM_TYPES_ID = UPDATE + "/{" + PARAM_JAVA_ENUM_TYPES_ID + "}";

    /**
     * javaBaseTypesId
     */
     public static final String PARAM_JAVA_BASE_TYPES_ID = "javaBaseTypesId";

    /**
     * /update/{javaBaseTypesId}
     */
     public static final String UPDATE_JAVA_BASE_TYPES_ID = UPDATE + "/{" + PARAM_JAVA_BASE_TYPES_ID + "}";

    /**
     * javaDateAndTimeId
     */
     public static final String PARAM_JAVA_DATE_AND_TIME_ID = "javaDateAndTimeId";

    /**
     * /update/{javaDateAndTimeId}
     */
     public static final String UPDATE_JAVA_DATE_AND_TIME_ID = UPDATE + "/{" + PARAM_JAVA_DATE_AND_TIME_ID + "}";

    /**
     * jpaConverterEntityId
     */
     public static final String PARAM_JPA_CONVERTER_ENTITY_ID = "jpaConverterEntityId";

    /**
     * /update/{jpaConverterEntityId}
     */
     public static final String UPDATE_JPA_CONVERTER_ENTITY_ID = UPDATE + "/{" + PARAM_JPA_CONVERTER_ENTITY_ID + "}";

    private JpaBatchServicePath() {
        super();
    }
}
