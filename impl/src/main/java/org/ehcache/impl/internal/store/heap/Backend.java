/*
 * Copyright Terracotta, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ehcache.impl.internal.store.heap;

import org.ehcache.config.EvictionVeto;
import org.ehcache.core.spi.cache.Store;
import org.ehcache.function.BiFunction;
import org.ehcache.impl.internal.store.heap.holders.OnHeapKey;
import org.ehcache.impl.internal.store.heap.holders.OnHeapValueHolder;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * The idea of this backend is to let all the store code deal in terms of {@code <K>} and hide the potentially different
 * key type of the underlying {@link org.ehcache.impl.internal.concurrent.ConcurrentHashMap}.
 */
interface Backend<K, V> {
  OnHeapValueHolder<V> remove(K key);

  OnHeapValueHolder<V> computeIfPresent(K key, BiFunction<K, OnHeapValueHolder<V>, OnHeapValueHolder<V>> biFunction);

  OnHeapValueHolder<V> compute(K key, BiFunction<K, OnHeapValueHolder<V>, OnHeapValueHolder<V>> biFunction);

  void clear();

  Iterable<K> keySet();

  Iterator<Map.Entry<K,OnHeapValueHolder<V>>> entrySetIterator();

  OnHeapValueHolder<V> get(K key);

  OnHeapValueHolder<V> putIfAbsent(K key, OnHeapValueHolder<V> value);

  boolean remove(K key, OnHeapValueHolder<V> value);

  boolean replace(K key, OnHeapValueHolder<V> oldValue, OnHeapValueHolder<V> newValue);

  int size();

  Map.Entry<K, OnHeapValueHolder<V>> getEvictionCandidate(Random random, int size, final Comparator<? super Store.ValueHolder<V>> prioritizer, final EvictionVeto<Object, OnHeapValueHolder<?>> evictionVeto);
}
