package fr.dawan.evalnico.tools;

import org.modelmapper.ModelMapper;

import fr.dawan.evalnico.dto.DG2LocationDto;
import fr.dawan.evalnico.dto.VilleDto;

public class DtoTools {

	private static ModelMapper myMapper = new ModelMapper();

	public static <TSource, TDestination> TDestination convert(TSource obj, Class<TDestination> clazz) {

		// ajouter les règles personnalisées
//		myMapper.typeMap(User.class, UserDto.class)
//		.addMappings(mapper->{
//			mapper.map(src->src.getFirstName(), UserDto::setFirstName);
//			mapper.map(src->src.getLastName(), UserDto::setLastName);
//		});
		myMapper.typeMap(DG2LocationDto.class, VilleDto.class).addMappings(mapper->{
            mapper.map(DG2LocationDto::getName, VilleDto::setNom);
            mapper.map(DG2LocationDto::getSlug, VilleDto::setSlug);
        });
		return myMapper.map(obj, clazz);
	}

}
