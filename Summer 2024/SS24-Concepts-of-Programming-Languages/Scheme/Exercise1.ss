Solution:

(define acc-factorial
  (lambda (n A)
    (if (> n 1)
        (acc-factorial (- n 1) (* n A))
        A
    )
  )
)

output: acc-factorial 5 1

trace: ,trace (acc-factorial 5 1)
