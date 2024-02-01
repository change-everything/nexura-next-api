package cn.nexura.nextapi.model.dto.interfaceinfo;

import cn.nexura.nextapi.model.InterfaceParams;
import cn.nexura.nextapi.model.InterfaceResponse;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author PeiYP
 * @since 2024年01月18日 17:21
 */
@Data
public class InterfaceInfoUpdateRequest implements Serializable {
    /**
     * 主键
     */
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 请求参数
     */
    private List<InterfaceParams> requestParams;

    /**
     * 响应参数
     */
    private List<InterfaceResponse> responseParams;

    /**
     * 接口地址
     */
    private String url;

    /**
     * 请求头
     */
    private String requestHeader;

    /**
     * 响应头
     */
    private String responseHeader;

    /**
     * 接口状态（0-关闭，1-开启）
     */
    private Integer status;

    /**
     * 请求类型
     */
    private String method;

    private static final long serialVersionUID = 1L;
}
