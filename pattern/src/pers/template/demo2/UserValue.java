package pers.template.demo2;

import pers.template.demo2.ignreo.UserDO;

/**
 * @auther ken.ck
 * @date 2021/7/17 09:35
 */
//@Repository
public class UserValue extends AbstractDynamicValue<Long, UserDO> {

    /** 获取主键 */
    @Override
    protected String getKey(Long id) {
        return String.format("Value:User:%s", id);
    }

    /** 获取值类 */
    @Override
    protected Class<UserDO> getValueClass() {
        return UserDO.class;
    }
}

