# ColorMirai

[返回](../README.md)


## 方法

1. 使用安卓模式登录
```
"LoginType":"ANDROID_PHONE"
```
2. 启动之后打开最新的botlog，拉到最下面，有个`Captcha link`，复制网址
3. 打开**浏览器**
4. 按下`F12`打开**浏览器**控制台
5. **浏览器**切换到**手机模式**(不懂自行百度)
6. 粘贴网址打开
7. 如果你不会从F12获取`ticket`复制下面的内容到**浏览器**控制台(console)，并回车
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

8. 把ticker打在控制台里面回车
9. 之后可能还需要发送手机短信，手机验证码也是直接打在控制台里面回车

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
