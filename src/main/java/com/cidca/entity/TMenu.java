package com.cidca.entity;

import javax.persistence.*;

@Entity
@Table(name = "t_menu")
public class TMenu {
    @Id
    @Column(name = "uuid")
    private String uuid;
    @Column(name = "menu_name")
    private String menuName;
    @Column(name = "menu_url")
    private String menuUrl;
    @Column(name = "menu_icon")
    private String menuIcon;
    @Column(name = "parent_id")
    private String parentId;

    @Transient
    private String checked;




    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    public String getMenuIcon() {
        return menuIcon;
    }

    public void setMenuIcon(String menuIcon) {
        this.menuIcon = menuIcon;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getChecked() {
        return checked;
    }

    public void setChecked(String checked) {
        this.checked = checked;
    }
}
