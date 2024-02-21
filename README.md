
![logo.png](next-api-frontend%2Fpublic%2Flogo.png)


# next API
## 项目介绍

基于 React + Spring Boot + Dubbo + Gateway 的 API 接口开放调用平台。管理员可以接入并发布接口，可视化各接口调用情况；用户可以开通接口调用权限、浏览接口及在线调试，并通过客户端 SDK 轻松调用接口。

## 流程图
![流程图.png](next-bi-backend%2Fimage%2F%E6%B5%81%E7%A8%8B%E5%9B%BE.png)


## 项目亮点

1. **安全可靠的API认证系统：**
    - 设计了高效的API签名认证算法，为每个用户分配唯一的Access Key（AK）和Secret Key（SK），以确保接口调用的安全性和可溯源性。
    - 结合Spring Cloud Gateway作为API网关，统一处理签名校验、请求参数校验，有效防止恶意调用和非法访问，提高系统的安全性。

2. **开发者友好的SDK工具：**
    - 基于Spring Boot Starter开发了简单易用的客户端SDK，一行代码即可完成接口调用，大大降低了开发者的调用成本，提升了开发体验。

3. **高性能的RPC框架应用：**
    - 使用Dubbo RPC框架实现了子系统间的高性能接口调用，通过抽象模型层和业务层代码的公共模块，有效减少了代码重复，提高了系统的可维护性和扩展性。

4. **灵活强大的API网关服务：**
    - Spring Cloud Gateway作为API网关，不仅实现了路由转发、访问控制、流量染色等功能，还集中处理了签名校验、请求参数校验、接口调用统计等业务逻辑，保障了系统的安全性和可维护性。

5. **接口对接的高可用性和稳定性：**
    - 成功对接了自主开发的BI系统、OJ系统、脑肿瘤分割系统，开放了核心业务API接口，接口调用可用性达到99.999%的水平，为用户提供了高效可靠的服务和功能。



## 截图
![login.png](next-api-backend%2Fimg%2Flogin.png)
![analysis.png](next-api-backend%2Fimg%2Fanalysis.png)
![interfaceinfo.png](next-api-backend%2Fimg%2Finterfaceinfo.png)
![interfacemanage.png](next-api-backend%2Fimg%2Finterfacemanage.png)
![createinterface.png](next-api-backend%2Fimg%2Fcreateinterface.png)
![updateinterface.png](next-api-backend%2Fimg%2Fupdateinterface.png)
![detail1.png](next-api-backend%2Fimg%2Fdetail1.png)
![detail2.png](next-api-backend%2Fimg%2Fdetail2.png)
![detail3.png](next-api-backend%2Fimg%2Fdetail3.png)
![my.png](next-api-backend%2Fimg%2Fmy.png)


## 技术栈


![Static Badge](https://img.shields.io/badge/Spring%20Boot-green)
![Static Badge](https://img.shields.io/badge/Spring%20Cloud%20Gateway-green)
![Static Badge](https://img.shields.io/badge/Redis-red)
![Static Badge](https://img.shields.io/badge/React-skyblue)
![Static Badge](https://img.shields.io/badge/Dubbo-skyblue)
...


## 作者

- [@peiYp](https://github.com/change-everything)


## 反馈

如果你有任何反馈，请联系我：pyptsguas@163.com

