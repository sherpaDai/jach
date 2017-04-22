/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.afrunt.jach.test;

import com.afrunt.jach.ACHMarshaller;
import com.afrunt.jach.ACHUnmarshaller;
import com.afrunt.jach.document.ACHDocument;
import com.afrunt.jach.metadata.MetadataCollector;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

/**
 * @author Andrii Frunt
 */
public class ACHMarshallerTest {
    private static final String[] ACH_FILES = {"ach.txt", "ach-iat.txt", "ach-return.txt", "ach-tr.txt","ach-payrol.txt"};

    @Test
    public void testMarshalling() {
        MetadataCollector metadataCollector = new MetadataCollector();
        ACHUnmarshaller unmarshaller = new ACHUnmarshaller(metadataCollector);

        ACHMarshaller marshaller = new ACHMarshaller(metadataCollector);


        for (String achFileName : ACH_FILES) {
            ACHDocument document = unmarshaller.unmarshal(getClass().getClassLoader().getResourceAsStream(achFileName));

            String out = marshaller.marshal(document);
            testFilesAreEquals(getClass().getClassLoader().getResourceAsStream(achFileName), new ByteArrayInputStream(out.getBytes()));
        }

    }

    private void testFilesAreEquals(InputStream is1, InputStream is2) {
        Scanner sc1 = new Scanner(is1);
        Scanner sc2 = new Scanner(is2);

        while (sc1.hasNextLine()) {
            String line1 = sc1.nextLine();
            String line2 = sc2.nextLine();
            Assert.assertEquals(line1, line2);
        }
    }
}
