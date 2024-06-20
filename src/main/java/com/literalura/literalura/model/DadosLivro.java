package com.literalura.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosLivro (@JsonProperty("title") String titulo,
                          @JsonAlias({"download_count"}) Integer totalDownloads,
                          @JsonAlias({"count"}) String count
) {
}