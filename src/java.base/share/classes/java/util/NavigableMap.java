package java.util;
/**
 * 一个扩展了导航方法的 {@link SortedMap}，用于返回与给定搜索目标最匹配的结果。
 * 方法 {@link #lowerEntry}、{@link #floorEntry}、{@link #ceilingEntry} 和 {@link #higherEntry}
 * 返回与键分别小于、小于等于、大于等于和大于给定键的 {@code Map.Entry} 对象，如果没有这样的键，则返回 {@code null}。
 * 类似地，方法 {@link #lowerKey}、{@link #floorKey}、{@link #ceilingKey} 和 {@link #higherKey} 仅返回相关联的键。
 * 所有这些方法都是为定位而设计的，而不是用于遍历条目。
 *
 * <p>{@code NavigableMap} 可以按升序或降序键顺序访问和遍历。
 * {@link #descendingMap} 方法返回一个视图，该视图中的所有关系和方向方法的感觉都是颠倒的。
 * 升序操作和视图的性能可能比降序的更快。
 * 方法 {@link #subMap(Object, boolean, Object, boolean) subMap(K, boolean, K, boolean)}、
 * {@link #headMap(Object, boolean) headMap(K, boolean)} 和 {@link #tailMap(Object, boolean) tailMap(K, boolean)}
 * 与同名的 {@code SortedMap} 方法不同，它们接受附加参数来描述下界和上界是包含还是排除。
 * 任何 {@code NavigableMap} 的子图必须实现 {@code NavigableMap} 接口。
 *
 * <p>此接口还定义了方法 {@link #firstEntry}、{@link #pollFirstEntry}、{@link #lastEntry} 和 {@link #pollLastEntry}，
 * 它们返回和/或移除最小和最大的映射（如果存在），否则返回 {@code null}。
 *
 * <p>方法 {@link #ceilingEntry}、{@link #firstEntry}、{@link #floorEntry}、{@link #higherEntry}、{@link #lastEntry}、
 * {@link #lowerEntry}、{@link #pollFirstEntry} 和 {@link #pollLastEntry} 返回 {@link Map.Entry} 实例，
 * 它们表示调用时的映射快照。它们不支持通过可选的 {@link Map.Entry#setValue setValue} 方法修改基础映射。
 *
 * <p>方法 {@link #subMap(Object, Object) subMap(K, K)}、{@link #headMap(Object) headMap(K)} 和 {@link #tailMap(Object) tailMap(K)}
 * 指定返回 {@code SortedMap}，以允许现有的 {@code SortedMap} 实现兼容地进行改造以实现 {@code NavigableMap}，
 * 但鼓励此接口的扩展和实现覆盖这些方法以返回 {@code NavigableMap}。
 * 同样，{@link #keySet()} 可以覆盖以返回 {@link NavigableSet}。
 *
 * <p>此接口是
 * <a href="{@docRoot}/java.base/java/util/package-summary.html#CollectionsFramework">
 * Java Collections Framework</a> 的成员。
 *
 * @param <K> 此映射维护的键的类型
 * @param <V> 映射值的类型
 * @since 1.6
 */

public interface NavigableMap<K,V> extends SortedMap<K,V> {
	/**
	 * 返回与严格小于给定键的最大键相关联的键值映射，如果没有这样的键，则返回 {@code null}。
	 *
	 * @param key 键
	 * @return 与小于 {@code key} 的最大键相关联的条目，
	 *		   如果没有这样的键，则返回 {@code null}
	 * @throws ClassCastException 如果指定的键不能与当前映射中的键进行比较
	 * @throws NullPointerException 如果指定的键为 null 且此映射不允许 null 键
	 */
	Map.Entry<K,V> lowerEntry(K key);
	
	/**
	 * 返回严格小于给定键的最大键，如果没有这样的键，则返回 {@code null}。
	 *
	 * @param key 键
	 * @return 小于 {@code key} 的最大键，
	 *		   如果没有这样的键，则返回 {@code null}
	 * @throws ClassCastException 如果指定的键不能与当前映射中的键进行比较
	 * @throws NullPointerException 如果指定的键为 null 且此映射不允许 null 键
	 */
	K lowerKey(K key);
	
	/**
	 * 返回与小于或等于给定键的最大键相关联的键值映射，如果没有这样的键，则返回 {@code null}。
	 *
	 * @param key 键
	 * @return 与小于或等于 {@code key} 的最大键相关联的条目，
	 *		   如果没有这样的键，则返回 {@code null}
	 * @throws ClassCastException 如果指定的键不能与当前映射中的键进行比较
	 * @throws NullPointerException 如果指定的键为 null 且此映射不允许 null 键
	 */
	Map.Entry<K,V> floorEntry(K key);
	
	/**
	 * 返回小于或等于给定键的最大键，如果没有这样的键，则返回 {@code null}。
	 *
	 * @param key 键
	 * @return 小于或等于 {@code key} 的最大键，
	 *		   如果没有这样的键，则返回 {@code null}
	 * @throws ClassCastException 如果指定的键不能与当前映射中的键进行比较
	 * @throws NullPointerException 如果指定的键为 null 且此映射不允许 null 键
	 */
	K floorKey(K key);
	
	/**
	 * 返回与大于或等于给定键的最小键相关联的键值映射，如果没有这样的键，则返回 {@code null}。
	 *
	 * @param key 键
	 * @return 与大于或等于 {@code key} 的最小键相关联的条目，
	 *		   如果没有这样的键，则返回 {@code null}
	 * @throws ClassCastException 如果指定的键不能与当前映射中的键进行比较
	 * @throws NullPointerException 如果指定的键为 null 且此映射不允许 null 键
	 */
	Map.Entry<K,V> ceilingEntry(K key);


    /**
     * Returns the least key greater than or equal to the given key,
     * or {@code null} if there is no such key.
     *
     * @param key the key
     * @return the least key greater than or equal to {@code key},
     *         or {@code null} if there is no such key
     * @throws ClassCastException if the specified key cannot be compared
     *         with the keys currently in the map
     * @throws NullPointerException if the specified key is null
     *         and this map does not permit null keys
     */
    K ceilingKey(K key);

    /**
     * Returns a key-value mapping associated with the least key
     * strictly greater than the given key, or {@code null} if there
     * is no such key.
     *
     * @param key the key
     * @return an entry with the least key greater than {@code key},
     *         or {@code null} if there is no such key
     * @throws ClassCastException if the specified key cannot be compared
     *         with the keys currently in the map
     * @throws NullPointerException if the specified key is null
     *         and this map does not permit null keys
     */
    Map.Entry<K,V> higherEntry(K key);

    /**
     * Returns the least key strictly greater than the given key, or
     * {@code null} if there is no such key.
     *
     * @param key the key
     * @return the least key greater than {@code key},
     *         or {@code null} if there is no such key
     * @throws ClassCastException if the specified key cannot be compared
     *         with the keys currently in the map
     * @throws NullPointerException if the specified key is null
     *         and this map does not permit null keys
     */
    K higherKey(K key);

    /**
     * Returns a key-value mapping associated with the least
     * key in this map, or {@code null} if the map is empty.
     *
     * @return an entry with the least key,
     *         or {@code null} if this map is empty
     */
    Map.Entry<K,V> firstEntry();

    /**
     * Returns a key-value mapping associated with the greatest
     * key in this map, or {@code null} if the map is empty.
     *
     * @return an entry with the greatest key,
     *         or {@code null} if this map is empty
     */
    Map.Entry<K,V> lastEntry();

    /**
     * Removes and returns a key-value mapping associated with
     * the least key in this map, or {@code null} if the map is empty.
     *
     * @return the removed first entry of this map,
     *         or {@code null} if this map is empty
     */
    Map.Entry<K,V> pollFirstEntry();

    /**
     * Removes and returns a key-value mapping associated with
     * the greatest key in this map, or {@code null} if the map is empty.
     *
     * @return the removed last entry of this map,
     *         or {@code null} if this map is empty
     */
    Map.Entry<K,V> pollLastEntry();

    /**
     * Returns a reverse order view of the mappings contained in this map.
     * The descending map is backed by this map, so changes to the map are
     * reflected in the descending map, and vice-versa.  If either map is
     * modified while an iteration over a collection view of either map
     * is in progress (except through the iterator's own {@code remove}
     * operation), the results of the iteration are undefined.
     *
     * <p>The returned map has an ordering equivalent to
     * {@link Collections#reverseOrder(Comparator) Collections.reverseOrder}{@code (comparator())}.
     * The expression {@code m.descendingMap().descendingMap()} returns a
     * view of {@code m} essentially equivalent to {@code m}.
     *
     * @return a reverse order view of this map
     */
    NavigableMap<K,V> descendingMap();

    /**
     * Returns a {@link NavigableSet} view of the keys contained in this map.
     * The set's iterator returns the keys in ascending order.
     * The set is backed by the map, so changes to the map are reflected in
     * the set, and vice-versa.  If the map is modified while an iteration
     * over the set is in progress (except through the iterator's own {@code
     * remove} operation), the results of the iteration are undefined.  The
     * set supports element removal, which removes the corresponding mapping
     * from the map, via the {@code Iterator.remove}, {@code Set.remove},
     * {@code removeAll}, {@code retainAll}, and {@code clear} operations.
     * It does not support the {@code add} or {@code addAll} operations.
     *
     * @return a navigable set view of the keys in this map
     */
    NavigableSet<K> navigableKeySet();

    /**
     * Returns a reverse order {@link NavigableSet} view of the keys contained in this map.
     * The set's iterator returns the keys in descending order.
     * The set is backed by the map, so changes to the map are reflected in
     * the set, and vice-versa.  If the map is modified while an iteration
     * over the set is in progress (except through the iterator's own {@code
     * remove} operation), the results of the iteration are undefined.  The
     * set supports element removal, which removes the corresponding mapping
     * from the map, via the {@code Iterator.remove}, {@code Set.remove},
     * {@code removeAll}, {@code retainAll}, and {@code clear} operations.
     * It does not support the {@code add} or {@code addAll} operations.
     *
     * @return a reverse order navigable set view of the keys in this map
     */
    NavigableSet<K> descendingKeySet();

    /**
     * Returns a view of the portion of this map whose keys range from
     * {@code fromKey} to {@code toKey}.  If {@code fromKey} and
     * {@code toKey} are equal, the returned map is empty unless
     * {@code fromInclusive} and {@code toInclusive} are both true.  The
     * returned map is backed by this map, so changes in the returned map are
     * reflected in this map, and vice-versa.  The returned map supports all
     * optional map operations that this map supports.
     *
     * <p>The returned map will throw an {@code IllegalArgumentException}
     * on an attempt to insert a key outside of its range, or to construct a
     * submap either of whose endpoints lie outside its range.
     *
     * @param fromKey low endpoint of the keys in the returned map
     * @param fromInclusive {@code true} if the low endpoint
     *        is to be included in the returned view
     * @param toKey high endpoint of the keys in the returned map
     * @param toInclusive {@code true} if the high endpoint
     *        is to be included in the returned view
     * @return a view of the portion of this map whose keys range from
     *         {@code fromKey} to {@code toKey}
     * @throws ClassCastException if {@code fromKey} and {@code toKey}
     *         cannot be compared to one another using this map's comparator
     *         (or, if the map has no comparator, using natural ordering).
     *         Implementations may, but are not required to, throw this
     *         exception if {@code fromKey} or {@code toKey}
     *         cannot be compared to keys currently in the map.
     * @throws NullPointerException if {@code fromKey} or {@code toKey}
     *         is null and this map does not permit null keys
     * @throws IllegalArgumentException if {@code fromKey} is greater than
     *         {@code toKey}; or if this map itself has a restricted
     *         range, and {@code fromKey} or {@code toKey} lies
     *         outside the bounds of the range
     */
    NavigableMap<K,V> subMap(K fromKey, boolean fromInclusive,
                             K toKey,   boolean toInclusive);

    /**
     * Returns a view of the portion of this map whose keys are less than (or
     * equal to, if {@code inclusive} is true) {@code toKey}.  The returned
     * map is backed by this map, so changes in the returned map are reflected
     * in this map, and vice-versa.  The returned map supports all optional
     * map operations that this map supports.
     *
     * <p>The returned map will throw an {@code IllegalArgumentException}
     * on an attempt to insert a key outside its range.
     *
     * @param toKey high endpoint of the keys in the returned map
     * @param inclusive {@code true} if the high endpoint
     *        is to be included in the returned view
     * @return a view of the portion of this map whose keys are less than
     *         (or equal to, if {@code inclusive} is true) {@code toKey}
     * @throws ClassCastException if {@code toKey} is not compatible
     *         with this map's comparator (or, if the map has no comparator,
     *         if {@code toKey} does not implement {@link Comparable}).
     *         Implementations may, but are not required to, throw this
     *         exception if {@code toKey} cannot be compared to keys
     *         currently in the map.
     * @throws NullPointerException if {@code toKey} is null
     *         and this map does not permit null keys
     * @throws IllegalArgumentException if this map itself has a
     *         restricted range, and {@code toKey} lies outside the
     *         bounds of the range
     */
    NavigableMap<K,V> headMap(K toKey, boolean inclusive);

    /**
     * Returns a view of the portion of this map whose keys are greater than (or
     * equal to, if {@code inclusive} is true) {@code fromKey}.  The returned
     * map is backed by this map, so changes in the returned map are reflected
     * in this map, and vice-versa.  The returned map supports all optional
     * map operations that this map supports.
     *
     * <p>The returned map will throw an {@code IllegalArgumentException}
     * on an attempt to insert a key outside its range.
     *
     * @param fromKey low endpoint of the keys in the returned map
     * @param inclusive {@code true} if the low endpoint
     *        is to be included in the returned view
     * @return a view of the portion of this map whose keys are greater than
     *         (or equal to, if {@code inclusive} is true) {@code fromKey}
     * @throws ClassCastException if {@code fromKey} is not compatible
     *         with this map's comparator (or, if the map has no comparator,
     *         if {@code fromKey} does not implement {@link Comparable}).
     *         Implementations may, but are not required to, throw this
     *         exception if {@code fromKey} cannot be compared to keys
     *         currently in the map.
     * @throws NullPointerException if {@code fromKey} is null
     *         and this map does not permit null keys
     * @throws IllegalArgumentException if this map itself has a
     *         restricted range, and {@code fromKey} lies outside the
     *         bounds of the range
     */
    NavigableMap<K,V> tailMap(K fromKey, boolean inclusive);

    /**
     * {@inheritDoc}
     *
     * <p>Equivalent to {@code subMap(fromKey, true, toKey, false)}.
     *
     * @throws ClassCastException       {@inheritDoc}
     * @throws NullPointerException     {@inheritDoc}
     * @throws IllegalArgumentException {@inheritDoc}
     */
    SortedMap<K,V> subMap(K fromKey, K toKey);

    /**
     * {@inheritDoc}
     *
     * <p>Equivalent to {@code headMap(toKey, false)}.
     *
     * @throws ClassCastException       {@inheritDoc}
     * @throws NullPointerException     {@inheritDoc}
     * @throws IllegalArgumentException {@inheritDoc}
     */
    SortedMap<K,V> headMap(K toKey);

    /**
     * {@inheritDoc}
     *
     * <p>Equivalent to {@code tailMap(fromKey, true)}.
     *
     * @throws ClassCastException       {@inheritDoc}
     * @throws NullPointerException     {@inheritDoc}
     * @throws IllegalArgumentException {@inheritDoc}
     */
    SortedMap<K,V> tailMap(K fromKey);

    /**
     * {@inheritDoc}
     * <p>
     * This method is equivalent to {@link #descendingMap descendingMap}.
     *
     * @implSpec
     * The implementation in this interface returns the result of calling the
     * {@code descendingMap} method.
     *
     * @return a reverse-ordered view of this map, as a {@code NavigableMap}
     * @since 21
     */
    default NavigableMap<K, V> reversed() {
        return this.descendingMap();
    }
}
