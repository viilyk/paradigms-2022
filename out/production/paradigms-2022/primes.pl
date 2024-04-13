% hard Unique LCM
init(N) :- eratosthene(2, N).
composite_make(I, STEP, N) :- I < N, assert(composite(I)), J is I + STEP, composite_make(J, STEP, N).
eratosthene(I, N) :- I < N + 1, not composite(I), J is I * I, composite_make(J, I, N), !.
eratosthene(I, N) :- I < N + 1, J is I + 1, eratosthene(J, N).

prime(I) :- not composite(I).

get_divisors(1, I, []) :- !.
get_divisors(N, I, [N]) :- prime(N), !.
get_divisors(N, I, [I | T]) :- I * I < N + 1, prime(I), 0 is N mod I, N1 is N / I, get_divisors(N1, I, T), !.
get_divisors(N, I, R) :- I * I < N + 1, J is I + 1, get_divisors(N, J, R).

prime_divisors(1, []) :- !.
prime_divisors(N, [N]) :- prime(N), !.
prime_divisors(N, R) :- number(N), get_divisors(N, 2, R), !.
prime_divisors(R, [H1, H2 | T]) :- H1 < H2 + 1, prime(H1), prime_divisors(R1, [H2 | T]), R is R1 * H1.

unique([], []) :- !.
unique([H], [H]) :- !.
unique([H1, H2 | T], [H1 | R]) :- not H1 = H2, unique([H2 | T], R).
unique([H1, H2 | T], R) :-  H1 = H2, unique([H2 | T], R).
unique_prime_divisors(N, R) :- prime_divisors(N, R1), unique(R1, R).

union([], B, B) :- !.
union(A, [], A) :- !.
union([H | T1], [H | T2], [H | T]) :- union(T1, T2, T), !.
union([H1 | T1], [H2 | T2], [H1 | T]) :- H1 < H2, union(T1, [H2 | T2], T), !.
union(A, B, R) :- union(B, A, R).

lcm(A, B, LCM) :- prime_divisors(A, R1), prime_divisors(B, R2), union(R1, R2, R), prime_divisors(LCM, R).
