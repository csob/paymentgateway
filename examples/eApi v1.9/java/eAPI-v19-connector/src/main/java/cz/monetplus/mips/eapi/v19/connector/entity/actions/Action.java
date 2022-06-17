package cz.monetplus.mips.eapi.v19.connector.entity.actions;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import cz.monetplus.mips.eapi.v19.connector.ActionDeserializer;
import cz.monetplus.mips.eapi.v19.connector.entity.SignBase;

@JsonDeserialize(using = ActionDeserializer.class)
public abstract class Action extends SignBase {
    
}
