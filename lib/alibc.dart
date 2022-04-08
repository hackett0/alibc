import 'dart:async';
import 'dart:convert';

import 'package:alibc/data.dart';
import 'package:flutter/services.dart';

class Alibc {
  static const MethodChannel _channel = const MethodChannel('alibc');

  static Future<AlibcResult> invoke(String method, [dynamic arguments]) async {
    return AlibcResult.fromMap(await _channel.invokeMethod(method, arguments));
  }

  /// 淘宝账号授权登录
  static Future<AlibcResult> login() async {
    return await invoke('login');
  }

  /// 检查是否登录
  static Future<AlibcResult> islogin() async {
    return await invoke('islogin');
  }

  /// 退出淘宝账号授权
  static Future<AlibcResult> logout() async {
    return await invoke('logout');
  }

  /// 打开链接
  static Future<AlibcResult> openURL(String url, AlibcShowParams show,
      {String kit = '',
      AlibcTaokeParams taoke = const AlibcTaokeParams(),
      Map<String, String> track = const {}}) async {
    final Map<String, dynamic> data = {};
    data['kit'] = kit;
    data['url'] = url;
    data['ShowParams'] = show.toMap();
    data['TaokeParams'] = taoke.toMap();
    data['TrackParams'] = track;
    return await invoke('openURL', data);
  }

  /// 打开淘宝页面
  /// page: [AlibcDetailPage] [AlibcShopPage] [AlibcMyCartsPage] [AlibcAddCartPage] [AlibcMyOrdersPage]
  static Future<AlibcResult> openPage(AlibcPageBase page, AlibcShowParams show,
      {AlibcTaokeParams taoke = const AlibcTaokeParams(),
      Map<String, String> track = const {}}) async {
    final Map<String, dynamic> data = {};
    data['PageParams'] = page.toMap();
    data['ShowParams'] = show.toMap();
    data['TaokeParams'] = taoke.toMap();
    data['TrackParams'] = track;
    return await invoke('openPage', data);
  }

  /// 是否使用同步淘客打点
  static Future<AlibcResult> setSyncForTaoke(bool issync) async {
    return await invoke('setSyncForTaoke', {'sync': issync ? 'true' : 'false'});
  }

  // 淘宝客参数
  static Future<AlibcResult> setTaokeParams(AlibcTaokeParams taoke) async {
    final Map<String, dynamic> data = {};
    data['TaokeParams'] = taoke.toMap();
    return await invoke('setTaokeParams', data);
  }

  /// 渠道信息
  static Future<AlibcResult> setChannel(String type, String channel) async {
    return await invoke('setChannel', {'type': type, 'channel': channel});
  }

  /// ISV的Code
  static Future<AlibcResult> setISVCode(String code) async {
    return await invoke('setISVCode', {'code': code});
  }

  /// ISV的版本，通常为本APP版本
  static Future<AlibcResult> setISVVersion(String version) async {
    return await invoke('setISVVersion', {'version': version});
  }
}
