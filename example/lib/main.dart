import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:alibc/alibc.dart';
import 'package:alibc/data.dart';

typedef ResultCallback = Future<dynamic> Function();

void main() => runApp(MyApp());

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  Map<String, String> result = {};

  @override
  void initState() {
    super.initState();
  }

  Widget _buildButton(String title, ResultCallback callback) {
    return Row(
      children: <Widget>[
        FlatButton(
          child: Text(title),
          onPressed: () {
            callback()
                .then((dynamic value) => setState(() {
                      result[title] = value.toString();
                    }))
                .catchError((value) => setState(() {
                      result[title] = value.toString();
                    }));
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
            _buildButton('初始化', _initTest),
            _buildButton('淘宝账号授权登录', _loginTest),
            _buildButton('检查是否登录', _isloginTest),
            _buildButton('退出淘宝账号授权', _logoutTest),
            _buildButton('打开链接', _openURLTest),
            _buildButton('打开淘宝店铺', _openShopTest),
            _buildButton('打开淘宝购物车', _openMyCartsTest),
            _buildButton('添加购物车', _openAddCartTest),
            _buildButton('打开淘宝订单', _openMyOrdersTest),
            _buildButton('淘客打点同步/异步', _setSyncForTaokeTest),
            _buildButton('是否使用支付宝', _setShouldUseAlipayTest),
            _buildButton('淘宝客配置', _setTaokeParamsTest),
            _buildButton('渠道信息', _setChannelTest),
            _buildButton('ISVCode', _setISVCodeTest),
            _buildButton('ISV版本', _setISVVersionTest),
          ],
        )),
      ),
    );
  }

  Future<dynamic> _initTest() async {
    return await Alibc.init();
  }

  Future<dynamic> _loginTest() async {
    return await Alibc.login();
  }

  Future<dynamic> _isloginTest() async {
    return await Alibc.islogin();
  }

  Future<dynamic> _logoutTest() async {
    return await Alibc.logout();
  }

  Future<dynamic> _openURLTest() async {
    return await Alibc.openURL(
        '',
        'https://uland.taobao.com/coupon/edetail?e=oSSExmvWXYYGQASttHIRqdYQwfcs8zoyxKXGKLqne1Hsx8cAhaH1SiZlT35kVCJr5R4kLBbVNWVsYgQTrXiDpq0TeAL%2BmcF17w9v818T2zNzQzL%2FHTq%2BPBemP0hpIIPvjDppvlX%2Bob8NlNJBuapvQ2MDg9t1zp0RRkY43XGTK8ko1aiZVhb9ykMuxoRQ3C%2BH5vl92ZYH25Cie%2FpBy9wBFg%3D%3D&traceId=0b15099215669559409745730e&union_lens=lensId:0b0b9f56_0c4c_16cd5da2c7f_3b31&xId=PwB9ZSWQxCtEwHxtbQc8iynshj5KEW16KP6OV6MAlpGpKCKmVGQMnjwQNhiGQpRY1gFyQHtqnYiv5wxGKTyCdf&tj1=1&tj2=1&relationId=518419440&activityId=23f4487e169647bd98b0d7fb2645947a',
        AlibcShowParams(
            backUrl: 'alisdk://',
            degradeUrl: '',
            openType: 'native',
            clientType: 'taobao',
            title: '',
            proxy: '',
            bar: '',
            failedMode: 'h5',
            originalOpenType: ''));
  }

  Future<dynamic> _openDetailTest() async {
    return await Alibc.openPage(
        AlibcDetailPage('618165954008'),
        AlibcShowParams(
            backUrl: 'alisdk://',
            degradeUrl: '',
            openType: 'native',
            clientType: 'taobao',
            title: '',
            proxy: '',
            bar: '',
            failedMode: 'h5',
            originalOpenType: ''));
  }

  Future<dynamic> _openShopTest() async {
    return await Alibc.openPage(
        AlibcShopPage('65626181'),
        AlibcShowParams(
            backUrl: 'alisdk://',
            degradeUrl: '',
            openType: 'native',
            clientType: 'taobao',
            title: '',
            proxy: '',
            bar: '',
            failedMode: 'h5',
            originalOpenType: ''));
  }

  Future<dynamic> _openMyCartsTest() async {
    return await Alibc.openPage(
        AlibcMyCartsPage(),
        AlibcShowParams(
            backUrl: 'alisdk://',
            degradeUrl: '',
            openType: 'native',
            clientType: 'taobao',
            title: '',
            proxy: '',
            bar: '',
            failedMode: 'h5',
            originalOpenType: ''));
  }

  Future<dynamic> _openAddCartTest() async {
    return await Alibc.openPage(
        AlibcAddCartPage('618165954008'),
        AlibcShowParams(
            backUrl: 'alisdk://',
            degradeUrl: '',
            openType: 'native',
            clientType: 'taobao',
            title: '',
            proxy: '',
            bar: '',
            failedMode: 'h5',
            originalOpenType: ''));
  }

  Future<dynamic> _openMyOrdersTest() async {
    return await Alibc.openPage(
        AlibcMyOrdersPage(0, true),
        AlibcShowParams(
            backUrl: 'alisdk://',
            degradeUrl: '',
            openType: 'native',
            clientType: 'taobao',
            title: '',
            proxy: '',
            bar: '',
            failedMode: 'h5',
            originalOpenType: ''));
  }

  bool isSyncForTaoke = false;
  Future<dynamic> _setSyncForTaokeTest() async {
    isSyncForTaoke = !isSyncForTaoke;
    return await Alibc.setSyncForTaoke(isSyncForTaoke);
  }

  bool isShouldUseAlipay = false;
  Future<dynamic> _setShouldUseAlipayTest() async {
    isShouldUseAlipay = !isShouldUseAlipay;
    return await Alibc.setShouldUseAlipay(isShouldUseAlipay);
  }

  Future<dynamic> _setTaokeParamsTest() async {
    return await Alibc.setTaokeParams(
        AlibcTaokeParams(pid: 'mm_112883640_11584347_72287650277', adzoneId: '72287650277'));
  }

  Future<dynamic> _setChannelTest() async {}

  Future<dynamic> _setISVCodeTest() async {}

  Future<dynamic> _setISVVersionTest() async {}
}
