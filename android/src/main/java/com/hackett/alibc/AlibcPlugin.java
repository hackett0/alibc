package com.hackett.alibc;

import androidx.annotation.NonNull;

import java.util.Map;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;

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

  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
    if (call.method.equals("login")) {
      handle.login(result);
    } else if (call.method.equals("islogin")) {
      handle.islogin(result);
    } else if (call.method.equals("logout")) {
      handle.logout(result);
    } else if (call.method.equals("openURL")) {
      handle.openURL(
              (String)call.argument("kit"),
              (String)call.argument("url"),
              AlibcHelpers.callShowParams(call),
              AlibcHelpers.callTaokeParams(call),
              (Map)call.argument("TrackParams"),
              result);
    } else if (call.method.equals("openPage")) {
      handle.openPage(
              AlibcHelpers.callPageParams(call),
              (String)call.argument("type"),
              AlibcHelpers.callShowParams(call),
              AlibcHelpers.callTaokeParams(call),
              (Map)call.argument("TrackParams"),
              result);

    }else if (call.method.equals("setSyncForTaoke")){
      handle.setSyncForTaoke((String)call.argument("sync") == "true");
    } else if (call.method.equals("setTaokeParams")){
      handle.setTaokeParams(AlibcHelpers.callTaokeParams(call));
    } else if (call.method.equals("setChannel")) {
      handle.setChannel(
              (String)call.argument("type"),
              (String)call.argument("channel"));
    } else if (call.method.equals("setISVCode")) {
      handle.setISVCode((String)call.argument("code"));
    } else if (call.method.equals("setISVVersion")) {
      handle.setISVVersion((String)call.argument("version"));
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
