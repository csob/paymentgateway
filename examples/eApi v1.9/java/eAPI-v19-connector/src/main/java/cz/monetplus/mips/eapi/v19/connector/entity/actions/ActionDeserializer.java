package cz.monetplus.mips.eapi.v19.connector.entity.actions;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import cz.monetplus.mips.eapi.v19.connector.entity.responses.Endpoint;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class ActionDeserializer extends StdDeserializer<Action> {

    public ActionDeserializer() {
        this(null);
    }

    public ActionDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Action deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        ObjectMapper mapper = (ObjectMapper) jsonParser.getCodec();
        JsonNode root = mapper.readTree(jsonParser);
        if(null != root.get("fingerprint")) {
            root = root.get("fingerprint");
        } else if(null != root.get("authenticate")) {
            root = root.get("authenticate");
        }

        if (root.get("browserInit") != null) {
            AuthInit fingerprint = new AuthInit();
            fingerprint.setBrowserInit(mapper.convertValue(root.get("browserInit"), Endpoint.class));
            return fingerprint;
        } else if (null != root.get("browserChallenge")) {
            AuthChallenge challenge = new AuthChallenge();
            challenge.setBrowserChallenge(mapper.convertValue(root.get("browserChallenge"), Endpoint.class));
            return challenge;
        } else if (null != root.get("sdkChallenge")) {
            AuthChallenge challenge = new AuthChallenge();
            challenge.setSdkChallenge(mapper.convertValue(root.get("sdkChallenge"), AuthChallenge.SdkChallenge.class));
            return challenge;
        } else if (null != root.get("sdkInit")) {
            AuthInit fingerprint = new AuthInit();
            fingerprint.setSdkInit(mapper.convertValue(root.get("sdkInit"), AuthInit.SdkInit.class));
            return fingerprint;
        }
        throw new RuntimeException("unable to deserialize Action");
    }

}
