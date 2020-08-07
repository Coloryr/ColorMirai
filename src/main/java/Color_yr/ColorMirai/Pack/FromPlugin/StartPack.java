package Color_yr.ColorMirai.Pack.FromPlugin;

import java.util.List;

/*
0 插件开始连接
Name：插件名字
Reg：注册的事件
 */
public class StartPack {
    private String Name;
    private List<Integer> Reg;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public List<Integer> getReg() {
        return Reg;
    }

    public void setReg(List<Integer> reg) {
        Reg = reg;
    }
}
