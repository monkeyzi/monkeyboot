
package com.monkeyzi.mboot.security.exception;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.monkeyzi.mboot.common.core.result.ResponseCode;
import lombok.SneakyThrows;

/**
 * OAuth2 异常格式化
 */
public class MbootAuth2ExceptionSerializer extends StdSerializer<MbootAuth2Exception> {
	public MbootAuth2ExceptionSerializer() {
		super(MbootAuth2Exception.class);
	}

	@Override
	@SneakyThrows
	public void serialize(MbootAuth2Exception value, JsonGenerator gen, SerializerProvider provider) {
		gen.writeStartObject();
		gen.writeObjectField("code", ResponseCode.ERROR.getCode());
		gen.writeStringField("msg", value.getMessage());
		gen.writeStringField("data", value.getErrorCode());
		gen.writeBooleanField("success", Boolean.FALSE);
		gen.writeEndObject();
	}
}
