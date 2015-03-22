/*
 * **********************************************************************
 *
 *  Copyright (C) 2010 - 2015
 *
 *  [Component.java]
 *  JACPFX Project (https://github.com/JacpFX/JacpFX/)
 *  All rights reserved.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an "AS IS"
 *  BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 *  express or implied. See the License for the specific language
 *  governing permissions and limitations under the License.
 *
 *
 * *********************************************************************
 */

package org.jacpfx.entities;

import org.jacpfx.util.Serializer;

import javax.websocket.*;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * Created by Andy Moncsek on 10.03.15.
 */
public class ChatDeEncoder implements Decoder.Binary<ChatMessage>, Encoder.Binary<ChatMessage> {
    @Override
    public ByteBuffer encode(ChatMessage chatMessage) throws EncodeException {
        byte[] result = new byte[0];
        try {
            result = Serializer.serialize(chatMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ByteBuffer.wrap(result);
    }

    @Override
    public ChatMessage decode(ByteBuffer byteBuffer) throws DecodeException {
        Object chatMessage = null;
        try {
            chatMessage = Serializer.deserialize(byteBuffer.array());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return ChatMessage.class.cast(chatMessage);
    }

    @Override
    public boolean willDecode(ByteBuffer byteBuffer) {
        Object chatMessage = null;
        try {
            chatMessage = Serializer.deserialize(byteBuffer.array());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (chatMessage instanceof ChatMessage) return true;
        return false;
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}
