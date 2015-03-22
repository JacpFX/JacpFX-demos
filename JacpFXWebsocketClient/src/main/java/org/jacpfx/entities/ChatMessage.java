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

import java.io.Serializable;

/**
 * Created by Andy Moncsek on 10.03.15.
 */
public class ChatMessage implements Serializable {
    private String message;

    private String sessionId;

    private String targetName;

    private String sourceName;


    public ChatMessage() {

    }

    public ChatMessage(String message, String name) {
        this(message, null, name,null);
    }

    public ChatMessage(String message, String sessionId, String targetName, String sourceName) {
        this.message = message;
        this.sessionId = sessionId;
        this.targetName = targetName;
        this.sourceName = sourceName;
    }


    public String getTargetName() {
        return this.targetName;
    }

    public String getSourceName() {
        return this.sourceName;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSessionId() {
        return this.sessionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChatMessage)) return false;

        ChatMessage that = (ChatMessage) o;

        if (this.message != null ? !this.message.equals(that.message) : that.message != null) return false;
        if (this.sessionId != null ? !this.sessionId.equals(that.sessionId) : that.sessionId != null) return false;
        if (this.sourceName != null ? !this.sourceName.equals(that.sourceName) : that.sourceName != null) return false;
        if (this.targetName != null ? !this.targetName.equals(that.targetName) : that.targetName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = this.message != null ? this.message.hashCode() : 0;
        result = 31 * result + (this.sessionId != null ? this.sessionId.hashCode() : 0);
        result = 31 * result + (this.targetName != null ? this.targetName.hashCode() : 0);
        result = 31 * result + (this.sourceName != null ? this.sourceName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ChatMessage{" +
                "message='" + message + '\'' +
                ", sessionId='" + sessionId + '\'' +
                ", targetName='" + targetName + '\'' +
                ", sourceName='" + sourceName + '\'' +
                '}';
    }
}
