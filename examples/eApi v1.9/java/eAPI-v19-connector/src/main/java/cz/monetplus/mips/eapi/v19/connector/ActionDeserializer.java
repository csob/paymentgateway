package cz.monetplus.mips.eapi.v19.connector;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import cz.monetplus.mips.eapi.v19.connector.entity.actions.Action;

import java.io.IOException;

public class ActionDeserializer extends StdDeserializer<Action> {

    public ActionDeserializer() {
        this(null);
    }

    public ActionDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Action deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        TreeNode node = jsonParser.getCodec().readTree(jsonParser);

        return null;
    }
}
