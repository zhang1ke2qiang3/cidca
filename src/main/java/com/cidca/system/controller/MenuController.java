package com.cidca.system.controller;

import com.cidca.entity.TMenu;
import com.cidca.entity.TUser;
import com.cidca.system.service.MenuService;
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
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    MenuService menuService;

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
    @RequestMapping(value = "/save")
    public @ResponseBody
    Map<String, Object> saveOrUpdateMuser(@RequestBody TMenu menu, HttpServletRequest request, HttpServletResponse response) throws Exception {
        try{
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
            menuService.delete(menu);
            return StringUtil.returnMapToView("200", "菜单删除成功！");
        }catch(Exception e){
            e.printStackTrace();
            return StringUtil.returnMapToView("500", "菜单删除失败！");
        }

    }
}
