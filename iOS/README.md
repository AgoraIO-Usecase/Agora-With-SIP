# API Example iOS

*English | [中文](README.zh.md)*

This project presents you a set of API examples to help you understand how to use Agora APIs.

## Prerequisites

- Xcode 10.0+
- Physical iOS device (iPhone or iPad)
- iOS simulator is NOT supported

## Quick Start

This section shows you how to prepare, build, and run the sample application.

### Prepare Dependencies

Change directory into **iOS** folder, run following command to install project dependencies,

```
pod install
```

Verify `Agora-SIP-Demo.xcworkspace` has been properly generated.

### Obtain an App Id

To build and run the sample application, get an App Id:

1. Create a developer account at [agora.io](https://dashboard.agora.io/signin/). Once you finish the signup process, you will be redirected to the Dashboard.
2. Navigate in the Dashboard tree on the left to **Projects** > **Project List**.
3. Save the **App Id** from the Dashboard for later use.
4. Generate a temp **Access Token** (valid for 24 hours) from dashboard page with given channel name, save for later use.

5. Open `Agora-SIP-Demo.xcworkspace` and edit the `AppID.m` file. Update `<#Your App Id#>` with your App Id, and change `<#Temp Token#>` with the temp Access Token generated from dashboard. Note you can leave the token variable `nil` if your project has not turned on security token.

    ```
      NSString *const appID = <#Your App ID#>;
      /* assign Token to nil if you have not enabled app certificate
       * before you deploy your own token server, you can easily generate a temp token for dev use
       * at https://dashboard.agora.io note the token generated are allowed to join corresponding room ONLY.
       */
      NSString *const token = <#Temp Token#>;
    ```

You are all set. Now connect your iPhone or iPad device and run the project.

## Contact Us

- For bugs and problems, please email us(bd@lighthk.com) 

## License

The MIT License (MIT)
