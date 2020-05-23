package com.hackett.alibc;

import androidx.annotation.NonNull;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/** AlibcPlugin */
public class AlibcPlugin implements FlutterPlugin, MethodCallHandler {
  private MethodChannel channel;

  private static AlibcHandle handle;

  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
    channel = new MethodChannel(flutterPluginBinding.getFlutterEngine().getDartExecutor(), "alibc");
    channel.setMethodCallHandler(this);
  }

  public static void registerWith(Registrar registrar) {
    handle = new AlibcHandle(registrar);
    
    final MethodChannel channel = new MethodChannel(registrar.messenger(), "alibc");
    channel.setMethodCallHandler(new AlibcPlugin());
  }

  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
    if (call.method.equals("getPlatformVersion")) {
      result.success("Android " + android.os.Build.VERSION.RELEASE);
    } else if (call.method.equals("initAlibc")){
      handle.initAlibc(call, result);
    }else if (call.method.equals("openItemDetail")){
      handle.openItemDetail(call, result);
    } else if (call.method.equals("loginTaoBao")){
      handle.loginTaoBao(result);
    } else if (call.method.equals("taoKeLogin")){
      handle.taoKeLogin(call, result);
    } else if (call.method.equals("loginOut")){
      handle.loginOut(result);
    }else if (call.method.equals("openByUrl")){
      handle.openByUrl(call, result);
    }else if (call.method.equals("openShop")){
      handle.openShop(call, result);
    }else if (call.method.equals("openCart")){
      handle.openCart(call, result);
    }else if (call.method.equals("syncForTaoke")){
      handle.syncForTaoke(call);
    }else if (call.method.equals("useAlipayNative")){
      handle.useAlipayNative(call);
    }else {
      result.notImplemented();
    }
  }

  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    channel.setMethodCallHandler(null);
  }
}
