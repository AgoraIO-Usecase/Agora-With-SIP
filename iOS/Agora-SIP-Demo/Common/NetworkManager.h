//
//  NetworkManager.h
//  Agora-SIP-Demo
//
//  Created by liuwangjun on 2020/7/14.
//  Copyright © 2020 liuwangjun. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "AFNetworking.h"

NS_ASSUME_NONNULL_BEGIN

@interface NetworkManager : NSObject
- (void)requestWithUrl:(NSString *)url
            parameters:(nullable NSDictionary*)parameters
               success:(nullable void (^)(id _Nullable responseObject))success
               failure:(nullable void (^)(NSError * _Nullable error))failure;
@end

NS_ASSUME_NONNULL_END
