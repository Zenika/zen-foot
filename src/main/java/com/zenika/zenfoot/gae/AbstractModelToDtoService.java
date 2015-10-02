package com.zenika.zenfoot.gae;

import com.zenika.zenfoot.gae.mapper.MapperFacadeFactory;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by armel on 01/10/15.
 */
public class AbstractModelToDtoService<MODEL, DTO> extends AbstractGenericService<MODEL> {

    protected MapperFacadeFactory mapperFacadeFactory;

    private Class<DTO> dtoClass;

    public AbstractModelToDtoService(IGenericDAO<MODEL> dao, MapperFacadeFactory mapperFacadeFactory) {
        super(dao);
        this.mapperFacadeFactory = mapperFacadeFactory;
        dtoClass = (Class<DTO>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[1];
    }


    public DTO getFromIDAsDto(Long id) {
        MODEL model = this.getFromID(id);
        return mapperFacadeFactory.getMapper().map(model, dtoClass);
    }

    public List<DTO> getAllAsDTO() {
        List<MODEL> all = this.getAll();
        List<DTO> dtos = new ArrayList<>(all.size());
        mapperFacadeFactory.getMapper().mapAsCollection(all, dtos, dtoClass);
        return dtos;
    }

}
