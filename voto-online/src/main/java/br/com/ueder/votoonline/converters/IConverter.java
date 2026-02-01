package br.com.ueder.votoonline.converters;

public interface IConverter< M,  D > {

    M toModel(D dto);

    D toDto(M model);

}
