/*
 * Copyright 2010 JBoss, a divison Red Hat, Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jboss.errai.bus.server;

import org.jboss.errai.bus.client.api.messaging.Message;
import org.jboss.errai.bus.client.api.messaging.MessageCallback;
import org.jboss.errai.bus.client.api.base.MessageBuilder;
import org.jboss.errai.bus.client.tests.support.SType;
import org.jboss.errai.bus.server.annotations.Service;

/**
 * User: christopherbrock
 * Date: 26-Jul-2010
 * Time: 5:13:29 PM
 */
@Service
public class TestService1 implements MessageCallback {
  public void callback(Message message) {
    SType sType = message.get(SType.class, "SType");
    System.out.println("SERVER: " + sType);

    MessageBuilder.createConversation(message)
        .subjectProvided()
        .with("SType", sType)
        .done().reply();
  }
}
