package cz.monetplus.mips.eapi.v19.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import cz.monetplus.mips.eapi.v19.connector.entity.SignBase;

@Profile("dev")
@Service
public class DummyCryptoService implements CryptoService {

    @Override
    public boolean isSignatureValid(SignBase signBase) {
        return true;
    }

    @Override
    public void createSignature(SignBase signBase) throws MipsException {
        signBase.dttm = "20000101010101";
        signBase.signature = "YmQyODk2NjY5MDIzOThlNDQzYzUzMzY5NjZjNDhkOGM4ZjRiOGNjZGJlMmFkMzhiYjZmODg0NTE0NzdkNDQwZA==";
    }
    
}
