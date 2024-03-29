package cn.nexura.nextapi.model.dto.interfaceinfo;

import cn.nexura.nextapi.model.InterfaceParams;
import cn.nexura.nextapi.model.InterfaceResponse;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author PeiYP
 * @since 2024年01月18日 17:19
 */
@Data
public class InterfaceInfoAddRequest implements Serializable {

    /**
     * 名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 接口地址
     */
    private String url;

    /**
     * 请求参数
     */
    private List<InterfaceParams> requestParams;

    /**
     * 响应参数
     */
    private List<InterfaceResponse> responseParams;

    /**
     * 请求头
     */
    private String requestHeader;

    /**
     * 响应头
     */
    private String responseHeader;

    /**
     * 请求类型
     */
    private String method;

}
