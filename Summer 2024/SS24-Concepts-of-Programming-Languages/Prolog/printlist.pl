% load into gprolog by calling consult('printlist.pl').

printlist([]) :- write(' Done!').
printlist([H|T]) :- write(H), printlist(T).
