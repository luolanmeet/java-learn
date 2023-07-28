package pers.converter;

import org.mapstruct.factory.Mappers;
import pers.entity.UserEntity;
import pers.po.UserPO;

import java.util.Date;

/**
 * @auther ken.ck
 * @date 2023/7/28 14:28
 */
public class UserConverterTest {

    public static void main(String[] args) {

        // 在 target 工程目录下看最终生成的类

        // 可以利用 spring 托管
        // @Mapper(componentModel = "spring")
        UserConverter userConverter = Mappers.getMapper(UserConverter.class);

        UserEntity userEntity = new UserEntity();
        userEntity.setId(11L);
        userEntity.setAge(25);
        // 枚举还不知道怎么玩好点，直接写 expression 是一种方式
//        userEntity.setGender(1);
        userEntity.setUserNick("cc");
        userEntity.setHobby("singing;dancing;basketball");
        userEntity.setGmtCreate(new Date());
        userEntity.setExtendProps("{}");

        UserPO userPO = userConverter.from(userEntity);
        System.out.println(userPO);

        UserEntity temp = new UserEntity();
        userConverter.copy(userEntity, temp);
        System.out.println(temp);
    }

}
