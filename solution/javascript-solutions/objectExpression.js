"use strict"

function Const (n) {
    this.n = n;
}
Const.prototype.evaluate = function() {
    return this.n;
}
Const.prototype.toString = function () {
    return this.n.toString();
}
Const.prototype.prefix = function () {
    return this.n.toString();
}

let variableargs = {x: 0, y: 1, z: 2};
function Variable (name) {
    this.name = name;
}
Variable.prototype.evaluate = function() {
    return arguments[variableargs[this.name]];
}
Variable.prototype.toString = function() {
    return this.name;
}
Variable.prototype.prefix = function() {
    return this.name;
}

function Operation(f, s, ...func) {
    this.f = f;
    this.func = func;
    this.s = s;
}
Operation.prototype.evaluate = function() {
    return this.f(...this.func.map(a => a.evaluate(...arguments)));
}
Operation.prototype.toString = function() {
    return (this.func.map(a => a.toString())).join(" ") + " " + this.s;
}
Operation.prototype.prefix = function() {
    return "(" + this.s + " " + (this.func.map(a => a.prefix())).join(" ") + ")";
}

function createOperation(f, s) {
    function NewF() {
        return new Operation(f, s, ...arguments);
    }
    NewF.prototype = Object.create(Operation.prototype);
    NewF.constructor = NewF;
    return NewF;
}
let Add = createOperation((f1, f2) => f1 + f2, "+");
let Subtract = createOperation((f1, f2) => f1 - f2, "-");
let Multiply = createOperation((f1, f2) => f1 * f2, "*");
let Divide = createOperation((f1, f2) => f1 / f2, "/");
let Negate = createOperation(n => -n, "negate");
let Min3 = createOperation((...a) => Math.min(...a), "min3");
let Max5 = createOperation((...a) => Math.max(...a), "max5");
let Sinh = createOperation(Math.sinh, "sinh");
let Cosh = createOperation(Math.cosh, "cosh");

let operations = {"+": Add, "-": Subtract, "*": Multiply, "/": Divide, "min3": Min3,
    "max5": Max5, "negate": Negate, "sinh": Sinh, "cosh": Cosh};
let countargs = {"+": 2, "-": 2, "*": 2, "/": 2, "negate": 1, "min3": 3, "max5": 5, "sinh": 1, "cosh": 1};

function parse(str) {
    let stack = [];
    for (const v of str.split(' ').filter(s => s !== "")) {
        if (variableargs[v] !== undefined) {
            stack.push(new Variable(v));
        } else if (operations[v] !== undefined) {
            let a = [];
            for (let i = 0; i < countargs[v]; i++) {
                a.unshift(stack.pop());
            }
            stack.push(new operations[v](...a));
        } else {
            stack.push(new Const(parseInt(v)));
        }
    }
    return stack.pop();
}

function baseParser(str) {

}
function parsePrefix(str) {
    let index = 0;
    let ch = str[0];
    let EOF = "\0";

    skipWhitespace();
    let res =  parseExpression();
    skipWhitespace();
    if (test(EOF)) {
        return res;
    }
    throw new ParserError("ss", 1);


    function next() {
        let res = get();
       // index < str.length - 1 ? ch = str[index++] : ch = EOF;\
        index++;
        return res;
    }

    function take(expected) {
        if (test(expected)) {
            next();
            return true;
        }
        return false;
    }

    function get() {
        if (index < str.length) {
            return str[index];
        }
        return EOF;
    }

    function test(expected) {
        return expected === get();
    }

    function parseExpression() {
        skipWhitespace();
        if (take("(")) {
            let res = parseExpression();
            skipWhitespace();
            if (take(")")) {
                return res;
            }
            return new ParserError("Expected \")\"", 3);
        }
        let a = parseToken();
        skipWhitespace();
        if (operations[a] !== undefined) {
            let stack = [];
            while (!test(")") && !test(EOF)) {
                skipWhitespace();
                stack.push(parseExpression());
            }
            if (countargs[a] === stack.length) {
                return new operations[a](...stack);
            }
            throw new ParserError("Wrong count of arguments", 1);
        }
        if (test(EOF) || test(")")) {
            return parseOperand(a);
        }
        throw new ParserError("Expected", 2);
    }

    function parseToken() {
        let res = "";
        while (!test(' ') && !test(EOF) && !test(")") && !test("(")) {
            res += next().toString();
        }
        return res;
    }

    function parseOperand(s) {
        if (variableargs[s] !== undefined) {
            return new Variable(s);
        }
        if (!isNaN(s)) {
            return new Const(Number(s));
        }
        throw new ParserError("Unknown argument", 3);
    }

    function skipWhitespace() {
        while (take(" ")) {
        }
    }
}

function ParserError(message, position) {
    Error.call(this, message);
    this.message = "At position " + position + ": " + message;
}
ParserError.prototype = Object.create(Error.prototype);
ParserError.prototype.constructor = ParserError;
ParserError.prototype.name = "ParserError";





