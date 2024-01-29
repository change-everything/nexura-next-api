package cn.nexura.nextapi.model.dto.interfaceinfo;

import lombok.Data;

import java.io.Serializable;

/**
 * 删除请求
 *
 * @author peiYP
 */
@Data
public class InterfaceInfoInvokeRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 用户请求
     */
    private String userRequestParams;

    /**
     * 名称
     */
    private String name;

    /**
     * 接口地址
     */
    private String url;


    /**
     * 请求类型
     */
    private String method;



    private static final long serialVersionUID = 1L;
}