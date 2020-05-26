package com.hackett.alibc;

import androidx.annotation.NonNull;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

public class AlibcPlugin implements FlutterPlugin, MethodCallHandler, ActivityAware {
  private MethodChannel channel;
  private static AlibcHandle handle;

  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
    handle = new AlibcHandle();
    channel = new MethodChannel(flutterPluginBinding.getFlutterEngine().getDartExecutor(), "alibc");
    channel.setMethodCallHandler(this);
  }

  ///activity 生命周期
  @Override
  public void onAttachedToActivity(ActivityPluginBinding activityPluginBinding) {
    handle.setActivity(activityPluginBinding.getActivity());
  }

  @Override
  public void onDetachedFromActivityForConfigChanges() {
    handle.setActivity(null);
  }

  @Override
  public void onReattachedToActivityForConfigChanges(ActivityPluginBinding activityPluginBinding) {
    handle.setActivity(activityPluginBinding.getActivity());
  }

  public static void registerWith(Registrar registrar) {
    handle = new AlibcHandle();
    handle.setActivity(registrar.activity());
    final MethodChannel channel = new MethodChannel(registrar.messenger(), "alibc");
    channel.setMethodCallHandler(new AlibcPlugin());
  }

  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
    if (call.method.equals("init")) {
      handle.init(result);
    } else if (call.method.equals("login")) {
      handle.login(result);
    } else if (call.method.equals("islogin")) {
      handle.islogin(result);
    } else if (call.method.equals("logout")) {
      handle.logout(result);
    } else if (call.method.equals("openURL")) {
      handle.openURL(
              AlibcHelpers.callParam(call, "kit", ""),
              AlibcHelpers.callParam(call, "url", ""),
              AlibcHelpers.callShowParams(call),
              AlibcHelpers.callTaokeParams(call),
              AlibcHelpers.callTrackParams(call),
              result);
    } else if (call.method.equals("openPage")) {
      handle.openPage(
              AlibcHelpers.callPageParams(call),
              AlibcHelpers.callParam(call, "type", ""),
              AlibcHelpers.callShowParams(call),
              AlibcHelpers.callTaokeParams(call),
              AlibcHelpers.callTrackParams(call),
              result);

    }else if (call.method.equals("setSyncForTaoke")){
      handle.setSyncForTaoke(AlibcHelpers.callParam(call, "sync", "false") == "true");
    } else if (call.method.equals("setShouldUseAlipay")) {
      handle.setShouldUseAlipay(AlibcHelpers.callParam(call, "use", "false") == "true");
    } else if (call.method.equals("setTaokeParams")){
      handle.setTaokeParams(AlibcHelpers.callTaokeParams(call));
    } else if (call.method.equals("setChannel")) {
      handle.setChannel(
              AlibcHelpers.callParam(call, "type", ""),
              AlibcHelpers.callParam(call, "channel", ""));
    } else if (call.method.equals("setISVCode")) {
      handle.setISVCode(AlibcHelpers.callParam(call, "code", ""));
    } else if (call.method.equals("setISVVersion")) {
      handle.setISVVersion(AlibcHelpers.callParam(call, "version", ""));
    } else {
      result.notImplemented();
    }
  }

  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    channel.setMethodCallHandler(null);
    handle = null;
  }

  @Override
  public void onDetachedFromActivity() {
    handle.setActivity(null);
  }
}
