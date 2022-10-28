package coloryr.colormirai.robot;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class PooledMap<Key, Value> extends AbstractMap<Key, Value> {
    private final long maxCount;

    private final Queue<Entry<Key, Value>> queue = new ConcurrentLinkedQueue<>();

    private static class Entry<Key, Value> implements Map.Entry<Key, Value> {
        private final Key key;

        private Value value;

        public Entry(Key key, Value value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public String toString() {
            return key + "=" + value;
        }

        public Key getKey() {
            return key;
        }

        public Value getValue() {
            return value;
        }

        public Value setValue(Value value) {
            return this.value = value;
        }
    }

    public PooledMap(long size) {
        maxCount = size;
    }

    @Override
    public Value put(Key key, Value value) {
        while (queue.size() >= maxCount) {
            queue.poll();
        }
        queue.add(new Entry<>(key, value));
        return value;
    }

    @Override
    public Value get(Object key) {
        for (Entry<Key, Value> type : queue) {
            if (type.key.equals(key)) {
                queue.remove(type);
                queue.add(type);
                return type.value;
            }
        }
        return null;
    }

    @Override
    public Set<Map.Entry<Key, Value>> entrySet() {
        return new HashSet<>(queue);

    }

    @Override
    public void clear() {
        queue.clear();
    }

    @Override
    public Set<Key> keySet() {
        Set<Key> set = new HashSet<>();
        for (Entry<Key, Value> e : queue) {
            set.add(e.getKey());

        }
        return set;
    }

    @Override
    public Value remove(Object obj) {
        for (Entry<Key, Value> e : queue) {
            if (e.getKey().equals(obj)) {
                queue.remove(e);
                return e.getValue();
            }
        }
        return null;
    }
}