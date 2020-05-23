import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:alibc/alibc.dart';

typedef ResultCallback = String Function();

void main() => runApp(MyApp());

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String _platformVersion = '';
  Map<String, String> result = {};

  @override
  void initState() {
    super.initState();
    initPlatformState();
  }
  
  Future<void> initPlatformState() async {
    String platformVersion;
    // Platform messages may fail, so we use a try/catch PlatformException.
    try {
      platformVersion = await Alibc.platformVersion;
    } on PlatformException {
      platformVersion = 'Failed to get platform version.';
    }

    if (!mounted) return;

    setState(() {
      _platformVersion = platformVersion;
    });
  }

  Widget _buildButton(String title, ResultCallback callback) {
    return Row(
      children: <Widget>[
        FlatButton(
          child: Text(title),
          onPressed: () {
            setState(() {
              result[title] = callback();
            });
          },
        ),
        Text(result[title] ?? '结果:')
      ],
    );
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('阿里百川SDK示例'),
        ),
        body: Center(
            child: Column(
          children: <Widget>[
            _buildButton('初始化', () => '结果: 1'),
            _buildButton('登录', () => '结果:1'),
            _buildButton('唤起淘宝', () => '结果:1'),
            _buildButton('打开商品', () => '结果:1'),
            _buildButton('打开购物车', () => '结果:1'),
          ],
        )),
      ),
    );
  }
}
