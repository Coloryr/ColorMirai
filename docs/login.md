# ColorMirai

[返回](../README.md)

## 无法登录解决

1. 启动之后会出现一个网址
2. 打开浏览器
3. 按下`F12`打开控制台
4. 切换到**手机模式**(不懂自行百度)
5. 复制弹窗的网址打开
6. 复制下面的内容到控制台(console)，并回车
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

7. 复制**网页**弹窗的内容复制到控制台中回车
8. 如果需要设备锁认证就打开网址完成设备锁认证

如果没有弹窗出现，检查下是不是用的**手机模式**，或者多试几次，如果还不行，去network哪里找`ticket`对应的值填到框里面。
还不会私聊发远程桌面
