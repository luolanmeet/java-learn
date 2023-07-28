package pers.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ValueMapping;
import pers.enums.GenderEnum;
import pers.entity.UserEntity;
import pers.po.UserPO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @auther ken.ck
 * @date 2023/7/28 14:24
 */
@Mapper(uses = {GenderEnum.class})
public interface UserConverter {

    @Mapping(target = "name", source = "userNick")
    @Mapping(target = "extendProps", ignore = true)
    @Mapping(target = "hobby", expression = "java(hobbyStr2List(userEntity.getHobby()))")
    UserPO from(UserEntity userEntity);

    @Mapping(target = "id", ignore = true)
    void copy(UserEntity source, @MappingTarget UserEntity target);

    default List<String> hobbyStr2List(String hobby) {
        if (hobby == null) {
            return new ArrayList<>();
        }
        return Arrays.asList(hobby.split(";"));
    }

}
