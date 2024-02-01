package cn.nexura.nextapi.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.WeightRandom;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import cn.nexura.common.model.entity.InterfaceInfo;
import cn.nexura.common.model.entity.User;
import cn.nexura.nextapi.annotation.AuthCheck;
import cn.nexura.nextapi.common.*;
import cn.nexura.nextapi.config.WxOpenConfig;
import cn.nexura.nextapi.constant.CommonConstant;
import cn.nexura.nextapi.constant.UserConstant;
import cn.nexura.nextapi.exception.BusinessException;
import cn.nexura.nextapi.exception.ThrowUtils;
import cn.nexura.nextapi.model.InterfaceInfoParams;
import cn.nexura.nextapi.model.InterfaceParams;
import cn.nexura.nextapi.model.InterfaceResponse;
import cn.nexura.nextapi.model.dto.interfaceinfo.InterfaceInfoAddRequest;
import cn.nexura.nextapi.model.dto.interfaceinfo.InterfaceInfoInvokeRequest;
import cn.nexura.nextapi.model.dto.interfaceinfo.InterfaceInfoQueryRequest;
import cn.nexura.nextapi.model.dto.interfaceinfo.InterfaceInfoUpdateRequest;
import cn.nexura.nextapi.model.enums.InterfaceStatusEnum;
import cn.nexura.nextapi.model.vo.InterfaceInfoVO;
import cn.nexura.nextapi.service.*;
import cn.nexura.sdk.client.NextApiClient;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.bean.WxOAuth2UserInfo;
import me.chanjar.weixin.common.bean.oauth2.WxOAuth2AccessToken;
import me.chanjar.weixin.mp.api.WxMpService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;
import java.util.Set;


@RestController
@RequestMapping("/interfaceInfo")
@Slf4j
public class InterfaceInfoController {

    @Resource
    private InterfaceInfoService interfaceInfoService;

    @Resource
    private UserService userService;

    @Resource
    private InterfaceParamsService paramsService;

    @Resource
    private InterfaceInfoParamsService interfaceInfoParamsService;

    @Resource
    private InterfaceResponseService interfaceResponseService;


    // region 增删改查
    /**
     * 创建
     *
     * @param interfaceInfoAddRequest
     * @param request
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Long> addInterfaceInfo(@RequestBody InterfaceInfoAddRequest interfaceInfoAddRequest, HttpServletRequest request) {
        if (interfaceInfoAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        InterfaceInfo interfaceInfo = new InterfaceInfo();
        BeanUtils.copyProperties(interfaceInfoAddRequest, interfaceInfo);
        // 校验
        interfaceInfoService.validInterfaceInfo(interfaceInfo, true);
        User loginUser = userService.getLoginUser(request);
        interfaceInfo.setUserId(loginUser.getId());
        interfaceInfo.setId(IdWorker.getId());

        // 设置请求参数
        List<InterfaceParams> requestParams = interfaceInfoAddRequest.getRequestParams();
        requestParams.forEach(requestParam -> {
            String paramName = requestParam.getParamName();
            InterfaceParams existParam = paramsService.getOne(Wrappers.lambdaQuery(InterfaceParams.class)
                    .eq(InterfaceParams::getParamName, paramName));
            if (existParam == null) {
                boolean save = paramsService.save(requestParam);
                InterfaceInfoParams interfaceInfoParams = new InterfaceInfoParams();
                interfaceInfoParams.setInterfaceInfoId(interfaceInfo.getId());
                interfaceInfoParams.setInterfaceParamId(requestParam.getId());
                interfaceInfoParamsService.save(interfaceInfoParams);
            } else {
                InterfaceInfoParams interfaceInfoParams = new InterfaceInfoParams();
                interfaceInfoParams.setInterfaceInfoId(interfaceInfo.getId());
                interfaceInfoParams.setInterfaceParamId(existParam.getId());
                interfaceInfoParamsService.save(interfaceInfoParams);
            }
        });

        List<InterfaceResponse> responseParams = interfaceInfoAddRequest.getResponseParams();
        String responseParamsStr = JSONUtil.toJsonStr(responseParams);
        interfaceInfo.setResponseParamsStr(responseParamsStr);

        boolean result = interfaceInfoService.save(interfaceInfo);
        if (!result) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
        long newInterfaceInfoId = interfaceInfo.getId();
        return ResultUtils.success(newInterfaceInfoId);
    }
    /**
     * 删除
     *
     * @param deleteRequest
     * @param request
     * @return
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteInterfaceInfo(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.getLoginUser(request);
        long id = deleteRequest.getId();
        // 判断是否存在
        InterfaceInfo oldInterfaceInfo = interfaceInfoService.getById(id);
        if (oldInterfaceInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 仅本人或管理员可删除
        if (!oldInterfaceInfo.getUserId().equals(user.getId()) && !userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean b = interfaceInfoService.removeById(id);
        return ResultUtils.success(b);
    }
    /**
     * 更新
     *
     * @param interfaceInfoUpdateRequest
     * @param request
     * @return
     */
    @PostMapping("/update")
    public BaseResponse<Boolean> updateInterfaceInfo(@RequestBody InterfaceInfoUpdateRequest interfaceInfoUpdateRequest,
                                                     HttpServletRequest request) {
        if (interfaceInfoUpdateRequest == null || interfaceInfoUpdateRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        InterfaceInfo interfaceInfo = new InterfaceInfo();
        BeanUtils.copyProperties(interfaceInfoUpdateRequest, interfaceInfo);
        // 参数校验
        interfaceInfoService.validInterfaceInfo(interfaceInfo, false);
        User user = userService.getLoginUser(request);
        long id = interfaceInfoUpdateRequest.getId();
        // 判断是否存在
        InterfaceInfo oldInterfaceInfo = interfaceInfoService.getById(id);
        if (oldInterfaceInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 仅本人或管理员可修改
        if (!oldInterfaceInfo.getUserId().equals(user.getId()) && !userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }

        // 解绑所有参数
        boolean remove = interfaceInfoParamsService.remove(Wrappers.lambdaQuery(InterfaceInfoParams.class)
                .eq(InterfaceInfoParams::getInterfaceInfoId, id));

        if (!remove) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "更新失败");
        }

        // 设置请求参数
        List<InterfaceParams> requestParams = interfaceInfoUpdateRequest.getRequestParams();
        requestParams.forEach(requestParam -> {
            String paramName = requestParam.getParamName();
            InterfaceParams existParam = paramsService.getOne(Wrappers.lambdaQuery(InterfaceParams.class)
                    .eq(InterfaceParams::getParamName, paramName));
            if (existParam == null) {
                boolean save = paramsService.save(requestParam);
                if (!save) {
                    throw new BusinessException(ErrorCode.SYSTEM_ERROR, "更新失败");
                }
                InterfaceInfoParams interfaceInfoParams = new InterfaceInfoParams();
                interfaceInfoParams.setInterfaceInfoId(interfaceInfo.getId());
                interfaceInfoParams.setInterfaceParamId(requestParam.getId());
                interfaceInfoParamsService.save(interfaceInfoParams);
            } else {
                InterfaceInfoParams interfaceInfoParams = new InterfaceInfoParams();
                interfaceInfoParams.setInterfaceInfoId(interfaceInfo.getId());
                interfaceInfoParams.setInterfaceParamId(existParam.getId());
                interfaceInfoParamsService.save(interfaceInfoParams);
            }
        });

        List<InterfaceResponse> responseParams = interfaceInfoUpdateRequest.getResponseParams();
        String responseParamsStr = JSONUtil.toJsonStr(responseParams);
        interfaceInfo.setResponseParamsStr(responseParamsStr);

        boolean result = interfaceInfoService.updateById(interfaceInfo);
        return ResultUtils.success(result);
    }
    /**
     * 根据 id 获取
     *
     * @param id
     * @return
     */
    @GetMapping("/get")
    public BaseResponse<InterfaceInfoVO> getInterfaceInfoById(long id) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        InterfaceInfo interfaceInfo = interfaceInfoService.getById(id);
        InterfaceInfoVO interfaceInfoVO = new InterfaceInfoVO();
        BeanUtil.copyProperties(interfaceInfo, interfaceInfoVO);
        List<InterfaceParams> interfaceParams = interfaceInfoParamsService.selectParamsByInterfaceId(id);
        interfaceInfoVO.setRequestParams(interfaceParams);
        String responseParamsStr = interfaceInfo.getResponseParamsStr();
        if (StrUtil.isNotBlank(responseParamsStr)) {
            List<InterfaceResponse> responseParams = JSONUtil.toList(responseParamsStr, InterfaceResponse.class);
            interfaceInfoVO.setResponseParams(responseParams);
        }
        return ResultUtils.success(interfaceInfoVO);
    }
    /**
     * 获取列表（仅管理员可使用）
     *
     * @param interfaceInfoQueryRequest
     * @return
     */
    @AuthCheck(mustRole = "admin")
    @GetMapping("/list")
    public BaseResponse<List<InterfaceInfoVO>> listInterfaceInfo(InterfaceInfoQueryRequest interfaceInfoQueryRequest) {
        InterfaceInfo interfaceInfoQuery = new InterfaceInfo();
        if (interfaceInfoQueryRequest != null) {
            BeanUtils.copyProperties(interfaceInfoQueryRequest, interfaceInfoQuery);
        }
        QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>(interfaceInfoQuery);
        List<InterfaceInfo> list = interfaceInfoService.list(queryWrapper);
        List<InterfaceInfoVO> interfaceInfoList = interfaceInfoService.listInterface(list);
        return ResultUtils.success(interfaceInfoList);
    }
    /**
     * 分页获取列表
     *
     * @param interfaceInfoQueryRequest
     * @param request
     * @return
     */
    @GetMapping("/list/page")
    public BaseResponse<Page<InterfaceInfoVO>> listInterfaceInfoByPage(InterfaceInfoQueryRequest interfaceInfoQueryRequest, HttpServletRequest request) {
        if (interfaceInfoQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        InterfaceInfo interfaceInfoQuery = new InterfaceInfo();
        BeanUtils.copyProperties(interfaceInfoQueryRequest, interfaceInfoQuery);
        long current = interfaceInfoQueryRequest.getCurrent();
        long size = interfaceInfoQueryRequest.getPageSize();
        String sortField = interfaceInfoQueryRequest.getSortField();
        String sortOrder = interfaceInfoQueryRequest.getSortOrder();
        String description = interfaceInfoQuery.getDescription();
        // description 需支持模糊搜索
        interfaceInfoQuery.setDescription(null);
        // 限制爬虫
        if (size > 50) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>(interfaceInfoQuery);
        queryWrapper.like(StringUtils.isNotBlank(description), "description", description);
        queryWrapper.orderBy(StringUtils.isNotBlank(sortField),
                sortOrder.equals(CommonConstant.SORT_ORDER_ASC), sortField);
        Page<InterfaceInfo> interfaceInfoPage = interfaceInfoService.page(new Page<>(current, size), queryWrapper);

        Page<InterfaceInfoVO> infoVoPage = new Page<>();
        List<InterfaceInfo> interfaceInfoPageRecords = interfaceInfoPage.getRecords();
        List<InterfaceInfoVO> interfaceInfoVOList = interfaceInfoService.listInterface(interfaceInfoPageRecords);
        infoVoPage.setRecords(interfaceInfoVOList);

        return ResultUtils.success(infoVoPage);
    }
    // endregion


    /**
     * 更新
     *
     * @param idRequest
     * @param request
     * @return
     */
    @PostMapping("/online")
    @AuthCheck(mustRole = "admin")
    public BaseResponse<Boolean> onlineInterfaceInfo(@RequestBody IdRequest idRequest,
                                                     HttpServletRequest request) throws InvocationTargetException, IllegalAccessException {
        if (idRequest == null || idRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        long id = idRequest.getId();


        // 判断是否存在
        InterfaceInfo oldInterfaceInfo = interfaceInfoService.getById(id);
        if (oldInterfaceInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }

        String name = oldInterfaceInfo.getName();
        String exampleRequestParams = oldInterfaceInfo.getExampleRequestParams();

        // Info验证接口是否可用
        User loginUser = userService.getLoginUser(request);
        String accessKey = loginUser.getAccessKey();
        String secretKey = loginUser.getSecretKey();

        NextApiClient nextApiClient = new NextApiClient(accessKey, secretKey);

        try {
            Method methodByName = ReflectUtil.getMethodByName(NextApiClient.class, name);
            String result = (String) methodByName.invoke(nextApiClient, exampleRequestParams);
            log.info(result);
            if (CommonConstant.INVOKE_ERROR.equals(result)) {
                throw new BusinessException(ErrorCode.OPERATION_ERROR, "接口不可用，发布失败");
            }
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "接口不可用，发布失败");
        }


        InterfaceInfo interfaceInfo = new InterfaceInfo();
        interfaceInfo.setId(id);
        interfaceInfo.setStatus(InterfaceStatusEnum.ONLINE.getValue());
        boolean result = interfaceInfoService.updateById(interfaceInfo);
        return ResultUtils.success(result);
    }


    /**
     * 更新
     *
     * @param idRequest
     * @param request
     * @return
     */
    @PostMapping("/offline")
    @AuthCheck(mustRole = "admin")
    public BaseResponse<Boolean> offlineInterfaceInfo(@RequestBody IdRequest idRequest,
                                                     HttpServletRequest request) {
        if (idRequest == null || idRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        long id = idRequest.getId();
        // 判断是否存在
        InterfaceInfo oldInterfaceInfo = interfaceInfoService.getById(id);
        if (oldInterfaceInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }

        InterfaceInfo interfaceInfo = new InterfaceInfo();
        interfaceInfo.setId(id);
        interfaceInfo.setStatus(InterfaceStatusEnum.OFFLINE.getValue());
        boolean result = interfaceInfoService.updateById(interfaceInfo);
        return ResultUtils.success(result);
    }

    /**
     * 调用方法
     *
     * @param invokeRequest
     * @param request
     * @return
     */
    @PostMapping("/invoke")
    public BaseResponse<Object> invokeInterface(@RequestBody InterfaceInfoInvokeRequest invokeRequest,
                                                 HttpServletRequest request) throws InvocationTargetException, IllegalAccessException {
        if (invokeRequest == null || invokeRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        long id = invokeRequest.getId();
        String userRequestParams = invokeRequest.getUserRequestParams();
        // 判断是否存在
        InterfaceInfo oldInterfaceInfo = interfaceInfoService.getById(id);
        if (oldInterfaceInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }

        if (!Objects.equals(oldInterfaceInfo.getStatus(), InterfaceStatusEnum.ONLINE.getValue())) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "接口未上线");
        }
        String name = oldInterfaceInfo.getName();

        User loginUser = userService.getLoginUser(request);
        String accessKey = loginUser.getAccessKey();
        String secretKey = loginUser.getSecretKey();
        // 调用接口
        NextApiClient nextApiClient = new NextApiClient(accessKey, secretKey);

        Method methodByName = ReflectUtil.getMethodByName(NextApiClient.class, name);
        String result = (String) methodByName.invoke(nextApiClient, userRequestParams);

        // TODO: 2024/1/31 返回统一标准

        return ResultUtils.success(result);
    }
}