# ColorMirai

[返回](../README.md)

## 无法登录解决

1. 在启动参数哪里加上`-Dmirai.slider.captcha.supported`
例如
```
"C:\Program Files\AdoptOpenJDK\jdk-8.0.282.8-hotspot\bin\java.exe" -Dmirai.slider.captcha.supported -Xmx1024M -jar ColorMirai-3.4.4-all.jar
```
3. 启动之后会出现一个弹窗
4. 打开浏览器
5. 按下`F12`打开控制台
6. 切换到**手机模式**(不懂自行百度)
7. 复制弹窗的网址打开
8. 复制下面的内容到控制台(console)，并回车
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
5. 完成滑块认证
6. 复制**网页**弹窗的内容复制到弹窗中回车
7. 如果需要设备锁认证就用在下一个弹窗中点`设备锁`
8. 完成设备锁后关闭弹窗即可

如果没有弹窗出现，检查下是不是用的**手机模式**，或者多试几次，如果还不行，群里私聊
