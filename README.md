# 阿里百川SDK

## 接入说明

## 1. 注册
[阿里百川](https://baichuan.taobao.com/),
注册阿里百川账号，创建应用，申请权限

## 2. 生成安全图片
登录阿里百川开发者控制台, 获取V5版安全图片，放入res/drawable/yw_1222.jpg

## 3. 修改文件
修改android目录下AndroidManifest.xml文件，增加内容:
xmlns:tools="http://schemas.android.com/tools"
tools:replace="android:label"

```
    <manifest
        xmlns:tools="http://schemas.android.com/tools">
        <application
            tools:replace="android:label">
    ...
```

