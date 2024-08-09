/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

/*
 * This file is available under and governed by the GNU General Public
 * License version 2 only, as published by the Free Software Foundation.
 * However, the following notice accompanied the original version of this
 * file:
 *
 * Written by Doug Lea with assistance from members of JCP JSR-166
 * Expert Group and released to the public domain, as explained at
 * http://creativecommons.org/publicdomain/zero/1.0/
 */

package java.util.concurrent.locks;

import java.util.concurrent.TimeUnit;

/**
 * {@code Lock} implementations provide more extensive locking
 * operations than can be obtained using {@code synchronized} methods
 * and statements.  They allow more flexible structuring, may have
 * quite different properties, and may support multiple associated
 * {@link Condition} objects.
 *
 * <p>A lock is a tool for controlling access to a shared resource by
 * multiple threads. Commonly, a lock provides exclusive access to a
 * shared resource: only one thread at a time can acquire the lock and
 * all access to the shared resource requires that the lock be
 * acquired first. However, some locks may allow concurrent access to
 * a shared resource, such as the read lock of a {@link ReadWriteLock}.
 *
 * <p>The use of {@code synchronized} methods or statements provides
 * access to the implicit monitor lock associated with every object, but
 * forces all lock acquisition and release to occur in a block-structured way:
 * when multiple locks are acquired they must be released in the opposite
 * order, and all locks must be released in the same lexical scope in which
 * they were acquired.
 *
 * <p>While the scoping mechanism for {@code synchronized} methods
 * and statements makes it much easier to program with monitor locks,
 * and helps avoid many common programming errors involving locks,
 * there are occasions where you need to work with locks in a more
 * flexible way. For example, some algorithms for traversing
 * concurrently accessed data structures require the use of
 * &quot;hand-over-hand&quot; or &quot;chain locking&quot;: you
 * acquire the lock of node A, then node B, then release A and acquire
 * C, then release B and acquire D and so on.  Implementations of the
 * {@code Lock} interface enable the use of such techniques by
 * allowing a lock to be acquired and released in different scopes,
 * and allowing multiple locks to be acquired and released in any
 * order.
 *
 * <p>With this increased flexibility comes additional
 * responsibility. The absence of block-structured locking removes the
 * automatic release of locks that occurs with {@code synchronized}
 * methods and statements. In most cases, the following idiom
 * should be used:
 *
 * <pre> {@code
 * Lock l = ...;
 * l.lock();
 * try {
 *   // access the resource protected by this lock
 * } finally {
 *   l.unlock();
 * }}</pre>
 *
 * When locking and unlocking occur in different scopes, care must be
 * taken to ensure that all code that is executed while the lock is
 * held is protected by try-finally or try-catch to ensure that the
 * lock is released when necessary.
 *
 * <p>{@code Lock} implementations provide additional functionality
 * over the use of {@code synchronized} methods and statements by
 * providing a non-blocking attempt to acquire a lock ({@link
 * #tryLock()}), an attempt to acquire the lock that can be
 * interrupted ({@link #lockInterruptibly}, and an attempt to acquire
 * the lock that can timeout ({@link #tryLock(long, TimeUnit)}).
 *
 * <p>A {@code Lock} class can also provide behavior and semantics
 * that is quite different from that of the implicit monitor lock,
 * such as guaranteed ordering, non-reentrant usage, or deadlock
 * detection. If an implementation provides such specialized semantics
 * then the implementation must document those semantics.
 *
 * <p>Note that {@code Lock} instances are just normal objects and can
 * themselves be used as the target in a {@code synchronized} statement.
 * Acquiring the
 * monitor lock of a {@code Lock} instance has no specified relationship
 * with invoking any of the {@link #lock} methods of that instance.
 * It is recommended that to avoid confusion you never use {@code Lock}
 * instances in this way, except within their own implementation.
 *
 * <p>Except where noted, passing a {@code null} value for any
 * parameter will result in a {@link NullPointerException} being
 * thrown.
 *
 * <h2>Memory Synchronization</h2>
 *
 * <p>All {@code Lock} implementations <em>must</em> enforce the same
 * memory synchronization semantics as provided by the built-in monitor
 * lock, as described in
 * Chapter 17 of
 * <cite>The Java Language Specification</cite>:
 * <ul>
 * <li>A successful {@code lock} operation has the same memory
 * synchronization effects as a successful <em>Lock</em> action.
 * <li>A successful {@code unlock} operation has the same
 * memory synchronization effects as a successful <em>Unlock</em> action.
 * </ul>
 *
 * Unsuccessful locking and unlocking operations, and reentrant
 * locking/unlocking operations, do not require any memory
 * synchronization effects.
 *
 * <h2>Implementation Considerations</h2>
 *
 * <p>The three forms of lock acquisition (interruptible,
 * non-interruptible, and timed) may differ in their performance
 * characteristics, ordering guarantees, or other implementation
 * qualities.  Further, the ability to interrupt the <em>ongoing</em>
 * acquisition of a lock may not be available in a given {@code Lock}
 * class.  Consequently, an implementation is not required to define
 * exactly the same guarantees or semantics for all three forms of
 * lock acquisition, nor is it required to support interruption of an
 * ongoing lock acquisition.  An implementation is required to clearly
 * document the semantics and guarantees provided by each of the
 * locking methods. It must also obey the interruption semantics as
 * defined in this interface, to the extent that interruption of lock
 * acquisition is supported: which is either totally, or only on
 * method entry.
 *
 * <p>As interruption generally implies cancellation, and checks for
 * interruption are often infrequent, an implementation can favor responding
 * to an interrupt over normal method return. This is true even if it can be
 * shown that the interrupt occurred after another action may have unblocked
 * the thread. An implementation should document this behavior.
 *
 * @see ReentrantLock
 * @see Condition
 * @see ReadWriteLock
 * @jls 17.4 Memory Model
 *
 * @since 1.5
 * @author Doug Lea
 */
/**
 * {@code Lock} 实现提供了比使用 {@code synchronized} 方法和语句更广泛的锁定操作。
 * 它们允许更灵活的结构化方式，可能具有完全不同的属性，并且可能支持多个关联的
 * {@link Condition} 对象。
 *
 * <p>锁是一种用于控制多个线程访问共享资源的工具。通常，一个锁提供对共享资源的独占访问：
 * 每次只有一个线程可以获取锁，并且所有对共享资源的访问都需要先获取锁。然而，有些锁可能允许多个线程并发访问共享资源，
 * 例如 {@link ReadWriteLock} 的读锁。
 *
 * <p>使用 {@code synchronized} 方法或语句提供了访问每个对象隐含的监视器锁的方式，但是强制所有的锁获取和释放都以块结构化的方式进行：
 * 当获取多个锁时，它们必须以相反的顺序释放，并且所有锁必须在相同的词法作用域内被释放。
 *
 * <p>尽管 {@code synchronized} 方法和语句的作用域机制使得使用监视器锁编程变得更加容易，并有助于避免许多常见的涉及锁的编程错误，
 * 但在某些情况下，你需要以更灵活的方式处理锁。例如，一些遍历并发访问的数据结构的算法要求使用“手递手”或“链式锁定”：
 * 你获取节点 A 的锁，然后是节点 B 的锁，接着释放节点 A 的锁并获取节点 C 的锁，然后释放节点 B 的锁并获取节点 D 的锁，以此类推。
 * 通过实现 {@code Lock} 接口，这些技术得以使用，它允许在一个锁的不同作用域中获取和释放锁，并允许以任意顺序获取和释放多个锁。
 *
 * <p>随着这种灵活性的增加，也带来了额外的责任。没有块结构化的锁定机制会移除在 {@code synchronized} 方法和语句中自动释放锁的功能。
 * 在大多数情况下，应该使用以下模式：
 *
 * <pre> {@code
 * Lock l = ...;
 * l.lock();
 * try {
 *   // 访问受此锁保护的资源
 * } finally {
 *   l.unlock();
 * }}</pre>
 *
 * 当锁定和解锁发生在不同的作用域时，必须注意确保所有在持有锁期间执行的代码都被 try-finally 或 try-catch 保护，以确保必要时释放锁。
 *
 * <p>{@code Lock} 实现提供了比使用 {@code synchronized} 方法和语句更多的功能，
 * 包括尝试非阻塞地获取锁 ({@link #tryLock()}), 可以中断的尝试获取锁 ({@link #lockInterruptibly}),
 * 以及可以超时的尝试获取锁 ({@link #tryLock(long, TimeUnit)}).
 *
 * <p>一个 {@code Lock} 类还可以提供与隐含监视器锁完全不同的行为和语义，
 * 如保证顺序、不可重入使用或死锁检测。如果实现提供了这样的特殊语义，则实现必须记录这些语义。
 *
 * <p>请注意，{@code Lock} 实例只是普通的对象，也可以作为 {@code synchronized} 语句的目标。
 * 获取一个 {@code Lock} 实例的监视器锁与调用该实例的任何 {@link #lock} 方法之间没有指定的关系。
 * 为了避免混淆，建议除了在它们自己的实现内部之外，不要将 {@code Lock} 实例用作这种方式的目标。
 *
 * <p>除非另有说明，对于任何参数传递 {@code null} 值都将导致抛出 {@link NullPointerException}。
 *
 * <h2>内存同步</h2>
 *
 * <p>所有 {@code Lock} 实现 <em>必须</em> 强制执行与内置监视器锁相同的内存同步语义，
 * 如《Java 语言规范》第 17 章所述：
 * <ul>
 * <li>成功的 {@code lock} 操作具有与成功 <em>Lock</em> 操作相同的内存同步效果。
 * <li>成功的 {@code unlock} 操作具有与成功 <em>Unlock</em> 操作相同的内存同步效果。
 * </ul>
 *
 * 失败的锁定和解锁操作，以及可重入的锁定/解锁操作不需要任何内存同步效果。
 *
 * <h2>实现考虑</h2>
 *
 * <p>三种形式的锁获取（可中断、不可中断和定时）可能在性能特征、排序保证或其他实现质量方面有所不同。
 * 此外，中断正在进行的锁获取的能力可能在给定的 {@code Lock} 类中不可用。因此，实现不需要为所有三种形式的锁获取定义完全相同的保证或语义，
 * 也不需要支持正在进行的锁获取的中断。实现需要清楚地记录每种锁定方法提供的语义和保证。它还必须遵守本接口中定义的中断语义，
 * 在支持锁获取的中断的情况下：这可能是完全支持，或者仅在方法入口时支持。
 *
 * <p>由于中断通常意味着取消，并且检查中断通常是不频繁的，实现可以倾向于响应中断而不是正常的返回方法。
 * 即使可以证明中断发生在另一个动作可能已经解除线程阻塞之后也是如此。实现应该记录这种行为。
 *
 * @see ReentrantLock
 * @see Condition
 * @see ReadWriteLock
 * @jls 17.4 内存模型
 *
 * @since 1.5
 * @author Doug Lea
 */
public interface Lock {

    /**
     * Acquires the lock.
     *
     * <p>If the lock is not available then the current thread becomes
     * disabled for thread scheduling purposes and lies dormant until the
     * lock has been acquired.
     *
     * <p><b>Implementation Considerations</b>
     *
     * <p>A {@code Lock} implementation may be able to detect erroneous use
     * of the lock, such as an invocation that would cause deadlock, and
     * may throw an (unchecked) exception in such circumstances.  The
     * circumstances and the exception type must be documented by that
     * {@code Lock} implementation.
     */
    void lock();

    /**
     * Acquires the lock unless the current thread is
     * {@linkplain Thread#interrupt interrupted}.
     *
     * <p>Acquires the lock if it is available and returns immediately.
     *
     * <p>If the lock is not available then the current thread becomes
     * disabled for thread scheduling purposes and lies dormant until
     * one of two things happens:
     *
     * <ul>
     * <li>The lock is acquired by the current thread; or
     * <li>Some other thread {@linkplain Thread#interrupt interrupts} the
     * current thread, and interruption of lock acquisition is supported.
     * </ul>
     *
     * <p>If the current thread:
     * <ul>
     * <li>has its interrupted status set on entry to this method; or
     * <li>is {@linkplain Thread#interrupt interrupted} while acquiring the
     * lock, and interruption of lock acquisition is supported,
     * </ul>
     * then {@link InterruptedException} is thrown and the current thread's
     * interrupted status is cleared.
     *
     * <p><b>Implementation Considerations</b>
     *
     * <p>The ability to interrupt a lock acquisition in some
     * implementations may not be possible, and if possible may be an
     * expensive operation.  The programmer should be aware that this
     * may be the case. An implementation should document when this is
     * the case.
     *
     * <p>An implementation can favor responding to an interrupt over
     * normal method return.
     *
     * <p>A {@code Lock} implementation may be able to detect
     * erroneous use of the lock, such as an invocation that would
     * cause deadlock, and may throw an (unchecked) exception in such
     * circumstances.  The circumstances and the exception type must
     * be documented by that {@code Lock} implementation.
     *
     * @throws InterruptedException if the current thread is
     *         interrupted while acquiring the lock (and interruption
     *         of lock acquisition is supported)
     */
    void lockInterruptibly() throws InterruptedException;

    /**
     * Acquires the lock only if it is free at the time of invocation.
     *
     * <p>Acquires the lock if it is available and returns immediately
     * with the value {@code true}.
     * If the lock is not available then this method will return
     * immediately with the value {@code false}.
     *
     * <p>A typical usage idiom for this method would be:
     * <pre> {@code
     * Lock lock = ...;
     * if (lock.tryLock()) {
     *   try {
     *     // manipulate protected state
     *   } finally {
     *     lock.unlock();
     *   }
     * } else {
     *   // perform alternative actions
     * }}</pre>
     *
     * This usage ensures that the lock is unlocked if it was acquired, and
     * doesn't try to unlock if the lock was not acquired.
     *
     * @return {@code true} if the lock was acquired and
     *         {@code false} otherwise
     */
    boolean tryLock();

    /**
     * 如果锁在给定的等待时间内是空闲的，并且当前线程没有被
     * {@linkplain Thread#interrupt 中断}，则获取锁。
     *
     * <p>如果锁可用，则此方法立即返回
     * 值 {@code true}。
     * 如果锁不可用，则
     * 当前线程将被禁用以进行线程调度
     * 目的，并处于休眠状态，直到发生以下三种情况之一：
     * <ul>
     * <li>当前线程获取了锁；或
     * <li>其他某个线程 {@linkplain Thread#interrupt 中断} 了当前线程，并且支持中断锁获取；或
     * <li>指定的等待时间到期
     * </ul>
     *
     * <p>如果锁被获取，则返回值 {@code true}。
     *
     * <p>如果当前线程：
     * <ul>
     * <li>在此方法入口时设置了中断状态；或
     * <li>在获取锁时被 {@linkplain Thread#interrupt 中断}，并且支持中断锁获取，
     * </ul>
     * 则抛出 {@link InterruptedException} 并清除当前线程的中断状态。
     *
     * <p>如果指定的等待时间到期，则返回值 {@code false}。
     * 如果时间
     * 小于或等于零，则方法根本不会等待。
     *
     * <p><b>实现注意事项</b>
     *
     * <p>在某些实现中，中断锁获取的能力
     * 可能是不可能的，如果可能的话可能是
     * 一个昂贵的操作。
     * 程序员应该意识到这种情况的可能性。一个
     * 实现应该在适用的情况下记录这一点。
     *
     * <p>实现可以选择优先响应中断而不是正常的
     * 方法返回，或报告超时。
     *
     * <p>{@code Lock} 实现可能能够检测
     * 锁的错误使用，例如会导致
     * 死锁的调用，并且在这种情况下可能会抛出
     * 一个（未检查的）异常。
     * 情况和异常类型必须由该
     * {@code Lock} 实现文档化。
     *
     * @param time 获取锁的最大等待时间
     * @param unit 时间参数 {@code time} 的时间单位
     * @return 如果获取了锁则返回 {@code true}，如果在获取锁之前等待时间到期则返回 {@code false}
     *
     * @throws InterruptedException 如果当前线程在获取锁时被中断
     *         （并且支持中断锁获取）
     */
    boolean tryLock(long time, TimeUnit unit) throws InterruptedException;

    /**
     * 释放锁。
     *
     * <p><b>实现注意事项</b>
     *
     * <p>{@code Lock} 实现通常会对哪个线程可以释放锁施加限制（通常只有
     * 锁的持有者才能释放锁），并且如果违反了这些限制可能会抛出
     * 一个（未检查的）异常。
     * 任何限制和异常类型必须由该 {@code Lock} 实现文档化。
     */
    void unlock();

    /**
     * 返回一个新的 {@link Condition} 实例，该实例绑定到此
     * {@code Lock} 实例。
     *
     * <p>在等待条件之前，锁必须被当前线程持有。
     * 调用 {@link Condition#await()} 将原子性地释放锁
     * 在等待之前，并在等待返回之前重新获取锁。
     *
     * <p><b>实现注意事项</b>
     *
     * <p>{@link Condition} 实例的确切操作取决于
     * {@code Lock} 实现，并且必须由该实现文档化。
     *
     * @return 为此 {@code Lock} 实例创建的一个新的 {@link Condition} 实例
     * @throws UnsupportedOperationException 如果此 {@code Lock}
     *         实现不支持条件
     */
    Condition newCondition();
}
