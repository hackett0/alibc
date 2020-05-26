import 'dart:async';

import 'package:alibc/data.dart';
import 'package:flutter/services.dart';

class Alibc {
  static const MethodChannel _channel = const MethodChannel('alibc');

  /// 初始化
  static Future<dynamic> init() async {
    return await _channel.invokeMethod('init');
  }

  /// 淘宝账号授权登录
  static Future<dynamic> login() async {
    return await _channel.invokeMethod('login');
  }

  /// 检查是否登录
  static Future<dynamic> islogin() async {
    return await _channel.invokeMethod('islogin');
  }

  /// 退出淘宝账号授权
  static Future<dynamic> logout() async {
    return await _channel.invokeMethod('logout');
  }

  /// 打开链接
  static Future<dynamic> openURL(String kit, String url, AlibcShowParams show,
      {AlibcTaokeParams taoke, Map<String, String> track}) async {
    final Map<String, String> data = {};
    data['kit'] = kit;
    data['url'] = url;
    if (show != null) data.addAll(show.toMap());
    if (taoke != null) data.addAll(taoke.toMap());
    if (track != null) data.addAll(track);
    return await _channel.invokeMethod('openURL', data);
  }

  /// 打开淘宝页面
  /// page: [AlibcDetailPage] [AlibcShopPage] [AlibcMyCartsPage] [AlibcAddCartPage] [AlibcMyOrdersPage]
  static Future<dynamic> openPage(AlibcPageBase page, AlibcShowParams show,
      {AlibcTaokeParams taoke, Map<String, String> track}) async {
    final Map<String, String> data = {};
    if (page != null) data.addAll(page.toMap());
    if (show != null) data.addAll(show.toMap());
    if (taoke != null) data.addAll(taoke.toMap());
    if (track != null) data.addAll(track);
    return await _channel.invokeMethod('openPage', data);
  }

  /// 是否使用同步淘客打点
  static Future<dynamic> setSyncForTaoke(bool issync) async {
    return await _channel.invokeMethod('setSyncForTaoke', {'sync': issync ? 'true' : 'false'});
  }

  /// 是否使用支付宝
  static Future<dynamic> setShouldUseAlipay(bool isuse) async {
    return await _channel.invokeMethod('setShouldUseAlipay', {'use': isuse ? 'true' : 'false'});
  }

  // 淘宝客参数
  static Future<dynamic> setTaokeParams(AlibcTaokeParams taoke) async {
    final Map<String, String> data = {};
    if (taoke != null) data.addAll(taoke.toMap());
    return await _channel.invokeMethod('setTaokeParams', data);
  }

  /// 渠道信息
  static Future<dynamic> setChannel(String type, String channel) async {
    return await _channel.invokeMethod('setChannel', {'type': type, 'channel': channel});
  }

  /// ISV的Code
  static Future<dynamic> setISVCode(String code) async {
    return await _channel.invokeMethod('setISVCode', {'code': code});
  }

  /// ISV的版本，通常为本APP版本
  static Future<dynamic> setISVVersion(String version) async {
    return await _channel.invokeMethod('setISVVersion', {'version': version});
  }
}
