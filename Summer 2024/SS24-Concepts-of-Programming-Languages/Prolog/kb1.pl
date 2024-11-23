% load into gprolog by calling consult('kb1.pl'). 

tasty(pizza). % simple fact
cheap(pizza).
cheap(pasta).
eats(student,pizza) :- tasty(pizza), cheap(pizza). % complex rule
eats(student, pasta):- tasty(pasta), cheap(pasta). % "a student eats pasta if the pasta is cheap"
gone(Y) :- eats(student,Y).
