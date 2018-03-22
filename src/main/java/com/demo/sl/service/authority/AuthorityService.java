package com.demo.sl.service.authority;

import com.demo.sl.dao.authority.IAuthorityDao;
import com.demo.sl.dao.function.IFunctionDao;
import com.demo.sl.entity.Authority;
import com.demo.sl.entity.Function;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 业务层 权限
 */
@Service
public class AuthorityService {
    @Resource
    IAuthorityDao authorityDao;
    @Resource
    IFunctionDao functionDao;

    /**
     * 有条件的得到权限列表
     * @param authority 条件
     * @return 非null 符合条件的对象
     */
    public Authority getAuthorityService(Authority authority){
        return authorityDao.getAuthority(authority);
    }

    /**
     * 授权操作业务（事务）
     * @param ids 条件
     * @param createBy 条件
     * @return boolean true/false
     */
    public Boolean pz_addAuthorityService(String[] ids,String createBy){
            //删除该角色的所有权限
        Authority authority = new Authority();
        authority.setRoleId(Integer.parseInt(ids[0]));
        authorityDao.delAuthority(authority);
        //得到选定的功能查询一次数据库原因 是只有一级二级菜单 其实还有三级菜单
        String idsSqlString="";
        for (int i=1;i<ids.length;i++){
            //得到二级菜单拼接
            idsSqlString+=Integer.parseInt(ids[i])+",";
        }
        if (idsSqlString!=null&&idsSqlString.contains(",")){
            //截取
            idsSqlString=idsSqlString.substring(0,idsSqlString.lastIndexOf(","));
            //得到需要的所有功能
            List<Function> functionList = functionDao.getFunctionListByIn(idsSqlString);
            if (functionList!=null&&functionList.size()>0){
                for (Function f:functionList){
                    authority.setCreatedBy(createBy);
                    authority.setCreationTime(new Date());
                    authority.setUserTypeId("0");
                    authority.setFunctionId(f.getId());
                    authorityDao.addAuthority(authority);
                }
            }
        }
        return true;
    }
}
