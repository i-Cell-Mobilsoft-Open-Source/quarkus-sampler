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
package hu.icellmobilsoft.quarkus.sample.common.csv;

import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.security.InvalidParameterException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import jakarta.enterprise.inject.spi.CDI;

import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import com.opencsv.CSVWriter;
import com.opencsv.ICSVWriter;
import com.opencsv.bean.MappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.exceptionhandler.ExceptionHandlerThrow;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import hu.icellmobilsoft.coffee.dto.exception.enums.CoffeeFaultType;
import hu.icellmobilsoft.coffee.module.csv.localization.LocalizedHeaderColumnNameWithPositionMappingStrategy;
import hu.icellmobilsoft.coffee.se.api.exception.BaseException;
import hu.icellmobilsoft.coffee.se.api.exception.BusinessException;
import hu.icellmobilsoft.coffee.se.api.exception.TechnicalException;
import hu.icellmobilsoft.coffee.se.logging.Logger;
import hu.icellmobilsoft.quarkus.sample.common.csv.strategy.HeaderColumnNameWithPositionMappingStrategy;

/**
 * Copy Csv utility from coffee, so there is pissibility to override DEFAULT_SEPARATOR and DEFAULT_QUOTE_CHARACTER.
 *
 * @author speter555
 * @since 0.14.0
 */
public abstract class CsvUtil {

    /**
     * Logger
     */
    private static final Logger LOGGER = Logger.getLogger(CsvUtil.class);

    /**
     * Default separator
     */
    protected static final char DEFAULT_SEPARATOR = ';';

    /**
     * FEFF, this is the Unicode character, which representation the UTF-8 byte order mark (EF BB BF)
     */
    private static final String UTF8_BOM = "\uFEFF";

    private CsvUtil() {
    }

    /**
     * Converts bean list to CSV.
     *
     * @param <T>
     *            type of beans
     * @param beans
     *            {@link List} of beans to convert
     * @param clazz
     *            class of beans
     * @return converted CSV text
     * @throws BaseException
     *             if CSV file cannot be generated from beans.
     */
    public static <T> String toCsv(List<T> beans, Class<T> clazz) throws BaseException {
        return toCsv(beans, clazz, getDefaultMappingStrategy(clazz));
    }

    /**
     * Converts bean list to localized CSV.
     *
     * @param <T>
     *            type of beans
     * @param beans
     *            {@link List} of beans to convert
     * @param clazz
     *            class of beans
     * @param language
     *            the language of the CSV
     * @return converted CSV text
     * @throws BaseException
     *             if CSV file cannot be generated from beans.
     */
    public static <T> String toLocalizedCsv(List<T> beans, Class<T> clazz, String language) throws BaseException {
        if (beans == null || clazz == null || language == null) {
            throw new InvalidParameterException("beans or clazz or language is null!");
        }
        CDI<Object> cdi = CDI.current();
        LocalizedHeaderColumnNameWithPositionMappingStrategy<T> mappingStrategy = cdi
                .select(LocalizedHeaderColumnNameWithPositionMappingStrategy.class)
                .get();
        mappingStrategy.setLanguage(language);
        try {
            return toCsv(beans, clazz, mappingStrategy);
        } finally {
            cdi.destroy(mappingStrategy);
        }
    }

    /**
     * Converts bean list to CSV.
     *
     * @param <T>
     *            type of beans
     * @param beans
     *            {@link List} of beans to convert
     * @param clazz
     *            class of beans
     * @param mappingStrategy
     *            the object that handle translating between the columns in the CSV file to an actual object
     * @return converted CSV text
     * @throws BaseException
     *             if CSV file cannot be generated from beans
     */
    public static <T> String toCsv(List<T> beans, Class<T> clazz, MappingStrategy<T> mappingStrategy) throws BaseException {
        if (beans == null || clazz == null || mappingStrategy == null) {
            throw new InvalidParameterException("beans or clazz or mappingStrategy is null!");
        }
        StringWriter sw = new StringWriter();
        CSVWriter csvWriter = new CSVWriter(
                sw,
                DEFAULT_SEPARATOR,
                ICSVWriter.NO_QUOTE_CHARACTER,
                ICSVWriter.DEFAULT_ESCAPE_CHARACTER,
                ICSVWriter.DEFAULT_LINE_END);
        StatefulBeanToCsv<T> bc = new StatefulBeanToCsv<>(
                initMappingStrategy(mappingStrategy, clazz),
                new ExceptionHandlerThrow(),
                true,
                csvWriter,
                new ArrayListValuedHashMap<>(),
                null);

        try {
            bc.write(beans);
            return sw.toString();
        } catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            LOGGER.error("CSV file generation error", e);
            throw new BusinessException(CoffeeFaultType.CSV_GENERATE_FAULT, e.getMessage());
        }
    }

    /**
     * Parse CSV file
     *
     * @param baseObjectSupplier
     *            main function processor.
     * @param csvEnum
     *            CSV enum defined mapping between csv file column and bean object.
     * @param csvSource
     *            CSV file.
     * @param <T>
     *            type of entity.
     * @return List of processd element.
     * @throws TechnicalException
     *             if any error occurs.
     */
    public static <T> List<T> parse(Supplier<T> baseObjectSupplier, Class<? extends Enum<? extends CsvFieldConverter<T>>> csvEnum, Reader csvSource)
            throws BaseException {
        if (Objects.isNull(baseObjectSupplier)) {
            throw new BusinessException(CoffeeFaultType.OPERATION_FAILED, "baseObjectSupplier is NULL");
        }
        if (Objects.isNull(csvEnum)) {
            throw new BusinessException(CoffeeFaultType.OPERATION_FAILED, "csvEnum is NULL");
        }
        if (Objects.isNull(csvSource)) {
            throw new BusinessException(CoffeeFaultType.OPERATION_FAILED, "csvSource is NULL");
        }

        Enum<? extends CsvFieldConverter<T>>[] enumConstants = csvEnum.getEnumConstants();
        List<T> results = new ArrayList<>();

        CSVParser parser = toCSVParser(csvSource);

        validateEncoding(parser);
        validateRequiredHeaders(parser, enumConstants);

        for (CSVRecord record : getRecords(parser)) {
            String recordValue = null;
            try {
                T target = baseObjectSupplier.get();
                for (Enum<? extends CsvFieldConverter<T>> enumConstant : enumConstants) {
                    recordValue = getRecordValue(record, enumConstant);
                    target = ((CsvFieldConverter<T>) enumConstant).assignTo(target, recordValue);
                }
                results.add(target);
            } catch (Exception e) {
                throw new BusinessException(CoffeeFaultType.OPERATION_FAILED, "Invalid record value");
            }
        }

        return results;
    }

    /**
     * Return the fault type for validateRequiredHeaders method
     * 
     * @return FaultType enum {@link CoffeeFaultType#INVALID_INPUT}
     */
    protected static Enum<?> getValidateRequiredHeadersFaultType() {
        return CoffeeFaultType.INVALID_INPUT;
    }

    private static <T> MappingStrategy<T> getDefaultMappingStrategy(Class<T> clazz) {
        MappingStrategy<T> mappingStrategy = new HeaderColumnNameWithPositionMappingStrategy<>();
        return initMappingStrategy(mappingStrategy, clazz);
    }

    private static <T> MappingStrategy<T> initMappingStrategy(MappingStrategy<T> mappingStrategy, Class<T> clazz) {
        mappingStrategy.setType(clazz);
        return mappingStrategy;
    }

    private static CSVParser toCSVParser(Reader csvSource) throws BaseException {
        try {
            return CSVFormat.EXCEL.withFirstRecordAsHeader().withDelimiter(DEFAULT_SEPARATOR).parse(csvSource);
        } catch (IOException e) {
            throw new BusinessException(CoffeeFaultType.OPERATION_FAILED, "Failed to parse csvSource to CSVParser!", e);
        }
    }

    private static void validateEncoding(CSVParser parser) throws BaseException {
        if (parser.getHeaderMap().keySet().stream().anyMatch(v -> v.contains(UTF8_BOM))) {
            throw new BusinessException(CoffeeFaultType.OPERATION_FAILED, "Invalid UTF-8 encoding, UTF-8 BOM not supported!");
        }
    }

    private static <T> void validateRequiredHeaders(CSVParser parser, Enum<? extends CsvFieldConverter<T>>[] enumConstants) throws BaseException {
        List<String> headers = Arrays.stream(enumConstants).map(Enum::name).collect(Collectors.toList());
        if (!parser.getHeaderMap().keySet().containsAll(headers)) {
            String message = MessageFormat.format("Not all required columns are present in the CSV file. Required columns are {0}", headers);
            throw new BusinessException(getValidateRequiredHeadersFaultType(), message);
        }
    }

    private static List<CSVRecord> getRecords(CSVParser parser) throws BaseException {
        try {
            return parser.getRecords();
        } catch (IOException e) {
            throw new BusinessException(CoffeeFaultType.OPERATION_FAILED, "Failed to read records!");
        }
    }

    private static <T> String getRecordValue(CSVRecord record, Enum<? extends CsvFieldConverter<T>> enumConstant) {

        return record.get(enumConstant);
    }

}
