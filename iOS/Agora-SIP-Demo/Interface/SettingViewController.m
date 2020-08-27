//
//  SettingViewController.m
//  Agora-SIP-Demo
//
//  Created by liuwangjun on 2020/7/14.
//  Copyright © 2020 liuwangjun. All rights reserved.
//

#import "SettingViewController.h"

@interface SettingViewController ()
@property (weak, nonatomic) IBOutlet UITextField *ipTF;
@property (weak, nonatomic) IBOutlet UITextField *portTF;

@end

@implementation SettingViewController

- (void)viewDidLoad
{
    [super viewDidLoad];
    self.navigationItem.title = @"SETTING";
    NSString *ip = [[NSUserDefaults standardUserDefaults] valueForKey:@"LocalIP"];
    NSString *port = [[NSUserDefaults standardUserDefaults] valueForKey:@"LocalPort"];
    
    if (ip.length > 0) self.ipTF.text = ip;
    if (port.length > 0) self.portTF.text = port;
}

- (IBAction)done:(id)sender
{
    if (self.ipTF.text.length > 0 && self.portTF.text.length == 0)
    {
        [self showTips:@"Enter port first"];
        return;
    }
    if (self.portTF.text.length > 0 && self.ipTF.text.length == 0)
    {
        [self showTips:@"Enter ip first"];
        return;
    }
    
    [[NSUserDefaults standardUserDefaults] setValue:self.ipTF.text forKey:@"LocalIP"];
    [[NSUserDefaults standardUserDefaults] setValue:self.portTF.text forKey:@"LocalPort"];
    [[NSUserDefaults standardUserDefaults] synchronize];
    [self.navigationController popViewControllerAnimated:YES];
}

- (void)showTips:(NSString *)message
{
    UIAlertController *alertController = [UIAlertController alertControllerWithTitle:@"Tips" message:message preferredStyle:UIAlertControllerStyleAlert];
    UIAlertAction *cancelAction = [UIAlertAction actionWithTitle:@"OK" style:UIAlertActionStyleCancel handler:^(UIAlertAction * _Nonnull action) {

    }];
    [alertController addAction:cancelAction];
    [self presentViewController:alertController animated:YES completion:nil];
}


@end
