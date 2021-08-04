package com.cidca.system.controller;

import com.cidca.entity.TRole;
import com.cidca.entity.TUser;
import com.cidca.system.service.RoleService;
import com.cidca.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

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
        return "/role/rolelist";//自动把String解析为视图
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
    @RequestMapping(value = "/save")
    public @ResponseBody
    Map<String, Object> saveMuser(@RequestBody TRole role, HttpServletRequest request, HttpServletResponse response) throws Exception {
        try{
            roleService.save(role);
            return StringUtil.returnMapToView("200", "角色添加成功！");
        }catch(Exception e){
            e.printStackTrace();
            return StringUtil.returnMapToView("500", "角色添加失败");
        }
    }

    /**
     * 修改角色  暂时不用
     * @param request
     * @param response
     * @return
     * @throws Exception
     * 坑2 @RequestBody 自动装配的语法要求：不允许在局部进行自动装配（即:只能写在method外class里）。
     *
     */
    @RequestMapping(value = "/update")
    public @ResponseBody
    Map<String, Object> updateUser(@RequestBody TRole role, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return StringUtil.returnMapToView("200", "未验证手机，请获取手机验证码");
    }

    /**
     * 删除角色
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
            roleService.delete(role);
            return StringUtil.returnMapToView("200", "角色删除成功");
        }catch (Exception e){
            e.printStackTrace();
            return StringUtil.returnMapToView("500", "角色删除成功");
        }

    }
}
