package coloryr.colormirai.demo.sdk;

class QQFriend {
    public long qq;
    public long friend;

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof QQFriend) {
            QQFriend friend = (QQFriend) obj;
            return friend.friend == this.friend &&
                    friend.qq == this.qq;
        }
        return false;
    }
}
