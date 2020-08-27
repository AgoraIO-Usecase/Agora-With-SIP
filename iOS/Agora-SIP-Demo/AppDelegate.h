//
//  AppDelegate.h
//  Agora-SIP-Demo
//
//  Created by liuwangjun on 2020/7/14.
//  Copyright © 2020 liuwangjun. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface AppDelegate : UIResponder <UIApplicationDelegate>
@property (strong, nonatomic) UIWindow *window;

- (NSString *)getRoomId;

- (NSString*)getTimeFormWithTimeCount:(NSInteger)timeCount;

- (NSString *)userHttpUrl;

@end

