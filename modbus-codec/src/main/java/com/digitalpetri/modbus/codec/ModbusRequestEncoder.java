/*
 * Copyright 2016 Kevin Herron
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

package com.digitalpetri.modbus.codec;

import com.digitalpetri.modbus.ModbusPdu;
import com.digitalpetri.modbus.requests.*;
import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.EncoderException;
import io.netty.util.ReferenceCountUtil;

public class ModbusRequestEncoder implements ModbusPduEncoder {

    @Override
    public ByteBuf encode(ModbusPdu modbusPdu, ByteBuf buffer) throws EncoderException {
        try {
            switch (modbusPdu.getFunctionCode()) {
                case ReadCoils:
                    return encodeReadCoils((ReadCoilsRequest) modbusPdu, buffer);

                case ReadDiscreteInputs:
                    return encodeReadDiscreteInputs((ReadDiscreteInputsRequest) modbusPdu, buffer);

                case ReadHoldingRegisters:
                    return encodeReadHoldingRegisters((ReadHoldingRegistersRequest) modbusPdu, buffer);

                case ReadInputRegisters:
                    return encodeReadInputRegisters((ReadInputRegistersRequest) modbusPdu, buffer);

                case WriteSingleCoil:
                    return encodeWriteSingleCoil((WriteSingleCoilRequest) modbusPdu, buffer);

                case WriteSingleRegister:
                    return encodeWriteSingleRegister((WriteSingleRegisterRequest) modbusPdu, buffer);

                case WriteMultipleCoils:
                    return encodeWriteMultipleCoils((WriteMultipleCoilsRequest) modbusPdu, buffer);

                case WriteMultipleRegisters:
                    return encodeWriteMultipleRegisters((WriteMultipleRegistersRequest) modbusPdu, buffer);

                case MaskWriteRegister:
                    return encodeMaskWriteRegister((MaskWriteRegisterRequest) modbusPdu, buffer);

                case CommunicationTest:
                    return encodeCommunicationTest((CommunicationTestRequest) modbusPdu, buffer);

                case PBGetVariable:
                    return encodePBGetVariable((PBGetVariableRequest) modbusPdu, buffer);

                case PBSetAndGetVariable:
                    return encodePBSetAndGetVariable((PBSetAndGetVariableRequest) modbusPdu, buffer);

                case PBGetCommandPackage:
                    return encodePBGetCommandPackage((PBGetCommandPackageRequest) modbusPdu, buffer);

                case PBSetAndGetCommandPackage:
                    return encodePBSetAndGetCommandPackage((PBSetAndGetCommandPackageRequest) modbusPdu, buffer);

                default:
                    throw new EncoderException("FunctionCode not supported: " + modbusPdu.getFunctionCode());
            }
        } finally {
            ReferenceCountUtil.release(modbusPdu);
        }
    }

    public ByteBuf encodeReadCoils(ReadCoilsRequest request, ByteBuf buffer) {
        buffer.writeByte(request.getFunctionCode().getCode());
        buffer.writeShort(request.getAddress());
        buffer.writeShort(request.getQuantity());

        return buffer;
    }

    public ByteBuf encodeReadDiscreteInputs(ReadDiscreteInputsRequest request, ByteBuf buffer) {
        buffer.writeByte(request.getFunctionCode().getCode());
        buffer.writeShort(request.getAddress());
        buffer.writeShort(request.getQuantity());

        return buffer;
    }

    public ByteBuf encodeReadHoldingRegisters(ReadHoldingRegistersRequest request, ByteBuf buffer) {
        buffer.writeByte(request.getFunctionCode().getCode());
        buffer.writeShort(request.getAddress());
        buffer.writeShort(request.getQuantity());

        return buffer;
    }

    public ByteBuf encodeReadInputRegisters(ReadInputRegistersRequest request, ByteBuf buffer) {
        buffer.writeByte(request.getFunctionCode().getCode());
        buffer.writeShort(request.getAddress());
        buffer.writeShort(request.getQuantity());

        return buffer;
    }

    public ByteBuf encodeWriteSingleCoil(WriteSingleCoilRequest request, ByteBuf buffer) {
        buffer.writeByte(request.getFunctionCode().getCode());
        buffer.writeShort(request.getAddress());
        buffer.writeShort(request.getValue());

        return buffer;
    }

    public ByteBuf encodeWriteSingleRegister(WriteSingleRegisterRequest request, ByteBuf buffer) {
        buffer.writeByte(request.getFunctionCode().getCode());
        buffer.writeShort(request.getAddress());
        buffer.writeShort(request.getValue());

        return buffer;
    }

    public ByteBuf encodeWriteMultipleCoils(WriteMultipleCoilsRequest request, ByteBuf buffer) {
        buffer.writeByte(request.getFunctionCode().getCode());
        buffer.writeShort(request.getAddress());
        buffer.writeShort(request.getQuantity());

        int byteCount = (request.getQuantity() + 7) / 8;
        buffer.writeByte(byteCount);

        buffer.writeBytes(request.getValues(), byteCount);

        return buffer;
    }

    public ByteBuf encodeWriteMultipleRegisters(WriteMultipleRegistersRequest request, ByteBuf buffer) {
        buffer.writeByte(request.getFunctionCode().getCode());
        buffer.writeShort(request.getAddress());
        buffer.writeShort(request.getQuantity());

        int byteCount = request.getQuantity() * 2;
        buffer.writeByte(byteCount);

        buffer.writeBytes(request.getValues(), byteCount);

        return buffer;
    }

    public ByteBuf encodeMaskWriteRegister(MaskWriteRegisterRequest request, ByteBuf buffer) {
        buffer.writeByte(request.getFunctionCode().getCode());
        buffer.writeShort(request.getAddress());
        buffer.writeShort(request.getAndMask());
        buffer.writeShort(request.getOrMask());

        return buffer;
    }

    private ByteBuf encodeCommunicationTest(CommunicationTestRequest request, ByteBuf buffer) {
        buffer.writeByte(request.getFunctionCode().getCode());
        return buffer;
    }

    private ByteBuf encodePBGetVariable(PBGetVariableRequest request, ByteBuf buffer) {
        buffer.writeByte(request.getFunctionCode().getCode());
        buffer.writeShort(request.getAddress());
        return buffer;
    }

    private ByteBuf encodePBSetAndGetVariable(PBSetAndGetVariableRequest request, ByteBuf buffer) {
        buffer.writeByte(request.getFunctionCode().getCode());
        buffer.writeShort(request.getAddress());
        buffer.writeInt((int) request.getValue());
        return buffer;
    }

    private ByteBuf encodePBGetCommandPackage(PBGetCommandPackageRequest request, ByteBuf buffer) {
        buffer.writeByte(request.getFunctionCode().getCode());
        buffer.writeShort(request.getAddress());
        return buffer;
    }

    private ByteBuf encodePBSetAndGetCommandPackage(PBSetAndGetCommandPackageRequest request, ByteBuf buffer) {
        buffer.writeByte(request.getFunctionCode().getCode());
        buffer.writeShort(request.getAddress());
        ByteBuf values = request.getValues();
        buffer.writeShort(values.readableBytes());
        buffer.writeBytes(values);
        return buffer;
    }

}
