package com.literalura.literalura.model;

public interface IConverteDados {
    <T> T obterDados(String json, Class<T> classe);
}
