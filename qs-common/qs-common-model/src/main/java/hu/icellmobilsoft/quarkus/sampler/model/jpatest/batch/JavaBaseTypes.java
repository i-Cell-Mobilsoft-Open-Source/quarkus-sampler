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

import java.math.BigDecimal;
import java.math.BigInteger;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import hu.icellmobilsoft.coffee.model.base.javatime.AbstractIdentifiedAuditEntity;

/**
 * Entity for java base types.
 * 
 * @author csaba.balogh
 * @since 2.0.0
 */
@Entity
@Table(name = "JAVA_BASE_TYPES")
public class JavaBaseTypes extends AbstractIdentifiedAuditEntity {

    /**
     * Primitive byte
     */
    @Column(name = "BYTE_PRIMITIVE")
    private byte bytePrimitive;

    /**
     * Wrapper byte
     */
    @Column(name = "BYTE_WRAPPER")
    private Byte byteWrapper;

    /**
     * Primitive short
     */
    @Column(name = "SHORT_PRIMITIVE")
    private short shortPrimitive;

    /**
     * Wrapper short
     */
    @Column(name = "SHORT_WRAPPER")
    private Short shortWrapper;

    /**
     * Primitive int
     */
    @Column(name = "INT_PRIMITIVE")
    private int intPrimitive;

    /**
     * Wrapper int
     */
    @Column(name = "INT_WRAPPER")
    private Integer intWrapper;

    /**
     * Primitive long
     */
    @Column(name = "LONG_PRIMITIVE")
    private long longPrimitive;

    /**
     * Wrapper long
     */
    @Column(name = "LONG_WRAPPER")
    private Long longWrapper;

    /**
     * Primitive float
     */
    @Column(name = "FLOAT_PRIMITIVE")
    private float floatPrimitive;

    /**
     * Wrapper float
     */
    @Column(name = "FLOAT_WRAPPER")
    private Float floatWrapper;

    /**
     * Primitive double
     */
    @Column(name = "DOUBLE_PRIMITIVE")
    private double doublePrimitive;

    /**
     * Wrapper double
     */
    @Column(name = "DOUBLE_WRAPPER")
    private Double doubleWrapper;

    /**
     * Primitive boolean
     */
    @Column(name = "BOOLEAN_PRIMITIVE")
    private boolean booleanPrimitive;

    /**
     * Wrapper boolean
     */
    @Column(name = "BOOLEAN_WRAPPER")
    private Boolean booleanWrapper;

    /**
     * Primitive char
     */
    @Column(name = "CHAR_PRIMITIVE")
    private char charPrimitive;

    /**
     * Wrapper char
     */
    @Column(name = "CHAR_WRAPPER")
    private Character charWrapper;

    /**
     * String
     */
    @Column(name = "STRING_DEFAULT")
    private String string;

    /**
     * BigInteger
     */
    @Column(name = "BIG_INTEGER")
    private BigInteger bigInteger;

    /**
     * BigDecimal
     */
    @Column(name = "BIG_DECIMAL")
    private BigDecimal bigDecimal;

    public byte getBytePrimitive() {
        return bytePrimitive;
    }

    public void setBytePrimitive(byte bytePrimitive) {
        this.bytePrimitive = bytePrimitive;
    }

    public Byte getByteWrapper() {
        return byteWrapper;
    }

    public void setByteWrapper(Byte byteWrapper) {
        this.byteWrapper = byteWrapper;
    }

    public short getShortPrimitive() {
        return shortPrimitive;
    }

    public void setShortPrimitive(short shortPrimitive) {
        this.shortPrimitive = shortPrimitive;
    }

    public Short getShortWrapper() {
        return shortWrapper;
    }

    public void setShortWrapper(Short shortWrapper) {
        this.shortWrapper = shortWrapper;
    }

    public int getIntPrimitive() {
        return intPrimitive;
    }

    public void setIntPrimitive(int intPrimitive) {
        this.intPrimitive = intPrimitive;
    }

    public Integer getIntWrapper() {
        return intWrapper;
    }

    public void setIntWrapper(Integer intWrapper) {
        this.intWrapper = intWrapper;
    }

    public long getLongPrimitive() {
        return longPrimitive;
    }

    public void setLongPrimitive(long longPrimitive) {
        this.longPrimitive = longPrimitive;
    }

    public Long getLongWrapper() {
        return longWrapper;
    }

    public void setLongWrapper(Long longWrapper) {
        this.longWrapper = longWrapper;
    }

    public float getFloatPrimitive() {
        return floatPrimitive;
    }

    public void setFloatPrimitive(float floatPrimitive) {
        this.floatPrimitive = floatPrimitive;
    }

    public Float getFloatWrapper() {
        return floatWrapper;
    }

    public void setFloatWrapper(Float floatWrapper) {
        this.floatWrapper = floatWrapper;
    }

    public double getDoublePrimitive() {
        return doublePrimitive;
    }

    public void setDoublePrimitive(double doublePrimitive) {
        this.doublePrimitive = doublePrimitive;
    }

    public Double getDoubleWrapper() {
        return doubleWrapper;
    }

    public void setDoubleWrapper(Double doubleWrapper) {
        this.doubleWrapper = doubleWrapper;
    }

    public boolean isBooleanPrimitive() {
        return booleanPrimitive;
    }

    public void setBooleanPrimitive(boolean booleanPrimitive) {
        this.booleanPrimitive = booleanPrimitive;
    }

    public Boolean getBooleanWrapper() {
        return booleanWrapper;
    }

    public void setBooleanWrapper(Boolean booleanWrapper) {
        this.booleanWrapper = booleanWrapper;
    }

    public char getCharPrimitive() {
        return charPrimitive;
    }

    public void setCharPrimitive(char charPrimitive) {
        this.charPrimitive = charPrimitive;
    }

    public Character getCharWrapper() {
        return charWrapper;
    }

    public void setCharWrapper(Character charWrapper) {
        this.charWrapper = charWrapper;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public BigInteger getBigInteger() {
        return bigInteger;
    }

    public void setBigInteger(BigInteger bigInteger) {
        this.bigInteger = bigInteger;
    }

    public BigDecimal getBigDecimal() {
        return bigDecimal;
    }

    public void setBigDecimal(BigDecimal bigDecimal) {
        this.bigDecimal = bigDecimal;
    }
}
