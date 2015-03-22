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
public class UserDeEncoder implements Decoder.Binary<User>, Encoder.Binary<User> {
    @Override
    public ByteBuffer encode(User user) throws EncodeException {
        byte[] result = new byte[0];
        try {
            result = Serializer.serialize(user);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ByteBuffer.wrap(result);
    }

    @Override
    public User decode(ByteBuffer byteBuffer) throws DecodeException {
        Object user = null;
        try {
            user = Serializer.deserialize(byteBuffer.array());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return User.class.cast(user);
    }

    @Override
    public boolean willDecode(ByteBuffer byteBuffer) {
        Object user = null;
        try {
            user = Serializer.deserialize(byteBuffer.array());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (user instanceof User) return true;
        return false;
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}
