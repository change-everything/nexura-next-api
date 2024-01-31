package cn.nexura.nextapi.model.vo;

import cn.nexura.common.model.entity.InterfaceInfo;
import cn.nexura.nextapi.model.InterfaceParams;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author PeiYP
 * @since 2024年01月28日 11:07
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class InterfaceInfoVO extends InterfaceInfo {

    /**
     * 调用次数
     */
    private Integer totalNum;

    /**
     * 创建者名称
     */
    private String userName;

    /**
     * 请求参数
     */
    private List<InterfaceParams> requestParams;

}
