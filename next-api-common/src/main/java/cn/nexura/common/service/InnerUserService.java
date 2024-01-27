package cn.nexura.common.service;



import cn.nexura.common.model.entity.User;
import cn.nexura.common.model.vo.UserVO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 内部用户服务
 *
 * @author peiYP
 */
public interface InnerUserService extends IService<User> {


    /**
     * 数据库中查是否已分配给用户秘钥（accessKey）
     * @param accessKey
     * @return
     */
    User getInvokeUser(String accessKey);

}
