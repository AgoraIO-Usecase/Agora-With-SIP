//
//  CallView.m
//  Agora-SIP-Demo
//
//  Created by liuwangjun on 2020/7/14.
//  Copyright © 2020 liuwangjun. All rights reserved.
//

#import "CallView.h"

@implementation CallView

- (instancetype)init
{
    self = [super init];
    self = [[NSBundle mainBundle] loadNibNamed:@"CallView" owner:self options:nil].firstObject;
    self.frame = [UIScreen mainScreen].bounds;
    return self;
}

- (void)show
{
    self.durationLabel.text = @"00:00";
    if (self.superview) return;
    [[UIApplication sharedApplication].delegate.window addSubview:self];
}

@end
