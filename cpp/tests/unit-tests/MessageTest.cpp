/*
 * #%L
 * %%
 * Copyright (C) 2017 BMW Car IT GmbH
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

#include <gtest/gtest.h>

#include "joynr/ImmutableMessage.h"
#include "joynr/Message.h"
#include "joynr/MutableMessage.h"
#include "joynr/DispatcherUtils.h"

#include "tests/utils/MockObjects.h"

using namespace ::testing;
using namespace joynr;

class ImmutableMessageTest : public ::testing::Test {
public:
    ImmutableMessageTest() {
        mutableMessage.setSender("sender");
        mutableMessage.setRecipient("recipient");
        mutableMessage.setExpiryDate(DispatcherUtils::convertTtlToAbsoluteTime(1234567));
        mutableMessage.setReplyTo("replyTo");
        mutableMessage.setEffort("effort");
        mutableMessage.setPayload("payload");
    }

protected:
    MutableMessage mutableMessage;

private:
    DISALLOW_COPY_AND_ASSIGN(ImmutableMessageTest);
};

TEST_F(ImmutableMessageTest, TestIsReceivedFromGlobalInMutableMessage)
{
    auto immutableMessage = mutableMessage.getImmutableMessage();
    const bool expectedValue = true;
    immutableMessage->setReceivedFromGlobal(expectedValue);
    EXPECT_EQ(immutableMessage->isReceivedFromGlobal(), expectedValue);
}

TEST_F(ImmutableMessageTest, TestIsLocalInMutableMessage)
{
    const bool expectedValue = true;
    mutableMessage.setLocalMessage(expectedValue);
    EXPECT_EQ(mutableMessage.isLocalMessage(), expectedValue);
}

TEST_F(ImmutableMessageTest, TestMessageRoundtripInMutableMessage)
{
    auto immutableMessage = mutableMessage.getImmutableMessage();

    EXPECT_EQ(mutableMessage.getSender(), immutableMessage->getSender());
    EXPECT_EQ(mutableMessage.getRecipient(), immutableMessage->getRecipient());
    EXPECT_EQ(mutableMessage.getExpiryDate(), immutableMessage->getExpiryDate());
    EXPECT_EQ(mutableMessage.getId(), immutableMessage->getId());
    EXPECT_EQ(mutableMessage.getType(), immutableMessage->getType());
    EXPECT_EQ(mutableMessage.getEffort(), *(immutableMessage->getEffort()));

    smrf::ByteArrayView byteArrayView = immutableMessage->getUnencryptedBody();
    std::string payload(byteArrayView.data(), byteArrayView.data() + byteArrayView.size());
    EXPECT_EQ(mutableMessage.getPayload(), payload);
}
