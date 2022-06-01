package com.cc.module.mine.entity;

/**
 * 桌面菜单实体
 */
public class MenuIconBean {

    private String permiId;     // 957885581164019700, #权限id
    private String permiName;   // "新上房源", #权限名称
    private String permiDesc;   //"", #权限描述
    private String permiCode;   //"new_esf", #权限编码
    private String permiIcon;   // "https://images-beta.yjzf.com/UPbrands/File/system/menu//99691c29c3e14e83a88afd109b3618f8.png", #权限图标
    private int hots;        // 0, #热点标识 0 无 1 host 2 new
    private String jumpType;    // 1, #调转方式 1原窗口 2 新窗口
    private String jumpPage;    // "/pages/ershoufang/ershoufang" #跳转路由

    private String router;

    public String getPermiId() {
        return permiId;
    }

    public void setPermiId(String permiId) {
        this.permiId = permiId;
    }

    public String getPermiName() {
        return permiName;
    }

    public void setPermiName(String permiName) {
        this.permiName = permiName;
    }

    public String getPermiDesc() {
        return permiDesc;
    }

    public void setPermiDesc(String permiDesc) {
        this.permiDesc = permiDesc;
    }

    public String getPermiCode() {
        return permiCode;
    }

    public void setPermiCode(String permiCode) {
        this.permiCode = permiCode;
    }

    public String getPermiIcon() {
        return permiIcon;
    }

    public void setPermiIcon(String permiIcon) {
        this.permiIcon = permiIcon;
    }

    public int getHots() {
        return hots;
    }

    public void setHots(int hots) {
        this.hots = hots;
    }

    public String getJumpType() {
        return jumpType;
    }

    public void setJumpType(String jumpType) {
        this.jumpType = jumpType;
    }

    public String getJumpPage() {
        return jumpPage;
    }

    public void setJumpPage(String jumpPage) {
        this.jumpPage = jumpPage;
    }


    public String getRouter() {
        return router;
    }

    public void setRouter(String router) {
        this.router = router;
    }
}
