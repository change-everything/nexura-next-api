package cn.nexura.common.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 接口信息
 * &#064;TableName  interface_info
 * @author peiyp
 */
@TableName(value ="interface_info")
@Data
public class InterfaceInfo implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 描述
     */
    @TableField(value = "description")
    private String description;

    /**
     * 接口地址
     */
    @TableField(value = "url")
    private String url;

    /**
     * 请求头
     */
    @TableField(value = "request_params")
    private String requestParams;

    /**
     * 请求头
     */
    @TableField(value = "example_request_params")
    private String exampleRequestParams;

    /**
     * 请求头
     */
    @TableField(value = "request_header")
    private String requestHeader;

    /**
     * 响应头
     */
    @TableField(value = "response_header")
    private String responseHeader;

    /**
     * 接口状态（0-关闭，1-开启）
     */
    @TableField(value = "status")
    private Integer status;

    /**
     * 请求类型
     */
    @TableField(value = "method")
    private String method;

    /**
     * 创建人
     */
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    private Date updateTime;

    /**
     * 是否删除(0-未删, 1-已删)
     */
    @TableField(value = "is_delete")
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}