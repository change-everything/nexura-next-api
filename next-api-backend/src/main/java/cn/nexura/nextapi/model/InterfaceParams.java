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
 * @TableName interface_params
 */
@TableName(value ="interface_params")
@Data
public class InterfaceParams implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 参数名
     */
    @TableField(value = "param_name")
    private String paramName;

    /**
     * 描述
     */
    @TableField(value = "description")
    private String description;

    /**
     * 是否必填(0-必填, 1-非必填)
     */
    @TableField(value = "is_must")
    private Integer isMust;

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