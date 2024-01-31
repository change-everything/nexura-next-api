package cn.nexura.nextapi.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 接口参数信息
 * @TableName interface_info_params
 */
@TableName(value ="interface_info_params")
@Data
public class InterfaceInfoParams implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 接口ID
     */
    @TableField(value = "interface_info_id")
    private String interfaceInfoId;

    /**
     * 参数ID
     */
    @TableField(value = "interface_param_id")
    private String interfaceParamId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}