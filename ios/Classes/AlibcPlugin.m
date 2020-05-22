#import "AlibcPlugin.h"
#if __has_include(<alibc/alibc-Swift.h>)
#import <alibc/alibc-Swift.h>
#else
// Support project import fallback if the generated compatibility header
// is not copied when this plugin is created as a library.
// https://forums.swift.org/t/swift-static-libraries-dont-copy-generated-objective-c-header/19816
#import "alibc-Swift.h"
#endif

@implementation AlibcPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftAlibcPlugin registerWithRegistrar:registrar];
}
@end
