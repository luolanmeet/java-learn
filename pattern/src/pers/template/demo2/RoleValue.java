package pers.template.demo2;

import pers.template.demo2.ignreo.RoleDO;

/**
 * @auther ken.ck
 * @date 2021/7/17 09:37
 */
//@Repository
public class RoleValue extends AbstractDynamicValue<Long, RoleDO> {

    /** 获取主键 */
    @Override
    protected String getKey(Long id) {
        return String.format("Value:Role:%s", id);
    }

    /** 获取值类 */
    @Override
    protected Class<RoleDO> getValueClass() {
        return RoleDO.class;
    }

}