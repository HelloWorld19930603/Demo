package com.yg.shop.server.pojo.vo;

import com.yg.shop.server.constant.SystemConstant;
import lombok.Data;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotNull;
import java.util.List;


/**
 * 添加我的群组
 *
 * @author ljh
 * @date 2022/11/23 10:16
 */

public class AddMyGroup {
    /**
     * 群url，string
     */
    @NotNull
    private String url;
    /**
     * 网页用户id
     */
    @NotNull
    private String webUserId;
    /**
     * 网页用户名称,做日志用
     */
    @NotNull
    private String webUserName;
    /**
     * 群标签
     */
    private List<String> tagList;

    public String getUrl() {
        String substring = url.substring(url.length() - 1);
        if (substring.equals(SystemConstant.replace)) {
            StringBuilder sb = new StringBuilder(url);
            return sb.replace(url.length()-1,url.length(),"").toString();
        }else{
            return url;
        }
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getWebUserId() {
        return webUserId;
    }

    public void setWebUserId(String webUserId) {
        this.webUserId = webUserId;
    }

    public String getWebUserName() {
        return webUserName;
    }

    public void setWebUserName(String webUserName) {
        this.webUserName = webUserName;
    }

    public List<String> getTagList() {
        return tagList;
    }

    public void setTagList(List<String> tagList) {
        this.tagList = tagList;
    }
}
