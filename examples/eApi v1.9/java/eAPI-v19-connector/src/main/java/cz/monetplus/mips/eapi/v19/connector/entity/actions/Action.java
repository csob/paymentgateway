package cz.monetplus.mips.eapi.v19.connector.entity.actions;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import cz.monetplus.mips.eapi.v19.connector.entity.ApiBase;
import cz.monetplus.mips.eapi.v19.connector.entity.Signable;

@JsonDeserialize(using = ActionDeserializer.class)
public abstract class Action extends ApiBase implements Signable {
    
}
