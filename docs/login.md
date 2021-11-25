# ColorMirai

[返回](../README.md)

## 新新版方法（小白必看）
1. 下载并在你的**手机**上安装`MiraiAndroid`
```
https://install.appcenter.ms/users/mzdluo123/apps/miraiandroid/distribution_groups/release
```
不能装在任何模拟器上
2. 在`MiraiAndroid`登录需要的QQ号并完成验证过程
3. 导出`device.json`
4. 替换原来的`device.json`

## 新版本方法
https://mirai.mamoe.net/topic/223/%E6%97%A0%E6%B3%95%E7%99%BB%E5%BD%95%E7%9A%84%E4%B8%B4%E6%97%B6%E5%A4%84%E7%90%86%E6%96%B9%E6%A1%88

~~注：在ColorMirai中设备的信息文件名叫info.json~~

## 旧版本方法
(这个是旧版本的使用说明)

1. 使用安卓模式登录
```
"LoginType":"ANDROID_PHONE"
```
2. 启动之后会出现一个网址
3. 打开**浏览器**
4. 按下`F12`打开**浏览器**控制台
5. **浏览器**切换到**手机模式**(不懂自行百度)
6. 复制**ColorMirai控制台**的网址打开
7. 复制下面的内容到**浏览器**控制台(console)，并回车
```js
/*
 * Copyright 2019-2021 Mamoe Technologies and contributors.
 *
 *  此源代码的使用受 GNU AFFERO GENERAL PUBLIC LICENSE version 3 许可证的约束, 可以在以下链接找到该许可证.
 *  Use of this source code is governed by the GNU AGPLv3 license that can be found through the following link.
 *
 *  https://github.com/mamoe/mirai/blob/master/LICENSE
 */

!(() => {
    let prompt = window.prompt;

    // jsbridge://CAPTCHA/onVerifyCAPTCHA?p=....#2
    /**
     * @type {string} url
     * @return {boolean}
     */
    function processUrl(url) {
        let prefix = "jsbridge://CAPTCHA/onVerifyCAPTCHA?p="
        if (url.startsWith(prefix)) {
            let json = url.substring(prefix.length);
            for (let i = json.length; i--; i > 0) {
                let j = json.substr(0, i)
                console.log(j);
                try {
                    let content = decodeURIComponent(j);
                    let obj = JSON.parse(content);
                    console.log(obj);
                    window.miraiSeleniumComplete = content;
                    prompt("MiraiSelenium - ticket", obj.ticket)
                    break;
                } catch (ignore) {
                }
            }
            return true;
        }
        return false;
    }

    (() => {
        let desc = Object.getOwnPropertyDescriptor(Image.prototype, "src");
        Object.defineProperty(Image.prototype, "src", {
            get: desc.get,
            set(v) {
                if (processUrl(v)) return;
                desc.set.call(this, v)
            }
        })
    })();


    (() => {
        let desc = Object.getOwnPropertyDescriptor(HTMLIFrameElement.prototype, "src");
        Object.defineProperty(HTMLIFrameElement.prototype, "src", {
            get: desc.get,
            set(v) {
                if (processUrl(v)) return;
                desc.set.call(this, v)
            }
        })
    })();

    (() => {
        let UserAgent = "${MIRAI_SELENIUM-USERAGENT}";
        if (UserAgent !== "${MIRAI_SELENIUM-USERAGENT}") {
            Object.defineProperty(Navigator.prototype, "userAgent", {
                get() {
                    return UserAgent
                }
            });
            document.querySelectorAll("script").forEach(it => it.remove());
        }
    })();
})()
```

7. 复制**网页**弹窗的内容复制到**ColorMirai控制台**中回车
8. 如果需要设备锁认证就打开网址完成设备锁认证

## 图解

![a](./img/a.png)  
![b](./img/b.png)  
![c](./img/c.png)  
![d](./img/d.png)

如果没有弹窗出现，检查下是不是用的**手机模式**，或者多试几次，如果还不行，去network哪里找`ticket`对应的值填到框里面。
还不会私聊发远程桌面

填ticket的是shabi  
填ticket的是shabi  
填ticket的是shabi
