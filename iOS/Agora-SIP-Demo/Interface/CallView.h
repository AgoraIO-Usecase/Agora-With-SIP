//
//  CallView.h
//  Agora-SIP-Demo
//
//  Created by liuwangjun on 2020/7/14.
//  Copyright © 2020 liuwangjun. All rights reserved.
//

#import <UIKit/UIKit.h>

NS_ASSUME_NONNULL_BEGIN

@interface CallView : UIView
@property (weak, nonatomic) IBOutlet UILabel *durationLabel;
@property (weak, nonatomic) IBOutlet UIButton *hangUpBtn;
//本地视图
@property (weak, nonatomic) IBOutlet UIView *viewLocalVideo;
//对方的视频视图
@property (weak, nonatomic) IBOutlet UIView *viewRemoteVideo;

- (void)show;

@end

NS_ASSUME_NONNULL_END
