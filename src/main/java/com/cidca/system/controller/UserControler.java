package com.cidca.system.controller;

import com.cidca.common.Constants;
import com.cidca.entity.TUser;
import com.cidca.system.service.UserService;
import com.cidca.util.*;
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
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

import javax.persistence.criteria.Predicate;

@SuppressWarnings({ "unused", "restriction" })
@Controller
@RequestMapping("/user")
public class UserControler {
    @Autowired
    UserService userService;

    /**
     * 跳转用户列表页面
     * @return
     * @throws Exception
     */
    @RequestMapping("/list")
    public String login() throws Exception {
        return "/user/userlist";//自动把String解析为视图
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
    @RequestMapping(value = "/saveUser")
    public @ResponseBody
    Map<String, Object> saveMuser(@RequestBody TUser user, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TUser vo = userService.save(user);
        if(null != vo && vo.getUuid() != null && !"".equals(vo.getUuid())){
            return StringUtil.returnMapToView("200", "用户添加成功！");
        }else{
            return StringUtil.returnMapToView("500", "用户添加失败！");
        }
    }

    /**
     * 跳转新增或修改
     * @param request
     * @param response
     * @return
     * @throws Exception
     * 坑2 @RequestBody 自动装配的语法要求：不允许在局部进行自动装配（即:只能写在method外class里）。
     *
     */
    @RequestMapping(value = "/goUser")
    public @ResponseBody
    String goSaveOrUpdateUser(@RequestBody TUser user, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return "/user/userAddOrUpdate";
    }

    /**
     * 修改用户
     * @param request
     * @param response
     * @return
     * @throws Exception
     * 坑2 @RequestBody 自动装配的语法要求：不允许在局部进行自动装配（即:只能写在method外class里）。
     *
     */
    @RequestMapping(value = "/updateUser")
    public @ResponseBody
    Map<String, Object> updateUser(@RequestBody TUser user, HttpServletRequest request, HttpServletResponse response) throws Exception {
        try{
            userService.update(user);
            return StringUtil.returnMapToView("200", "用户更新成功！");
        }catch (Exception e){
            e.printStackTrace();
            return StringUtil.returnMapToView("500", "用户更新失败！");
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
    @RequestMapping(value = "/delUser")
    public @ResponseBody
    Map<String, Object> delUser(@RequestBody TUser user, HttpServletRequest request, HttpServletResponse response) throws Exception {
        try{
            String uuid = user.getUuid();
            userService.deleteById(uuid);
            return StringUtil.returnMapToView("200", "用户删除成功！");
        }catch (Exception e){
            e.printStackTrace();
            return StringUtil.returnMapToView("500", "用户删除成功！");
        }

    }

    /**
     * 用户-列表
     * @param user
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
    @RequestMapping(value = "/getUserList")
    public @ResponseBody Map<String, Object> getNoticeList(
            TUser user,Integer pageIndex,Integer pageSize,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        // 查询条件
        Specification<TUser> spec = new Specification<TUser>() {
            Predicate ca = null;
            @Override
            public Predicate toPredicate(Root<TUser> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> pList = new ArrayList<Predicate>();
                if (StringUtils.isNotEmpty(user.getUsername())) {
                    ca = cb.like(root.get("username").as(String.class), "%" +user.getUsername()+ "%");
                    pList.add(ca);
                }
                Predicate[] pre = new Predicate[pList.size()];
                query.where(pList.toArray(pre));
                return query.getRestriction();
            }
        };
        int startIndex=pageIndex-1;
        PageRequest pageable = PageRequest.of(startIndex, pageSize);
        Page<TUser> pageList = userService.findAll(spec, pageable);
        map.put("resultcode", "200");
        map.put("total", pageList.getTotalElements());
        map.put("rows", pageList.getContent());
        return map;
    }
}
