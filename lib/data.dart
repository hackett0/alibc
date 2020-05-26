import 'dart:convert';

abstract class AlibcPageBase {
  AlibcPageBase(this.type);

  final String type;

  Map<String, String> toMap();
}

// 展示参数配置
class AlibcShowParams {
  AlibcShowParams(
      {this.backUrl = 'alisdk://',
      this.degradeUrl = '',
      this.openType = 'native',
      this.clientType = 'taobao',
      this.title = '',
      this.proxy = '',
      this.bar = '',
      this.failedMode = '',
      this.originalOpenType = ''});

  // 小把手
  // 唤端返回的scheme
  // 如果不传默认将不展示小把手；如果想展示小把手，可以自己传入自定义的scheme，或者传入百川提供的默认scheme：'alisdk://'
  final String backUrl;

  // 自定义降级链接
  // h5用在没有安装淘宝、天猫客户端时打开的webview的链接
  final String degradeUrl;

  // 页面打开方式
  // native: 表示唤端
  // auto: 表示不做设置
  final String openType;

  // 唤端类型
  // taobao: 唤起淘宝客户端
  // tmall: 唤起天猫客户端
  final String clientType;

  // 唤端失败模式
  // none: 不做处理
  // brower: 跳转浏览器(不推荐使用)
  // download: 跳转下载页
  // h5: 应用内webview打开
  final String failedMode;

  final String title;
  final String proxy;
  final String bar;
  final String originalOpenType;

  Map<String, String> toMap() {
    return {
      'backUrl': backUrl,
      'degradeUrl': degradeUrl,
      'openType': openType,
      'clientType': clientType,
      'title': title,
      'proxy': proxy,
      'bar': bar,
      'failedMode': failedMode,
      'originalOpenType': originalOpenType,
    };
  }
}

// 淘客参数配置
// 配置aid或pid的方式分佣
// 注：1、如果走adzoneId的方式分佣打点，需要在extraParams中显式传入taokeAppkey，否则打点失败；
//     2、如果是打开店铺页面(shop)，需要在extraParams中显式传入sellerId，否则同步打点转链失败）
class AlibcTaokeParams {
  AlibcTaokeParams({this.pid = '', this.unionId = '', this.subPid = '', this.adzoneId = '', this.extraParams});
  final String pid;
  final String unionId;
  final String subPid;
  final String adzoneId;
  final Map<String, String> extraParams;

  Map<String, String> toMap() {
    return {
      'pid': pid,
      'unionId': unionId,
      'subPid': subPid,
      'adzoneId': adzoneId,
      'extraParams': extraParams != null ? jsonEncode(extraParams) : '',
    };
  }
}

// 商品详情页
class AlibcDetailPage extends AlibcPageBase {
  AlibcDetailPage(this.itemId) : super('detail');
  final String itemId;

  @override
  Map<String, String> toMap() {
    return {'type': type, 'itemId': itemId};
  }
}

// 店铺页
class AlibcShopPage extends AlibcPageBase {
  AlibcShopPage(this.shopId) : super('shop');
  final String shopId;

  @override
  Map<String, String> toMap() {
    return {'type': type, 'shopId': shopId};
  }
}

// 购物车页
class AlibcMyCartsPage extends AlibcPageBase {
  AlibcMyCartsPage() : super('cart');

  @override
  Map<String, String> toMap() {
    return {'type': type};
  }
}

// 加入购物车
class AlibcAddCartPage extends AlibcPageBase {
  AlibcAddCartPage(this.itemId) : super('addCart');
  final String itemId;

  @override
  Map<String, String> toMap() {
    return {'type': type, 'itemId': itemId};
  }
}

// 我的订单页
// status   默认跳转页面(0:全部, 1:待付款, 2:待发货, 3:待收货, 4:待评价)
// allOrder 为 true 显示所有订单，为false只显示通过当前app下单的订单
class AlibcMyOrdersPage extends AlibcPageBase {
  AlibcMyOrdersPage(this.status, this.allOrder) : super('myOrder');

  final int status;
  final bool allOrder;

  @override
  Map<String, String> toMap() {
    return {'type': type, 'status': status.toString(), 'allOrder': allOrder ? 'true' : 'false'};
  }
}
