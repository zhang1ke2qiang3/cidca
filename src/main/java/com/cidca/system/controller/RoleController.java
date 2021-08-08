package com.cidca.system.controller;

import com.cidca.entity.TRole;
import com.cidca.entity.TRole;
import com.cidca.entity.TUser;
import com.cidca.queryvo.RoleMenuVo;
import com.cidca.system.service.RoleService;
import com.cidca.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Controller
@RequestMapping("/role")
public class RoleController {
    @Autowired
    RoleService roleService;

    /**
     * 跳转用户列表页面
     * @return
     * @throws Exception
     */
    @RequestMapping("/list")
    public String login() throws Exception {
        return "/role/roleList";//自动把String解析为视图
    }

    /**
     * 新增或者修改角色
     * @param request
     * @param response
     * @return
     * @throws Exception
     * 坑2 @RequestBody 自动装配的语法要求：不允许在局部进行自动装配（即:只能写在method外class里）。
     *
     */
    @RequestMapping(value = "/saveOrUpdate")
    public @ResponseBody
    Map<String, Object> saveOrUpdateRole(@RequestBody TRole role, HttpServletRequest request, HttpServletResponse response) throws Exception {
        try{
            roleService.save(role);
            return StringUtil.returnMapToView("200", "角色添加成功！");
        }catch(Exception e){
            e.printStackTrace();
            return StringUtil.returnMapToView("500", "角色添加失败");
        }
    }

    /**
     * 删除用户
     * @param request
     * @param response
     * @return
     * @throws Exception
     * 坑2 @RequestBody 自动装配的语法要求：不允许在局部进行自动装配（即:只能写在method外class里）。
     *
     */
    @RequestMapping(value = "/del")
    public @ResponseBody
    Map<String, Object> delUser(@RequestBody TRole role, HttpServletRequest request, HttpServletResponse response) throws Exception {
        try{
//            TRole menu = menuService.findById(uuid);
            roleService.delete(role);
            return StringUtil.returnMapToView("200", "菜单删除成功！");
        }catch(Exception e){
            e.printStackTrace();
            return StringUtil.returnMapToView("500", "菜单删除失败！");
        }

    }


    /**
     * 菜单管理-查看详情
     * @param uuid
     * @param
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
//	@RequiresRoles("1")
//	@RequiresPermissions("external")
    @RequestMapping(value = "/roleDetail")
    public @ResponseBody Map<String, Object> roleDetail(String uuid, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        if (StringUtils.isNotEmpty(uuid)) {
            Optional<TRole> role = roleService.findById(Integer.parseInt(uuid));
            if (null != role) {
                map.put("obj", role);
                map.put("resultcode", "100");
            }
        }else{
            return StringUtil.returnMapToView("200", "未查询到信息！");
        }
        return map;
    }

    /**
     * 菜单-列表
     * @param role
     * @param pageIndex
     * @param pageSize
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @SuppressWarnings("serial")
    //	@RequiresRoles("1")
    //	@RequiresPermissions("external")
    @RequestMapping(value = "/getRoleList")
    public @ResponseBody Map<String, Object> getNoticeList(
            TRole role, Integer pageIndex, Integer pageSize,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        // 查询条件
        Specification<TRole> spec = new Specification<TRole>() {
            Predicate ca = null;
            @Override
            public Predicate toPredicate(Root<TRole> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> pList = new ArrayList<Predicate>();
                if (StringUtils.isNotEmpty(role.getRolename())) {
                    ca = cb.like(root.get("rolename").as(String.class), "%" +role.getRolename().trim()+ "%");
                    pList.add(ca);
                }
                Predicate[] pre = new Predicate[pList.size()];
                query.where(pList.toArray(pre));
                return query.getRestriction();
            }
        };
        int startIndex=pageIndex-1;
        PageRequest pageable = PageRequest.of(startIndex, pageSize);
        Page<TRole> pageList = roleService.findAll(spec, pageable);
        map.put("resultcode", "200");
        map.put("total", pageList.getTotalElements());
        map.put("rows", pageList.getContent());
        return map;
    }

    /**
     * 给角色配置菜单
     * @param request
     * @param response
     * @return
     * @throws Exception
     * 坑2 @RequestBody 自动装配的语法要求：不允许在局部进行自动装配（即:只能写在method外class里）。
     *
     */
    @RequestMapping(value = "/saveRoleMenu")
    public @ResponseBody
    Map<String, Object> saveRoleMenu(@RequestBody RoleMenuVo roleMenuVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
            String roleid =roleMenuVo.getRoleid();
            String menuids = roleMenuVo.getMenuids();
            boolean flag = roleService.saveRoleMenu(roleid,menuids);
            if(flag){
                return StringUtil.returnMapToView("200", "配置成功！");
            }else{
                return StringUtil.returnMapToView("500", "配置失败！");
            }
    }
}
