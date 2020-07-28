import 'dart:async';
import 'dart:convert';

import 'package:alibc/data.dart';
import 'package:flutter/services.dart';

class Alibc {
  static const MethodChannel _channel = const MethodChannel('alibc');
  static bool _init = false;

  static Future<AlibcResult> invoke(String method, [dynamic arguments]) async {
    return AlibcResult.fromMap(await _channel.invokeMethod(method, arguments));
  }

  /// 初始化
  static Future<AlibcResult> init() async {
    if (_init) {
      return AlibcResult.success(null);
    }

    _init = true;
    return await invoke('init');
  }

  /// 淘宝账号授权登录
  static Future<AlibcResult> login() async {
    var r = await init();
    return r.isSuccess ? await invoke('login') : r;
  }

  /// 检查是否登录
  static Future<AlibcResult> islogin() async {
    var r = await init();
    return r.isSuccess ? await invoke('islogin') : r;
  }

  /// 退出淘宝账号授权
  static Future<AlibcResult> logout() async {
    var r = await init();
    return r.isSuccess ? await invoke('logout') : r;
  }

  /// 打开链接
  static Future<AlibcResult> openURL(String url, AlibcShowParams show,
      {String kit, AlibcTaokeParams taoke, Map<String, String> track}) async {
    final Map<String, dynamic> data = {};
    data['kit'] = kit;
    data['url'] = url;
    if (show != null) data['ShowParams'] = show.toMap();
    if (taoke != null) data['TaokeParams'] = taoke.toMap();
    if (track != null) data['TrackParams'] = track;
    var r = await init();
    return r.isSuccess ? await invoke('openURL', data) : r;
  }

  /// 打开淘宝页面
  /// page: [AlibcDetailPage] [AlibcShopPage] [AlibcMyCartsPage] [AlibcAddCartPage] [AlibcMyOrdersPage]
  static Future<AlibcResult> openPage(AlibcPageBase page, AlibcShowParams show,
      {AlibcTaokeParams taoke, Map<String, String> track}) async {
    final Map<String, dynamic> data = {};
    if (page != null) data['PageParams'] = page.toMap();
    if (show != null) data['ShowParams'] = show.toMap();
    if (taoke != null) data['TaokeParams'] = taoke.toMap();
    if (track != null) data['TrackParams'] = track;
    var r = await init();
    return r.isSuccess ? await invoke('openPage', data) : r;
  }

  /// 是否使用同步淘客打点
  static Future<AlibcResult> setSyncForTaoke(bool issync) async {
    var r = await init();
    return r.isSuccess ? await invoke('setSyncForTaoke', {'sync': issync ? 'true' : 'false'}) : r;
  }

  /// 是否使用支付宝
  static Future<AlibcResult> setShouldUseAlipay(bool isuse) async {
    var r = await init();
    return r.isSuccess ? await invoke('setShouldUseAlipay', {'use': isuse ? 'true' : 'false'}) : r;
  }

  // 淘宝客参数
  static Future<AlibcResult> setTaokeParams(AlibcTaokeParams taoke) async {
    final Map<String, dynamic> data = {};
    if (taoke != null) data['TaokeParams'] = taoke.toMap();
    var r = await init();
    return r.isSuccess ? await invoke('setTaokeParams', data) : r;
  }

  /// 渠道信息
  static Future<AlibcResult> setChannel(String type, String channel) async {
    var r = await init();
    return r.isSuccess ? await invoke('setChannel', {'type': type, 'channel': channel}) : r;
  }

  /// ISV的Code
  static Future<AlibcResult> setISVCode(String code) async {
    var r = await init();
    return r.isSuccess ? await invoke('setISVCode', {'code': code}) : r;
  }

  /// ISV的版本，通常为本APP版本
  static Future<AlibcResult> setISVVersion(String version) async {
    var r = await init();
    return r.isSuccess ? await invoke('setISVVersion', {'version': version}) : r;
  }
}
