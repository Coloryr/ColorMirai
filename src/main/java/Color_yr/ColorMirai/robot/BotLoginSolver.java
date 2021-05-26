package Color_yr.ColorMirai.robot;

import Color_yr.ColorMirai.ColorMiraiMain;
import kotlin.coroutines.Continuation;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.utils.LoginSolver;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Scanner;

public class BotLoginSolver extends LoginSolver {

    @Override
    public boolean isSliderCaptchaSupported() {
        return true;
    }

    @Nullable
    @Override
    public String onSolvePicCaptcha(@NotNull Bot bot, @NotNull byte[] bytes, @NotNull Continuation<? super String> continuation) {
        System.out.println("登录需要输入验证码，请在根目录下打开captcha.png查看验证码，然后在控制台输入验证码后回车");
        File file = new File(ColorMiraiMain.RunDir, "captcha.png");
        try {
            if (file.exists())
                file.delete();
            file.createNewFile();
            FileOutputStream outputStream = new FileOutputStream(file);
            outputStream.write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Scanner scanner = new Scanner(System.in);
        String temp = scanner.nextLine().trim().replace("\n", "");
        return temp;
    }

    @Nullable
    @Override
    public String onSolveSliderCaptcha(@NotNull Bot bot, @NotNull String s, @NotNull Continuation<? super String> continuation) {
        System.out.println("登录需要滑动验证码, 请在浏览器中打开以下链接并完成验证码, 完成后在控制台输入ticket");
        System.out.println("操作过程请看：https://github.com/Coloryr/ColorMirai/blob/main/docs/login.md");
        System.out.println("网址：" + s);
        Scanner scanner = new Scanner(System.in);
        String temp = scanner.nextLine().trim().replace("\n", "");
        return temp;
    }

    @Nullable
    @Override
    public String onSolveUnsafeDeviceLoginVerify(@NotNull Bot bot, @NotNull String s, @NotNull Continuation<? super String> continuation) {
        System.out.println("需要需要进行账户安全认证，请在浏览器中打开以下链接并完成验证，完成后输入任意继续登录");
        System.out.println("操作过程请看：https://github.com/Coloryr/ColorMirai/blob/main/docs/login.md");
        System.out.println("网址：" + s);
        Scanner scanner = new Scanner(System.in);
        String temp = scanner.nextLine().trim().replace("\n", "");
        return temp;
    }
}
