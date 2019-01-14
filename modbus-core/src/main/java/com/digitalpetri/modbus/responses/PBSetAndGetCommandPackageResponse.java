/*
 * Copyright 2019 Erwin Wagasow
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.digitalpetri.modbus.responses;

import com.digitalpetri.modbus.FunctionCode;
import io.netty.buffer.ByteBuf;

public class PBSetAndGetCommandPackageResponse extends ByteBufModbusResponse{
    private final int address;

    public int getAddress() {
        return address;
    }

    public PBSetAndGetCommandPackageResponse(int address, ByteBuf data) {
        super(data, FunctionCode.PBSetAndGetCommandPackage);
        this.address = address;
    }

    public ByteBuf getValues(){
        return super.content();
    }
}