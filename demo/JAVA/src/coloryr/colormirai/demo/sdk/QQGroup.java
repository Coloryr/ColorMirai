package coloryr.colormirai.demo.sdk;

class QQGroup {
    public long qq;
    public long group;

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof QQGroup) {
            QQGroup group = (QQGroup) obj;
            return group.group == this.group &&
                    group.qq == this.qq;
        }
        return false;
    }
}
