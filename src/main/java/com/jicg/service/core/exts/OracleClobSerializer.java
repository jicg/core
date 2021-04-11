package  com.jicg.service.core.exts;

import cn.hutool.db.sql.SqlUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Clob;
import java.sql.SQLException;

/**
 * @author jicg on 2021/4/1
 */
public class OracleClobSerializer extends JsonSerializer<Clob> {
    @Override
    public void serialize(Clob clob, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(SqlUtil.clobToStr(clob));
    }
}