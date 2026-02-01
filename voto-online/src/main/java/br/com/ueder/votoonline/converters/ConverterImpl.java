package br.com.ueder.votoonline.converters;

import java.util.List;

public abstract class ConverterImpl<M, D> implements IConverter<M, D> {

        public List<M> toModel(List<D> dtos) {
            if (dtos == null) {
                return List.of();
            }
            return dtos.stream().map(this::toModel).toList();
        }

        public List<D> toDto(List<M> models) {
            if (models == null) {
                return List.of();
            }
            return models.stream().map(this::toDto).toList();
       }
}