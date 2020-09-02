//
//  IMHelpVC.m
//  22call
//
//  Created by DongDong on 2020/4/22.
//  Copyright © 2020 qizhi. All rights reserved.
//

#import "IMHelpVC.h"

@interface IMHelpVC ()
@property (weak, nonatomic) IBOutlet UILabel *helpContentL;
@property (weak, nonatomic) IBOutlet UILabel *contactUsL;
@property (weak, nonatomic) IBOutlet UIButton *callBtn;
@property (weak, nonatomic) IBOutlet UIButton *CMailboxBtn;
@property (weak, nonatomic) IBOutlet NSLayoutConstraint *helpContentLTopLC;
@end

@implementation IMHelpVC

- (void)viewDidLoad {
    [super viewDidLoad];
}


- (void)backClick:(id)sender
{
    [self.navigationController popViewControllerAnimated:YES];
}

- (IBAction)callClick:(id)sender
{
    NSMutableString *str=[[NSMutableString alloc] initWithFormat:@"telprompt://%@",@"008613922202463"];
    [[UIApplication sharedApplication] openURL:[NSURL URLWithString:str] options:@{UIApplicationOpenURLOptionsSourceApplicationKey:@YES} completionHandler:^(BOOL success) {
    }];
}

- (IBAction)copyMailboxClick:(id)sender
{
    //UIPasteboard：该类支持写入和读取数据，类似剪贴板
    UIPasteboard *pasteBoard = [UIPasteboard generalPasteboard];
    pasteBoard.string = @"bd@qzlink.com";
    UIAlertController *alert = [UIAlertController alertControllerWithTitle:nil message:@"Copy successfully" preferredStyle:UIAlertControllerStyleAlert];
    UIAlertAction *cancel = [UIAlertAction actionWithTitle:@"OK" style:UIAlertActionStyleCancel handler:^(UIAlertAction * _Nonnull action) {
        
    }];
    [alert addAction:cancel];
    [self presentViewController:alert animated:YES completion:nil];
}

@end
