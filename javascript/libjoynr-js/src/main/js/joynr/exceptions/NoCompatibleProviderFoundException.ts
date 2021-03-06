/*
 * #%L
 * %%
 * Copyright (C) 2019 BMW Car IT GmbH
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
import Version from "../../generated/joynr/types/Version";
import DiscoveryException from "./DiscoveryException";

class NoCompatibleProviderFoundException extends DiscoveryException {
    public interfaceName: string;
    public discoveredVersions: Version[];
    public name = "";
    /**
     * Used for serialization.
     */
    public _typeName = "joynr.exceptions.NoCompatibleProviderFoundException";
    /**
     * Constructor of NoCompatibleProviderFoundException object used for reporting
     * error conditions during discovery and arbitration when only providers
     * with incompatible versions are found. At least one such provider must
     * have been found, otherwise DiscoveryException will be used.
     *
     * @param [settings] the settings object for the constructor call
     * @param [settings.detailMessage] message containing details
     *            about the error
     * @param [settings.interfaceName] the name of the interface
     * @param [settings.discoveredVersions] list of discovered but incompatible provider versions
     */
    public constructor(settings: { detailMessage: string; interfaceName: string; discoveredVersions: Version[] }) {
        super(settings);
        Object.defineProperty(this, "name", {
            enumerable: false,
            configurable: false,
            writable: true,
            value: "NoCompatibleProviderFoundException"
        });

        this.discoveredVersions = settings.discoveredVersions;
        this.interfaceName = settings.interfaceName;
    }

    public static _typeName = "joynr.exceptions.NoCompatibleProviderFoundException";
}

export = NoCompatibleProviderFoundException;
