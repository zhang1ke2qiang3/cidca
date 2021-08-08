package com.cidca.system.controller;

import com.cidca.common.Constants;
import com.cidca.entity.TMenu;
import com.cidca.entity.TMenu;
import com.cidca.entity.TMuser;
import com.cidca.system.service.MenuService;
import com.cidca.system.service.SystemService;
import com.cidca.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
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
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    MenuService menuService;
    @Autowired
    SystemService systemService;

    /**
     * 跳转菜单列表页面
     * @return
     * @throws Exception
     */
    @RequestMapping("/list")
    public String login() throws Exception {
        return "/menu/menulist";//自动把String解析为视图
    }

    /**
     * 新增用户
     * @param request
     * @param response
     * @return
     * @throws Exception
     * 坑2 @RequestBody 自动装配的语法要求：不允许在局部进行自动装配（即:只能写在method外class里）。
     *
     */
    @RequestMapping(value = "/saveOrUpdate")
    public @ResponseBody
    Map<String, Object> saveOrUpdateMuser(@RequestBody TMenu menu, HttpServletRequest request, HttpServletResponse response) throws Exception {
        try{
            if("".equals(menu.getUuid())){
                String uuid = UUID.randomUUID().toString().replace("-","");
                menu.setUuid(uuid);
            }
            menuService.saveOrUpdate(menu);
            return StringUtil.returnMapToView("200", "菜单保存成功");
        }catch(Exception e){
            e.printStackTrace();
            return StringUtil.returnMapToView("500", "菜单保存失败！");
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
    Map<String, Object> delUser(@RequestBody TMenu menu, HttpServletRequest request, HttpServletResponse response) throws Exception {
        try{
//            TMenu menu = menuService.findById(uuid);
            menuService.delete(menu.getUuid());
            return StringUtil.returnMapToView("200", "菜单删除成功！");
        }catch(Exception e){
            e.printStackTrace();
            return StringUtil.returnMapToView("500", "菜单删除失败！");
        }

    }

    /**
     * 菜单-列表
     * @param menu
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
    @RequestMapping(value = "/getMenuList")
    public @ResponseBody Map<String, Object> getNoticeList(
            TMenu menu,Integer pageIndex,Integer pageSize,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        // 查询条件
        Specification<TMenu> spec = new Specification<TMenu>() {
            Predicate ca = null;
            @Override
            public Predicate toPredicate(Root<TMenu> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> pList = new ArrayList<Predicate>();
                if (StringUtils.isNotEmpty(menu.getMenuName())) {
                    ca = cb.like(root.get("menuName").as(String.class), "%" +menu.getMenuName().trim()+ "%");
                    pList.add(ca);
                }
                Predicate[] pre = new Predicate[pList.size()];
                query.where(pList.toArray(pre));
                return query.getRestriction();
            }
        };
        int startIndex=pageIndex-1;
        PageRequest pageable = PageRequest.of(startIndex, pageSize);
        Page<TMenu> pageList = menuService.findAll(spec, pageable);
        map.put("resultcode", "200");
        map.put("total", pageList.getTotalElements());
        map.put("rows", pageList.getContent());
        return map;
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
    @RequestMapping(value = "/menuDetail")
    public @ResponseBody Map<String, Object> menuDetail(String uuid, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        if (StringUtils.isNotEmpty(uuid)) {
            TMenu menu = menuService.findById(uuid);
            if (null != menu) {
                map.put("obj", menu);
                map.put("resultcode", "100");
            }
        }else{
            return StringUtil.returnMapToView("200", "未查询到信息！");
        }
        return map;
    }

    /**
     * 角色菜单
     * @param menu
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
    @RequestMapping(value = "/getRoleMenuList")
    public @ResponseBody Map<String, Object> getRoleMenuList(
            TMenu menu,Integer pageIndex,Integer pageSize,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        // 查询条件
        Specification<TMenu> spec = new Specification<TMenu>() {
            Predicate ca = null;
            @Override
            public Predicate toPredicate(Root<TMenu> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> pList = new ArrayList<Predicate>();
                if (StringUtils.isNotEmpty(menu.getMenuName())) {
                    ca = cb.like(root.get("menuName").as(String.class), "%" +menu.getMenuName().trim()+ "%");
                    pList.add(ca);
                }
                Predicate[] pre = new Predicate[pList.size()];
                query.where(pList.toArray(pre));
                return query.getRestriction();
            }
        };
        int startIndex=pageIndex-1;
        PageRequest pageable = PageRequest.of(startIndex, pageSize);
        Page<TMenu> pageList = menuService.findAll(spec, pageable);
        List menuList = pageList.getContent();
        String roleid = request.getParameter("roleid");
//        TMuser user = (TMuser) request.getSession().getAttribute(Constants.SESSION_KEY);
        String principal = (String) SecurityUtils.getSubject().getPrincipal();//用shiro获取当前登录用户名
        List roleMenuList = systemService.getMenuByRoleid(Integer.parseInt(roleid));

        List<TMenu> newMenuList = new ArrayList<TMenu>();
        if(null != menuList && null != roleMenuList){
            for(int i = 0; i < menuList.size();i++){
                TMenu rmenu = (TMenu) menuList.get(i);
                for(int j = 0; j < roleMenuList.size(); j++){
                    Map rMap = (Map)roleMenuList.get(j);
                    if(rmenu.getUuid().equals(rMap.get("uuid"))){
                        rmenu.setChecked("1");
                    }
                }
                newMenuList.add(rmenu);
            }
        }

        map.put("resultcode", "200");
        map.put("total", pageList.getTotalElements());
        map.put("rows", newMenuList);
        return map;
    }
}
