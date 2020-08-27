//
//  NetworkManager.m
//  Agora-SIP-Demo
//
//  Created by liuwangjun on 2020/7/14.
//  Copyright © 2020 liuwangjun. All rights reserved.
//

#import "NetworkManager.h"

@interface NetworkManager ()
@property (nonatomic, strong) AFHTTPSessionManager *manager;
@property (nonatomic, assign) NSTimeInterval timeInterval;
@end

@implementation NetworkManager

- (AFHTTPSessionManager *)manager
{
    return _manager;
}

- (instancetype)init
{
    self = [super init];
    if (self)
    {
        [self initDefaultData];
    }
    
    return self;
}

- (void)initDefaultData
{
    _timeInterval = 30.0;
    _manager = [[AFHTTPSessionManager alloc] init];
    _manager.requestSerializer = [AFJSONRequestSerializer serializer];
}

- (void)requestWithUrl:(NSString *)url
            parameters:(nullable NSDictionary*)parameters
               success:(nullable void (^)(id _Nullable responseObject))success
               failure:(nullable void (^)(NSError * _Nullable error))failure
{

    _manager.requestSerializer.timeoutInterval = _timeInterval;
    [_manager GET:url parameters:parameters headers:nil progress:nil success:^(NSURLSessionDataTask * _Nonnull task, id  _Nullable responseObject) {
        if (success)
        {
            success(responseObject);
        }
    } failure:^(NSURLSessionDataTask * _Nullable task, NSError * _Nonnull error) {
        if (failure)
        {
            failure(error);
        }
    }];
}

@end
