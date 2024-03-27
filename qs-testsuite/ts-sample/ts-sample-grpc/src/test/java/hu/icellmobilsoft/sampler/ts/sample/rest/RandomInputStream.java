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
package hu.icellmobilsoft.sampler.ts.sample.rest;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Random;

import hu.icellmobilsoft.coffee.se.logging.Logger;

/**
 * Generate random inputstream with specific size - default 1MB
 * 
 * @author imre.scheffer
 * @since 0.1.0
 * 
 */
public class RandomInputStream extends InputStream {

    private static Logger LOG = Logger.getLogger(RandomInputStream.class);

    private long length;

    // default 1MB
    private long maxLength = 1024 * 1024;

    private Random random = new Random();

    /**
     * Generate 1MB size inputStream with random data
     */
    public RandomInputStream() {
        super();
    }

    /**
     * Generate specified length inputStream with random data
     * 
     * @param lengthInByte
     *            Length of generated inputstream, default 1MB
     */
    public RandomInputStream(long lengthInByte) {
        super();
        this.maxLength = lengthInByte;
    }

    @Override
    public int read() throws IOException {
        if (length <= maxLength) {
            length++;
            return random.nextInt(256);
        } else {
            return -1;
        }
    }

    /**
     * Only for visual testing size
     * 
     * @param args
     *            none
     */
    public static void main(String[] args) {
        try (RandomInputStream is = new RandomInputStream(2 * 1024 * 1024)) {
            File tempFile = File.createTempFile("inputstream", "txt");
            Files.copy(is, tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            LOG.info("File: [{0}], length: [{1}] B", tempFile.getAbsolutePath(), tempFile.length());
        } catch (Exception e) {
            LOG.error("Error in writing inputstream to file: " + e.getLocalizedMessage(), e);
        }
    }

}
