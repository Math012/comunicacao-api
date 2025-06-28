package com.luizalebs.comunicacao_api.infraestructure.client.config;


import com.luizalebs.comunicacao_api.infraestructure.exception.UnexpectedErrorClient;
import feign.Response;
import feign.codec.ErrorDecoder;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class FeignError implements ErrorDecoder {
    @Override
    public Exception decode(String s, Response response) {
        String msg = msgError(response);

        if (response.status() == 400) {
            throw new RuntimeException("Erro: " + msg);
        }
        throw new UnexpectedErrorClient("Erro: " + msg);

    }

    private String msgError(Response response){
        try {
            if (Objects.isNull(response)){
                return "";
            }
            return new String(response.body().asInputStream().readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
