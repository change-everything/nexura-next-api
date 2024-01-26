package cn.nexura.nextapi.model.dto.userinterfaceinfo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author PeiYP
 * @since 2024年01月18日 17:19
 */
@Data
public class UserInterfaceInfoAddRequest implements Serializable {
    /**
     * 调用用户 id
     */
    private Long userId;
    /**
     * 接口 id
     */
    private Long interfaceInfoId;
    /**
     * 总调用次数
     */
    private Integer totalNum;
    /**
     * 剩余调用次数
     */
    private Integer leftNum;
}
