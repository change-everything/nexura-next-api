package cn.nexura.nextapi.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 接口参数信息
 * @TableName interface_response
 */
@TableName(value ="interface_response")
@Data
public class InterfaceResponse implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 响应名称
     */
    @TableField(value = "response_name")
    private String responseName;

    /**
     * 描述
     */
    @TableField(value = "description")
    private String description;

    /**
     * 响应类型
     */
    @TableField(value = "response_type")
    private String responseType;

    /**
     * 示例值
     */
    @TableField(value = "example_value")
    private String exampleValue;

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