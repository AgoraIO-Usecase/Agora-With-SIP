//
//  AppDelegate.h
//  Agora-SIP-Demo
//
//  Created by liuwangjun on 2020/7/14.
//  Copyright © 2020 liuwangjun. All rights reserved.
//

#import <UIKit/UIKit.h>

#define AgoraAppId @"89a88081d17e4348900c6054595ef75a"
#define DefaultPort @"9898"
#define DefaultIP @"39.99.148.35"

@interface AppDelegate : UIResponder <UIApplicationDelegate>
@property (strong, nonatomic) UIWindow *window;

- (NSString *)getRoomId;

- (NSString*)getTimeFormWithTimeCount:(NSInteger)timeCount;

- (NSString *)userHttpUrl;

- (NSString *)appHttpUrl;
@end

