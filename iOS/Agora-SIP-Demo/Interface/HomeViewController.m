//
//  HomeViewController.m
//  Agora-SIP-Demo
//
//  Created by liuwangjun on 2020/7/14.
//  Copyright © 2020 liuwangjun. All rights reserved.
//

#import "HomeViewController.h"
#import "CallPhoneViewController.h"
#import "CallSIPViewController.h"
#import "SettingViewController.h"

@interface HomeViewController ()

@end

@implementation HomeViewController

- (void)viewDidLoad
{
    [super viewDidLoad];
    self.navigationItem.title = @"HOME";
}

- (IBAction)callPhone:(id)sender
{
    CallPhoneViewController *controller = [[CallPhoneViewController alloc] init];
    [self.navigationController pushViewController:controller animated:YES];
}

- (IBAction)callSip:(id)sender
{
    CallSIPViewController *controller = [[CallSIPViewController alloc] init];
    [self.navigationController pushViewController:controller animated:YES];
}

- (IBAction)setting:(id)sender
{
    SettingViewController *controller = [[SettingViewController alloc] init];
    [self.navigationController pushViewController:controller animated:YES];
}


@end
