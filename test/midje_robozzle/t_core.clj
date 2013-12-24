(ns midje_robozzle.t-core
  (:use [midje.sweet])
  (:use [midje_robozzle.core]))

(fact "forward changes a position with respect to its heading"
      (forward (position north [5 50])) => (position north [5 49])
      (forward (position south [5 50])) => (position south [5 51])
      (forward (position east [5 50])) => (position east [6 50])
      (forward (position west [5 50])) => (position west [4 50]))

(fact "left changes the heading but not the coords of a position"
      (left (position north ...unimportant...)) 
      => (position west ...unimportant...)
      (left (position south ...unimportant...)) 
      => (position east ...unimportant...)
      (left (position east ...unimportant...)) 
      => (position north ...unimportant...)
      (left (position west ...unimportant...)) 
      => (position south ...unimportant...))

(fact "right changes the heading but not the coords of a position"
      (right (position north ...unimportant...)) 
      => (position east ...unimportant...)
      (right (position south ...unimportant...)) 
      => (position west ...unimportant...)
      (right (position east ...unimportant...)) 
      => (position south ...unimportant...)
      (right (position west ...unimportant...)) 
      => (position north ...unimportant...))

(fact "trail is a sequence of coords from journey positions"
      (trail []) => []
      (trail [ (position ...unimportant... ...first...)
              (position ...unimportant... ...second...)
              (position ...unimportant... ...third...)])
        => [...first... ...second... ...third...])
(fact "a journey is created by successively applying moves to positions"
      (journey ...start... []) => [...start...]
      (journey ...start... [...move...])
      => [ ...start... ...next... ]
      (provided
          (...move... ...start...) => ...next...))

(fact
  (let [start (position north [0 0])
        winning-moves [left forward right right forward forward]
        moves-stop-short [left forward right right forward ]
        misses-star-moves [left forward right right forward right]
        goal-stars [[-1 0] [1 0]]]
    (win? start winning-moves goal-stars 5000) => truthy
    (win? start misses-star-moves goal-stars 5000) => falsey
    (win? start moves-stop-short goal-stars 5000) => falsey

    (win? start winning-moves goal-stars 6) => truthy
    (win? start winning-moves goal-stars 5) => falsey
    ))

"number of moves"
