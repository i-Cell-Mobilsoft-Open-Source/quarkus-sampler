/*-
 * #%L
 * Sampler
 * %%
 * Copyright (C) 2022 - 2023 i-Cell Mobilsoft Zrt.
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
package hu.icellmobilsoft.quarkus.sampler.model.jpatest.batch;

import java.sql.Blob;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

import hu.icellmobilsoft.coffee.model.base.javatime.AbstractIdentifiedAuditEntity;

/**
 * Entity for java data.
 * 
 * @author csaba.balogh
 * @since 2.0.0
 */
@Entity
@Table(name = "JAVA_DATA")
public class JavaData extends AbstractIdentifiedAuditEntity {

    /**
     * Default Blob
     */
    @Lob
    @Column(name = "DEFAULT_BLOB")
    private Blob defaultBlob;

    /**
     * Default primitive byte array
     */
    @Column(name = "DEFAULT_PRIMITIVE_BYTE_ARRAY")
    private byte[] defaultPrimitiveByteArray;

    /**
     * Default wrapper byte array
     */
    @Column(name = "DEFAULT_WRAPPER_BYTE_ARRAY")
    private byte[] defaultWrapperByteArray;

    /**
     * LOB primitive byte array
     */
    @Lob
    @Column(name = "LOB_PRIMITIVE_BYTE_ARRAY")
    private byte[] lobPrimitiveByteArray;

    /**
     * LOB wrapper byte array
     */
    @Lob
    @Column(name = "LOB_WRAPPER_BYTE_ARRAY")
    private byte[] lobWrapperByteArray;

    public Blob getDefaultBlob() {
        return defaultBlob;
    }

    public void setDefaultBlob(Blob defaultBlob) {
        this.defaultBlob = defaultBlob;
    }

    public byte[] getDefaultPrimitiveByteArray() {
        return defaultPrimitiveByteArray;
    }

    public void setDefaultPrimitiveByteArray(byte[] defaultPrimitiveByteArray) {
        this.defaultPrimitiveByteArray = defaultPrimitiveByteArray;
    }

    public byte[] getDefaultWrapperByteArray() {
        return defaultWrapperByteArray;
    }

    public void setDefaultWrapperByteArray(byte[] defaultWrapperByteArray) {
        this.defaultWrapperByteArray = defaultWrapperByteArray;
    }

    public byte[] getLobPrimitiveByteArray() {
        return lobPrimitiveByteArray;
    }

    public void setLobPrimitiveByteArray(byte[] lobPrimitiveByteArray) {
        this.lobPrimitiveByteArray = lobPrimitiveByteArray;
    }

    public byte[] getLobWrapperByteArray() {
        return lobWrapperByteArray;
    }

    public void setLobWrapperByteArray(byte[] lobWrapperByteArray) {
        this.lobWrapperByteArray = lobWrapperByteArray;
    }
}
