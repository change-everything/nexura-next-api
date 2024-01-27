package cn.nexura.nextapi.service.impl.inner;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.nexura.common.model.entity.User;
import cn.nexura.common.model.vo.LoginUserVO;
import cn.nexura.common.model.vo.UserVO;
import cn.nexura.common.service.InnerUserService;
import cn.nexura.nextapi.common.ErrorCode;
import cn.nexura.nextapi.constant.CommonConstant;
import cn.nexura.nextapi.exception.BusinessException;
import cn.nexura.nextapi.mapper.UserMapper;
import cn.nexura.nextapi.model.dto.user.UserQueryRequest;
import cn.nexura.nextapi.model.enums.UserRoleEnum;
import cn.nexura.nextapi.service.UserService;
import cn.nexura.nextapi.utils.SqlUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.bean.WxOAuth2UserInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static cn.nexura.nextapi.constant.UserConstant.USER_LOGIN_STATE;

/**
 * 用户服务实现
 *
 * @author peiYP
 */
@DubboService
public class InnerUserServiceImpl extends ServiceImpl<UserMapper, User> implements InnerUserService {

    @Override
    public User getInvokeUser(String accessKey) {

        if(StrUtil.isAllBlank(accessKey)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        LambdaQueryWrapper<User> wrapper = Wrappers.lambdaQuery(User.class);
        wrapper.eq(User::getAccessKey, accessKey);
        return baseMapper.selectOne(wrapper);
    }
}
