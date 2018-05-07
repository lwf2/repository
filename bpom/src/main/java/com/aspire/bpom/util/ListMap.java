package com.aspire.bpom.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 有顺序的Map，遍历时按照先进先出顺序取键值对
 * 
 * @author chenpeng
 * 
 * @param <K>
 * @param <V>
 */
public class ListMap<K, V> implements Map<K, V> {
	public static final String DEFUALT_DELIMITER = "&";

	private List<_Entry<K, V>> entryList = new ArrayList<_Entry<K, V>>();

	private boolean equals(Object _key, Object key) {
		return _key == key || _key != null && _key.equals(key);
	}

	@Override
	public int size() {
		return entryList.size();
	}

	@Override
	public boolean isEmpty() {
		return this.size() == 0;
	}

	@Override
	public boolean containsKey(Object key) {
		for (Entry<K, V> entry : entryList) {
			K _key = entry.getKey();
			if (equals(_key, key)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean containsValue(Object value) {
		for (Entry<K, V> entry : entryList) {
			V _value = entry.getValue();
			if (equals(_value, value)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 获取第一个键为key的值
	 */
	@Override
	public V get(Object key) {
		return get(key, 0);
	}

	/**
	 * 获取第i个键为key的值
	 */
	public V get(Object key, int i) {
		int _i = 0;
		for (Entry<K, V> entry : entryList) {
			K _key = entry.getKey();
			if (equals(_key, key)) {
				if (_i == i) {
					return entry.getValue();
				}
			}
		}
		return null;
	}

	/**
	 * 往最后端添加键值对
	 * 
	 * @param key
	 *            不能为空
	 */
	@Override
	public V put(K key, V value) {
		_Entry<K, V> entry = new _Entry<K, V>(key, value);
		entryList.add(entry);
		return value;
	}

	/**
	 * 将键值对插入到i的位置，原来i位置及其后的元素往后移动
	 * 
	 * @param key
	 *            不能为空
	 */
	public V put(int i, K key, V value) {
		_Entry<K, V> entry = new _Entry<K, V>(key, value);
		entryList.add(i, entry);
		return value;
	}

	/**
	 * 删除第一个键为key的值
	 */
	@Override
	public V remove(Object key) {
		return remove(key, 0);
	}

	/**
	 * 删除第i个键为key的值
	 */
	public V remove(Object key, int i) {
		int _i = 0;
		for (Entry<K, V> entry : entryList) {
			K _key = entry.getKey();
			if (equals(_key, key)) {
				if (_i == i) {
					entryList.remove(entry);
					return entry.getValue();
				}
			}
		}
		return null;
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		for (Entry<? extends K, ? extends V> e : m.entrySet()) {
			_Entry<K, V> entry = new _Entry<K, V>(e.getKey(), e.getValue());
			entryList.add(entry);
		}
	}

	@Override
	public void clear() {
		this.entryList = new ArrayList<_Entry<K, V>>();
	}

	@Override
	public Set<K> keySet() {
		Set<K> keySet = new HashSet<K>();
		for (Entry<K, V> entry : entryList) {
			keySet.add(entry.getKey());
		}
		return keySet;
	}

	@Override
	public Collection<V> values() {
		List<V> values = new ArrayList<V>();
		for (Entry<K, V> entry : entryList) {
			values.add(entry.getValue());
		}
		return values;
	}

	@Override
	public Set<Entry<K, V>> entrySet() {
		return new HashSet<Entry<K, V>>(entryList);
	}

	/**
	 * 将键值对连接
	 * 
	 * @param connector
	 *            连接字符串
	 * @param nullable
	 *            值是否可以为空
	 * @return
	 */
	public String connect(String connector, boolean nullable) {
		String ret = "";
		boolean flag = true;
		for (Entry<K, V> entry : entryList) {
			if (!nullable && isNull(entry.getValue())) {
				continue;
			}
			if (flag) {
				ret += entry.toString();
				flag = false;
			} else {
				ret += connector + entry.toString();
			}
		}
		return ret;
	}

	@Override
	public String toString() {
		return connect(DEFUALT_DELIMITER, false);
	}

	private static boolean isNull(Object o) {
		return o == null || "null".equals(o.toString());
	}

	private static class _Entry<K, V> implements Entry<K, V> {
		K key;
		V value;

		public _Entry(K key, V value) {
			this.key = key;
			this.value = value;
		}

		@Override
		public K getKey() {
			return key;
		}

		@Override
		public V getValue() {
			return value;
		}

		@Override
		public V setValue(V value) {
			this.value = value;
			return this.value;
		}

		@SuppressWarnings("rawtypes")
		public final boolean equals(Object o) {
			if (!(o instanceof Map.Entry))
				return false;
			Map.Entry e = (Map.Entry) o;
			Object k1 = getKey();
			Object k2 = e.getKey();
			if (k1 == k2 || (k1 != null && k1.equals(k2))) {
				Object v1 = getValue();
				Object v2 = e.getValue();
				if (v1 == v2 || (v1 != null && v1.equals(v2)))
					return true;
			}
			return false;
		}

		public final int hashCode() {
			return (key == null ? 0 : key.hashCode()) ^ (value == null ? 0 : value.hashCode());
		}

		public final String toString() {
			return getKey() + "=" + getValue();
		}
	}

	public static ListMap<String,String> valueOf(String plainText) {
		return valueOf(plainText, DEFUALT_DELIMITER);
	}

	public static ListMap<String,String> valueOf(String text, String delimiter) {
		if(text==null){
			return null;
		}
		ListMap<String,String> map = new ListMap<String,String>();
		String[] keyValues = text.split(delimiter);
		for (String keyValue : keyValues) {
			int index = keyValue.indexOf("=");
			if(index==-1){//没有等号
				map.put(keyValue, null);
			}else if(index==0){//等号在最前
				//do nothing
			}else{
				String key = keyValue.substring(0,index);
				String value = keyValue.substring(index+1);
				map.put(key, value);
			}
		}
		return map;
	}

	public List<? extends Entry<K, V>> entryList() {
		return entryList;
	}
}