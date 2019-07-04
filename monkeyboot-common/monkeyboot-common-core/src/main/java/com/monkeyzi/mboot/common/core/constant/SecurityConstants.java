package com.monkeyzi.mboot.common.core.constant;

/**
 * Security 常量
 *
 */
public interface SecurityConstants {
    /**
     * 用户信息分隔符
     */
    String USER_SPLIT = ":";

    /**
     * 用户信息头
     */
    String USER_HEADER = "x-user-header";

    /**
     * 角色信息头
     */
    String ROLE_HEADER = "x-role-header";

    /**
     * 基础角色
     */
    String BASE_ROLE = "ROLE_USER";

    /**
     * 授权码模式
     */
    String AUTHORIZATION_CODE = "authorization_code";

    /**
     * 密码模式
     */
    String PASSWORD = "password";

    /**
     * 刷新token
     */
    String REFRESH_TOKEN = "refresh_token";

    /**
     * 默认的处理验证码的url前缀
     */
    String DEFAULT_VALIDATE_CODE_URL_PREFIX = "/validate/code";

    /**
     * 手机号的处理验证码的url前缀
     */
    String MOBILE_VALIDATE_CODE_URL_PREFIX = "/validate/smsCode";

    /**
     * 默认生成图形验证码宽度
     */
    String DEFAULT_IMAGE_WIDTH = "100";

    /**
     * 默认生成图像验证码高度
     */
    String DEFAULT_IMAGE_HEIGHT = "35";

    /**
     * 默认生成图形验证码长度
     */
    String DEFAULT_IMAGE_LENGTH = "4";

    /**
     * 默认生成图形验证码过期时间
     */
    int DEFAULT_IMAGE_EXPIRE = 60;

    /**
     * 边框颜色，合法值： r,g,b (and optional alpha) 或者 white,black,blue.
     */
    String DEFAULT_COLOR_FONT = "blue";

    /**
     * 图片边框
     */
    String DEFAULT_IMAGE_BORDER = "no";

    /**
     * 默认图片间隔
     */
    String DEFAULT_CHAR_SPACE = "5";

    /**
     * 默认保存code的前缀
     */
    String DEFAULT_CODE_KEY = "DEFAULT_CODE_KEY";

    /**
     * 验证码文字大小
     */
    String DEFAULT_IMAGE_FONT_SIZE = "30";
    /**
     * mboot公共前缀
     */
    String  MBOOT_PREFIX = "mboot:";
    /**
     * oauth 相关前缀
     */
    String OAUTH_PREFIX = "oauth:";
    /**
     * 缓存client的redis key，这里是hash结构存储
     */
    String CACHE_CLIENT_KEY = "oauth_client_details";
    /**
     * 客户端模式
     */
    String CLIENT_CREDENTIALS = "client_credentials";

    /**
     * OAUTH模式登录处理地址
     */
    String OAUTH_LOGIN_PRO_URL = "/token/form";
    /**
     * OAUTH URL
     */
    String OAUTH_TOKEN_URL = "/oauth/token";

    /**
     * 手机号登录URL
     */
    String SMS_TOKEN_URL = "/mobile/token/sms";

    /**
     * 社交登录URL
     */
    String SOCIAL_TOKEN_URL = "/mobile/token/social";
    /**
     * 自定义登录URL
     */
    String MOBILE_TOKEN_URL = "/mobile/token/*";
    /**
     * 登出URL
     */
    String LOGOUT_URL = "/oauth/remove/token";
    /**
     * 获取授权码地址
     */
    String AUTH_CODE_URL = "/oauth/authorize";
    /**
     * 登录页面
     */
    String LOGIN_PAGE = "/token/login";


    /**
     * 项目的license
     */
    String MBOOT_LICENSE = "made by monkeyzi";

    /**
     * 用户ID字段
     */
    String DETAILS_USER_ID = "user_id";

    /**
     * 用户名字段
     */
    String DETAILS_USERNAME = "username";

    /**
     * 用户部门字段
     */
    String DETAILS_DEPT_ID = "dept_id";

    /**
     * 租户ID 字段
     */
    String DETAILS_TENANT_ID = "tenant_id";

    /**
     * 协议字段
     */
    String DETAILS_LICENSE = "license";

    /**
     * 资源服务器默认bean名称
     */
    String RESOURCE_SERVER_CONFIGURER = "resourceServerConfigurerAdapter";

    /**
     * 菜单
     */
    Integer MENU = 0;
    /**
     * 菜单根部
     */
    Integer MENU_ROOT=-1;
    /**
     * 部门树的根
     */
    Integer DEPT_ROOT=0;

}
