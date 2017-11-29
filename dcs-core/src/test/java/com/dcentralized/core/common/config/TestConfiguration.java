/*
 * Copyright (c) 2014-2017 dCentralizedSystems, LLC. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy of
 * the License at http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed
 * under the License is distributed on an "AS IS" BASIS, without warranties or
 * conditions of any kind, EITHER EXPRESS OR IMPLIED.  See the License for the
 * specific language governing permissions and limitations under the License.
 */

package com.dcentralized.core.common.config;

import java.util.HashMap;
import java.util.Map;

/**
 * The test-scoped, write-only half of the XenonConfiguration class.
 */
public final class TestConfiguration {
    private TestConfiguration() {

    }

    private static Map<String, String> original = new HashMap<>();

    public static String override(Class<?> subsystem, String property, String value) {
        String key = ConfigurationSource.buildKey(subsystem, property);
        String old = System.getProperty(key);

        original.put(key, old);

        if (value != null) {
            System.setProperty(key, value);
        } else {
            System.clearProperty(key);
        }

        return old;
    }

    public static void restore() {
        original.forEach((key, value) -> {
            if (value != null) {
                System.setProperty(key, value);
            } else {
                System.clearProperty(key);
            }
        });
        original.clear();
    }
}
