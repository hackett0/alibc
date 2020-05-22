import 'dart:async';

import 'package:flutter/services.dart';

class Alibc {
  static const MethodChannel _channel =
      const MethodChannel('alibc');

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }
}
