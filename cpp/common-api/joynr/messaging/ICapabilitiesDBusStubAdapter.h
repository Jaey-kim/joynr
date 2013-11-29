/*
* This file was generated by the CommonAPI Generators.
*
*/
#ifndef JOYNR_MESSAGING_I_Capabilities_DBUS_STUB_ADAPTER_H_
#define JOYNR_MESSAGING_I_Capabilities_DBUS_STUB_ADAPTER_H_

#include <joynr/messaging/ICapabilitiesStub.h>

#include <CommonAPI/DBus/DBusStubAdapterHelper.h>
#include <CommonAPI/DBus/DBusFactory.h>

namespace joynr {
namespace messaging {

typedef CommonAPI::DBus::DBusStubAdapterHelper<ICapabilitiesStub> ICapabilitiesDBusStubAdapterHelper;

class ICapabilitiesDBusStubAdapter: public ICapabilitiesStubAdapter, public ICapabilitiesDBusStubAdapterHelper {
 public:
    ICapabilitiesDBusStubAdapter(
            const std::string& commonApiAddress,
            const std::string& dbusInterfaceName,
            const std::string& dbusBusName,
            const std::string& dbusObjectPath,
            const std::shared_ptr<CommonAPI::DBus::DBusProxyConnection>& dbusConnection,
            const std::shared_ptr<CommonAPI::StubBase>& stub);
    


 protected:
    virtual const char* getMethodsDBusIntrospectionXmlData() const;
};

} // namespace messaging
} // namespace joynr

#endif // JOYNR_MESSAGING_I_Capabilities_DBUS_STUB_ADAPTER_H_
