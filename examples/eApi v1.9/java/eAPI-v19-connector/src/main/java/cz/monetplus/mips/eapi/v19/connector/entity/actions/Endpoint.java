package cz.monetplus.mips.eapi.v19.connector.entity.actions;

import com.fasterxml.jackson.annotation.JsonIgnore;
import cz.monetplus.mips.eapi.v19.connector.entity.SignBase;

import java.util.LinkedHashMap;
import java.util.Map;

public class Endpoint extends SignBase {
    public String url;
    public String method;
    @JsonIgnore
    public Map<String, String> vars = new LinkedHashMap<>();

    @Override
    public String toSign() {
        StringBuilder sb = new StringBuilder();
        add(sb, method);
        add(sb, url);
        deleteLast(sb);
        if (null != vars) vars.values().forEach(value -> add(sb, value));
        return sb.toString();
    }
}