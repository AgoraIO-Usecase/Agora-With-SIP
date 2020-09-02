//
//  AppDelegate.m
//  Agora-SIP-Demo
//
//  Created by liuwangjun on 2020/7/14.
//  Copyright © 2020 liuwangjun. All rights reserved.
//

#import "AppDelegate.h"
#import "HomeViewController.h"

@interface AppDelegate ()
@property (strong, nonatomic) UINavigationController *navi;
@end

@implementation AppDelegate


- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions
{
    self.window = [[UIWindow alloc] initWithFrame:[UIScreen mainScreen].bounds];
    HomeViewController *home = [[HomeViewController alloc] init];
    UINavigationController *navi = [[UINavigationController alloc] initWithRootViewController:home];
    navi.navigationBar.tintColor = [UIColor blackColor];
    self.window.rootViewController = navi;
    self.navi = navi;
    [self.window makeKeyAndVisible];
    return YES;
}

- (NSString *)getRoomId
{
    NSDate *date = [NSDate date];
    NSDateFormatter *formatter = [[NSDateFormatter alloc ] init];
    [formatter setDateFormat:@"MMddHHmmss"];
    NSString *dateStr = [formatter stringFromDate:date];
    int x =1000 +  (arc4random() % 9000);
    
    return [NSString stringWithFormat:@"%@%d",dateStr,x];
}

- (NSString*)getTimeFormWithTimeCount:(NSInteger)timeCount
{
    NSString *timeForm = @"00:00";
    if (timeCount<60)
    {
        if (timeCount<10)
        {
            timeForm = [NSString stringWithFormat:@"00:0%ld", timeCount];
        }else
        {
            timeForm = [NSString stringWithFormat:@"00:%ld", timeCount];
        }
    }else
    {
        NSUInteger minute = timeCount/60;
        if (minute<60)
        {
            NSUInteger second = timeCount%60;
            NSString *minute_s = @"00";
            if (minute<10)
            {
                minute_s = [NSString stringWithFormat:@"0%ld", minute];
            }else
            {
                minute_s = [NSString stringWithFormat:@"%ld", minute];
            }
            if (second<10)
            {
                timeForm = [NSString stringWithFormat:@"%@:0%ld", minute_s, second];
            }else
            {
                timeForm = [NSString stringWithFormat:@"%@:%ld", minute_s, second];
            }
        }else
        {
            NSUInteger hour = timeCount/(60*60);
            NSString *hour_s = @"00";
            if (hour<10)
            {
                hour_s = [NSString stringWithFormat:@"0%ld", hour];
            }else
            {
                hour_s = [NSString stringWithFormat:@"%ld", hour];
            }
            NSUInteger minute_a = (timeCount%(60*60))/60;
            NSString *minute_s = @"00";
            if (minute_a<10)
            {
                minute_s = [NSString stringWithFormat:@"0%ld", minute_a];
            }else
            {
                minute_s = [NSString stringWithFormat:@"%ld", minute_a];
            }
            NSUInteger second = (timeCount%(60*60))%60;
            if (second<10)
            {
                timeForm = [NSString stringWithFormat:@"%@:%@:0%ld", hour_s, minute_s, second];
            }else
            {
                timeForm = [NSString stringWithFormat:@"%@:%@:%ld", hour_s, minute_s, second];
            }
        }
    }
    return timeForm;
}

- (NSString *)userHttpUrl
{
    NSString *ip = [[NSUserDefaults standardUserDefaults] valueForKey:@"LocalIP"];
    NSString *port = [[NSUserDefaults standardUserDefaults] valueForKey:@"LocalPort"];
    NSString *url = [NSString stringWithFormat:@"http://%@:%@/api/meet", DefaultIP, DefaultPort];
    if (ip.length > 0 && port.length > 0)
    {
        url = [NSString stringWithFormat:@"http://%@:%@/api/meet",ip,port];
    }
    
    return url;
}

- (NSString *)appHttpUrl
{
    NSString *ip = [[NSUserDefaults standardUserDefaults] valueForKey:@"LocalIP"];
    NSString *port = [[NSUserDefaults standardUserDefaults] valueForKey:@"LocalPort"];
    NSString *url = [NSString stringWithFormat:@"http://%@:%@/api/app", DefaultIP, DefaultPort];
    if (ip.length > 0 && port.length > 0)
    {
        url = [NSString stringWithFormat:@"http://%@:%@/api/app",ip,port];
    }
    
    return url;
}
@end
