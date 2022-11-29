package cz.monetplus.mips.eapi.v19.connector.entity.responses;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import cz.monetplus.mips.eapi.v19.connector.entity.ApiBase;
import cz.monetplus.mips.eapi.v19.connector.entity.Signable;
import lombok.*;

import java.util.LinkedHashMap;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor @AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Endpoint extends ApiBase implements Signable {


    private String url;
    private String method;
    @JsonIgnore
    public Map<String, String> vars = new LinkedHashMap<>();

    @Override
    public String toSign() {
        StringBuilder sb = new StringBuilder();
        add(sb, url);
        add(sb, method);
        if (null != vars) vars.values().forEach(value -> add(sb, value));
        deleteLast(sb);
        return sb.toString();
    }
}