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
package hu.icellmobilsoft.quarkus.sampler.common.jpa.service;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import org.hibernate.type.BasicType;
import org.hibernate.type.SqlTypes;

import hu.icellmobilsoft.coffee.model.base.javatime.AbstractIdentifiedAuditEntity;
import hu.icellmobilsoft.coffee.tool.utils.date.DateUtil;
import hu.icellmobilsoft.frappee.hibernate.batch.HibernateBatchService;
import hu.icellmobilsoft.frappee.hibernate.util.HibernateEntityHelper;
import hu.icellmobilsoft.quarkus.sampler.common.jpa.EntityHelper;

/**
 * Real batch save with JPA
 * 
 * @author imre.scheffer
 * @author czenczl
 * @since 0.1.0
 *
 */
@Dependent
public class ProjectHibernateBatchService extends HibernateBatchService {

    private final EntityHelper entityHelper;

    @Inject
    public ProjectHibernateBatchService(EntityHelper entityHelper, EntityManager em, HibernateEntityHelper hibernateEntityHelper) {
        super(em, hibernateEntityHelper);
        this.entityHelper = entityHelper;
    }

    @Override
    protected void setBasicTypePsObject(PreparedStatement ps, int parameterIndex, BasicType<?> basicType, Object value) throws SQLException {
        switch (basicType.getJdbcType().getJdbcTypeCode()) {
        case SqlTypes.DATE:
            setDatePsObject(ps, parameterIndex, value);
            break;
        case SqlTypes.TIME:
        case SqlTypes.TIME_WITH_TIMEZONE:
            setTimePsObject(ps, parameterIndex, value);
            break;
        case SqlTypes.TIMESTAMP:
        case SqlTypes.TIMESTAMP_UTC:
        case SqlTypes.TIMESTAMP_WITH_TIMEZONE:
            setTimestampPsObject(ps, parameterIndex, value);
            break;
        case SqlTypes.BOOLEAN:
            setBooleanPsObject(ps, parameterIndex, value);
            break;
        case SqlTypes.CHAR:
            ps.setString(parameterIndex, String.valueOf(value));
            break;
        case SqlTypes.BLOB:
        case SqlTypes.VARBINARY:
        case SqlTypes.LONGVARBINARY:
            setBinaryPsObject(ps, parameterIndex, value);
            break;
        case SqlTypes.TINYINT:
            if (value instanceof Enum) {
                Enum<?> v = (Enum<?>) value;
                ps.setInt(parameterIndex, v.ordinal());
            }
            if (value instanceof Byte) {
                ps.setByte(parameterIndex, (Byte) value);
            }
            break;
        case SqlTypes.VARCHAR:
            ps.setString(parameterIndex, String.valueOf(value));
            break;
        default:
            ps.setObject(parameterIndex, value);
        }
    }

    @Override
    protected <E> void handleInsertAudit(E entity) {
        if (entity instanceof AbstractIdentifiedAuditEntity) {
            AbstractIdentifiedAuditEntity e = (AbstractIdentifiedAuditEntity) entity;
            e.setCreationDate(DateUtil.nowUTC());
            e.setCreatorUser(entityHelper.currentUser());
        }
    }

    @Override
    protected <E> void handleUpdateAudit(E entity) {
        if (entity instanceof AbstractIdentifiedAuditEntity) {
            AbstractIdentifiedAuditEntity e = (AbstractIdentifiedAuditEntity) entity;
            e.setModificationDate(DateUtil.nowUTC());
            e.setModifierUser(entityHelper.currentUser());
        }
    }

}
