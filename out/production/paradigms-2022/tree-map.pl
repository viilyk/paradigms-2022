% node(Key, Value, Priority, LeaftSon, RightSon).

split(null, _, null, null) :- !.
split(node(TK, TV, TP, TL, TR), K, L, node(TK, TV, TP, TL1, TR)) :- K < TK,  split(TL, K, L, TL1), !.
split(node(TK, TV, TP, TL, TR), K, node(TK, TV, TP, TL, TR1), R) :- split(TR, K, TR1, R).


merge(T1, null, T1) :- !.
merge(null, T2, T2) :- !.
merge(node(TK1, TV1, TP1, TL1, TR1), node(TK2, TV2, TP2, TL2, TR2), node(TK1, TV1, TP1, TL1, TR)) :- TP1 > TP2,
    merge(TR1, node(TK2, TV2, TP2, TL2, TR2), TR), !.
merge(node(TK1, TV1, TP1, TL1, TR1), node(TK2, TV2, TP2, TL2, TR2), node(TK2, TV2, TP2, TL, TR2)) :-
    merge(node(TK1, TV1, TP1, TL1, TR1), TL2, TL).

insert(T, K, V, P, R) :-
	split(T, K, T1, T2),
	merge(T1, node(K, V, P, null, null), R1),
	merge(R1, T2, R), !.

map_build([], null) :- !.
map_build([(K, V) | T], R) :- map_build(T, R1), map_put(R1, K, V, R).

map_get(node(TK, TV, TP, TL, TR), TK, TV) :- !.
map_get(node(TK, TV, TP, TL, TR), K, V) :- K < TK, map_get(TL, K, V), !.
map_get(node(TK, TV, TP, TL, TR), K, V) :- map_get(TR, K, V).

map_put(T, K, V, R) :- map_get(T, K, _), map_remove(T, K, R1), rand_int(2147483647, P), insert(R1, K, V, P, R), !.
map_put(T, K, V, R) :- rand_int(2147483647, P), insert(T, K, V, P, R).

map_remove(T, K, R) :- K1 is K - 1, split(T, K1, T1, T2), split(T2, K, T3, T4), merge(T1, T4, R).

map_maxKey(node(TK, _, _, _, null), TK) :- !.
map_maxKey(node(TK, TV, TP, TL, TR), R) :- map_maxKey(TR, R).

map_minKey(node(TK, _, _, null, _), TK) :- !.
map_minKey(node(TK, TV, TP, TL, TR), R) :- map_minKey(TL, R).

map_floorKey(T, K, R) :- split(T, K, T1, T2), map_maxKey(T1, R).

