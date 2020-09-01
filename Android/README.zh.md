# Agora-With-SIP Android

*[English](README.md) | 中文*

这个开源示例项目演示了Agora视频SDK与启智SIP的协同使用。

## 环境准备

- Android Studio 3.0+
- Android 真机设备
- 支持模拟器

## 运行示例程序

这个段落主要讲解了如何编译和运行实例程序。

### 创建Agora账号并获取AppId

在编译和启动实例程序前，你需要首先获取一个可用的App Id:

1. 在[agora.io](https://dashboard.agora.io/signin/)创建一个开发者账号
2. 前往后台页面，点击左部导航栏的 **项目 > 项目列表** 菜单
3. 复制后台的 **App Id** 并备注，稍后启动应用时会用到它
4. 在项目页面生成临时 **Access Token** (24小时内有效)并备注，注意生成的Token只能适用于对应的频道名。

5. 打开 `Android/APIExample` 并编辑 `app/src/main/res/values/string_config.xml`，将你的 AppID 和 Token 分别替换到 `<#Your App Id#>` 与 `<#Temp Access Token#>`

    ```
    <string name="agora_app_id" translatable="false">YOUR APP ID</string>
    // 如果你没有打开Token功能，token可以直接给null或者不填
    <string name="agora_access_token" translatable="false">YOUR ACCESS TOKEN</string>
    ```

然后你就可以编译并运行项目了。

## 联系我们

- 售后技术支持与Bug报告，可与我们进行联系：bd@lighthk.com

## 代码许可

The MIT License (MIT)
