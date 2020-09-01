# Agora-With-SIP iOS

*[English](README.md) | 中文*

这个开源示例项目演示了Agora视频SDK与启智SIP的协同使用。

## 环境准备

- XCode 10.0 +
- iOS 真机设备
- 不支持模拟器

## 运行示例程序

这个段落主要讲解了如何编译和运行实例程序。

### 安装依赖库

切换到 **iOS** 目录，运行以下命令使用CocoaPods安装依赖，Agora视频SDK会在安装后自动完成集成。

```
pod install
```

运行后确认 `Agora-SIP-Demo.xcworkspace` 正常生成即可。

### 创建Agora账号并获取AppId

在编译和启动实例程序前，你需要首先获取一个可用的App Id:

1. 在[agora.io](https://dashboard.agora.io/signin/)创建一个开发者账号
2. 前往后台页面，点击左部导航栏的 **项目 > 项目列表** 菜单
3. 复制后台的 **App Id** 并备注，稍后启动应用时会用到它
4. 在项目页面生成临时 **Access Token** (24小时内有效)并备注，注意生成的Token只能适用于对应的频道名。

5. 打开 `Agora-SIP-Demo.xcworkspace` 并编辑 `AppID.m`，将你的 AppID 和 Token 分别替换到 `<#Your App Id#>` 与 `<#Temp Token#>`

    ```
      NSString *const appID = <#Your App ID#>;
      /* 如果没有打开鉴权Token, 这里的token值给nil就好
       * 生成Token需要参照官方文档部署Token服务器，开发阶段若想先不部署服务器, 可以在https://dashbaord.agora.io生成
       * 临时Token. 请注意生成Token时指定的频道名, 该Token只允许加入对应的频道
       */
      NSString *const token = <#Temp Token#>;
    ```

然后你就可以使用 `Agora-SIP-Demo.xcworkspace` 编译并运行项目了。

## 联系我们

- 售后技术支持与Bug报告，可与我们进行联系：bd@lighthk.com

## 代码许可

The MIT License (MIT)
