package Color_yr.ColorMirai.Pack;

import java.util.List;

/*
开始数据包
Name：插件名字
Reg：注册的事件
 */
public class PackStart {
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
