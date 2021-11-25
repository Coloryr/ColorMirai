package coloryr.colormirai.demo.sdk;

class QQMember {
    public long qq;
    public long group;
    public long member;

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof QQMember) {
            QQMember member = (QQMember) obj;
            return member.group == this.group &&
                    member.member == this.member &&
                    member.qq == this.qq;
        }
        return false;
    }
}
