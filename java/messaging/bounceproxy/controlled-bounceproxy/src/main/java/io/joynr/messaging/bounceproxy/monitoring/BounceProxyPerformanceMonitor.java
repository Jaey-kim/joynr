/*
 * #%L
 * joynr::java::messaging::bounceproxy::controlled-bounceproxy
 * %%
 * Copyright (C) 2011 - 2017 BMW Car IT GmbH
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
package io.joynr.messaging.bounceproxy.monitoring;

import java.util.Map;

/**
 * Monitor for performance measures of a bounce proxy instance.
 * 
 * @author christina.strobel
 * 
 */
public interface BounceProxyPerformanceMonitor {

    /**
     * Returns performance measures as key value pairs.
     * 
     * @return performance measures as map of string-integer key-value pairs
     */
    public Map<String, Integer> getAsKeyValuePairs();

}
