/*
 * 版权所有 (c) 2021, 2023, Oracle 及/或其关联公司。保留所有权利。
 * 请勿更改或移除版权声明或此文件头。
 *
 * 本代码是自由软件；您可以在 GNU 通用公共许可证版本 2 的条款下重新发布和/或修改，
 * 由自由软件基金会发布。Oracle 指定此特定文件适用“Classpath”例外，如 LICENSE 文件中所述。
 *
 * 本代码发布的目的是希望它会有用，但没有任何保证；甚至没有适销性或特定用途适用性的隐含保证。
 * 有关更多详细信息，请参阅随附此代码的 LICENSE 文件中的 GNU 通用公共许可证版本 2。
 *
 * 您应该已经收到与此作品一起提供的 GNU 通用公共许可证版本 2 的副本；
 * 如果没有，请写信至自由软件基金会，Inc.，地址为 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA。
 *
 * 如果您需要更多信息或有任何问题，请联系 Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA 或访问 www.oracle.com。
 */

package java.util;

import jdk.internal.util.NullableKeyValueHolder;

/**
 * 表示一个具有明确顺序的 Map，支持在两端进行操作，并且是可逆的。
 * {@code SequencedMap} 的<概念顺序>类似于 {@link SequencedCollection} 的元素顺序，
 * 但排序适用于映射而不是单个元素。
 * <p>
 * 该 Map 上的批量操作，包括 {@link #forEach forEach} 和 {@link #replaceAll replaceAll} 方法，
 * 都在此 Map 的遇见顺序（encounter order）中操作。
 * <p>
 * 通过 {@link #keySet keySet}、{@link #values values}、{@link #entrySet entrySet}、
 * {@link #sequencedKeySet sequencedKeySet}、{@link #sequencedValues sequencedValues}、
 * 和 {@link #sequencedEntrySet sequencedEntrySet} 方法提供的视图集合
 * 都反映了此 Map 的遇见顺序。虽然 {@code keySet}、{@code values} 和 {@code entrySet}
 * 方法的返回值不是排序<i>类型</i>，但这些视图集合中的元素确实反映了此 Map 的遇见顺序。
 * 因此，以下语句返回的迭代器都提供了 {@code sequencedMap} 中映射的遇见顺序。
 * {@snippet :
 *     var it1 = sequencedMap.entrySet().iterator();
 *     var it2 = sequencedMap.sequencedEntrySet().iterator();
 * }
 * <p>
 * 该接口提供方法来添加映射、检索映射，以及在 Map 的遇见顺序的两端删除映射。
 * <p>
 * 该接口还定义了 {@link #reversed} 方法，该方法提供此 Map 的逆序<视图>。
 * 在逆序视图中，首和尾的概念颠倒，后继和前驱的概念也是如此。
 * 此 Map 的第一个映射是逆序视图的最后一个映射，反之亦然。
 * 在逆序视图中，某个映射的后继是其在逆序视图中的前驱，反之亦然。
 * 所有尊重 Map 的遇见顺序的方法都仿佛该顺序是倒置的。例如，
 * 逆序视图的 {@link #forEach forEach} 方法按从此 Map 的最后一个映射到第一个映射的顺序报告映射。
 * 此外，逆序视图的所有视图集合也反映了此 Map 的遇见顺序的逆序。
 * 例如，
 * {@snippet :
 *     var itr = sequencedMap.reversed().entrySet().iterator();
 * }
 * 提供了此 Map 中映射的遇见顺序的逆序，即从最后一个映射到第一个映射。
 * {@code reversed} 方法的可用性及其对所有适用方法和视图的排序语义的影响，
 * 允许方便地按正序或逆序迭代、搜索、复制和流化此 Map 的映射。
 * <p>
 * Map 的逆序视图通常不可序列化，即使原始 Map 是可序列化的。
 * <p>
 * 通过迭代 {@link #entrySet} 视图、{@link #sequencedEntrySet} 视图及其逆序视图获取的 {@link Map.Entry} 实例，
 * 保持与基础 Map 的连接。此连接仅在迭代期间保证。未指定连接是否在迭代之外保持。
 * 如果基础 Map 允许，调用 Entry 的 {@link Map.Entry#setValue setValue} 方法将修改基础映射的值。
 * 然而，未指定基础映射值的修改是否在 {@code Entry} 实例中可见。
 * <p>
 * 方法 {@link #firstEntry}、{@link #lastEntry}、{@link #pollFirstEntry} 和 {@link #pollLastEntry}
 * 返回的 {@link Map.Entry} 实例表示调用时映射的快照。
 * 它们不支持通过可选的 {@link Map.Entry#setValue setValue} 方法修改基础 Map。
 * <p>
 * 根据实现的不同，其他方式返回的 {@code Entry} 实例可能与基础 Map 相连，也可能不相连。
 * 例如，考虑以下方式获取的 {@code Entry}：
 * {@snippet :
 *     var entry = sequencedMap.sequencedEntrySet().getFirst();
 * }
 * 本接口未指定通过这种方式获取的 {@code Entry} 的 {@code setValue} 方法是否会更新基础 Map 中的映射，
 * 或者是否会抛出异常，或者对基础 Map 的更改是否在该 {@code Entry} 中可见。
 * <p>
 * 此接口对 {@code equals} 和 {@code hashCode} 方法的要求与 {@link Map#equals Map.equals} 和
 * {@link Map#hashCode Map.hashCode} 的定义相同。因此，如果 {@code Map} 和 {@code SequencedMap}
 * 具有相等的映射（不论顺序），则它们将比较为相等。
 * <p>
 * 此类是
 * <a href="{@docRoot}/java.base/java/util/package-summary.html#CollectionsFramework">
 * Java Collections Framework</a> 的成员。
 *
 * @param <K> 此 Map 维护的键的类型
 * @param <V> 映射值的类型
 * @since 21
 */
public interface SequencedMap<K, V> extends Map<K, V> {
    /**
     * 返回此 Map 的逆序<视图>。返回视图中映射的遇见顺序是此 Map 中映射的遇见顺序的逆序。
     * 逆序影响所有对顺序敏感的操作，包括返回视图的视图集合上的操作。
     * 如果实现允许对此视图进行修改，则修改会“写入”基础 Map。
     * 对基础 Map 的更改在逆序视图中是否可见，取决于实现。
     *
     * @return 此 Map 的逆序视图
     */
    SequencedMap<K, V> reversed();

    /**
     * 返回此 Map 中的第一个键值映射，如果 Map 为空，则返回 {@code null}。
     *
     * @implSpec
     * 此接口的实现获取此 Map 的 entrySet 的迭代器。
     * 如果迭代器有一个元素，则返回该元素的不可修改副本。否则，返回 null。
     *
     * @return 第一个键值映射，或如果此 Map 为空则返回 {@code null}
     */
    default Map.Entry<K,V> firstEntry() {
        var it = entrySet().iterator();
        return it.hasNext() ? new NullableKeyValueHolder<>(it.next()) : null;
    }

    /**
     * 返回此 Map 中的最后一个键值映射，如果 Map 为空，则返回 {@code null}。
     *
     * @implSpec
     * 此接口的实现获取此 Map 的逆序视图的 entrySet 的迭代器。
     * 如果迭代器有一个元素，则返回该元素的不可修改副本。否则，返回 null。
     *
     * @return 最后一个键值映射，或如果此 Map 为空则返回 {@code null}
     */
    default Map.Entry<K,V> lastEntry() {
        var it = reversed().entrySet().iterator();
        return it.hasNext() ? new NullableKeyValueHolder<>(it.next()) : null;
    }

    /**
     * 移除并返回此 Map 中的第一个键值映射，如果 Map 为空，则返回 {@code null}（可选操作）。
     *
     * @implSpec
     * 此接口的实现获取此 Map 的 entrySet 的迭代器。
     * 如果迭代器有一个元素，它会在迭代器上调用 {@code remove}，然后
