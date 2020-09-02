//
//  CallSIPViewController.m
//  Agora-SIP-Demo
//
//  Created by liuwangjun on 2020/7/14.
//  Copyright © 2020 liuwangjun. All rights reserved.
//

#import "CallSIPViewController.h"
#import <AgoraRtcKit/AgoraRtcEngineKit.h>
#import "AppDelegate.h"
#import "NetworkManager.h"
#import "CallView.h"
#import "IMHelpVC.h"

@interface CallSIPViewController ()<AgoraRtcEngineDelegate>
@property (weak, nonatomic) IBOutlet UITextField *roomIdTF;
@property (weak, nonatomic) IBOutlet UITextField *callerTF;
@property (weak, nonatomic) IBOutlet UITextField *sipTF;
@property (weak, nonatomic) IBOutlet UITextField *gwTF;

@property (strong, nonatomic) AgoraRtcEngineKit *agoraKit;
@property (strong, nonatomic) NetworkManager *network;
@property (copy, nonatomic) NSString *roomId;
@property (copy, nonatomic) NSString *caller;
@property (copy, nonatomic) NSString *callee;
@property (strong, nonatomic) CallView *callView;

@property (nonatomic, strong) NSTimer *__nullable timer;
@property (nonatomic, assign) NSUInteger timeCount;

@property (copy, nonatomic) NSString *token;
@property (copy, nonatomic) NSString *uid;
@end

@implementation CallSIPViewController

- (AgoraRtcEngineKit *)agoraKit
{
    if (_agoraKit == nil)
    {
        _agoraKit = [AgoraRtcEngineKit sharedEngineWithAppId:AgoraAppId delegate:self];
    }
    return _agoraKit;
}

- (NetworkManager *)network
{
    if (_network == nil)
    {
        _network = [[NetworkManager alloc] init];
    }
    return _network;
}

- (CallView *)callView
{
    if (_callView == nil)
    {
        _callView = [[CallView alloc] init];
        [_callView.hangUpBtn addTarget:self action:@selector(hangUpKillCallee) forControlEvents:UIControlEventTouchUpInside];
    }
    return _callView;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    self.navigationItem.title = @"CALL SIP";
    [self refreshRoomId:nil];
}

- (IBAction)refreshRoomId:(id)sender
{
    AppDelegate *appDelegate = (AppDelegate *)[UIApplication sharedApplication].delegate;
    self.roomIdTF.text = [appDelegate getRoomId];
}

- (IBAction)videoCall:(id)sender
{
//    BOOL fillAll = [self fillAll];
//    if (!fillAll) return;
//    [self setupVideo];
//    [self setupLocalVideo];
//    self.roomId = self.roomIdTF.text;
}

- (IBAction)audioCall:(id)sender
{
    BOOL fillAll = [self fillAll];
    if (!fillAll) return;
    
    [self.agoraKit enableAudio];
    [self.agoraKit enableLocalAudio:YES];
    
     self.roomId = self.roomIdTF.text;
    self.caller = self.callerTF.text;
    self.callee = self.sipTF.text;
    
    NSMutableDictionary *parameters = [NSMutableDictionary dictionary];
    parameters[@"caller"] = self.callerTF.text;
    parameters[@"phone"] = self.sipTF.text;
    parameters[@"room_id"] = self.roomIdTF.text;
    parameters[@"gw"] = self.gwTF.text;
    
    AppDelegate *appDelegate = (AppDelegate *)[UIApplication sharedApplication].delegate;
    NSString *url = [appDelegate userHttpUrl];
    url = [NSString stringWithFormat:@"%@/%@",url,@"callSIP"];
    
    __weak __typeof__(self) weakSelf = self;
    [self.network requestWithUrl:url parameters:parameters success:^(id  _Nullable responseObject) {
        NSLog(@"%@", responseObject);
        NSString *code = responseObject[@"code"];
        if ([code isEqualToString:@"000000"])
        {
            [weakSelf getTokenForRoomid:weakSelf.roomId];
        }else
        {
            [weakSelf showTips:responseObject[@"msg"]];
        }
    } failure:^(NSError * _Nullable error) {
        [weakSelf showTips:error.userInfo.description];
    }];
}

- (BOOL)fillAll
{
    if (self.roomIdTF.text.length == 0)
    {
        [self showTips:@"Enter roomId first"];
        return NO;
    }
    if (self.callerTF.text.length == 0)
    {
        [self showTips:@"Enter caller first"];
        return NO;
    }
    if (self.sipTF.text.length == 0)
    {
        [self showTips:@"Enter sip first"];
        return NO;
    }
    
    if (self.gwTF.text.length == 0)
    {
        [self showTips:@"Enter gw first"];
        return NO;
    }
    
    return YES;
}

- (void)showTips:(NSString *)message
{
    UIAlertController *alertController = [UIAlertController alertControllerWithTitle:@"Tips" message:message preferredStyle:UIAlertControllerStyleAlert];
    UIAlertAction *cancelAction = [UIAlertAction actionWithTitle:@"OK" style:UIAlertActionStyleCancel handler:^(UIAlertAction * _Nonnull action) {

    }];
    [alertController addAction:cancelAction];
    [self presentViewController:alertController animated:YES completion:nil];
}


- (void)setupLocalVideo
{
    AgoraRtcVideoCanvas *videoCanvas = [[AgoraRtcVideoCanvas alloc] init];
    videoCanvas.uid = 0;
    videoCanvas.view = self.callView.viewLocalVideo;
    videoCanvas.renderMode = AgoraVideoRenderModeHidden;
    [self.agoraKit setupLocalVideo:videoCanvas];
}

//对方挂断
- (void)hangUp
{
    [self leaveChannel];
    [self.callView removeFromSuperview];
    if (self.timer)
    {
        [self.timer invalidate];
        self.timer = nil;
    }
}

//主动挂断 通知对方挂断
- (void)hangUpKillCallee
{
   [self hangUp];
    NSMutableDictionary *parameters = [NSMutableDictionary dictionary];
    parameters[@"caller"] = self.caller;
    parameters[@"callee"] = self.callee;
    parameters[@"roomid"] = self.roomId;
    
    AppDelegate *appDelegate = (AppDelegate *)[UIApplication sharedApplication].delegate;
    NSString *url = [appDelegate userHttpUrl];
    url = [NSString stringWithFormat:@"%@/%@",url,@"killCall"];
    [self.network requestWithUrl:url parameters:parameters success:^(id  _Nullable responseObject) {
        NSLog(@"%@", responseObject);
    } failure:^(NSError * _Nullable error) {
    }];
}

- (void)joinChannel
{
    [self.agoraKit joinChannelByToken:self.token channelId:self.roomId info:@"" uid:[self.uid integerValue] joinSuccess:^(NSString *channel, NSUInteger uid, NSInteger elapsed) {
        NSLog(@"Join channel");
    }];
    [UIApplication sharedApplication].idleTimerDisabled = YES;
    [self.callView show];
    if (!self.timer)
    {
        self.timeCount = 0;
        self.timer = [NSTimer scheduledTimerWithTimeInterval:1 target:self selector:@selector(updateTime:) userInfo:nil repeats:YES];
    }
}

- (void)getTokenForRoomid:(NSString*)roomid
{
    AppDelegate *appDelegate = (AppDelegate *)[UIApplication sharedApplication].delegate;
    NSString *url = [appDelegate appHttpUrl];
    url = [NSString stringWithFormat:@"%@/%@?roomid=%@", url, @"getTokenForRoomid", roomid];
    __weak __typeof__(self) weakSelf = self;
    [self.network requestWithUrl:url parameters:nil success:^(id  _Nullable responseObject) {
        NSLog(@"%@", responseObject);
        NSString *code = responseObject[@"code"];
        if ([code isEqualToString:@"000000"])
        {
            NSString *token = responseObject[@"token"];
            NSString *uid = responseObject[@"uid"];
            if ([token isKindOfClass:[NSString class]])
            {
                weakSelf.token = token;
            }
            if ([uid isKindOfClass:[NSString class]])
            {
                weakSelf.uid = uid;
            }
            [weakSelf joinChannel];
        }else
        {
            [weakSelf showTips:responseObject[@"msg"]];
        }
    } failure:^(NSError * _Nullable error) {
        [weakSelf showTips:error.userInfo.description];
    }];
}

- (void)updateTime:(id)sender
{
    self.timeCount ++;
    AppDelegate *appDelegate = (AppDelegate *)[UIApplication sharedApplication].delegate;
    self.callView.durationLabel.text = [appDelegate getTimeFormWithTimeCount:self.timeCount];
}

- (void)leaveChannel
{
    if (self.agoraKit)
    {
        [self.agoraKit leaveChannel:^(AgoraChannelStats *stat) {
        }];
        [UIApplication sharedApplication].idleTimerDisabled = NO;
        [AgoraRtcEngineKit destroy];
        self.agoraKit.delegate = nil;
        self.agoraKit = nil;
    }
}

#pragma mark --AgoraRtcEngineDelegate
- (void)rtcEngine:(AgoraRtcEngineKit *)engine firstRemoteVideoDecodedOfUid:(NSUInteger)uid size: (CGSize)size elapsed:(NSInteger)elapsed
{
    AgoraRtcVideoCanvas *videoCanvas = [[AgoraRtcVideoCanvas alloc] init];
    videoCanvas.uid = uid;
    videoCanvas.view = self.callView.viewRemoteVideo;
    videoCanvas.renderMode = AgoraVideoRenderModeHidden;
    [self.agoraKit setupRemoteVideo:videoCanvas];
}

- (void)rtcEngine:(AgoraRtcEngineKit * _Nonnull)engine didOfflineOfUid:(NSUInteger)uid reason:(AgoraUserOfflineReason)reason
{
    NSLog(@"didOfflineOfUid");
    [self hangUp];
    [self showTips:@"The other party hangs up"];
}

- (IBAction)help:(id)sender {
    IMHelpVC *controller = [[IMHelpVC alloc] init];
    [self.navigationController pushViewController:controller animated:YES];
}

@end
