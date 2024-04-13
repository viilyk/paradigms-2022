"use strict"

let cnst = n => () => n;
let variableargs = {x: 0, y: 1, z: 2};
let variable = name => (...args) => args[variableargs[name]];
let operation = f => (...func) => (...args) => f(...func.map(a => a(...args)));
let add = operation((f1, f2) => f1 + f2);
let subtract = operation((f1, f2) => f1 - f2);
let divide = operation((f1, f2) => f1 / f2);
let multiply = operation((f1, f2) => f1 * f2);
let negate = operation(f => -f);
let sinh = operation(Math.sinh);
let cosh = operation(Math.cosh);
let pi = cnst(Math.PI);
let e = cnst(Math.E);

// let expr = subtract(multiply(cnst(2), variable("x")), cnst(3));
